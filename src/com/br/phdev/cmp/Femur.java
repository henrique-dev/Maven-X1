package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Femur extends Perna{
    public Femur(int id, PCA9685 modulo, int servoCanal, int posInicial){
        super(id, modulo, servoCanal, posInicial);
    }
}
