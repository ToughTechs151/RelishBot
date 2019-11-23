/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class CartridgeSubsystem extends Subsystem {

  private Solenoid cartridgeSolenoid;

  @Override
  public void initDefaultCommand() {

  }

  public CartridgeSubsystem() {
    cartridgeSolenoid = new Solenoid(0);
  }

  public void extend() {
    cartridgeSolenoid.set(false);
  }

  public void retract() {
    cartridgeSolenoid.set(true);
  }

  // public void off() {
  //   cartridgeSolenoid.set(DoubleSolenoid.Value.kOff);
  // }
}
