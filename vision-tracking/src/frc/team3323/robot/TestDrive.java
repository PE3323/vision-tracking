package org.usfirst.frc.team3323.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class TestDrive extends Command 
{
private Chassis drivetrain;
	
	public TestDrive( Chassis drivetrain )
	{
		requires(drivetrain);
		this.drivetrain = drivetrain;
	}

    protected void execute() 
    {
    	drivetrain.drivingTest();
    	SmartDashboard.putString("Test", "Working");
    }

    protected boolean isFinished()
    {
        return false;
    }
}
