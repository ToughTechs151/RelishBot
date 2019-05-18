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

        y = new JoystickButton(joystick, RobotMap.Y);
        y.whileHeld(new ExtendBackPistonCommand());
        x = new JoystickButton(joystick, RobotMap.X);
        x.whileHeld(new ExtendFrontPistonCommand());

        b = new JoystickButton(joystick, RobotMap.B); //once you're lined up reasonably straight
        b.whileHeld(new DriveToTargetPIDCommand(Robot.DRIVE_KP, Robot.DRIVE_KI, Robot.DRIVE_KD, Robot.DRIVE_BASE_SPEED));
        a = new JoystickButton(joystick, RobotMap.A);
        a.whileHeld(new DriveStraightCommand());
    }
}
