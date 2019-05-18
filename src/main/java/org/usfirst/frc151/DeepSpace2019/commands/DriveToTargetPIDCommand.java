/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019.commands;

import org.usfirst.frc151.DeepSpace2019.Robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveToTargetPIDCommand extends PIDCommand {
  
  private int[] mostRecentErrors = {0, 0, 0, 0};
  private double baseSpeed = 0;
  
  public DriveToTargetPIDCommand(double p, double i, double d, double baseSpeed) {
    super(p, i, d);
    requires(Robot.chassisSubsystem);
    this.baseSpeed = baseSpeed;
    setSetpoint(0);
    getPIDController().setAbsoluteTolerance(2);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.pixyInUseByCommand = true;
    for(int i = 0; i < mostRecentErrors.length; i++) {
      mostRecentErrors[i] = 0;
    }
    getPIDController().setSetpoint(0);
    getPIDController().enable();
  }

  @Override
  protected double returnPIDInput() {
    double[] pixyResults = new double[3];
    if(Robot.pixyCam.getCenterCoordinates(pixyResults, (byte) 0x01, 0x02)) {
      int error = (int) (pixyResults[0] - (158 - pixyResults[2]/10));
      SmartDashboard.putNumber("Center X", pixyResults[0]);
      SmartDashboard.putNumber("Center Y", pixyResults[1]);
      if(Math.abs(error) < 500) {
        appendToArray(mostRecentErrors, error);
      }
    }

    SmartDashboard.putNumber("Error", averageOfArray(mostRecentErrors));
    return averageOfArray(mostRecentErrors);
  }

  @Override
  protected void usePIDOutput(double output) {
    if(output < 0) {
      Robot.chassisSubsystem.drive(-Math.abs(baseSpeed), -Math.abs(baseSpeed) - output); //WORKS WELL WHEN OUTPUT IS NEGATIVE, so when output is negative, do this. else, add it to left side
    } else if(output > 0) {
      Robot.chassisSubsystem.drive(-Math.abs(baseSpeed) + output, -Math.abs(baseSpeed));
    }
    SmartDashboard.putNumber("PID Output", output);
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    getPIDController().disable();
    Robot.chassisSubsystem.drive(0, 0);
    Robot.pixyInUseByCommand = false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  private void appendToArray(int[] arr, int val) {
    int[] arrCopy = arr.clone();
    for(int i = 0; i < arr.length-1; i++) {
      arr[i] = arrCopy[i+1];
    }
    arr[arr.length-1] = val;
  }

  private double averageOfArray(int[] arr) {
    int total = 0;
    for(int i = 0; i < arr.length; i++) {
      total += arr[i];
    }
    return total/arr.length;
  }
}
