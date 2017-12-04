package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Perna implements Membro {

    private Tarso tarso;
    private Femur femur;
    private Base base;
    private PernaThread thread;

    public Perna(Tarso tarso, Femur femur, Base base) {
        this.tarso = tarso;
        this.femur = femur;
        this.base = base;        
    }

    public Tarso getTarso() {
        return tarso;
    }

    public void setTarso(Tarso tarso) {
        this.tarso = tarso;
    }

    public Femur getFemur() {
        return femur;
    }

    public void setFemur(Femur femur) {
        this.femur = femur;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }
    
    public void iniciar(){
        if (thread != null){
            thread.start();
        }
        else{
            thread = new PernaThread();
            thread.start();
        }
    }
    
    public void parar(){
        thread = null;
    }
    
    public class PernaThread extends Thread{                
        
        @Override
        public void run(){
            while (true){
                tarso.mover();
                femur.mover();
                base.mover();
            }
        }
        
    }

}
