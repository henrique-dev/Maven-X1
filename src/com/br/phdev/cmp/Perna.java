package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Perna implements Membro {            

    private Base base;
    //private PernaThread thread;

    public Perna(Base base) {
        this.base = base;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public void parar() {
        //thread = null;
    }              
    
}
