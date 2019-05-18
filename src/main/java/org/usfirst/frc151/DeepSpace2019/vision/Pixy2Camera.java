/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc151.DeepSpace2019.vision;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Random;

/**
 * Add your docs here.
 */
public class Pixy2Camera {
    private I2C pixy2 = null;
    public final static int PIXY_BUFFERSIZE = 0x104;
    public final static int PIXY_SEND_HEADER_SIZE = 4;
    public final static int PIXY_MAX_PROGNAME = 33;
    public final static int PIXY_DEFAULT_ARGVAL = 0x80000000;
    public final static int PIXY_CHECKSUM_SYNC = 0xc1af;
    public final static int PIXY_NO_CHECKSUM_SYNC = 0xc1ae;
    public final static int PIXY_GETVERSION_TYPE = 0x0e;
    public final static int PIXY_GETBLOCKS_TYPE = 0x20;
    public final static int PIXY_RESPONSE_BLOCKS = 0x21;
    public final static int RESPONSE_BLOCK_LENGTH = 14;

    // Color Connected Component signature map
    public final static byte CCC_SIG1 = 0x01;
    public final static byte CCC_SIG2 = 0x02;
    public final static byte CCC_SIG3 = 0x04;
    public final static byte CCC_SIG4 = 0x08;
    public final static byte CCC_SIG5 = 0x10;
    public final static byte CCC_SIG6 = 0x20;
    public final static byte CCC_SIG7 = 0x40;

    public static int numBlocksSeen = 0;
    /**
     * Combines two bytes into an integer
     * 
     * @param upper
     * @param lower
     * @return
     */
    private int bytesToInt(byte upper, byte lower) {
        return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
    }

    /**
     * Creates a new array that is equal to a section of the original array. Like
     * the substring method
     * 
     * @param arr   The original array
     * @param start The index to start copying from
     * @param end   The index to stop copying at
     * @return An array filled with values from arr[start] to arr[end-1]
     */
    private byte[] arraySubsection(byte[] arr, int start, int end) {
        byte[] res = new byte[end - start];
        for (int i = start; i < end; i++) {
            res[i - start] = arr[i];
        }
        return res;
    }

    /**
     * Pixy2 Camera class
     * 
     * Uses the IC2 class to communicates to the Pixy2. Follow the Pixy2 API:
     * https://docs.pixycam.com/wiki/doku.php?id=wiki:v2:protocol_reference
     * 
     */
    public Pixy2Camera(Port port, int deviceAddress) {
        pixy2 = new I2C(port, deviceAddress);
    }

