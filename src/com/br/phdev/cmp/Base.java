package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Base extends Componente {

    private boolean elevandobase;
    private Femur femur;

    public Base(PCA9685 modulo, int servoCanal, int posInicial, Femur femur) {
        super(modulo, servoCanal, posInicial);
        super.posInicial = posInicial;
        this.femur = femur;
    }

    public void levantarMetade() {
        boolean invr;
        int valor1 = super.posInicial;
        int valor2 = super.getServo().getMovMax();
        int valor3 = (valor1 > valor2 ? valor1 - valor2 : valor2 - valor1) / 2;
        invr = super.getServo().getMovMax() > super.getServo().getMovMin();
        if (!invr)
            super.getServo().setPosicao(super.posInicial - valor3);
        else
            super.getServo().setPosicao(super.posInicial + valor3);                
    }

    public void abaixarMetade() {
        boolean invr;
        invr = (super.getServo().getMovMax() > super.getServo().getMovMin());
        int valor1 = super.posInicial;
        int valor2;
        if (!invr)            
            valor2 = super.getServo().getMovMin();
        else
            valor2 = super.getServo().getMovMax();
        
        int valor3 = (valor1 > valor2 ? valor1 - valor2 : valor2 - valor1) / 2;                            
        
        if (!invr)
            super.getServo().setPosicao(super.posInicial + valor3);
        else
            super.getServo().setPosicao(super.posInicial - valor3);

    }
    
    public boolean estaElevandobase(){
        return elevandobase;
    }
    
    public void setElevandobase(boolean elevandobase){
        this.elevandobase = elevandobase;
    }
    
    public void elevarBase(){        
        boolean invr = (femur.getServo().getMovMax() > femur.getServo().getMovMin());        
        int pos = femur.getServo().getPosicao();                        
        if (invr){
            if (pos - 1 == femur.getServo().getMovMin()){
                elevandobase = false;
                return;
            }            
            femur.getServo().setPosicao(pos - 1);
        }
        else{
            if (pos + 1 == femur.getServo().getMovMin()){
                elevandobase = false;
                return;
            }
            femur.getServo().setPosicao(pos + 1);
        }
    }
    
    

    public Femur getFemur() {
        return femur;
    }

    public void setFemur(Femur femur) {
        this.femur = femur;
    }

    @Override
    public Servo getServo() {
        return super.servo;
    }

}
