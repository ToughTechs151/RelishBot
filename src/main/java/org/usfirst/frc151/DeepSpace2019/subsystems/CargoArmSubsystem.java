/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc151.DeepSpace2019.OI;
import org.usfirst.frc151.DeepSpace2019.Robot;
import org.usfirst.frc151.DeepSpace2019.RobotMap;
import org.usfirst.frc151.DeepSpace2019.commands.DriveCargoArmWithTriggersCommand;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class CargoArmSubsystem extends Subsystem {
  /** Hardware */
  WPI_TalonSRX _talon = null;

  /** Used to create string thoughout loop */
  StringBuilder _sb = new StringBuilder();
  int _loops = 0;

  /** Save the target position to servo to */
  double targetPositionRotations;

  public static int kTimeoutMs = 30;
  public static boolean kSensorPhase = false;
  //true = positive position value; false = negative position value
  public static boolean kMotorInvert = false;
  public static int kSlotIdx = 0;
  public static int kPIDLoopIdx = 0;

  /**
   *  column 1 = TOWARDS HATCH
   *  column 2 = TOWARDS BUMPER
   */
  //POT VALUES MUST BE IN ASCENDING ORDER
  static double[][] powerTable = {
    {0, 0.0, 0.2},
    {210, 0.15, 0.15},
    {340, 0.3, 0.0},
    {900, 0.0, 0.0},
    {10000, 0.0, 0.0}
  };

  public CargoArmSubsystem() {

    _talon = new WPI_TalonSRX(1);

    /* Config the sensor used for Primary PID and sensor direction */
    _talon.configSelectedFeedbackSensor(FeedbackDevice.Analog, kPIDLoopIdx, kTimeoutMs);
    _talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    _talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

    /* Config the peak and nominal outputs, 12V means full */
    _talon.configNominalOutputForward(0, kTimeoutMs);
    _talon.configNominalOutputReverse(0, kTimeoutMs);
    _talon.configPeakOutputForward(1, kTimeoutMs);
    _talon.configPeakOutputReverse(-1, kTimeoutMs);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveCargoArmWithTriggersCommand());
  }

  public void driveManual(OI oi) {
    double joyVal = deadzone(oi.getJoystick().getRawAxis(RobotMap.RIGHT_JOYSTICK_Y));
    int colRef = joyVal > 0 ? 1 : 2;
    double scaleFactor = getMaxPower(_talon.getSelectedSensorPosition(), colRef);

    /**
     * negative because the motor and the arm drive in opposite directions
     */
    double motorOutput = -joyVal * scaleFactor;

    if(joyVal == 0) {
      motorOutput = 0;
    }

    _talon.set(ControlMode.PercentOutput, motorOutput);
  }


  public double deadzone(double val) {
    return Math.abs(val) > 0.05 ? val : 0;
  }

  public void printData() {
      /* Get Talon/Victor's current output percentage */
      double motorOutput = _talon.getMotorOutputPercent();
  
      /* Prepare line to print */
      _sb.append("\tout:");
      /* Cast to int to remove decimal places */
      _sb.append((int) (motorOutput * 100));
      _sb.append("%"); // Percent
  
      _sb.append("\tpos:");
      _sb.append(_talon.getSelectedSensorPosition(0));
      _sb.append("u"); // Native units
  
    if (++_loops >= 10) {
      _loops = 0;
      System.out.println(_sb.toString());
    }

    /* Reset built string for next loop */
    _sb.setLength(0);
  }

  static double getMaxPower(int potVal, int colRef) {
    double maxOutput = 0;
    
    for(int r = 0; r < 5; r++) {
        try {
          if(inRangeInclusive(potVal, powerTable[r][0], powerTable[r+1][0])) {
            maxOutput = powerTable[r][colRef];
            return maxOutput;
          }
        } catch(ArrayIndexOutOfBoundsException e) {
          // maxOutput = powerTable[r][colRef];
          maxOutput = 3000;
        }
      }
    return maxOutput;
  }

  static boolean inRangeInclusive(int val, double min, double max) {
    return val >= min && val <= max;
  }
}
