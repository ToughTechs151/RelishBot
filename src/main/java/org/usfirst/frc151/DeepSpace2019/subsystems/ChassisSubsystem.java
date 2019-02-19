/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019.subsystems;

import org.usfirst.frc151.DeepSpace2019.OI;
import org.usfirst.frc151.DeepSpace2019.RobotMap;
import org.usfirst.frc151.DeepSpace2019.commands.DriveWithJoysticksCommand;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class ChassisSubsystem extends Subsystem {

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

    public void hatchDirection() {
        dir = 1;
    }

    public void cargoDirection() {
        dir = -1;
    }

    public ChassisSubsystem() {
        frontRight = new Talon(RobotMap.FRONT_RIGHT);
        backRight = new Talon(RobotMap.BACK_RIGHT);
        frontLeft = new Talon(RobotMap.FRONT_LEFT);
        backLeft = new Talon(RobotMap.BACK_LEFT);
        
        SpeedControllerGroup right = new SpeedControllerGroup(frontRight, backRight);
        SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, backLeft);

        driveTrain = new DifferentialDrive(left, right);

        left.setInverted(true);
        right.setInverted(true);

        // gyro = new ADXRS450_Gyro();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoysticksCommand());
    }

    public void drive (double left, double right) {
        driveTrain.tankDrive(left, right);
    }

    public void driveArcade(double throttle, double turn) {
        driveTrain.arcadeDrive(throttle, turn, true);
    }

    public void drive (OI oi) {
        double rightVal = deadzone(oi.getJoystick().getRawAxis(RobotMap.RIGHT_JOYSTICK_Y));
        double leftVal = deadzone(oi.getJoystick().getRawAxis(RobotMap.LEFT_JOYSTICK_Y));
        drive(leftVal * speedMultiplier * dir, rightVal * speedMultiplier * dir);
    }

    public void driveArcade(OI oi) {
        double throttle = deadzone(oi.getJoystick().getRawAxis(RobotMap.LEFT_JOYSTICK_Y));
        double turn = deadzone(oi.getJoystick().getRawAxis(RobotMap.RIGHT_JOYSTICK_X));
        drive(throttle * straightGain * dir, turn * turnGain * dir);
    }
    
    private double deadzone(double val) {
        return Math.abs(val) < 0.05 ? val : 0;
    }
}