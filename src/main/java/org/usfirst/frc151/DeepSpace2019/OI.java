package org.usfirst.frc151.DeepSpace2019;

import org.usfirst.frc151.DeepSpace2019.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc151.DeepSpace2019.subsystems.*;
import edu.wpi.first.wpilibj.buttons.*;


public class OI {

    Joystick joystick = null;
    protected JoystickButton rightJoystick;
    protected JoystickButton leftJoystick;
    


    public OI(int joystickChannel) {
        joystick = new Joystick(joystickChannel);

    }

    public Joystick getJoystick() {
        return joystick;
    }
}

