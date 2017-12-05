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
        //this.servo.mover();
    }
    
    public void levantar(){        
        this.servo.setPosicao(this.servo.getMovMax());        
        //this.servo.mover();
    }
    
    public void levantar(int pos){
        if (servo.getMovMax() > servo.getMovMin())
            this.servo.setPosicao(servo.getPosicao() + pos);
        else
            this.servo.setPosicao(servo.getPosicao() - pos);
    }
    
    public void abaixar(){
        this.servo.setPosicao(this.servo.getMovMin());
        //this.servo.mover();
    }
    
    public void abaixar(int pos){
        if (servo.getMovMax() > servo.getMovMin())
            this.servo.setPosicao(servo.getPosicao() - pos);
        else
            this.servo.setPosicao(servo.getPosicao() + pos);
    }
    
    public void parar(){
        //this.servo.mover(0);
        this.servo.setPosicao(0);
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

    
    public void setLimites(int movMax, int movMin){
        this.servo.setMovMin(movMin);
        this.servo.setMovMax(movMax);
    }
    
}
