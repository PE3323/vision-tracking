package org.usfirst.frc.team3323.robot;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem 
{

	private SpeedController driveMotorLeft = new Jaguar(4);
	private SpeedController driveMotorRight = new Talon(5);
	RobotDrive driveTrain = new RobotDrive(driveMotorLeft, driveMotorRight);
    
    public void drivingTest()
	{
		driveMotorLeft.set(0.5);
	}

	@Override
	protected void initDefaultCommand()
	{
		// TODO Auto-generated method stub
	}
}

