package org.usfirst.frc.team1989.robot;

public class ClimberCmd implements cmd{

	// Attributes: 2 motors, joystick, encoder position, and a toggle for the process.
	CANTalon1989 climberLeft; //encoder
	CANTalon1989 climberRight;
	JsScaled driveStick;
	int encoderStopPos;
	boolean climberRoutine;
	
	// Constructor with 2 motors - needed after last motor was placed onto robot	
	public ClimberCmd(CANTalon1989 climberLeft, CANTalon1989 climberRight, JsScaled driveStick){
		this.climberLeft = climberLeft;
		this.climberRight = climberRight;
		this.driveStick = driveStick;
	}
	
	// Constructor with 1 motor - initial design only had 1, also a fail safe if one motor breaks down.
	public ClimberCmd(CANTalon1989 climberLeft, JsScaled driveStick){
		this.climberLeft = climberLeft;
		this.driveStick = driveStick;
	}
	
	// Move robot up the rope
	public void climberLift(){
		climberLeft.set(-1);
		climberRight.set(-1);
	}
	
	// Drop the robot down the rope
	public void climberDrop(){
		climberLeft.set(1);
		climberRight.set(1);	
	}
	
	// Stop climber motors
	public void climberStop(){
		climberLeft.set(0);
		climberRight.set(0);
	}
	
	// Have motors hold robot's position
	public void climberActive(){
		encoderStopPos = climberLeft.getEncPosition();
		climberRoutine = true;
	}
	
	// Will keep the climber at a set position using encoder values need to check electrical constraints
	public void climberHoldPosition(){
		
		if(encoderStopPos < climberLeft.getEncPosition()){
			climberLeft.set(-.5);
			climberRight.set(-.5);
		} //else if (encoderStopPos + 1000 > climberLeft.getEncPosition()){
			//climberLeft.set(.5);
			//climberRight.set(.5);
	//	} 
		else{
			climberLeft.set(0);
			climberRight.set(0);
		}
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
		encoderStopPos = 0;
		climberRoutine = false;
	}

	@Override
	public void teleopPeriodic() {
		
		// If button is clicked, begin the routine.
		if(driveStick.getRawButton(9) == true){
			climberActive();
		}
		
		
		/* This part needs to be re-examined it has been re-written below as a potential suggestion 
		if(climberRoutine == true){
			climberHoldPosition();
		}
		
		if(climberRoutine == false){
			if(driveStick.getRawButton(4) == true){
				climberLeft.set(-1);
				climberRight.set(-1);
			} else if (driveStick.getRawButton(10) == true){
				climberLeft.set(1);
				climberRight.set(1);
			}
			else{
				climberLeft.set(0);
				climberRight.set(0);
			}
		}*/
		
		// Rewrite still in progress.......
		if(climberRoutine == true){
			
			
			
			
		}
		
		// If we aren't in the preset routine, allow user activated controls
		if(climberRoutine == false){
			if(driveStick.getRawButton(4) == true){
				climberLift();
				/*climberLeft.set(-1);
				climberRight.set(-1);*/
			} else if (driveStick.getRawButton(6) == true){
				climberDrop();
				/*climberLeft.set(1);
				climberRight.set(1);*/
			}
			else{
				climberLeft.set(0);
				climberRight.set(0);
			}
		
		}
	}

}
