/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019;

import java.awt.Button;

import org.usfirst.frc151.DeepSpace2019.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc151.DeepSpace2019.subsystems.*;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * Driver OI Controls
 */
public class DriverOI extends OI {

    public DriverOI(int channel) {
    super(channel);				
    b = new JoystickButton(joystick, RobotMap.B);
    x = new JoystickButton(joystick, RobotMap.X);
    b.whenPressed(new DriveCargoCamera());
    x.whenPressed(new DriveHatchCamera());
    }
}
