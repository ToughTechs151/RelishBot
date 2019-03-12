/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc151.DeepSpace2019.RobotMap;
import org.usfirst.frc151.DeepSpace2019.commands.RetractPistonCommand;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Add your docs here.
 */
public class ClimberSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private DoubleSolenoid piston = null;

  public ClimberSubsystem () {
    piston = new DoubleSolenoid(RobotMap.CLIMBER_FORWARD, RobotMap.CLIMBER_REVERSE);
  }

  @Override
  public void initDefaultCommand() {
    
  }

  public void extendPiston() {
    piston.set(DoubleSolenoid.Value.kForward);
  }

  public void retractPiston() {
    piston.set(DoubleSolenoid.Value.kReverse);
  }
}
