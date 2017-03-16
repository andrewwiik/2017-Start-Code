# 2017 GearWorks Robot Code for RVR87

## Robot Build

In our robot build we decided to use 4 Spark Motor Controllers, putting 2 motors on each of our rear wheels with a chain connecting the 2 rear wheels to the 2 front wheels, this gave us a tank-drive like system. Our Gear holder, nicknamed the "cereal box" is on the rear of robot with our fuel collecting mechanism being on the front of our robot. At full speed our robot goes approximately 14ft/sec. For our whinch we are using 2 talon motor controllers.


## Robot Modes

### Testing Mode

#### Summary
Testing Mode is commonly used to test the function of the robot before a competition or just when the team wants to make sure all of the vital systems and code for their robot is functioning properly. Our testing mode includes simple driving and turning commands that are executed as if the robot is in autonomous mode. In our testing mode the various tests are iterated through the use of the **START** button on our Logitech F310 Gamepad. Our tests assume the robots gear holder is facing away from the wall. Our tests are executed in the following order:

1. Drive Forward 3 Feet
2. Drive Backwards 1 Foor
3. Turn 90 Degrees to the Left
4. Turn 180 Degrees to the Right


### Autonomous Mode

#### Summary
Autonomous Mode is used during the first 15 seconds of a match where the robot is tasked with performing without any human input. In the competition this year it was obvious that the most benificial autonomous action would be taking the gear that is pre-loaded onto the robot and placing it on one of the pegs. Our autonomous mode consists of a total of 7 options that the Drive Team may choose to use for any particular match and they are as follows:


1. When the Robot starts in the **Right** starting position
   * Place the gear on the right peg and backup
   * Place the gear on the right peg and don't backup
   * Cross the baseline
2. When the Robot starts in the **Center** starting position
   * Place the gear on the center peg and backup
   * Place the gear on the center peg and don't backup
   * Cross the baseline
3. When the Robot starts in the **Left** starting position
   * Place the gear on the left peg and backup
   * Place the gear on the left peg and don't backup
   * Cross the baseline
   
When in autonomous our motors are inverted because our gear holder aka the "cereal box" is on the rear of our robot. After autonomous mode is finished the motors are un-inverted in preperation for the robot most likely entering teleop mode.

#### Selecting the Autonomous Mode

##### Step 1
Make sure the driver station is using the "Default" Dashboard Type as pictured below.

![Autonomous Mode Selection Step 1](https://raw.githubusercontent.com/andrewwiik/2017-Start-Code/master/autonomous-dashboard-type.png "Dashboard Type Selection")

##### Step 2
Select the desired autonomous mode using the field highlighted in the picture below.

![Autonomous Mode Selection Step 2](https://raw.githubusercontent.com/andrewwiik/2017-Start-Code/master/autonomous-dashboard-selection.png "Autonomous Mode Selection")

### Teleop Mode

#### Summary
Teleop Mode is used for the remainder of the match after the autonomous mode portion is complete. For the tele-operated portion of the match we are using a Logitech F310 Gamepad as our primary input device to control our robot. The mapping of the controls on the gamepad are as depicted below:

![Logitech F310 Gamepad Map](https://raw.githubusercontent.com/andrewwiik/2017-Start-Code/master/gamepad-map.png "Logitech F310 Gamepad Map")


A second person on our Drive Team uses a seperate joystick for the mechanical operations of our robot such as operating the winch, and collecting/dispensing fuel.

## Troubleshooting

### Autonomous

**All the Robot's motors are not going the forward when attempting to move forward?**

Go to the *DriveTrain.java* file under the *Subsystems* folder and locate the following method:

```java
public void invertMotors(boolean shouldInvert) {
		
		firstDiabloDrive.setInvertedMotor(MotorType.kFrontLeft, shouldInvert);
		firstDiabloDrive.setInvertedMotor(MotorType.kFrontRight, shouldInvert);
		secondDiabloDrive.setInvertedMotor(MotorType.kFrontLeft, shouldInvert);
		secondDiabloDrive.setInvertedMotor(MotorType.kFrontRight, shouldInvert);
}
```

and start changing incorrect motors to the following, replacing the *ROBOT_DRIVE_OBJECT* and *MOTOR_TYPE* with the same text that was present in the old line, don't forget to comment out the old line once you add the modified one.

```java
ROBOT_DRIVE_OBJECT.setInvertedMotor(MOTOR_TYPE, shouldInvert ? false : true);
```
