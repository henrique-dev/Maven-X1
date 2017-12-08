package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Base extends Componente {

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
        invr = super.getServo().getMovMax() > super.getServo().getMovMin();
        int valor1 = super.posInicial;
        int valor2;
        if (!invr)            
            valor2 = super.getServo().getMovMin();
        else
            valor2 = super.getServo().getMovMax();
        
        int valor3 = (valor1 > valor2 ? valor1 - valor2 : valor2 - valor1) / 2;     
        System.out.println(valor3);
        if (!invr)
            super.getServo().setPosicao(super.posInicial + valor3);
        else
            super.getServo().setPosicao(super.posInicial - valor3);
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
