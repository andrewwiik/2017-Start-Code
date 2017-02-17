package org.usfirst.frc.team1989.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.CANTalon;
public class MecDriveCmd implements cmd {
	
	// Instantiate Basic Components
	CANTalon1989 driveFrontLeft;
	CANTalon1989 driveFrontRight;
	CANTalon1989 driveBackLeft;//encoder motor
	CANTalon1989 driveBackRight;// encoder motor
	RobotDrive driveTrain;
	JsScaled driveStick;
	
	// Par of the Encoder Test (needs class scope, or it would go in test init)
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
	
	// Check the values of each encorder and display it to the smart dashboard
	// This is part of the test code for the encoder based drive
	public void encoderCheck(){
		
		// If going straight get encoder values.  Otherwise set them to 0.
		if(Math.abs(driveStick.sgetTwist()) < 0.1 && Math.abs(driveStick.sgetX()) < 0.1 && Math.abs(driveStick.sgetY()) > 0.1){
			encoderLeftCount = driveBackLeft.getEncPosition();
			encoderRightCount = driveBackRight.getEncPosition();
		} 
		if(Math.abs(driveStick.sgetTwist()) > 0.1 || Math.abs(driveStick.sgetX()) > 0.1){
			encoderLeftCount = 0;
			encoderRightCount = 0;
			
		}
		// Output data to the dash
		Components.writemessage.setmessage(0, encoderLeftCount.toString());
		Components.writemessage.setmessage(0, encoderRightCount.toString());
			
	}
	
	public void encoderDrive(){
		// When going straight, attempt to make any necessary corrections
		if(driveStick.sgetTwist() < 0.1 && driveStick.sgetX() < 0.1 && driveStick.sgetY() > 0.1){
			
			
			if(encoderLeftCount < encoderRightCount){
				driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, -0.05, 0);
			}
			if(encoderLeftCount > encoderRightCount){
				driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, 0.05, 0);
			}
			// Why is this here? I understand the equals condition, but inside you are checking the exact condition above that would be tripped.  
			/*else{
				if(encoderLeftCount < encoderRightCount){
					driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, driveStick.sgetTwist(), 0);
				}
			}*/
		} /*else{
			driveTrain.mecanumDrive_Cartesian(driveStick.sgetX()/2, driveStick.sgetY()/2, driveStick.sgetTwist(), 0);
		}*/
			
	}
	
	public void autonomousInit(){}
	public void autonomousPeriodic() {
		driveTrain.mecanumDrive_Cartesian(driveStick.pX, driveStick.pY, driveStick.pTwist,0);//Last 0 is gyro angle needs to be checked if we get one
	}
	public void teleopInit(){}
	public void teleopPeriodic(){
		
		driveTrain.mecanumDrive_Cartesian(driveStick.sgetX(), driveStick.sgetY(), driveStick.sgetTwist(), 0);
		Components.writemessage.updatedash();
	}
	public void testInit(){}
	
	
	
	public void testPeriodic(){
		//driveTrain.mecanumDrive_Cartesian(driveStick.pX, driveStick.pY, driveStick.pTwist,0);//Last 0 is gyro angle needs to be checked if we get one
		// Display encoder values
		encoderCheck();
		encoderDrive();
		driveTrain.mecanumDrive_Cartesian(driveStick.sgetX(), driveStick.sgetY(), driveStick.sgetTwist(), 0);
		Components.writemessage.updatedash();
	}
	

	    
}
