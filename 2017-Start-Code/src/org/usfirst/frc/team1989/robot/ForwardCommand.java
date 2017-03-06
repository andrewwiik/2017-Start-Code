package org.usfirst.frc.team1989.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team6027.robot.commands.DriveDistance;

public class Forward extends CommandGroup {
    public Forward(){
        addSequential(new DriveDistance(200));
    }
}
