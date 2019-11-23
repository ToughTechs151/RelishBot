package org.usfirst.frc151.DeepSpace2019;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc151.DeepSpace2019.vision.PixyPacket;
import org.usfirst.frc151.DeepSpace2019.subsystems.*;
import org.usfirst.frc151.DeepSpace2019.vision.Pixy2Camera;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.cscore.*;


public class Robot extends TimedRobot {

    /**
     * How to scale drive outputs to account for mechanical deadband.
     */
    public static final int SCALED_DRIVE = 2;

    public static PixyPacket[] pixyPacketArr = new PixyPacket[2];

    public static final double DRIVE_KP = 0.01;
    public static final double DRIVE_KI = 0.0;
    public static final double DRIVE_KD = 0.0;
    public static final double DRIVE_BASE_SPEED = 0.57;

    public static final double TURN_KP = 0.015;
    public static final double TURN_KI = 0.00015;
    public static final double TURN_KD = 0.0;
    public static final double TURN_BASE_SPEED = 0.55;

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static DriverOI driverOI;
    public static CoDriverOI coDriverOI;
    //define subsystems
    public static ChassisSubsystem chassisSubsystem;
    public static HopperSubsystem hopper;
    public static final LauncherPIDSubsystem launcherPIDSubsystem = new LauncherPIDSubsystem();
    public static CartridgeSubsystem cartridgePiston = new CartridgeSubsystem();
  
    @Override
    public void robotInit() {
        chassisSubsystem = new ChassisSubsystem();
        hopper = new HopperSubsystem();
        driverOI = new DriverOI(0);
        coDriverOI = new CoDriverOI(1);
    }
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        /*
        if(!pixyInUseByCommand) {
            pixyCam.getBlocks(pixyPacketArr, (byte) 0x01, 0x02);
        }
        */
        Scheduler.getInstance().run();
    }
}
