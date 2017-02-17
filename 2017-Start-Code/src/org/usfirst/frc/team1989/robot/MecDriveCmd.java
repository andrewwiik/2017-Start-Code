package org.usfirst.frc.team1989.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.CANTalon;
public class MecDriveCmd implements cmd {
	 
	
	
	CANTalon1989 driveFrontLeft;
	CANTalon1989 driveFrontRight;
	CANTalon1989 driveBackLeft;//encoder motor
	CANTalon1989 driveBackRight;// encoder motor
	
	RobotDrive driveTrain;
	JsScaled driveStick;
	
;
	Integer encoderLeftCount;
	Integer encoderRightCount;
	
	
	public MecDriveCmd(CANTalon1989 driveFrontLeft, CANTalon1989 driveBackLeft, CANTalon1989 driveFrontRight, CANTalon1989 driveBackRight,
			JsScaled driveStick){
		this.driveFrontLeft = driveFrontLeft;
		this.driveBackLeft = driveBackLeft;
		this.driveFrontRight = driveFrontRight;
		this.driveBackRight = driveBackRight;
		driveTrain = new RobotDrive(this.driveFrontLeft, this.driveBackLeft, this.driveFrontRight, this.driveBackRight);
		this.driveStick = driveStick;
		this.driveFrontRight.setInverted(true);
		this.driveBackRight.setInverted(true);
	}
	
	
	public void encoderCheck(){
		if(Math.abs(driveStick.sgetTwist()) < 0.1 && Math.abs(driveStick.sgetX()) < 0.1 && Math.abs(driveStick.sgetY()) > 0.1){
			encoderLeftCount = driveBackLeft.getEncPosition();
			encoderRightCount = driveBackRight.getEncPosition();
			Components.writemessage.setmessage(0, encoderLeftCount.toString());
			Components.writemessage.setmessage(1, encoderRightCount.toString());
			
		} else if(Math.abs(driveStick.sgetTwist()) > 0.1 || Math.abs(driveStick.sgetX()) > 0.1){
			encoderLeftCount = 0;
			encoderRightCount = 0;
			Components.writemessage.setmessage(0, encoderLeftCount.toString());
			Components.writemessage.setmessage(0, encoderRightCount.toString());
		}
			
	}
	
	
	
	
	
	
	
	
	
	
	
	

	public void autonomousInit(){}
	public void autonomousPeriodic() {
		driveTrain.mecanumDrive_Cartesian(driveStick.pX, driveStick.pY, driveStick.pTwist,0);//Last 0 is gyro angle needs to be checked if we get one
	}
	public void teleopInit(){}
	public void teleopPeriodic(){
			encoderCheck();
			
			
			driveTrain.mecanumDrive_Cartesian(driveStick.sgetX(), driveStick.sgetY(), driveStick.sgetTwist(), 0);
			Components.writemessage.updatedash();
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
