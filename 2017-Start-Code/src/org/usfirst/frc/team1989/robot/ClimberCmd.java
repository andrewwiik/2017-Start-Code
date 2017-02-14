package org.usfirst.frc.team1989.robot;

public class ClimberCmd implements cmd{

	
	CANTalon1989 climberLeft; //encoder
	CANTalon1989 climberRight;
	JsScaled driveStick;
	int encoderStopPos = 0;
	boolean climberStayActive = false;
	
	
	
	public ClimberCmd(CANTalon1989 climberLeft, CANTalon1989 climberRight, JsScaled driveStick){
		this.climberLeft = climberLeft;
		this.climberRight = climberRight;
		this.driveStick = driveStick;
	}
	public ClimberCmd(CANTalon1989 climberLeft, JsScaled driveStick){
		this.climberLeft = climberLeft;
		this.driveStick = driveStick;
	}
	
	public void climberLift(){
		climberLeft.set(-1);
		climberRight.set(-1);
	}
	
	public void climberManualReverse(){
		climberLeft.set(1);
		climberRight.set(1);	
	}
	public void climberStop(){
		climberLeft.set(0);
		climberRight.set(0);
	}
	
	public void climberStayStart(){
		encoderStopPos = climberLeft.getEncPosition();
		climberStayActive = !climberStayActive;
	}
	
	public void climberStay(){//will keep the climber at a set position using encoder values need to check electrical constraints
		
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
		
	}

	@Override
	public void teleopPeriodic() {
		// TODO Auto-generated method stub
		if(driveStick.getRawButton(9) == true){
			climberStayStart();
		}
		if(climberStayActive == true){
			climberStay();
		}
		
		if(climberStayActive == false){
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
		}
		
		
	}

}
