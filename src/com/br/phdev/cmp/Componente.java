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
        this.servo.mover();
    }
    
    public void levantar(){
        int lim1 = this.servo.getMovMax();
        int lim2 = this.servo.getMovMin();
        
        if (lim1 > lim2){
            
        }
    }
    
    public void levantar(int pos){
        
    }
    
    public void abaixar(){
        
    }
    
    public void abaixar(int pos){
        
    }
    
    public void parar(){
        this.servo.mover(0);
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
