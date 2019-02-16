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
public class CoDriverOI extends OI {

    public CoDriverOI(int channel) {
    super(channel);				
    back = new JoystickButton(joystick, RobotMap.BACK);
    start = new JoystickButton(joystick, RobotMap.START);
    leftBumper = new JoystickButton(joystick, RobotMap.LEFT_BUMPER);
    rightBumper = new JoystickButton(joystick, RobotMap.RIGHT_BUMPER);

    leftBumper.whenPressed(new OpenBeak());
    rightBumper.whenPressed(new CloseBeak());
    start.whenPressed(new HatchArmDown());
    back.whenPressed(new HatchArmUp());
    }
}