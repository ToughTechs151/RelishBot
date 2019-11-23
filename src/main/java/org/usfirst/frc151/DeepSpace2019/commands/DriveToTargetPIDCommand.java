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
  
  private int[] mostRecentErrors = {3, 3, 3, 3};
  
  public DriveToTargetPIDCommand(double p, double i, double d) {
    super(p, i, d);
    requires(Robot.chassisSubsystem);
    setSetpoint(0);
    getPIDController().setAbsoluteTolerance(2);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //Robot.pixyInUseByCommand = true;
    //Robot.ledSubsystem.setLED(Relay.Value.kReverse);
    getPIDController().setSetpoint(0);
    getPIDController().enable();
  }

  @Override
  protected double returnPIDInput() {
    double[] pixyResults = new double[3];
    /*
    if(Robot.pixyCam.getCenterCoordinates(pixyResults, (byte) 0x01, 0x02)) {
      int error = (int) (pixyResults[0] - (158 + pixyResults[2]/2));
      SmartDashboard.putNumber("Center X", pixyResults[0]);
      SmartDashboard.putNumber("Center Y", pixyResults[1]);
      if(Math.abs(error) < 500) {
        appendToArray(mostRecentErrors, error);
      }
    }
*/
    SmartDashboard.putNumber("Error", averageOfArray(mostRecentErrors));
    return averageOfArray(mostRecentErrors);
  }

  @Override
  protected void usePIDOutput(double output) {
    SmartDashboard.putNumber("PID Output", output);
    Robot.chassisSubsystem.drive(-0.6, -0.6 - output);
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
    //Robot.ledSubsystem.setLED(Relay.Value.kOff);
    //Robot.pixyInUseByCommand = false;
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
