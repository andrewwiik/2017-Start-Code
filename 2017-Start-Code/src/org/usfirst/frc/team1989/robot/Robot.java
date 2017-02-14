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

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements cmd{
	
	
	CANTalon1989 driveFrontLeft = new CANTalon1989(3);
	CANTalon1989 driveFrontRight = new CANTalon1989(9);
	CANTalon1989 driveBackLeft = new CANTalon1989(7);
	CANTalon1989 driveBackRight = new CANTalon1989(25);
	CANTalon1989 climberLeft = new CANTalon1989(4);
	CANTalon1989 climberRight = new CANTalon1989(2);
	CANTalon1989 gearMotor = new CANTalon1989(6);
	
	Integer tmpint = new Integer(0);


	
	double driveramp = 6.0;
	public String type = ""; // holds class type
	public static double Kp = 0.03; // const for multiplying gyro angle

		
	int autoStatus = 0;
	int autoMode = 0;

	// Instantiating Timer
	Timer t1 = new Timer();
	

	// Instantiating Servo
	Servo servoX = new Servo(0);
	Servo servoY = new Servo(1);

	// Instantiating Joysticks
	JsScaled driveStick = new JsScaled(0);
	JsScaled uStick = new JsScaled(1);//The uStick will stand for the utility joystick responsible for shooting and arm movement
	
	Gyro gyro;


	MecDriveCmd mDrive = new MecDriveCmd(driveFrontLeft, driveBackLeft, driveFrontRight, driveBackRight, driveStick);
	CameraControl camControl = new CameraControl(servoX, servoY, driveStick);
	GearPushCmd gearPusher = new GearPushCmd(gearMotor, driveStick);
	ClimberCmd climber = new ClimberCmd(climberLeft, climberRight, driveStick);
	@Override
	public void robotInit() {
		SharedStuff.cmdlist.add(mDrive);
		SharedStuff.cmdlist.add(camControl);
		SharedStuff.cmdlist.add(gearPusher);
		SharedStuff.cmdlist.add(climber);
		camControl.cameraReset();
		t1.start();
		CameraServer.getInstance().startAutomaticCapture();
		
		/*try{
			gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
			//System.out.println("gyro connected");
		}
		catch (NullPointerException e){
			gyro = null;
			//System.out.println("gyro not connected");
		}*/
	}

	
	@Override
	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous
	 ?//*/
	@Override
	public void autonomousPeriodic() {
		
	
	}

	/**
	 * This function is called periodically during operator control
	 */
	
	
	public void teleopInit(){
		
	}
	@Override
	public void teleopPeriodic() {
		
		
		tmpint ++;
		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).teleopPeriodic();
		}
		
	
	// From Mr. P - Count the number of times we run "periodically" during a second.
	/*if(t1.get() >= 1){
		//System.out.println(mDrive.driveBackLeft.getEncPosition());
		//System.out.println(mDrive.driveBackRight.getEncPosition());
		//System.out.println("count " + tmpint);
		tmpint = 0;
		t1.stop();
		t1.reset();
		t1.start();
	}*/

	// Drive Code Test
	/*if(driveStick.getRawButton(6) == true){	


		climberLeft.set(-0.75);
		climberRight.set(-0.75);
	} else{
		climberLeft.set(0);
		climberRight.set(0);
	}*/
	
		
	
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

		
	}
}
	
