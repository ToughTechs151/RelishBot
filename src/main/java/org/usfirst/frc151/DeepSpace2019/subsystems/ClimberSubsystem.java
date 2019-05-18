/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc151.DeepSpace2019.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Add your docs here.
 */
public class ClimberSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private DoubleSolenoid frontPiston = null;
  private DoubleSolenoid backPiston = null;

  public ClimberSubsystem () {
    frontPiston = new DoubleSolenoid(RobotMap.FRONT_CLIMBER_FORWARD, RobotMap.FRONT_CLIMBER_REVERSE);
    backPiston = new DoubleSolenoid(RobotMap.BACK_CLIMBER_FORWARD, RobotMap.BACK_CLIMBER_REVERSE);
  }

  @Override
  public void initDefaultCommand() {
    
  }

  public void extendFrontPiston() {
    frontPiston.set(DoubleSolenoid.Value.kForward);
  }

  public void retractFrontPiston() {
    frontPiston.set(DoubleSolenoid.Value.kReverse);
  }

  public void extendBackPiston() {
    backPiston.set(DoubleSolenoid.Value.kForward);
  }

  public void retractBackPiston() {
    backPiston.set(DoubleSolenoid.Value.kReverse);
  }
}