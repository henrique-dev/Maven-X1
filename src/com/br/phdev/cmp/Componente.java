package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Componente implements Membro{
        
    private Servo servo;   
    
    public Componente(PCA9685 modulo, int servoCanal, int posInicial){
        this.servo = new Servo(modulo, servoCanal, posInicial);       
    }
    
    public void setServo(Servo servo){
        this.servo = servo;
    }
    
    public Servo getServo(){
        return this.servo;
    }
    
}
