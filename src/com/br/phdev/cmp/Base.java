package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Base extends Componente {    

    private Femur femur;

    public Base(PCA9685 modulo, int servoCanal, int posInicial, Femur femur) {
        super(modulo, servoCanal, posInicial);
        super.posInicial = posInicial;
        this.femur = femur;
    }

    public Femur getFemur() {
        return femur;
    }

    public void setFemur(Femur femur) {
        this.femur = femur;
    }
    
    

}
