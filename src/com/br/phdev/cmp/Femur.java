package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Femur extends Componente{
    
    private Tarso tarso;
    
    public Femur(PCA9685 modulo, int servoCanal, int posInicial, Tarso tarso){
        super(modulo, servoCanal, posInicial);
        super.posInicial = posInicial;
        this.tarso = tarso;
    }

    public Tarso getTarso() {
        return tarso;
    }

    public void setTarso(Tarso tarso) {
        this.tarso = tarso;
    }
    
    
}
