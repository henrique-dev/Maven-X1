package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Femur extends Componente{
    public Femur(PCA9685 modulo, int servoCanal, int posInicial){
        super(modulo, servoCanal, posInicial);
    }
}
