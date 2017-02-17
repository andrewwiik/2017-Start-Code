package org.usfirst.frc.team1989.robot;

public class BallSystemCmd implements cmd {

	CANTalon1989 ballConveyor;
	CANTalon1989 ballOutputWheel;
	JsScaled driveStick;
	Boolean ballIntakeActive = false;
	
	
	public BallSystemCmd(CANTalon1989 ballConveyor, CANTalon1989 ballOutputWheel, JsScaled driveStick){
		this.ballConveyor = ballConveyor;
		this.ballOutputWheel = ballOutputWheel;
		this.driveStick = driveStick;
	}
	
	
	public void ballIntake(){
		if (ballIntakeActive == true){
			ballConveyor.set(-1);
			ballOutputWheel.set(-1);
		}
		else{
			ballConveyor.set(0);
			ballOutputWheel.set(0);
		}
	}
	
	public void ballOutput(){
		ballConveyor.set(-1);
		ballOutputWheel.set(1);
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
		Components.writemessage.setmessage(5, ballIntakeActive.toString() );
		if(driveStick.getRawButton(1) == true){
			ballOutput();
		}
		if(driveStick.getRawButton(2) == true){
			ballIntakeActive = !ballIntakeActive;
		}
		
		ballIntake();
	}

}
