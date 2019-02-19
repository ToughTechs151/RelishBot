/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc151.DeepSpace2019.OI;
import org.usfirst.frc151.DeepSpace2019.Robot;
import org.usfirst.frc151.DeepSpace2019.commands.DriveCargoClawWithTriggersCommand;

/**
 * Add your docs here.
 */
public class CargoClawSubsystem extends Subsystem {

  WPI_TalonSRX rightClaw = null;
  WPI_TalonSRX leftClaw = null;

  public CargoClawSubsystem() {
    rightClaw = new WPI_TalonSRX(2);
    leftClaw = new WPI_TalonSRX(3);

    rightClaw.set(ControlMode.PercentOutput, 0);
    leftClaw.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveCargoClawWithTriggersCommand());
  }

  public void driveWithJoysticks(OI oi) {
    double rightTrigger = Robot.coDriverOI.getJoystick().getRawAxis(3);
    double leftTrigger = Robot.coDriverOI.getJoystick().getRawAxis(2);
    
    double output = rightTrigger - leftTrigger;

    //if output is positive, push: set right as positie and left as negative.
    //if output is negative, pull: set right as negative and left as positive
    
    rightClaw.set(ControlMode.PercentOutput, output);
    leftClaw.set(ControlMode.PercentOutput, -output);
  }

  /**
   * @param speed The value from -1 to 1 given to the motors. Positive to push ball out, negative to pull ball in
   */
  public void driveClaw(double speed) {
    rightClaw.set(ControlMode.PercentOutput, speed);
    leftClaw.set(ControlMode.PercentOutput, -speed);
  }
}
