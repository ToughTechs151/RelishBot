package org.usfirst.frc151.DeepSpace2019.subsystems;


import org.ietf.jgss.Oid;
import org.usfirst.frc151.DeepSpace2019.RobotMap;
import org.usfirst.frc151.DeepSpace2019.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;



public class Cargo extends Subsystem {

    Spark rightGrabber;
    Spark leftGrabber;


    public Cargo() {
        rightGrabber = new Spark(RobotMap.CARGO_RIGHT);
        leftGrabber = new Spark(RobotMap.CARGO_LEFT);
    }

    @Override
    public void initDefaultCommand() {
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }
    public void turnWheelsGrab() {
        // GrabCargo command calls this to grab the ball
    }

    public void turnWheelsRelease() {
        // ReleaseCargo command calls this to release the ball
    }
    
    public void turnWheelsAuto(double speed) {
    	leftGrabber.set(speed);
        rightGrabber.set(speed);
    }
}

