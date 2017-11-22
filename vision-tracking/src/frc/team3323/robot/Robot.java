package org.usfirst.frc.team3323.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot
{
	private Joystick joystickRight = new Joystick(1);
	private Joystick joystickLeft = new Joystick(0);
	private JoystickButton targetSwitch = new JoystickButton(joystickRight, 1);
	private JoystickButton testSwitch = new JoystickButton(joystickLeft, 1);
	
    private Chassis chassis = new Chassis();
    private OwenVision tracker = new OwenVision(chassis.driveTrain, 50, 0, 0);
    private AutoTarget target = new AutoTarget(tracker);
    private Autonomous autoMode = new Autonomous(chassis.driveTrain, target);
	private TestDrive test = new TestDrive(chassis);
	
	public void robotInit()
	{
		targetSwitch.whileHeld(target);
		testSwitch.whileHeld(test);	
	}
	
	public void robotPeriodic()
	{
		SmartDashboard.putNumber("Phase", autoMode.phase);
		SmartDashboard.putNumber("Time", autoMode.startTime);
		tracker.track();
	}
	
	public void teleopPeriodic()
	{
		chassis.driveTrain.tankDrive(joystickRight, joystickLeft);
	}
	
	public void autonomousInit()
	{
		System.out.println("Github is the essence of pure evil");
		autoMode.startTime = 0;
		autoMode.phase = 0;
		SmartDashboard.putData("Select a Position", autoMode.positionChooser);
		SmartDashboard.putData("Select a Task", autoMode.taskChooser);
	}
	
	public void autonomousPeriodic()
	{		
		if( autoMode.startTime == 0 )
			autoMode.startTime = System.currentTimeMillis();
		autoMode.autoExecute();
	}
}
