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
	int encPulseRatio = 76;
	int breakDistance = 912;
	double minPower = 0.2;
	double speedRamp = 0.05;
	boolean driveFlag = false;
	
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
			driveBackLeft.setEncPosition(0);
			driveBackRight.setEncPosition(0);
			
		}
		// Output data to the dash
		Components.writemessage.setmessage(0, encoderLeftCount.toString());
		Components.writemessage.setmessage(0, encoderRightCount.toString());
			
	}
	
	
	
	
	
	
	
	
	
	public void driveFoward(double inches, double power){//assumes encoder reset to 0, must be driven backwards
		double pulseCount = inches * encPulseRatio;
		if (power > 0){
			speedRamp = -speedRamp;
		}
		if (driveBackLeft.getEncPosition() < pulseCount || driveBackRight.getEncPosition() < pulseCount){
				if (pulseCount - driveBackLeft.getEncPosition() < breakDistance && pulseCount - driveBackLeft.getEncPosition() > breakDistance *.875){
					power +=speedRamp;
				} else if(pulseCount - driveBackLeft.getEncPosition() < breakDistance *.875 && pulseCount - driveBackLeft.getEncPosition() > breakDistance *.75){
					power += speedRamp * 2;
				} else if(pulseCount - driveBackLeft.getEncPosition() < breakDistance *.75 && pulseCount - driveBackLeft.getEncPosition() > breakDistance *.625){
					power += speedRamp * 3;
				} else if(pulseCount - driveBackLeft.getEncPosition() < breakDistance *.625 && pulseCount - driveBackLeft.getEncPosition() > breakDistance *.5){
					power += speedRamp * 4;
				} else if (pulseCount - driveBackLeft.getEncPosition() < breakDistance * 0.5 && pulseCount - driveBackLeft.getEncPosition() > breakDistance *.375){
					power += speedRamp *5;
				} else if (pulseCount - driveBackLeft.getEncPosition()*0.375 < breakDistance && pulseCount - driveBackLeft.getEncPosition() > breakDistance *.25){
					power += speedRamp * 6;
				} else if (pulseCount - driveBackLeft.getEncPosition() < breakDistance * 0.25 && pulseCount - driveBackLeft.getEncPosition() > breakDistance * .125){
					power += speedRamp *7;
				} else if (pulseCount - driveBackLeft.getEncPosition() < breakDistance *.125){
					power += speedRamp * 8;
				}
			if (power >= minPower){
				driveStick.pY = power;
			} else{
				driveStick.pY = minPower;
			}
		} 
		
		else {
			driveStick.pY = 0;
			driveFlag = false;
		}
	}
	
	
	public void encoderDrive(double jsX, double jsY, double jsTwist){
		if(jsX == 0 && jsTwist == 0 && jsY > 0){
			
			
			
			
			
			
		} else{
			driveTrain.mecanumDrive_Cartesian(jsX, jsY, jsTwist, 0);
			driveBackLeft.setEncPosition(0);
			driveBackRight.setEncPosition(0);
		}
		
		
		
		
		
		
		
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
		driveTrain.mecanumDrive_Cartesian(driveStick.sgetX(), driveStick.sgetY(), driveStick.sgetTwist(), 0);
		Components.writemessage.updatedash();
	}
	

	    
}
