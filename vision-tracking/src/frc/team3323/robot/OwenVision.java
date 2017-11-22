package org.usfirst.frc.team3323.robot;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * A Class for the capturing, processing and feeding images. Also controls the robot drivetrain to Aim.
 */
public class OwenVision
{
	private UsbCamera targetingCamera =  CameraServer.getInstance().startAutomaticCapture();
	private CvSink imageSink = CameraServer.getInstance().getVideo();
	private CvSource outputStream = CameraServer.getInstance().putVideo("Tracked", 640, 480);
	private Mat input = new Mat();
	private Mat colorCorrected = new Mat();
	private Mat masked = new Mat();
	private Scalar maskLowerValue = new Scalar(150/2, 100, 20); 
	private Scalar maskUpperValue = new Scalar(260/2, 255, 255);
	private Mat hierarchy= new Mat(); 
	private int border = 5;
	private Scalar blue = new Scalar(255, 0, 0);
	private Scalar green = new Scalar(0, 255, 0);
	private Scalar red = new Scalar(0, 0, 255);
	private Scalar goalColor = blue;
	private Mat output = new Mat();
	private Rect goalRect = new Rect(100,100,300,300);
	private Rect trackedRect;
	private Double goalRectTR_x = new Double(goalRect.tl().x + goalRect.width);
	private Double goalRectBL_x = new Double(goalRect.br().x - goalRect.width);
	private Double goalRectTL_x = new Double(goalRect.tl().x);
	private Double goalRectBR_x = new Double(goalRect.br().x);
	
	private Double trackedRectTR_x;
	private Double trackedRectBL_x;
	private Double trackedRectTL_x;
	private Double trackedRectBR_x;
	private boolean leftLineMatch;
	private boolean rightLineMatch;
	private RobotDrive driveTrain;
	private boolean correctDistance;
	private int tol;
	private double distanceSpeed;
	private double  turnSpeed;
	
	
	/**
	 * Giving the tracking code a RobotDrive and some important numbers. Aids in tuning.
	 * @param tol The accuracy in pixels
	 * @param distanceSpeed  The speed when correcting distance
	 * @param turnSpeed the speed when correcting shooter direction
	 */
	public OwenVision(RobotDrive drivetrain,int tol,double distanceSpeed, double  turnSpeed)
	{
		this.driveTrain = drivetrain;
		this.tol = tol;
		this.distanceSpeed = distanceSpeed;
		this.turnSpeed = turnSpeed;
		targetingCamera.setResolution(640, 480);
	}
	
	public void track()
	{
		imageSink.grabFrame(input);	
		input.copyTo(output);
		Imgproc.cvtColor(input, colorCorrected, Imgproc.COLOR_BGR2HSV);
		Core.inRange(colorCorrected, maskLowerValue, maskUpperValue, masked);
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(masked, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
		for (MatOfPoint contour:contours)	
		{
			Rect boundingRect = Imgproc.boundingRect(contour) ;
			Rect trackedRect = new Rect( boundingRect.x- border, boundingRect.y- border, boundingRect.width + (border * 2), boundingRect.height + (border * 2));
			this.trackedRect = trackedRect;
			trackedRectTR_x = trackedRect.tl().x + trackedRect.width;
			trackedRectBL_x = trackedRect.br().x - trackedRect.width;
			trackedRectTL_x = trackedRect.tl().x;
			trackedRectBR_x = trackedRect.br().x;
			if (boundingRect.width > 100 && boundingRect.height > 100)
				Imgproc.rectangle(output, trackedRect.br(), trackedRect.tl(), red, border);
				
		}
		Imgproc.rectangle(output, goalRect.tl(),goalRect.br(), goalColor, border);
		outputStream.putFrame(output);
		SmartDashboard.putBoolean("Distance Check", correctDistance);
	}

	public void checkForMatch() 
	{
		
		if (((trackedRectTL_x+trackedRectBL_x)/2)+tol >= ((goalRectTL_x+goalRectBL_x)/2)-tol)
			leftLineMatch=true;
		else
			leftLineMatch=false;
		
		if (((trackedRectTR_x+trackedRectBR_x)/2)+tol >= ((goalRectTR_x+goalRectBR_x)/2)-tol)
			rightLineMatch=true;
		else
			rightLineMatch=false;
		if (rightLineMatch==true&&leftLineMatch==true)
			goalColor=green;
		else
			goalColor=blue;
			distance();
	}
	
	public void distance()
	{
		if (trackedRect.area()+100==goalRect.area()||trackedRect.area()-100==goalRect.area())
			correctDistance=true;
		else if (trackedRect.area()+100>goalRect.area()||trackedRect.area()-100>goalRect.area())
			driveTrain.drive(distanceSpeed,0);
		else if (trackedRect.area()+100<goalRect.area()||trackedRect.area()-100<goalRect.area())
			driveTrain.drive(-distanceSpeed,0);
		
		if (correctDistance=true)
			turn();
	}

	private void turn()
	{
		if (rightLineMatch==true&&leftLineMatch==true)
			SmartDashboard.putString("Mode", "Pew Pew");
		else
			{
			if (((trackedRectTR_x+trackedRectBR_x)/2)+tol > ((goalRectTR_x+goalRectBR_x)/2)-tol||((trackedRectTR_x+trackedRectBR_x)/2)+tol < ((goalRectTR_x+goalRectBR_x)/2)-tol);
			
			driveTrain.drive(turnSpeed, 1);
			if (((trackedRectTL_x+trackedRectBL_x)/2)+tol > ((goalRectTL_x+goalRectBL_x)/2)-tol||((trackedRectTL_x+trackedRectBL_x)/2)+tol < ((goalRectTL_x+goalRectBL_x)/2)-tol);
		 	driveTrain.drive(turnSpeed, 1);
			}
	}
	
}
