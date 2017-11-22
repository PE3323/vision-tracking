package org.usfirst.frc.team3323.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous 
{
	int phase;
	long startTime;
	private RobotDrive driveTrain;
	private AutoTarget target;
	SendableChooser<String> positionChooser = new SendableChooser<>();
	SendableChooser<String> taskChooser = new SendableChooser<>();
	private final String Right = "Right";
	private final String Left = "Left";
	private final String Center = "Center";
	private final String BaseLine = "Base Line";
	private final String Gear = "Gear";
	private final String HighGoal = "High Goal";
	
	public Autonomous( RobotDrive drivetrain, AutoTarget target)
	{
		this.driveTrain = drivetrain;
		this.target = target;
	}
	

	
	private int executePhase( long duration, double speed, double curve)
	{
		long dur = System.currentTimeMillis() - startTime;
		System.out.println("Phases" + phase + ":" + dur);
		if( dur < duration )
		{
			driveTrain.drive(speed, curve);
		}
		else 
		{
			phase++;
			startTime = System.currentTimeMillis();
			System.out.println("New phase: " + phase );
		}
		
		return phase;
	}

	public void autoExecute()
	{
		positionChooser.addDefault(Right, Right);
		positionChooser.addObject(Left, Left);
		positionChooser.addObject(Center, Center);
		
		taskChooser.addDefault(BaseLine, BaseLine);
		taskChooser.addObject(HighGoal, HighGoal);
		taskChooser.addObject(Gear, Gear);
		
	switch (positionChooser.getSelected()) 
	{
	case Left:
		switch (taskChooser.getSelected())
		{
		case BaseLine:
			SmartDashboard.putString("Auto Status", "Left Base Line");
		if( phase == 0 )
			executePhase(2000, -1, 0 );
		if( phase == 1 )
			executePhase(0, 0, 0);
		break;
		
		case Gear:
			SmartDashboard.putString("Auto Status", "Left Gear");
			if( phase == 0 )
				executePhase(2000, -1, 0 );
			if( phase == 1 )
				executePhase(500, -1, 1);
			if( phase == 2 )
				executePhase(1000, -1, 0);
			if( phase == 4 )
				executePhase(0, 0, 0);
			break;
			
		case HighGoal:
			SmartDashboard.putString("Auto Status", "Left Boiler");
			if( phase == 0 )
				executePhase(1000, -1, 0);
			if( phase == 1 )
				executePhase(500, -1, 1);
			if( phase == 3 )
				executePhase(1500, -1, 0);
			if( phase == 2 )
				executePhase(500, -1, -1);
			if( phase == 3 )
				executePhase(1000, -1, 0 );
			if( phase == 3 )
				executePhase(0, 0, 0);
			target.execute();
		break;
		
		}
		break;
		
	case Right:
		switch (taskChooser.getSelected())
		{
		case BaseLine:
			SmartDashboard.putString("Auto Status", "Right Base Line");
		if( phase == 0 )
			executePhase(2000, -1, 0 );
		if( phase == 1 )
			executePhase(0, 0, 0);
		break;
		
		case Gear:
			SmartDashboard.putString("Auto Status", "Right Gear");
			if( phase == 0 )
				executePhase(2000, -1, 0 );
			if( phase == 1 )
				executePhase(500, -1, -1);
			if( phase == 2 )
				executePhase(1000, -1, 0);
			if( phase == 4 )
				executePhase(0, 0, 0);
		break;
		
		case HighGoal:
			SmartDashboard.putString("Auto Status", "Right Boiler");
			if( phase == 0 )
				executePhase(300, -1, 1);
			if( phase == 1 )
				executePhase(300, -1, -1);
			if( phase == 2 )
				executePhase(4000, -1, 0 );
			if( phase == 3 )
				executePhase(0, 0, 0);
			target.execute();
		break;
		
		}
		break;
		
	case Center:
		switch (taskChooser.getSelected())
		{
		case BaseLine:
			SmartDashboard.putString("Auto Status", "Center Base Line");
		if( phase == 0 )
			executePhase(500, -1, 1 );
		if( phase == 1 )
			executePhase(5000, -1, 0 );
		if( phase == 2 )
			executePhase(0, 0, 0);
		break;
		
		case Gear:
			SmartDashboard.putString("Auto Status", "Center Gear");
			if( phase == 0 )
				executePhase(2000, -1, 0 );
			if( phase == 1 )
				executePhase(500, -1, 1);
		break;
		
		case HighGoal:
			SmartDashboard.putString("Auto Status", "Center Boiler");
			if( phase == 0 )
				executePhase(500, -1, 1);
			if( phase == 1 )
				executePhase(500, -1, -1);
			if( phase == 2 )
				executePhase(4000, -1, 0 );
			if( phase == 3 )
				executePhase(0, 0, 0);
			target.execute();
		break;
		
		}
		break;
		
	}
	}	
	}
