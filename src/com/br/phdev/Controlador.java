package com.br.phdev;

import com.br.phdev.cmp.Membro;
import com.br.phdev.cmp.Perna;
import com.br.phdev.driver.PCA9685;

import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

//import java.com.br.phdev.driver;

public class Controlador {

	private	final int PERNA_1 = 0;
	private	final int PERNA_2 = 1;
	private	final int PERNA_3 = 2;
	private	final int PERNA_4 = 3;

	public static void main(String[] args) throws I2CFactory.UnsupportedBusNumberException {

		PCA9685 modulo = new PCA9685();
		modulo.setPWMFreq(60);
		
		Membro[] pernas = new Perna[4];
		pernas[PERNA_1] = new Perna();
		pernas[PERNA_2] = new Perna();
		pernas[PERNA_3] = new Perna();
		pernas[PERNA_4] = new Perna();
		


	}


}
