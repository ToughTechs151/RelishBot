package org.usfirst.frc151.DeepSpace2019;

import java.awt.Button;

import org.usfirst.frc151.DeepSpace2019.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc151.DeepSpace2019.subsystems.*;
import edu.wpi.first.wpilibj.buttons.*;

public class OI {
	Joystick joystick = null;

	protected JoystickButton x;
	protected JoystickButton a;
	protected JoystickButton b;
	protected JoystickButton y;
	protected JoystickButton leftBumper;
	protected JoystickButton rightBumper;
	protected JoystickButton leftTrigger;
	protected JoystickButton rightTrigger;
	protected JoystickButton back;
	protected JoystickButton start;
	protected JoystickButton leftJoystick;
	protected JoystickButton rightJoystick;

	public OI(int joystickChannel) {		
		joystick = new Joystick(joystickChannel);	
		x = null; 
		a = null;
		b = null;
		y = null;
		leftBumper = null;
		rightBumper = null;
		leftTrigger = null;
		rightTrigger = null;
		back = null;
		start = null;
		leftJoystick = null;
		rightJoystick = null;
	}

	public Joystick getJoystick() {
		return joystick;
    }
    
}
// public class OI {

//     Joystick joystick = null;
//     protected JoystickButton rightJoystick;
//     protected JoystickButton leftJoystick;

//     JoystickButton buttonB;
//     JoystickButton buttonX;


//     public OI(int joystickChannel) {
//         joystick = new Joystick(joystickChannel);
        
//         buttonB = new JoystickButton(joystick, 2);
//         buttonX = new JoystickButton(joystick, 3);
//         buttonB.whenPressed(new CargoDrive());
//         buttonX.whenPressed(new HatchDrive());

//     }

//     public Joystick getJoystick() {
//         return joystick;
//     }
// }
