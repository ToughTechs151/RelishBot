/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class GearTarget {
	PixyPacket block1, block2;
	double avgWidth;
	double avgHeight;
	double avgArea;
	double avgX;
	double angle;
	double distance;
	
	
	final double TARGET_X = 160;
	
	final double DEGREES_PER_PIXEL = 75.0 / 320.0;
	
	public GearTarget(PixyPacket p1, PixyPacket p2) {
		block1 = p1;
		block2 = p2;
		if (block1 == null && block2 != null)
			block1 = block2;
		else if (block1 != null && block2 == null)
			block2 = block1;
		doMath();
	}
	
	private void doMath(){
		avgWidth = (block1.Width + block2.Width) / 2;
		avgHeight = (block1.Height + block2.Height) / 2;
		avgArea = avgHeight * avgWidth;
		avgX = (block1.X + block2.X) / 2;
		angle = (avgX - TARGET_X) * DEGREES_PER_PIXEL;
		distance = 106.83987509669 * Math.pow(.96060468112129, avgHeight);
	}
	

	public double distance() {
		return distance;
	}
	
	public double angle() {
		return angle; 
	}
	public String toString() {
		return "" +
	"avgWidth: " + avgWidth + 
	" avgHeight: " + avgHeight +
	" avgArea: " + avgArea +
	" avgX: " + avgX +
	" dpp: " + DEGREES_PER_PIXEL +
	" angle: " + angle +
	" distance: " + distance;
	}
}