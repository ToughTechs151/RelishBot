package org.usfirst.frc151.DeepSpace2019;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc151.DeepSpace2019.commands.*;
import org.usfirst.frc151.DeepSpace2019.subsystems.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.*;


public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static DriverOI driverOI;
    public static CoDriverOI coDriverOI;
    public static ChassisSubsystem chassisSubsystem;
    public static CargoClawSubsystem cargoClawSubsystem;
    public static HatchSubsystem hatchSubsystem;
    public static MjpegServer cameraSwitchServer = null;
    public static UsbCamera hatchCamera = null;
    public static UsbCamera cargoCamera = null;
    public static UsbCameraSubsystem cameraSubSystem = null;

    @Override
    public void robotInit() {
        chassisSubsystem = new ChassisSubsystem();
        cargoClawSubsystem = new CargoClawSubsystem();
        hatchSubsystem = new HatchSubsystem();
        cameraSubSystem = new UsbCameraSubsystem();

        driverOI = new DriverOI(0);
        coDriverOI = new CoDriverOI(1);

        chooser.setDefaultOption("Autonomous Command", null);

        SmartDashboard.putData("Auto mode", chooser);

        try {
            hatchCamera = CameraServer.getInstance().startAutomaticCapture("HatchCam", 0);
            cargoCamera = CameraServer.getInstance().startAutomaticCapture("CargoCam", 1);
            cameraSwitchServer = CameraServer.getInstance().addSwitchedCamera("switchCam");
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
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
        Scheduler.getInstance().run();
    }
}
