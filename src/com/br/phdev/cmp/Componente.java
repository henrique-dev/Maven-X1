package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Componente implements Membro{
        
    protected Servo servo;  
    protected int posInicial;
    
    public Componente(PCA9685 modulo, int servoCanal, int posInicial){
        this.servo = new Servo(modulo, servoCanal, posInicial);       
    }
    
    public void resetarPosicao(){
        this.servo.setPosicao(posInicial);
    }
    
    protected void setServo(Servo servo){
        this.servo = servo;
    }
    
    protected Servo getServo(){
        return this.servo;
    }
    
    public void mover(){
        this.servo.mover();
    }
    
    public void mover(int posicao){
        this.servo.mover(posicao);
    }
    
}
