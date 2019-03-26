/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019.subsystems;

import org.usfirst.frc151.DeepSpace2019.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class HatchSubsystem extends Subsystem {

    private Compressor compressor;
    private DoubleSolenoid hatchArm;
    private DoubleSolenoid beak;

    public HatchSubsystem() {
        try {
            beak = new DoubleSolenoid(RobotMap.BEAK_SOLENOID_PORT_CLOSE, RobotMap.BEAK_SOLENOID_PORT_OPEN);
        } catch (Exception e) {
            System.out.println("!!!ERROR:beak solenoid missing");
        }

        try {
            hatchArm = new DoubleSolenoid(RobotMap.ARM_SOLENOID_PORT_STOW, RobotMap.ARM_SOLENOID_PORT_DEPLOY);
        } catch (Exception e) {
            System.out.println("Hatch arm solenoid missing");
        }
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void extendArm() {
        hatchArm.set(Value.kForward);
    }

    public void retractArm() {
        hatchArm.set(Value.kReverse);
    }

    public void openBeak() {
        beak.set(Value.kForward);
    }

    public void closeBeak() {
        beak.set(Value.kReverse);
    }
}
