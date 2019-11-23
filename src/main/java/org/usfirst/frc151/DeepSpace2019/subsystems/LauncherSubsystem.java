package org.usfirst.frc151.DeepSpace2019.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc151.DeepSpace2019.RobotMap;

public class LauncherSubsystem extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
  
    private SpeedController launcherMotor;
    private Encoder launcherEncoder;
  
    public LauncherSubsystem() {
      launcherMotor = new Talon(RobotMap.LAUNCHER_MOTOR);
      launcherEncoder = new Encoder(RobotMap.LAUNCHER_ENCODER_A, RobotMap.LAUNCHER_ENCODER_B);
    }
  
    public void driveLauncher(double speed) {
      System.out.println("Encoder count: " + launcherEncoder.get());
      launcherMotor.set(speed);
    }
  
    @Override
    public void initDefaultCommand() {
      // Set the default command for a subsystem here.
      // setDefaultCommand(new MySpecialCommand());
    }
  }
  