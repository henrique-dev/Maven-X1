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
        int valor1 = super.getServo().getPosicao();
        int valor2 = super.getServo().getMovMax();
        super.levantar((valor1 > valor2 ? valor1 - valor2 : valor2 - valor1) / 2);
    }

    public void abaixarMetade() {
        int valor1 = super.getServo().getPosicao();
        int valor2 = super.getServo().getMovMax();
        super.abaixar((valor1 > valor2 ? valor1 - valor2 : valor2 - valor1) / 2);
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
