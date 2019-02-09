/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package org.usfirst.frc151.DeepSpace2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PixyCamera {
	String name;
	PixyPacket values;
	I2C pixy;
	Port port = Port.kOnboard;
	PixyPacket[] packets;
	PixyException pExc;
	String print;

	public PixyCamera(String id, I2C argPixy, PixyPacket[] argPixyPacket, PixyException argPixyException,
			PixyPacket argValues) {
		pixy = argPixy;
		packets = argPixyPacket;
		pExc = argPixyException;
		values = argValues;
		name = "Pixy_" + id;
	}

	public int cvt(byte upper, byte lower) {
		return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
	}

	public PixyPacket readPacket(int Signature) throws PixyException {
		int Checksum;
		int Sig;
		byte[] rawData = new byte[32];
		try {
			pixy.readOnly(rawData, 32);
		} catch (RuntimeException e) {
			SmartDashboard.putString(name + "Status", e.toString());
			System.out.println(name + "  " + e);
		}
		if (rawData.length < 32) {
			SmartDashboard.putString(name + "Status", "raw data length " + rawData.length);
			System.out.println("byte array length is broken length=" + rawData.length);
			return null;
		}
		for (int i = 0; i <= 16; i++) {
			int syncWord = cvt(rawData[i + 1], rawData[i + 0]); 
			if (syncWord == 0xaa55) {
				syncWord = cvt(rawData[i + 3], rawData[i + 2]); 
				if (syncWord != 0xaa55) { 
					i -= 2;
				}
				Checksum = cvt(rawData[i + 5], rawData[i + 4]);
				Sig = cvt(rawData[i + 7], rawData[i + 6]);
				if (Sig <= 0 || Sig > packets.length) {
					break;
				}

				packets[Sig - 1] = new PixyPacket();
				packets[Sig - 1].X = cvt(rawData[i + 9], rawData[i + 8]);
				packets[Sig - 1].Y = cvt(rawData[i + 11], rawData[i + 10]);
				packets[Sig - 1].Width = cvt(rawData[i + 13], rawData[i + 12]);
				packets[Sig - 1].Height = cvt(rawData[i + 15], rawData[i + 14]);
				if (Checksum != Sig + packets[Sig - 1].X + packets[Sig - 1].Y + packets[Sig - 1].Width
						+ packets[Sig - 1].Height) {
					packets[Sig - 1] = null;
					throw pExc;
				}
				break;
			} else
				SmartDashboard.putNumber("syncword: ", syncWord);
		}
		PixyPacket pkt = packets[Signature - 1];
		packets[Signature - 1] = null;
		return pkt;
	}

	private byte[] readData(int len) {
		byte[] rawData = new byte[len];
		try {
			pixy.readOnly(rawData, len);
		} catch (RuntimeException e) {
			SmartDashboard.putString(name + "Status", e.toString());
			System.out.println(name + "  " + e);
		}
		if (rawData.length < len) {
			SmartDashboard.putString(name + "Status", "raw data length " + rawData.length);
			System.out.println("byte array length is broken length=" + rawData.length);
			return null;
		}
		return rawData;
	}

	private int readWord() {
		byte[] data = readData(2);
		if (data == null) {
			return 0;
		}
		return cvt(data[1], data[0]);
	}

	private PixyPacket readBlock(int checksum) {
		byte[] data = readData(12);
		if (data == null) {
			return null;
		}
		PixyPacket block = new PixyPacket();
		block.Signature = cvt(data[1], data[0]);
		if (block.Signature <= 0 || block.Signature > 7) {
			return null;
		}
		block.X = cvt(data[3], data[2]);
		block.Y = cvt(data[5], data[4]);
		block.Width = cvt(data[7], data[6]);
		block.Height = cvt(data[9], data[8]);

		int sum = block.Signature + block.X + block.Y + block.Width + block.Height;
		if (sum != checksum) {
			return null;
		}
		return block;
	}

	private final int MAX_SIGNATURES = 7;
	private final int OBJECT_SIZE = 14;
	private final int START_WORD = 0xaa55;
	private final int START_WORD_CC = 0xaa5;
	private final int START_WORD_X = 0x55aa;

	public boolean getStart() {
		int numBytesRead = 0;
		int lastWord = 0xffff;
		while (numBytesRead < (OBJECT_SIZE * MAX_SIGNATURES)) {
			int word = readWord();
			numBytesRead += 2;
			if (word == 0 && lastWord == 0) {
				return false;
			} else if (word == START_WORD && lastWord == START_WORD) {
				return true;
			} else if (word == START_WORD_CC && lastWord == START_WORD) {
				return true;
			} else if (word == START_WORD_X) {
				byte[] data = readData(1);
				numBytesRead += 1;
			}
			lastWord = word;
		}
		return false;
	}

	private boolean skipStart = false;

	public PixyPacket[] readBlocks() {
		int maxBlocks = 2;
		PixyPacket[] blocks = new PixyPacket[maxBlocks];

		if (!skipStart) {
			if (getStart() == false) {
				return null;
			}
		} else {
			skipStart = false;
		}
		for (int i = 0; i < maxBlocks; i++) {
			blocks[i] = null;
			int checksum = readWord();
			if (checksum == START_WORD) {
				skipStart = true;
				return blocks;
			} else if (checksum == START_WORD_CC) {
				skipStart = true;
				return blocks;
			} else if (checksum == 0) {
				return blocks;
			}
			blocks[i] = readBlock(checksum);
		}
		return blocks;
	}
}
