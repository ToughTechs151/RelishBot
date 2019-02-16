package org.usfirst.frc151.DeepSpace2019.subsystems;

import org.usfirst.frc151.DeepSpace2019.RobotMap;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class Hatch extends Subsystem {

    private Compressor compressor;
    private DoubleSolenoid hatchArm;
    private DoubleSolenoid beak;


    public Hatch() {
        try {
            beak = new DoubleSolenoid(RobotMap.BEAK_SOLENOID_PORT_CLOSE, RobotMap.BEAK_SOLENOID_PORT_OPEN);
            addChild("Beak", beak);
        } catch (Exception e) {
            System.out.println("!!!ERROR:beak solenoid missing");
        }
       

        hatchArm = new DoubleSolenoid(RobotMap.ARM_SOLENOID_PORT_STOW, RobotMap.ARM_SOLENOID_PORT_DEPLOY);
        addChild("Arm", hatchArm);

        compressor = new Compressor(RobotMap.COMPRESSOR);
        addChild("Compressor", compressor);
        }

    
    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void extendArm() {
        hatchArm.set(Value.kReverse);
    }

    public void retractArm() {
        hatchArm.set(Value.kForward);
    }

    public void extendBeak() {
        beak.set(Value.kReverse);
    }

    public void retractBeak() {
        beak.set(Value.kForward);
    }

    public void compressorOn() {
        compressor.setClosedLoopControl(true);
    }

    public void compressorOff() {
        compressor.setClosedLoopControl(false);
    }
}
