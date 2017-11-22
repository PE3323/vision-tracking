package org.usfirst.frc.team3323.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * When executed, this command will call the methods in the OwenVision
 * to auto target the High Efficency Goal.
 */
public class AutoTarget extends Command 
{
	private OwenVision vision;

	public AutoTarget(OwenVision vision)
	 {
		this.vision = vision;
	   
	 }

    protected void execute() 
    {
    	vision.checkForMatch();
    	SmartDashboard.putString("Auto Target Command Executed", "True");
    }

    protected boolean isFinished()
    {
        return false;
    }
}
