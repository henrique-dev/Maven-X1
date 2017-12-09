package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Perna implements Membro {

    private boolean elevandobase = false;
    private boolean descendobase = false;
    
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

    public boolean estaElevandobase() {
        return elevandobase;
    }

    public void setElevandobase(boolean elevandobase) {
        this.elevandobase = elevandobase;
        descendobase = false;
    }
    
    public boolean estaDescendobase(){
        return descendobase;
    }
    
    public void setDescendobase(boolean descendobase){
        this.descendobase = descendobase;
        elevandobase = false;
    }

    public void elevarBase() {
        boolean invr = (base.getFemur().getServo().getMovMax() > base.getFemur().getServo().getMovMin());
        boolean invrt = (base.getFemur().getTarso().getServo().getMovMax() > base.getFemur().getTarso().getServo().getMovMin());
        int posb = base.getFemur().getServo().getPosicao();        
        int post = base.getFemur().getTarso().getServo().getPosicao();
        
        if (invr) {
            if (posb - 1 <= base.getFemur().getServo().getMovMin()) {
                elevandobase = false;
                return;
            }            
            base.getFemur().getServo().setPosicao(posb - 1);            
        } else {
            if (posb + 1 >= base.getFemur().getServo().getMovMin()) {
                elevandobase = false;
                return;
            }
            base.getFemur().getServo().setPosicao(posb + 1);            
        }
        if (invrt){
            if (post + 1 >= base.getFemur().getTarso().getServo().getMovMax()) {
                elevandobase = false;
                return;
            }            
            base.getFemur().getTarso().getServo().setPosicao(post + 1);            
        } else {
            if (post - 1 <= base.getFemur().getTarso().getServo().getMovMax()) {
                elevandobase = false;
                return;
            }
            base.getFemur().getTarso().getServo().setPosicao(post - 1);            
        }
    }
    
    public void descerBase(){
        boolean invr = (base.getFemur().getServo().getMovMax() > base.getFemur().getServo().getMovMin());
        int posb = base.getFemur().getServo().getPosicao();        
        
        if (invr) {
            if (posb + 1 >= base.getFemur().getServo().getMovMax()) {
                descendobase = false;
                return;
            }
            base.getFemur().getServo().setPosicao(posb + 1);
            base.getFemur().getTarso().abaixar(movt);
        } else {
            if (posb - 1 <= base.getFemur().getServo().getMovMax()) {
                descendobase = false;
                return;
            }
            base.getFemur().getServo().setPosicao(posb - 1);
            base.getFemur().getTarso().abaixar(movt);
        }
    }

}
