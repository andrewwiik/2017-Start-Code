package org.usfirst.frc.team1989.robot;

public class ClimberCmd implements cmd{

	
	CANTalon1989 climberLeft; //encoder
	CANTalon1989 climberRight;
	JsScaled driveStick;
	
	
	
	public ClimberCmd(CANTalon1989 climberLeft, CANTalon1989 climberRight, JsScaled driveStick){
		this.climberLeft = climberLeft;
		this.climberRight = climberRight;
		this.driveStick = driveStick;
	}
	public ClimberCmd(CANTalon1989 climberLeft, JsScaled driveStick){
		
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
		
	}

}
