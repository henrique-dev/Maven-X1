package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Base extends Componente {        

    public Base(PCA9685 modulo, int servoCanal, int posInicial) {
        super(modulo, servoCanal, posInicial);
        super.posInicial = posInicial;
    }

}
