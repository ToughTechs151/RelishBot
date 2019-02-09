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

    public static OI oi;
    public static Chassis chassis;
    public static Cargo cargo;
    public static Hatch hatch;
    public static USBCamera usbCam;
    public static MjpegServer server;
    public static UsbCamera cam1, cam2;
    public static Vision vision;

    @Override
    public void robotInit() {

        chassis = new Chassis();
        cargo = new Cargo();
        hatch = new Hatch();
        usbCam = new USBCamera();
        cam1 = CameraServer.getInstance().startAutomaticCapture(0);
        cam2 = CameraServer.getInstance().startAutomaticCapture(1);
        server = new MjpegServer("server", 0);
        server.setSource(cam1);
        vision = new Vision();

        oi = new OI(0);


        chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

        SmartDashboard.putData("Auto mode", chooser);
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

        hatch.compressorOn();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
