package org.usfirst.frc151.RelishBot;

import edu.wpi.first.wpilibj.RobotBase;


public final class Main {
  private Main() {
  }

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
