/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019;

/**
 * Add your docs here.
 */
public class RobotMap {
    //RELAY PORTS
    public static final int LED_RELAY = 0;
    
    //PWM PORTS
    public static final int FRONT_RIGHT = 1;
    public static final int BACK_RIGHT = 2;
    public static final int FRONT_LEFT = 3;
    public static final int BACK_LEFT = 4;
    // public static final int FRONT_RIGHT = 3;
    // public static final int BACK_RIGHT = 4;
    // public static final int FRONT_LEFT = 1;
    // public static final int BACK_LEFT = 2;

    //CAN IDs
    public static final int CARGO_RIGHT = 2;
    public static final int CARGO_LEFT = 3;

    //PCM PORTS
    public static final int ARM_SOLENOID_PORT_STOW = 2;
    public static final int ARM_SOLENOID_PORT_DEPLOY = 3;
    public static final int BEAK_SOLENOID_PORT_CLOSE = 1;
    public static final int BEAK_SOLENOID_PORT_OPEN = 0;
    public static final int FRONT_CLIMBER_FORWARD = 4;
    public static final int FRONT_CLIMBER_REVERSE = 5;
    public static final int BACK_CLIMBER_FORWARD = 6;
    public static final int BACK_CLIMBER_REVERSE = 7;

    //OI AXES
    public static final int LEFT_JOYSTICK_X = 0;
	public static final int LEFT_JOYSTICK_Y = 1;
	public static final int RIGHT_JOYSTICK_X = 4;
	public static final int RIGHT_JOYSTICK_Y = 5;
    public static final int LEFT_TRIGGER = 2;
    public static final int RIGHT_TRIGGER = 3;

    //OI BUTTON PORTS
	public static final int A = 1;
    public static final int B = 2;
    public static final int X = 3;
	public static final int Y = 4;
	public static final int LEFT_BUMPER = 5;
	public static final int RIGHT_BUMPER = 6;
	public static final int BACK = 7;
	public static final int START = 8;
	public static final int LEFT_JOYSTICK = 9;
    public static final int RIGHT_JOYSTICK = 10;
}
