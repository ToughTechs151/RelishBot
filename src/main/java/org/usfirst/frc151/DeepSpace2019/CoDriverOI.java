/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019;

import org.usfirst.frc151.DeepSpace2019.commands.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * CoDriver OI Controls
 */
public class CoDriverOI extends OI {

    public CoDriverOI(int channel) {
        super(channel);
        
        start = new JoystickButton(joystick, RobotMap.START);
        start.whenPressed(new IncrementHopperCommand(0.35));

        back = new JoystickButton(joystick, RobotMap.BACK);
        back.whenPressed(new IncrementHopperCommand(-0.35));

        rightBumper = new JoystickButton(joystick, RobotMap.RIGHT_BUMPER);
        rightBumper.whileHeld(new ReleaseBallPerSecondThroughHopperCommandGroup());
    
        leftBumper = new JoystickButton(joystick, RobotMap.LEFT_BUMPER);    
        leftBumper.whenPressed(new ReleaseBallThroughCartridgeCommandGroup());

        a = new JoystickButton(joystick, RobotMap.A);
        a.whenPressed(new ChangeLauncherSpeedCommand(-2300));

        b = new JoystickButton(joystick, RobotMap.B);
        y = new JoystickButton(joystick, RobotMap.Y);
        x = new JoystickButton(joystick, RobotMap.X);
    
        leftJoystick = new JoystickButton(joystick, RobotMap.LEFT_JOYSTICK);
        rightJoystick = new JoystickButton(joystick, RobotMap.RIGHT_JOYSTICK);
    }
}