package com.br.phdev;

import com.br.phdev.cmp.Base;
import com.br.phdev.cmp.Femur;
import com.br.phdev.cmp.Perna;
import com.br.phdev.cmp.Tarso;
import com.br.phdev.driver.PCA9685;

import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;
import java.util.Scanner;

public class Ajustar {    
    
    private PCA9685 modulo;

    public Ajustar() throws I2CFactory.UnsupportedBusNumberException {

        modulo = new PCA9685();
        modulo.setPWMFreq(60);

        for (int i = 0; i < 16; i++) {
            modulo.setPWM(i, 0, 0);
            delay(200);
        }
        
    }
    
    public void setServoPos(int ch, int pos){
        
    }        

    private void delay(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }        

    public static void main(String[] args) throws I2CFactory.UnsupportedBusNumberException {

        Scanner entrada = new Scanner(System.in);

        Ajustar aj = new Ajustar();

        int servoChannel = 0;
        int servoPos = 0;

        

        while (true) {
            System.out.println("AJUSTANDO SERVO\n\n");
            System.out.println("Informe o canal do servo: ");
            servoChannel = entrada.nextInt();
            System.out.println("Informe a posicao para o servo: ");
            servoPos = entrada.nextInt();
            System.out.println("Movendo para " + servoPos);
            if (servoPos >= 150 && servoPos <= 600)
                aj.setServoPos(servoChannel, servoPos);                               
        }

        // 375 servo middle
    }

}
