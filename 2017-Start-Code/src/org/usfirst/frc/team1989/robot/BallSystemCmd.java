package org.usfirst.frc.team1989.robot;

public class BallSystemCmd implements cmd {

	CANTalon1989 ballConveyor;
	CANTalon1989 ballOutputWheel;
	JsScaled driveStick;
	Boolean ballIntakeActive;
	Boolean ballOutputActive;
		
	public BallSystemCmd(CANTalon1989 ballConveyor, CANTalon1989 ballOutputWheel, JsScaled driveStick){
		this.ballConveyor = ballConveyor;
		this.ballOutputWheel = ballOutputWheel;
		this.driveStick = driveStick;
	}
	
	public void ballIntake(){
		ballConveyor.set(-1);
		ballOutputWheel.set(-1);
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
		ballIntakeActive = false;
		ballOutputActive = false;
	}

	@Override
	public void teleopPeriodic() {
		// TODO Auto-generated method stub
		Components.writemessage.setmessage(5, ballIntakeActive.toString() );
		if(driveStick.getRawButton(9) == true){
			ballOutputActive = true;
			ballIntakeActive = false;
		}
		if(driveStick.getRawButton(7) == true){
			ballOutputActive = false;
			ballIntakeActive = true;
		}
		// Turn off Ball motors
		if(driveStick.getRawButton(8) == true){
			ballIntakeActive = false;
			ballOutputActive = false;
		}
		if(ballOutputActive)
		ballOutput();
		
		if(ballIntakeActive)
		ballIntake();
	}

}
