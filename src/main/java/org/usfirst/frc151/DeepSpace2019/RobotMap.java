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
//#region Constants
// These are configration independent constants, giving symbolic names to 
// ports on the RoboRio, Driver Station, PVM, VRM, etc. 
// These constants are here for completeness, in any given year you will
// only use a fraction of these constants. 
// These are commented out to prevent unused variable warnings. Uncomment them 
// as required. They are private because thy should not be used outside of this
// file. To use them in other classes, create a public static final variable 
// with a descriptiove name in the mapping section of this file and assign it 
// the value of one of these protected constants. The descriptive name should 
// reflect what it is used *for* and not the assignment. For example, use
// public static final BALL_THROW_CT = OPERATOR_PORT;
// public static final BALL_THROW_BT = X_BUTTON;

// Driver station constants
//#region Controller Numbers
    // Use _CT for variables assigned to these values.
	// private static final int DRIVER_PORT = 0;
    // private static final int OPERATOR_PORT = 1;
//#endregion Controller Numbers
		
//#region XboxOne Joysticks
    // Use _JS for variables assigned to these values.
	private static final int LEFT_STICK_X = 0;
	private static final int LEFT_STICK_Y = 1;
	// private static final int LEFT_TRIGGER = 2;
	// private static final int RIGHT_TRIGGER = 3;
	private static final int RIGHT_STICK_X = 4;
	private static final int RIGHT_STICK_Y = 5;
//#endregion XboxOne Joysticks
    
//#region XboxOne Buttons
    // Use _BT for variables assigned to these values.
	// private static final int A_BUTTON = 1;
	// private static final int B_BUTTON = 2;
	// private static final int X_BUTTON = 3;
	// private static final int Y_BUTTON = 4;
	// private static final int LEFT_BUMBER_BUTTON = 5;
	// private static final int RIGT_BUMPER_BUTTON = 6;
	// private static final int LOGO_LEFT_BUTTON = 7;
	// private static final int LOGO_RIGHT_BUTTON = 8;
	// private static final int LEFT_STICK_BUTTON = 9;
    // private static final int RIGHT_STICK_BUTTON = 10;
//#endregion XboxOne Buttons

// roboRIO constants
//#region PWM ports
    // Use _PWM for variables assigned to these values.
    // private static final int PWM00 = 0;
    private static final int PWM01 = 1;
    private static final int PWM02 = 2;
    private static final int PWM03 = 3;
    private static final int PWM04 = 4;
    // private static final int PWM05 = 5;
    // private static final int PWM06 = 6;
    // private static final int PWM07 = 7;
    // private static final int PWM08 = 8;
    // private static final int PWM09 = 9;
//#endregion PWM ports
//#region Digital IO ports
    // Use _DIO for variables assigned to these values.
    // private static final int DIO00 = 0;
    // private static final int DIO01 = 1;
    // private static final int DIO02 = 2;
    // private static final int DIO03 = 3;
    // private static final int DIO04 = 4;
    // private static final int DIO05 = 5;
    // private static final int DIO06 = 6;
    // private static final int DIO07 = 7;
    // private static final int DIO08 = 8;
    // private static final int DIO09 = 9;
//#endregion Digital IO ports
//#region Analog Input ports
    // Use _AI for variables assigned to these values.
    // private static final int AI00 = 0;
    // private static final int AI01 = 1;
    // private static final int AI01 = 2;
//#endregion Analog Input ports
//#region Relay ports
    //TODO These values are incorrect. Find real values.
    // Use _RLF and _RLR for variables assigned to these values.
    // private static final int RELAY_FWD_0 = 0;
    // private static final int RELAY_REV_0 = 1;
    // private static final int RELAY_FWD_1 = 0;
    // private static final int RELAY_REV_1 = 1;
    // private static final int RELAY_FWD_2 = 0;
    // private static final int RELAY_REV_2 = 1;
//#endregion Relay ports

//#endregion Constants

    public static final int FRONT_RIGHT = PWM01;
    public static final int BACK_RIGHT = PWM02;
    public static final int FRONT_LEFT = PWM03;
    public static final int BACK_LEFT = PWM04;

    public static final int RIGHT_JOYSTICK_Y = RIGHT_STICK_Y;
    public static final int LEFT_JOYSTICK_Y = LEFT_STICK_Y;
    public static final int RIGHT_JOYSTICK_X = RIGHT_STICK_X;
    
    // the following values have been chosen at random
    public static final int ARM_SOLENOID_PORT_CLOSE = 6;
    public static final int ARM_SOLENOID_PORT_OPEN = 7;
    public static final int BEAK_SOLENOID_PORT_CLOSE = 8;
    public static final int BEAK_SOLENOID_PORT_OPEN = 9;
    public static final int COMPRESSOR = 10;
    public static final int CARGO_LEFT = 4;
	public static final int CARGO_RIGHT = 5;
}