    /**
     * Sends the version request packet to Pixy and asks for the version response
     * packet. The response packet contains the Pixy2 version information. This
     * method uses the I2C class transaction method to send and receive packets.
     * 
     * @return True if the transaction was successful; false if it was aborted.
     */
    public boolean getVersion() {
        byte requestPacket[] = new byte[PIXY_BUFFERSIZE];
        requestPacket[0] = (byte) (PIXY_NO_CHECKSUM_SYNC & 0xFF);
        requestPacket[1] = (byte) ((PIXY_NO_CHECKSUM_SYNC >> 8) & 0xFF);
        requestPacket[2] = (byte) PIXY_GETVERSION_TYPE;
        requestPacket[3] = (byte) 0x0;

        byte[] responsePacket = new byte[PIXY_BUFFERSIZE];

        if (pixy2.transaction(requestPacket, 0x4, responsePacket, 0x20) == false) {
            SmartDashboard.putRaw("Pixy Versions", responsePacket);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @return
     */
    public boolean getResolution() {
        byte[] requestPacket = new byte[PIXY_BUFFERSIZE];
        requestPacket[0] = (byte) (PIXY_NO_CHECKSUM_SYNC & 0xFF);
        requestPacket[1] = (byte) ((PIXY_NO_CHECKSUM_SYNC >> 8) & 0xFF);
        requestPacket[2] = (byte) 0x0C;
        requestPacket[3] = (byte) 0x01;
        requestPacket[4] = (byte) 0x00; // Type (unused - reserved for future versions). 0-255

        byte[] responsePacket = new byte[PIXY_BUFFERSIZE];

        if (pixy2.transaction(requestPacket, 0x05, responsePacket, 0x20)) {
            SmartDashboard.putRaw("Pixy Resolution", responsePacket);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sends the set lamp request packet to the pixy. The lamps include two white
     * LEDs in the top corners of the pixy, and an RGB LED in the bottom center of
     * the pixy. This method uses the I2C class transaction method to send and
     * receive packets.
     * 
     * @param whiteLEDstate 1 to turn on the white LEDs, 0 to turn them off
     * @param rgbLEDstate   1 to turn on all channels of the RGB LED, 0 to turn it
     *                      off
     * @return True if the transaction was successful; false if it was aborted.
     */
    public boolean setLamps(byte whiteLEDstate, byte rgbLEDstate) {
        byte[] requestPacket = new byte[PIXY_BUFFERSIZE];
        requestPacket[0] = (byte) (PIXY_NO_CHECKSUM_SYNC & 0xFF);
        requestPacket[1] = (byte) ((PIXY_NO_CHECKSUM_SYNC >> 8) & 0xFF);
        requestPacket[2] = (byte) 0x16;
        requestPacket[3] = (byte) 0x2;
        requestPacket[4] = (byte) whiteLEDstate;
        requestPacket[5] = (byte) rgbLEDstate;

        byte[] responsePacket = new byte[PIXY_BUFFERSIZE];

        if (pixy2.transaction(requestPacket, 0x6, responsePacket, responsePacket.length) == false) {
            SmartDashboard.putRaw("Pixy Lamp", responsePacket);
            return true;
        } else {
            SmartDashboard.putString("result", "Lamps won't turn off!!");
            return false;
        }
    }

    Random random = new Random();

    /**
     * Sends the GetBlocks command to Pixy2 and waits for the GetBlocks response.
     * This method uses the I2C class transaction method to send and receive
     * packets.
     * 
     * @param pixyPacketArray The array of PixyPackets to be populated
     * @param signature       The signature of the block you're looking for (set in
     *                        Pixymon)
     * @param numBlocks       The number of blocks to return
     * @return True if the transaction was successful; false if it was aborted
     */
    public boolean getBlocks(PixyPacket[] pixyPacketArray, byte signature, int numBlocks) {
        byte requestPacket[] = new byte[PIXY_BUFFERSIZE];
        requestPacket[0] = (byte) (PIXY_NO_CHECKSUM_SYNC & 0xff);
        requestPacket[1] = (byte) ((PIXY_NO_CHECKSUM_SYNC >> 8) & 0xff);
        requestPacket[2] = (byte) PIXY_GETBLOCKS_TYPE;
        requestPacket[3] = (byte) 0x02; // length of payload
        requestPacket[4] = (byte) signature;
        requestPacket[5] = (byte) numBlocks; // max blocks to return

        PixyPacket pixyPacket = null;
        byte responsePacket[] = new byte[PIXY_BUFFERSIZE];

        boolean successful = false;

        // I don't like this method of doing it. I don't think it will work if you're
        // trying to get more than one block. Gotta come back to this tomorrow...
        if (pixy2.transaction(requestPacket, 0x6, responsePacket, 0x40) == false) {
            SmartDashboard.putRaw("PIXY CCC Blocks", responsePacket);
            for (int i = 0; i < numBlocks; i++) {
                pixyPacketArray[i] = new PixyPacket();
                pixyPacket = pixyPacketArray[i];
                if (responsePacket[2] == PIXY_RESPONSE_BLOCKS) {
                    pixyPacket.setSignature(bytesToInt(responsePacket[7 + i * RESPONSE_BLOCK_LENGTH],
                            responsePacket[6 + i * RESPONSE_BLOCK_LENGTH]));
                    pixyPacket.setX(bytesToInt(responsePacket[9 + RESPONSE_BLOCK_LENGTH * i],
                            responsePacket[8 + RESPONSE_BLOCK_LENGTH * i]));
                    pixyPacket.setY(bytesToInt(responsePacket[11 + RESPONSE_BLOCK_LENGTH * i],
                            responsePacket[10 + RESPONSE_BLOCK_LENGTH * i]));
                    pixyPacket.setWidth(bytesToInt(responsePacket[13 + RESPONSE_BLOCK_LENGTH * i],
                            responsePacket[12 + RESPONSE_BLOCK_LENGTH * i]));
                    pixyPacket.setHeight(bytesToInt(responsePacket[15 + RESPONSE_BLOCK_LENGTH * i],
                            responsePacket[14 + RESPONSE_BLOCK_LENGTH * i]));

                    if(i == 0 && pixyPacket.getX() < 500) {
                        numBlocksSeen = 1;
                    } else if(i == 0 && pixyPacket.getX() > 500) {
                        numBlocksSeen = 0;
                    } else if(i == 1 && pixyPacket.getX() < 500) {
                        numBlocksSeen = 2;
                    }

                    double numBlocksSeenNoise = numBlocksSeen + random.nextDouble() * 0.01;
                    SmartDashboard.putNumber("Number of Blocks Seen", numBlocksSeenNoise);
                    String key = "pixypacket " + i;
                    SmartDashboard.putString(key, pixyPacket.toString());
                    successful = true;
                }
            }
        } else {
            System.out.println("no version");
        }
        return successful;
    }

    PixyPacket[] arr = new PixyPacket[PIXY_BUFFERSIZE];

    /**
     * Sends the GetBlocks command and calculates the center coordinates as well as width of the bounding
     * box of two rectangles.
     * 
     * @param result The array to fill. When method runs, arr[0] = x, arr[1] = y, and arr[2] = width
     * @param signature The signature of blocks to return
     * @param numBlocks The number of blocks to return
     * @return True if successful, false otherwise. "Success" is only if the camera sees two blocks.
     */
    public boolean getCenterCoordinates(double[] result, byte signature, int numBlocks) {
        boolean successful = false;

        if (getBlocks(arr, signature, numBlocks)) {
            successful = true;
            int smallerXIndex = 0;
            int biggerXIndex = 0;
            int biggerXVal = 0;
            for (int i = 0; i < numBlocks; i++) {
                try {
                    if (arr[i].getX() > biggerXVal) {
                        biggerXVal = arr[i].getX();
                        biggerXIndex = i;
                    }
                } catch (NullPointerException e) {
                    System.out.println(e);
                }
            }

            smallerXIndex = Math.abs(biggerXIndex - 1);

            int topLeftX, topLeftY, bottomRightX, bottomRightY;

            double centerX, centerY;

            topLeftX = arr[smallerXIndex].getX() - arr[smallerXIndex].getWidth() / 2;
            topLeftY = arr[smallerXIndex].getY() - arr[smallerXIndex].getHeight() / 2;
            bottomRightX = arr[biggerXIndex].getX() + arr[biggerXIndex].getWidth() / 2;
            bottomRightY = arr[biggerXIndex].getY() + arr[biggerXIndex].getHeight() / 2;
            centerX = (topLeftX + bottomRightX) / 2;
            centerY = (topLeftY + bottomRightY) / 2;
            result[0] = centerX;
            result[1] = centerY;
            result[2] = bottomRightX - topLeftX;
        } else {
            // System.out.println("aborted");
        }
        return successful;
    }
}
