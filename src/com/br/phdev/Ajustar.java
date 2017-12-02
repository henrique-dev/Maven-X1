package com.br.phdev;

import com.br.phdev.driver.PCA9685;	

import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;
import java.util.Scanner;

public class Ajustar {

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

		while(true){
			System.out.println("AJUSTANDO SERVO\n\n");
			System.out.println("Informe o canal do servo: ");
			servoChannel = entrada.nextInt();
			System.out.println("Informe a posicao para o servo: ");
			servoPos = entrada.nextInt();
			System.out.println("Movendo para " + servoPos);
			servoBoard.setPWM(servoChannel, 0, servoPos);
			waitFor(500);
			//servoBoard.setPWM(servoChannel, 0, 0);
		}

		// 375 servo middle

	}


}
