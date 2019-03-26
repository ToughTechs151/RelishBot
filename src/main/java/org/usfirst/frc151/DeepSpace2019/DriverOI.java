/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019;

import org.usfirst.frc151.DeepSpace2019.commands.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Relay;

/**
 * Driver OI Controls
 */
public class DriverOI extends OI {

    public DriverOI(int channel) {
        super(channel);
        a = new JoystickButton(joystick, RobotMap.A);
        y = new JoystickButton(joystick, RobotMap.Y);

        a.whenPressed(new ExtendPistonCommand());
        y.whenPressed(new RetractPistonCommand());

        b = new JoystickButton(joystick, RobotMap.B);
        b.whileHeld(new DriveToTargetPIDCommand(Robot.DRIVE_KP, Robot.DRIVE_KI, Robot.DRIVE_KD));

        x = new JoystickButton(joystick, RobotMap.X);
        x.whenPressed(new SetLEDCommand(Relay.Value.kReverse));
    }
}
