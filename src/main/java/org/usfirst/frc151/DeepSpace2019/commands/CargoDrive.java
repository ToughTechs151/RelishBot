package org.usfirst.frc151.DeepSpace2019.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc151.DeepSpace2019.Robot;

/**
 *
 */
public class CargoDrive extends Command {


    public CargoDrive() {

        requires(Robot.chassis);
        requires(Robot.usbCam);

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.usbCam.cameraSwitch(Robot.cam1, Robot.server);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.chassis.positiveDir();

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
