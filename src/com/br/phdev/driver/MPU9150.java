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

/**
 *
 * @author PH
 */
public class MPU9150 {
    
    private I2CBus bus;
    private I2CDevice mpuDriver;
    private byte[] accelData, gyroData;
    
    public MPU9150() throws I2CFactory.UnsupportedBusNumberException{
        
        try{
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
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        
    }
    
}
