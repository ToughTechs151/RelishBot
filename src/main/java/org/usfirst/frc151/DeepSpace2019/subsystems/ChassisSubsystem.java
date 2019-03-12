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
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ChassisSubsystem extends Subsystem {

    private Talon frontRight;
    private Talon backRight;
    private Talon frontLeft;
    private Talon backLeft;

    DifferentialDrive driveTrain = null;
//    public ADXRS450_Gyro gyro = null;
    
    //drive constants
    /**
     * The scaling factor between the joystick value and the speed controller
     */
    private static double speedMultiplier = 1.0;
    /**
     * The scale factor for normal mode
     */
    private static final double normal = 1.0;

    /**
     * The scale factor for crawl mode
     */
    private static final double crawl = 0.5;

    /**
     * The minimum (closest to 0) speed controller command for the right side of the drive train to start moving forward. Must be empirically derived.
     */
    private static double mechDeadbandRightForward = 0.25;

    /**
     * The maximum (closest to 0) speed controller command for the right side of the drive train to start moving backward. Must be empirically derived.
     * Must be negative.
     */
    private static double mechDeadbandRightBackward = -0.27;

    /**
     * The minimum (closest to 0) speed controller command for the left side of the drive train to start moving forward. Must be empirically derived.
     */
    private static double mechDeadbandLeftForward = 0.23;

    /**
     * The maximum (closest to 0) speed controller command for the left side of the drive train to start moving backward. Must be empirically derived.
     * Must be negative.
     */
    private static double mechDeadbandLeftBackward = -0.29;

    /**
     * The minimum joystick value to actually send a command to the speed controller, to prevent noise near 0.
     */
    private static double softwareDeadband = 0.05;

    //arcade drive constant
	private double turnGain = 0.75;

    /**
     * The direction which is "forward"; 1 represents the hatch side and -1 represents the cargo side.
     */
    private int dir = 1;

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

    public void drive(double left, double right) {
        driveTrain.tankDrive(left, right);
    }

    public void driveArcade(double throttle, double turn) {
        driveTrain.arcadeDrive(throttle, turn, true);
    }

    /**
     * Method to drive in tank drive based on joystick input.
     * @param oi The oi to base joystick values off of (Driver or CoDriver.)
     * @param scale Whether or not to scale output values to account for mechanical deadband.
     */
    public void drive(OI oi, boolean scale) {
        speedMultiplier = oi.getJoystick().getRawButton(RobotMap.RIGHT_BUMPER) ? crawl : normal;
        dir = oi.getJoystick().getRawButton(RobotMap.LEFT_BUMPER) ? -1 : 1;

        double rightVal = 0;
        double leftVal = 0;

        if(dir == 1) {
            rightVal = getRightValue(oi.getJoystick().getRawAxis(RobotMap.RIGHT_JOYSTICK_Y), scale);
            leftVal = getLeftValue(oi.getJoystick().getRawAxis(RobotMap.LEFT_JOYSTICK_Y), scale);
        } else if(dir == -1) {
            rightVal = getRightValue(oi.getJoystick().getRawAxis(RobotMap.LEFT_JOYSTICK_Y), scale);
            leftVal = getLeftValue(oi.getJoystick().getRawAxis(RobotMap.RIGHT_JOYSTICK_Y), scale);
        }

        SmartDashboard.putNumber("Left scaled value", leftVal);
        SmartDashboard.putNumber("Right scaled value", rightVal);
        
        drive(leftVal * speedMultiplier * dir, rightVal * speedMultiplier * dir);
    }

    /**
     * Method to drive in arcade drive based on joystick input.
     * @param oi The oi to base joystick values off of (Driver or CoDriver.)
     */
    public void driveArcade(OI oi) {
        speedMultiplier = oi.getJoystick().getRawButton(RobotMap.RIGHT_BUMPER) ? crawl : normal;
        dir = oi.getJoystick().getRawButton(RobotMap.LEFT_BUMPER) ? -1 : 1;

        double throttle = deadzone(oi.getJoystick().getRawAxis(RobotMap.LEFT_JOYSTICK_Y));
        double turn = deadzone(oi.getJoystick().getRawAxis(RobotMap.RIGHT_JOYSTICK_X));
        
        driveArcade(throttle * speedMultiplier * dir, turn * speedMultiplier * turnGain * dir);
    }
    
    /**
     * The method to scale raw right joystick input to a usable drive output.
     * @param val The input from a joystick.
     * @param scale Whether or not to scale the output to account for mechanical deadband. If true, value is scaled to account for mechanical deadband. If false, value is 
     *              simply returned as long as it is not in the range <code> (-softwareDeadband < val < softwareDeadband)</code>, in which case 0 is returned.
     * @return Scaled drive output.
     */
    private double getRightValue(double val, boolean scale) {
        if(scale) {
            if(val > 0) {
                return Math.abs(val) < softwareDeadband ? 0 : (((1.0 - mechDeadbandRightForward) * (val -1.0) / (1.0 - softwareDeadband)) + 1.0);
            } else {   
                return Math.abs(val) < softwareDeadband ? 0 : (((1.0 + mechDeadbandRightBackward) * (val + 1.0) / (1.0 - softwareDeadband)) - 1.0);
            }
        } else {
            return deadzone(val);
        }
    }

    /**
     * The method to scale raw left joystik input to a usable drive output.
     * @param val The input from a joystick.
     * @param scale Whether or not to scale the output to account for mechanical deadband. If true, value is scaled to account for mechanical deadband. If false, value is 
     *              simply returned as long as it is not in the range <code> (-softwareDeadband < val < softwareDeadband)</code>, in which case 0 is returned.
     * @return Scaled drive output.
     */
    private double getLeftValue(double val, boolean scale) {
        if(scale) {
            if(val > 0) {
                return Math.abs(val) < softwareDeadband ? 0 : (((1.0 - mechDeadbandLeftForward) * (val - 1.0) / (1.0 - softwareDeadband)) + 1.0);
            } else {
                return Math.abs(val) < softwareDeadband ? 0 : (((1.0 + mechDeadbandLeftBackward) * (val + 1.0) / (1.0 - softwareDeadband)) - 1.0);
            }
        } else {
            return deadzone(val);
        }
    }

    /**
     * Method to reduce noise around the 0 position of the joystick.
     * @param val The raw joystick input.
     * @return If the input is outside the range <code> (-softwareDeadband < val < softwareDeadband)</code>, it is returned. Else, 0 is returned.
     */
    private double deadzone(double val) {
        return Math.abs(val) > softwareDeadband ? val : 0;
    }
}