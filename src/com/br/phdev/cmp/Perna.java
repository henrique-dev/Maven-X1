package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Perna implements Membro{

	private int id;
	private Servo servo;
	
	public Perna(int id, PCA9685 modulo, int servoChannel, int posInicial){
		this.servo = new Servo(modulo, servoChannel, posInicial);
		this.id = id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setServo(Servo servo){
		this.servo = servo;
	}
	
	public Servo getServo(){
		return this.servo;
	}

}
