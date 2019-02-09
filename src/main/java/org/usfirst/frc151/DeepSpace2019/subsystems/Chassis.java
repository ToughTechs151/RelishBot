package org.usfirst.frc151.DeepSpace2019.subsystems;

import org.usfirst.frc151.DeepSpace2019.OI;
import org.usfirst.frc151.DeepSpace2019.RobotMap;
import org.usfirst.frc151.DeepSpace2019.commands.DriveWithJoysticks;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends Subsystem {

    private Talon frontRight;
    private Talon backRight;
    private Talon frontLeft;
    private Talon backLeft;

    DifferentialDrive driveTrain = null;
//    public ADXRS450_Gyro gyro = null;
	private double turnGain = 0.75;
    private double straightGain = 1.0;
    
    double speedMultiplier = 1;
    public static final double TURBO = 1.00;
    public static final double CRAWL = 0.5;
    double direction = 1;
    private int dir = 1;

    public void positiveDir() {
        dir = 1;
    }

    public void negativeDir() {
        dir = -1;
    }

    public Chassis() {
        frontRight = new Talon(RobotMap.FRONT_RIGHT);
        backRight = new Talon(RobotMap.BACK_RIGHT);
        frontLeft = new Talon(RobotMap.FRONT_LEFT);
        backLeft = new Talon(RobotMap.BACK_LEFT);
        
        SpeedControllerGroup right = new SpeedControllerGroup(frontRight, backRight);
        SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, backLeft);

        driveTrain = new DifferentialDrive(left, right);

        left.setInverted(true);
        right.setInverted(true);

//        gyro = new ADXRS450_Gyro();

        
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoysticks());
    }

    public void drive (double left, double right) {
        driveTrain.tankDrive(left, right);

    }

    public void drive (OI oi) {
        double rightVal = deadzone(oi.getJoystick().getRawAxis(RobotMap.RIGHT_JOYSTICK_VERTICAL_AXIS));
        double leftVal = deadzone(oi.getJoystick().getRawAxis(RobotMap.LEFT_JOYSTICK_VERTICAL_AXIS));
        drive(leftVal * speedMultiplier * dir, rightVal * speedMultiplier * dir);

    }
    public void driveArcade (OI oi) {
		double throttle = deadzone(oi.getJoystick().getRawAxis(RobotMap.LEFT_JOYSTICK_VERTICAL_AXIS));
		double turn = deadzone(oi.getJoystick().getRawAxis(RobotMap.RIGHT_JOYSTICK_LATERAL_AXIS));

		if(throttle != 0)
			turn = turnGain * (turn * Math.abs(throttle));
		else
			turn *= turnGain;
		
		double initLeft = throttle - turn;
		double initRight = throttle + turn;

        // double left = initLeft * straightGain;
        // double right = initRight * straightGain;
		double left = straightGain * (initLeft + skim(initRight));
		double right = straightGain * (initRight + skim(initLeft));
	
        drive(left, right);
    }
    
    private double skim(double speed) {
		//Maximum PWM range is -1<=x<=1, so make up for that
		if(speed > 1.0) {
			return -(speed - 1.0);
		} else if(speed < -1.0) {
			return -(speed + 1.0);
		}
		return 0;
	}
    public double deadzone(double val) {
        if(Math.abs(val) > 0.05) {
            return val;
        }
        else {
            return 0;
        }
    }
}

