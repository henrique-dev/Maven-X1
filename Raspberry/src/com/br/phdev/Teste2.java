/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev;

import com.br.phdev.driver.PCA9685;
import com.pi4j.io.i2c.I2CFactory;
import java.util.Scanner;

/**
 *
 * @author PH
 */
public class Teste2 {
    
    private static void waitFor(long howMuch) {
        try {
            Thread.sleep(howMuch);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public static void main(String[] args) throws I2CFactory.UnsupportedBusNumberException {

        Scanner entrada = new Scanner(System.in);

        PCA9685 servoBoard = new PCA9685();
        servoBoard.setPWMFreq(60);

        int servoMin = 150;
        int servoMax = 600;

        int servoChannel = 0;
        int servoPos = 0;

        for (int i = 0; i < 16; i++) {
            servoBoard.setPWM(i, 0, 0);
            waitFor(200);
        }

        while (true) {
            System.out.println("TESTE COM 4 SERVOS MEXENDO SIMULTANEAMENTE");
            servoBoard.setPWM(0, 0, 150);
            servoBoard.setPWM(1, 0, 150);
            servoBoard.setPWM(2, 0, 150);
            servoBoard.setPWM(3, 0, 150);
            waitFor(500);
            servoBoard.setPWM(0, 0, 600);
            servoBoard.setPWM(1, 0, 600);
            servoBoard.setPWM(2, 0, 600);
            servoBoard.setPWM(3, 0, 600);
            waitFor(500);
        }

        // 375 servo middle
    }
    
}
