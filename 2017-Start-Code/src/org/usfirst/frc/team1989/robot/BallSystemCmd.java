package org.usfirst.frc.team1989.robot;

import edu.wpi.first.wpilibj.Timer;

public class BallSystemCmd implements cmd {

	CANTalon1989 ballConveyor;
	CANTalon1989 ballOutputWheel;
	JsScaled driveStick;
	Boolean ballIntakeActive;
	Boolean ballOutputActive;
	Timer rampTime = new Timer();
	Timer cheatTimer = new Timer(); 
	int directionCheck = 1;
	boolean systemRunning = false;
	double wheelSpeed = 0;
	Integer state1 = 0;
	int state2 = 0;	
	public BallSystemCmd(CANTalon1989 ballConveyor, CANTalon1989 ballOutputWheel, JsScaled driveStick){
		this.ballConveyor = ballConveyor;
		this.ballOutputWheel = ballOutputWheel;
		this.driveStick = driveStick;
	}
	
	public void ballIntake(){
		systemRunning = true;
		ballConveyor.set(-1);
		if (state1 == 0){
			rampTime.stop();
			rampTime.reset();
			rampTime.start();
			state1 = 1;
		}
		
		if (wheelSpeed > -1){
			if(rampTime.get() > 0.25){
				wheelSpeed -= 0.2;
				rampTime.stop();
				rampTime.reset();
				rampTime.start();
			}
		}
		ballOutputWheel.set(wheelSpeed);
		}
	
	public void ballOutput(){
		systemRunning = true;
		ballConveyor.set(-1);
		if (state2 == 0){
			rampTime.stop();
			rampTime.reset();
			rampTime.start();
			state2 = 1;
		}
		
		if (wheelSpeed < 1){
			if(rampTime.get() > 0.25){
				wheelSpeed += 0.2;
				rampTime.stop();
				rampTime.reset();
				rampTime.start();
			} 
		}
		
		ballOutputWheel.set(wheelSpeed);
	
	}
	
	
	
	
	@Override
	public void autonomousInit() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void autonomousPeriodic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testPeriodic() {
		// TODO Auto-generated method stub
		
		}

	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub
		ballIntakeActive = false;
		ballOutputActive = false;
	}

	@Override
	public void teleopPeriodic() {
		// TODO Auto-generated method stub
		//Components.writemessage.setmessage(5, ballIntakeActive.toString() );
		if(driveStick.getRawButton(11) == true){
			//ballOutputActive = true;
			//ballIntakeActive = false;
		}
		if(driveStick.getRawButton(7) == true){
			//ballOutputActive = false;
			//ballIntakeActive = true;
			ballIntake();
		} 		 //Turn off Ball motors
		else if(driveStick.getRawButton(8) == true){
			//ballIntakeActive = false;
			//ballOutputActive = false;
			ballOutput();
			
			
		} else {
			
			ballConveyor.set(0);
			ballOutputWheel.set(wheelSpeed);
			if (systemRunning == true){
				rampTime.stop();			
				rampTime.reset();
				rampTime.start(); 
				systemRunning = false;
				if (state1 != 0 || state2 != 0){
					state1 = state2 = 0;
				}
			}
				if (wheelSpeed < 0){
					if(rampTime.get() > 0.25){
						wheelSpeed += 0.2;
						rampTime.stop();
						rampTime.reset();
						rampTime.start();
					
					}
				} if (wheelSpeed > 0){
						if(rampTime.get() > 0.25){
							wheelSpeed -= 0.2;
							rampTime.stop();
							rampTime.reset();
							rampTime.start();
						}
				}
			}	
			
		//if(ballOutputActive){
		//ballOutput();
	
		//if(ballIntakeActive){
		//ballIntake();
		}
	}



