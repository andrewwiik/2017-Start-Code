package org.usfirst.frc.team1989.robot;

//import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
//import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements cmd{

	// Define class attributes
	
	private CommandGroup autonomousCommandGroup;
	
	Integer tmpint;
	int autoStatus;
	int autoMode;;
	Timer t1;
	Integer encoderPulseRatio = 1;// needs to be determined, possibly at various speeds, will be in inches, default at 0.6 speed, ratio of distance to gear pulses
	Integer averageEncPos = 1;//calculate
	Double checkAmpValue;
		MecDriveCmd mDrive = new MecDriveCmd(Components.driveFrontLeft, Components.driveBackLeft, Components.driveFrontRight, Components.driveBackRight, Components.driveStick);
		CameraControl camControl = new CameraControl(Components.servoX, Components.servoY, Components.driveStick);
		GearPushCmd gearPusher = new GearPushCmd(Components.gearMotor, Components.driveStick);
		ClimberCmd climber = new ClimberCmd(Components.climberLeft, Components.climberRight, Components.driveStick);
		BallSystemCmd ballSystem = new BallSystemCmd(Components.ballConveyor, Components.ballOutputWheel, Components.driveStick);
	@Override
	public void robotInit() {
		// Implement the functions for the components
	
		//EncoderDistanceCMD encDisTest = new EncoderDistanceCMD(Components.driveBackLeft, Components.driveBackRight, Components.driveStick);
		
		// Add functions to the cmdlist
		SharedStuff.cmdlist.add(mDrive);
		SharedStuff.cmdlist.add(camControl);
		SharedStuff.cmdlist.add(gearPusher);
		SharedStuff.cmdlist.add(climber);
		SharedStuff.cmdlist.add(ballSystem);
		
		// Activate the camera 
		camControl.cameraReset();
		CameraServer.getInstance().startAutomaticCapture();
		
		//t1.start();
	}

	
	@Override
	public void autonomousInit() {
// 		autoStatus = 0;
// 		autoMode = 0;
// 		Components.driveBackLeft.setEncPosition(0);
// 		Components.driveBackRight.setEncPosition(0);
// 		gearPusher.pushRoutine = false;
		autonomousCommandGroup = new Forward();
		autonomousCommandGroup.start();
		
	}
		
	
	public void auto1(){
		if(autoStatus == 0){
			if(averageEncPos < 95 * encoderPulseRatio){
				Components.driveStick.pY = -0.6;
			} else{
				autoStatus = 1;
				Components.driveStick.pY = -0.4; //slow enough to drive but need to not smash the wall	
				 checkAmpValue = Components.driveBackLeft.getOutputCurrent();
			}
		}	
		if (autoStatus == 1){	
			if (Components.driveBackLeft.getOutputCurrent() < checkAmpValue*2){//not sure if that is correct
				Components.driveStick.pY = -0.4;
			} else{
				Components.driveStick.pY = 0.0;
				autoStatus = 2;
				gearPusher.gearPushStart();
			}				
		}
		if (autoStatus == 2){
			if(gearPusher.pushRoutine == true){
				gearPusher.gearPush();
			} else{
				autoStatus = 3;
			}
		}
		if(autoStatus == 3){
			if(averageEncPos < 10 * encoderPulseRatio){
				Components.driveStick.pY = 0.6;
			} else{
				Components.driveStick.pY = 0.0;
			}
		}
	}
	
	
	
	
	/**
	 * This function is called periodically during autonomous
	 ?//*/
	@Override
	public void autonomousPeriodic() {
// 		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
// 			SharedStuff.cmdlist.get(i).autonomousPeriodic();
// 		}
		Scheduler.getInstance().run();
	
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopInit(){
		Components.driveBackLeft.setEncPosition(0);
		Components.driveBackRight.setEncPosition(0);
	}
	@Override
	public void teleopPeriodic() {
		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).teleopPeriodic();
		}
		
		
	}

	public void testInit(){
		// Values for testing the number of times a second that we run our periodic
		tmpint = new Integer(0);
		t1 = new Timer();
	}
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
		// Find out how often we run
		if(t1.get() >= 1){
			//System.out.println(mDrive.driveBackLeft.getEncPosition());
			//System.out.println(mDrive.driveBackRight.getEncPosition());
			//System.out.println("count " + tmpint);
			tmpint ++;
			t1.stop();
			t1.reset();
			t1.start();
		}
		
		// Climber Test Code
		if(Components.driveStick.getRawButton(6) == true){	
			Components.climberLeft.set(-0.75);
			Components.climberRight.set(-0.75);
		} else{
			Components.climberLeft.set(0);
			Components.climberRight.set(0);
		}
		
	}
}
	
