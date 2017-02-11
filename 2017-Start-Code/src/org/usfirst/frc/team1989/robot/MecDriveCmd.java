package org.usfirst.frc.team1989.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

import com.ctre.CANTalon;
public class MecDriveCmd implements cmd {
	 
	
	
	CANTalon1989 driveFrontLeft;
	CANTalon1989 driveFrontRight;
	CANTalon1989 driveBackLeft;//encoder motor
	CANTalon1989 driveBackRight;// encoder motor
	
	RobotDrive driveTrain;
	JsScaled driveStick;
	
;
	long encoderLeftCount;
	long encoderRightCount;
	
	
	public MecDriveCmd(CANTalon1989 driveFrontLeft, CANTalon1989 driveBackLeft, CANTalon1989 driveFrontRight, CANTalon1989 driveBackRight,
			JsScaled driveStick){
		this.driveFrontLeft = driveFrontLeft;
		this.driveBackLeft = driveBackLeft;
		this.driveFrontRight = driveFrontRight;
		this.driveBackRight = driveBackRight;
		driveTrain = new RobotDrive(driveFrontLeft, driveBackLeft, driveFrontRight, driveBackRight);
		this.driveStick = driveStick;
		driveFrontRight.setInverted(true);
		driveBackRight.setInverted(true);
	}
	

	public void autonomousInit(){}
	public void autonomousPeriodic() {
		driveTrain.mecanumDrive_Cartesian(driveStick.pX, driveStick.pY, driveStick.pTwist,0);//Last 0 is gyro angle needs to be checked if we get one
	}
	public void teleopInit(){}
	public void teleopPeriodic(){
		
		
			driveTrain.mecanumDrive_Cartesian(driveStick.sgetX(), driveStick.sgetY(), driveStick.sgetTwist(), 0);
		
		
	}
	public void testInit(){}
	public void testPeriodic(){
		
		if(driveStick.sgetTwist() < 0.1 && driveStick.sgetX() < 0.1 && driveStick.sgetY() > 0.1){
			if(encoderLeftCount < encoderRightCount){
				driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, -0.05, 0);
			}
			else if(encoderLeftCount > encoderRightCount){
				driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, 0.05, 0);
			}
			else{
				if(encoderLeftCount < encoderRightCount){
					driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, driveStick.sgetTwist(), 0);
				}
			}
		} else{
			driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, driveStick.sgetTwist(), 0);
		}
	}
	

	    
}
