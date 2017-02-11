package org.usfirst.frc.team1989.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Timer;

public class GearPushCmd implements cmd {

	//Class Attributes
	CANTalon1989 gearPush;
	JsScaled driveStick;
	JsScaled utilityStick;
	Timer gearTimer = new Timer();
	int state = 0;
	boolean finished = true;
	
	
	//Constructor
	public GearPushCmd(CANTalon1989 gearPush, JsScaled driveStick, JsScaled utilityStick){
		this.gearPush = gearPush;
		this.driveStick = driveStick;
		this.utilityStick = utilityStick;
		
		
	}
	
	public GearPushCmd(CANTalon1989 gearPush, JsScaled driveStick){
		this.driveStick = driveStick;
		this.gearPush = gearPush;
	}
	//Class Method
	public void gearPushSet(){
		finished = false;
	}
	
	public void gearPush(){
		if (state == 0){
			state = 1;
			gearPush.set(-1);
			gearTimer.stop();
			gearTimer.reset();
			gearTimer.start();
		}	
		else if (state == 1){
			gearPush.set(-1);
			if (gearTimer.get() > 1){
				state = 2;
				gearPush.set(1);
				gearTimer.stop();
				gearTimer.reset();
				gearTimer.start();
			}
		}
		else if (state == 2){
			gearPush.set(1);
			if (gearTimer.get() > 1){
				state = 3;
				gearTimer.stop();
				gearTimer.reset();
			}
		}
		else if (state == 3){
			state = 0;
			gearPush.set(0);
			finished = true;
		}		
	}
	
	
	public void Foward(){
		gearPush.set(-1);
	}
	public void Back(){
		gearPush.set(1);
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
		if(driveStick.getRawButton(3) == true){
			gearPushSet();
		}
		if(finished == false){
			gearPush();
		}
		
		
		if(driveStick.getRawButton(10) == true){
			gearPush.set(-1);
		}
		else if(driveStick.getRawButton(11) == true){
			gearPush.set(1);
		}
		else{
			gearPush.set(0);
		}
		
	}

}
