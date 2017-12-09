package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Perna implements Membro {

    public static int ATE_O_LIMITE = -1;

    private boolean elevandobase = false;
    private boolean descendobase = false;
    private boolean restaurandobase = false;

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

    public boolean estaDescendobase() {
        return descendobase;
    }

    public void setDescendobase(boolean descendobase) {
        this.descendobase = descendobase;
        elevandobase = false;
    }

    public boolean estaRestaurandoBase() {
        return restaurandobase;
    }

    public void setRestaurandoBase(boolean restaurandobase) {
        this.restaurandobase = restaurandobase;
    }

    public void elevarBase(int arg) {                

        int limite;
        boolean invr = (base.getFemur().getServo().getMovMax() > base.getFemur().getServo().getMovMin());
        boolean invrt = (base.getFemur().getTarso().getServo().getMovMax() > base.getFemur().getTarso().getServo().getMovMin());
        int posb = base.getFemur().getServo().getPosicao();
        int post = base.getFemur().getTarso().getServo().getPosicao();
        
        if (arg < 0)
            limite = base.getFemur().getServo().getMovMin();
        else
            limite = arg;

        if (invr) {
            if (posb - 1 <= limite) {
                elevandobase = false;
                return;
            }
            int novaPos = posb - 1;
            base.getFemur().getServo().setPosicao(novaPos);
            base.getFemur().setPosInicial(novaPos);
        } else {
            if (posb + 1 >= limite) {
                elevandobase = false;
                return;
            }
            int novaPos = posb + 1;
            base.getFemur().getServo().setPosicao(novaPos);
            base.getFemur().setPosInicial(novaPos);
        }
        if (invrt) {
            if (post + 1 >= base.getFemur().getTarso().getServo().getMovMax()) {
                elevandobase = false;
                return;
            }
            int novaPos = post + 1;
            base.getFemur().getTarso().getServo().setPosicao(post + 1);
            base.getFemur().getTarso().setPosInicial(novaPos);
        } else {
            if (post - 1 <= base.getFemur().getTarso().getServo().getMovMax()) {
                elevandobase = false;
                return;
            }
            int novaPos = post - 1;
            base.getFemur().getTarso().getServo().setPosicao(novaPos);
            base.getFemur().getTarso().setPosInicial(novaPos);
        }

    }

    public void descerBase(int arg) {

        int limite;
        boolean invr = (base.getFemur().getServo().getMovMax() > base.getFemur().getServo().getMovMin());
        boolean invrt = (base.getFemur().getTarso().getServo().getMovMax() > base.getFemur().getTarso().getServo().getMovMin());
        int posb = base.getFemur().getServo().getPosicao();
        int post = base.getFemur().getTarso().getServo().getPosicao();
        
        if (arg < 0)
            limite = base.getFemur().getServo().getMovMin();
        else
            limite = arg;

        if (invr) {
            if (posb + 1 >= base.getFemur().getServo().getMovMax()) {
                descendobase = false;
                return;
            }
            int novaPos = posb + 1;
            base.getFemur().getServo().setPosicao(novaPos);
            base.getFemur().getServo().setPosicaoInicial(novaPos);
        } else {
            if (posb - 1 <= base.getFemur().getServo().getMovMax()) {
                descendobase = false;
                return;
            }
            int novaPos = posb - 1;
            base.getFemur().getServo().setPosicao(novaPos);
            base.getFemur().setPosInicial(novaPos);
        }

        if (invrt) {
            if (post - 1 >= base.getFemur().getTarso().getServo().getMovMin()) {
                elevandobase = false;
                return;
            }
            int novaPos = post - 1;
            base.getFemur().getTarso().getServo().setPosicao(novaPos);
            base.getFemur().getTarso().setPosInicial(novaPos);
        } else {
            if (post + 1 <= base.getFemur().getTarso().getServo().getMovMin()) {
                elevandobase = false;
                return;
            }
            int novaPos = post + 1;
            base.getFemur().getTarso().getServo().setPosicao(novaPos);
            base.getFemur().getTarso().setPosInicial(novaPos);
        }

    }

    public void restaurarAlturaBase() {

    }

}
