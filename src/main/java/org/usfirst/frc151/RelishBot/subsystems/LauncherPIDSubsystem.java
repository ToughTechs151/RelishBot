/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.RelishBot.subsystems;

import java.util.Random;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc151.RelishBot.RobotMap;

/**
 * Add your docs here.
 */
public class LauncherPIDSubsystem extends PIDSubsystem {
  
  private SpeedController launcherMotor;
  private Encoder launcherEncoder;
  private Random randomizer = new Random();
  
  /**
   * 512 Pulses Per Revolution
   */
  public LauncherPIDSubsystem() {
    super("LauncherPIDSubsystem", RobotMap.kPl, RobotMap.kIl, RobotMap.kDl);
    launcherMotor = new Talon(RobotMap.LAUNCHER_MOTOR);
    launcherEncoder = new Encoder(RobotMap.LAUNCHER_ENCODER_A, RobotMap.LAUNCHER_ENCODER_B);
    launcherEncoder.setPIDSourceType(PIDSourceType.kRate);
    setSetpoint(0);
    setAbsoluteTolerance(0);
    disable();
  }

  @Override
  public void initDefaultCommand() {

  }

  private double currentSpeed;
  @Override
  protected double returnPIDInput() {
    currentSpeed = getSpeed();
    //there is a feature where the dashboard will freeze the plot if the output stays the same... fix it by adding random noise
    SmartDashboard.putNumber("launcher speed", currentSpeed + 0.001 * randomizer.nextDouble());
    return getSpeed();
  }

  private double currentOutput;

  @Override
  protected void usePIDOutput(double output) {
    SmartDashboard.putNumber("launcher output", output);
    currentOutput = output;
    launcherMotor.set(output);
  }

  public void stop() {
    double initialGap = currentOutput;
    double gap = initialGap;
    double initialTime = System.currentTimeMillis();
    while(gap > 0) {
      double newOutput = initialGap - (1000 - (System.currentTimeMillis()-initialTime)) * initialGap;
      gap = newOutput;
      launcherMotor.set(newOutput);
    }
    launcherMotor.set(0);
  }

  /**
   * 9.549 is a conversion factor from rad/sec (units of the getRate() method) to rev/min (units of the setpoint)
   * @return
   */
  private double getSpeed() {
    return launcherEncoder.getRate() / 9.549;
  }
}
