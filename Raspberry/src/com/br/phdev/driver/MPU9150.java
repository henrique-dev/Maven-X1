/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.driver;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PH
 */
public class MPU9150 {

    private I2CBus bus;
    private I2CDevice mpuDriver;
    private byte[] accelData, gyroData;

    public MPU9150() throws I2CFactory.UnsupportedBusNumberException {

        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);
            System.out.println("Conectado ao barramento OK");

            mpuDriver = bus.getDevice(0x68);
            System.out.println("Conectado ao dispositivo OK");

            // iniciando sensor
            mpuDriver.write(0x6B, (byte) 0b00000000);
            mpuDriver.write(0x6C, (byte) 0b00000000);
            System.out.println("Confirando dispositivo OK");

            mpuDriver.write(0x1B, (byte) 0b11100000);
            mpuDriver.write(0x1C, (byte) 0b00000001);
            System.out.println("Confirando sensores OK");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Double> readAccel() {

        try {
            accelData = new byte[6];
            //gyroData = new byte[6];

            //You can read one registry at a time,
            //or you can read multiple consecutive ones, 
            //in our case we are reading 6 consecutive registries
            //from 0x3B, meaning we are reading all the 
            //accelerometer measurements
            int r = mpuDriver.read(0x3B, accelData, 0, 6);
            if (r != 6) {
                //System.out.println("Error reading accel data, < 6 bytes");
            }
            //Convert the values to integers, using the
            //helper method asInt
            int accelX = ((int) (accelData[0] / 4));
            int accelY = ((int) (accelData[2] / 4));
            int accelZ = ((int) (accelData[4] / 4));
            double Ax = Math.atan(accelX / Math.sqrt(accelY * accelY + accelZ * accelZ)) * 180 / Math.PI;
            double Ay = Math.atan(accelY / Math.sqrt(accelX * accelX + accelZ * accelZ)) * 180 / Math.PI;
            double Az = Math.atan(accelZ / Math.sqrt(accelX * accelX + accelY * accelY)) * 180 / Math.PI;
            List<Double> ret = new ArrayList<>();
            Ax = Math.round(Ax);
            Ay = Math.round(Ay);
            Az = Math.round(Az);
            ret.add(Ax);
            ret.add(Ay);
            ret.add(Az);
            return ret;
        } catch (Exception ex) {
            
        }
        return null;

    }

    public List<Double> readInfo() {

        try {
            gyroData = new byte[6];
            int r = mpuDriver.read(0x43, gyroData, 0, 6);
            accelData = new byte[6];
            r = mpuDriver.read(0x3B, accelData, 0, 6);

            List<Double> ret = new ArrayList<>();

            int ax = (((int) accelData[0] << 8) | accelData[1]);
            int ay = (((int) accelData[2] << 8) | accelData[3]);
            int az = (((int) accelData[4] << 8) | accelData[5]);

            int gx = (((int) gyroData[0] << 8) | gyroData[1]);
            int gy = (((int) gyroData[2] << 8) | gyroData[3]);
            int gz = (((int) gyroData[4] << 8) | gyroData[5]);

            // System.err.println(gyroData[0]);
            ret.add((double) ax);
            ret.add((double) ay);
            ret.add((double) az);
            ret.add((double) gx);
            ret.add((double) gy);
            ret.add((double) gz);
            return ret;
        } catch (IOException ex) {

        }
        return null;

    }

}
