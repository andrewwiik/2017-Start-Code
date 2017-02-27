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
	int encPulseRatio = 63;
	int breakDistance = 912;
	double minPower = 0.2;
	double speedRamp = 0.05;
	boolean driveFlag = false;
	
	// Par of the Encoder Test (needs class scope, or it would go in test init)
	Integer encoderLeftCount = 0;
	Integer encoderRightCount= 0;
	Integer driveStateTemp = 0;
	Integer cycleDelay = 0;
	Integer deltaEncValue;
	Double driveTempAdjustment;
	Double driveTwistAdjustment = 0.0;
	double temp = 8;
	
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
			driveBackLeft.setEncPosition(0);
			driveBackRight.setEncPosition(0);
			
		}
		// Output data to the dash
		Components.writemessage.setmessage(0, encoderLeftCount.toString());
		Components.writemessage.setmessage(1, encoderRightCount.toString());
			
	}
	
	
	
	
	
	
	
	
	
	public void driveFoward(double inches, double power){//assumes encoder reset to 0, must be driven backwards
		double pulseCount = inches * encPulseRatio;
		if (power > 0){
			speedRamp = -speedRamp;
		}
		if (Math.abs(driveBackLeft.getEncPosition()) < pulseCount || Math.abs(driveBackRight.getEncPosition()) < pulseCount){
			if ((pulseCount - Math.abs(driveBackLeft.getEncPosition())) <= (breakDistance *  (temp /8))){
					power += speedRamp; 
					temp -=1;
			
		}
			
			driveStick.pY = power;
		}
		else {
			driveStick.pY = 0.0;
			driveFlag = false;
		} 
	}
	
	
	public void encoderDrive(double jsX, double jsY, double jsTwist){
		if(jsX == 0 && jsTwist == 0 && jsY > 0){
			if(driveStateTemp == 0){
				encoderLeftCount = driveBackLeft.getEncPosition();
				encoderRightCount = driveBackRight.getEncPosition();
				deltaEncValue = encoderRightCount - encoderLeftCount;
					if(deltaEncValue != 0){
						driveStateTemp = 1;
						driveTempAdjustment = (double) (deltaEncValue/200);
					} else {
						driveTrain.mecanumDrive_Cartesian(jsX, jsY, jsTwist + driveTwistAdjustment, 0);
					}
			    } else if (driveStateTemp == 1){
				
				driveTrain.mecanumDrive_Cartesian(jsX, jsY, jsTwist + driveTempAdjustment + driveTwistAdjustment, 0);
				cycleDelay+=1;
				     if (cycleDelay == 2){
					      driveStateTemp = 2;
					      cycleDelay = 0;
				     }				
			    } 
			    else if(driveStateTemp == 2){
				 driveTwistAdjustment+= driveTempAdjustment/2;
				 driveTrain.mecanumDrive_Cartesian(jsX, jsY, jsTwist + driveTwistAdjustment, 0);
				 driveStateTemp = 0;
			     }
			
			
			
			
			
			} else{
				driveStateTemp = 0;
				driveTrain.mecanumDrive_Cartesian(jsX, jsY, jsTwist, 0);
				driveBackLeft.setEncPosition(0);
				driveBackRight.setEncPosition(0);
		}	
	}
	
	

	
	
	
	
	
	
	
	
	
	
	public void autonomousInit(){
		driveBackLeft.enableBrakeMode(true);
		driveBackRight.enableBrakeMode(true);
		driveFrontLeft.enableBrakeMode(true);
		driveFrontRight.enableBrakeMode(true);
	}
	public void autonomousPeriodic() {
		driveFoward(114, 0.6);
		Components.writemessage.setmessage(5, driveStick.pY.toString());
		Components.writemessage.updatedash();
		driveTrain.mecanumDrive_Cartesian(driveStick.pX, driveStick.pY, driveStick.pTwist,0);//Last 0 is gyro angle needs to be checked if we get one
		
	}
	public void teleopInit(){}
	public void teleopPeriodic(){
		encoderLeftCount = driveBackLeft.getEncPosition();
		encoderRightCount = driveBackRight.getEncPosition();
		driveTrain.mecanumDrive_Cartesian(driveStick.sgetX(), driveStick.sgetY(), driveStick.sgetTwist(), 0);
		Components.writemessage.setmessage(0, encoderLeftCount.toString());
		Components.writemessage.setmessage(1, encoderRightCount.toString());
			
		Components.writemessage.updatedash();
	}
	public void testInit(){}
	
	
	
	public void testPeriodic(){
		//driveTrain.mecanumDrive_Cartesian(driveStick.pX, driveStick.pY, driveStick.pTwist,0);//Last 0 is gyro angle needs to be checked if we get one
		// Display encoder values
		encoderCheck();
		driveTrain.mecanumDrive_Cartesian(driveStick.sgetX(), driveStick.sgetY(), driveStick.sgetTwist(), 0);
		Components.writemessage.updatedash();
	}
	

	    
}
