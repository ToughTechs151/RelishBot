/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
package org.usfirst.frc151.DeepSpace2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PixyCamera extends Subsystem {
  String name;
  I2C pixy;
  PixyPacket values;
  Port port = Port.kOnboard;
  PixyPacket[] packets;
  String print;
  
  public Pixy(String id, I2C argPixy, PixyPacket[] argPixyPacket, PixyException argPixyException, PixyPacket argValues) {
    pixy = argPixy;
    packets = argPixyPacket;
    pExc = argPixyException;
    values = argValues;
    name = "Pixy_" + id;
  }

  public int cvt(byte upper, byte lower) {
    return (((int) upper & 0xff) << 8) | ((int)lower & 0xff);
  }

  public PixyPacket readPacket(int Signature) throws PixyException {
    int Checksum;
    int Sig;
    byte[] rawData  = new byte[32];
    try {
      pixy.readOnly(rawData, 32);
    } catch (RuntimeException e) {
      SmartDashboard.putString(name + "Status", e.toString());
      System.out.println(name + " " + e);
    }
    if (rawData.length < 32) {
      SmartDashboard.putString(name + "Status", "raw data length " + rawData.length);
      System.out.println("byte array length is broken length=" + rawData.length);
      return null;
    }
    for (int i = 0; i<= 16; i++) {
      int syncWord = cvt(rawData[i + 1], rawData [1 + 0];
      
      if (syncWord == 0xaa55) {
        
        syncWord = cvt(rawData[i + 3], rawData[i +2]);
        
        if (syncWord != 0xaa55) {
          i -=2;
        }

        Checksum = cvt(rawData[i + 5], rawData[i + 4]);
        Sig = cvt(rawData[i +7], rawData[i + 6]);

        if (Sig <= 0 || Sig > packets.length) {
          break;
        }

        packets[Sig - 1] = new PixyPacket();
        packets[Sig - 1].X = cvt(rawData[i + 9], rawData[i + 8]);
        packets[Sig - 1].Y = cvt(rawData[i + 11], rawData[i + 10]);
        packets[Sig - 1].Width = cvt(rawData[i + 13], rawData[i + 12]);
        packets[Sig - 1].Height = cvt(rawData[i + 15], rawData[i + 14]);

        if (Check !=)
      }
    }
  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
*/