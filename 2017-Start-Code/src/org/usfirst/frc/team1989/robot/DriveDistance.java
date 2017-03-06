
package org.usfirst.frc.team1989.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1989.robot.Robot;

public class DriveDistance extends Command {
    private PIDController pid;

    public DriveDistance(double setpoint){
        requires(MecDriveCmd.driveTrain);
        requires(Components.driveBackLeft);
        pid = new PIDController(0.27, 0, 0, Components.driveBackLeft.getEncPos()*62, new pidOutput());
        pid.setAbsoluteTolerance(3);
        pid.setSetpoint(setpoint);
    }
    @Override
    protected void initialize() {
        pid.enable();
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return pid.onTarget();
    }

    @Override
    protected void end() {
        pid.disable();
        pid.reset();
        Components.driveBackLeft.setEncPosition(0);
    }

    @Override
    protected void interrupted() {
        pid.disable();
        pid.reset();
        Components.driveBackLeft.setEncPosition(0);
    }
    private class pidOutput implements PIDOutput {
        @Override
        public void pidWrite(double output) {
            if(output<0){
                MecDriveCmd.driveTrain.tankDrive(output*-0.53,output*-0.5);
            }else{
                MecDriveCmd.driveTrain.tankDrive(output*-0.50,output*-0.55);
            }
        }
    }
}
