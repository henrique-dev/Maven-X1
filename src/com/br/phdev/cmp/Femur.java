package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Femur extends Componente{
    
    private Tarso tarso;
    
    public Femur(PCA9685 modulo, int servoCanal, int posInicial, Tarso tarso){
        super(modulo, servoCanal, posInicial);
        super.posInicial = posInicial;
        this.tarso = tarso;
    }
    
    @Override
    public void levantar(){
        int posi = super.getServo().getPosicao();
        int posf = super.getServo().getMovMax();
        int mov = posi > posf ? posi - posf : posf - posi;
        super.levantar();
        tarso.abaixar(mov);
    }                
    
    @Override
    public void abaixar(){
        int posi = super.getServo().getPosicao();
        int posf = super.getServo().getMovMax();
        int mov = posi > posf ? posi - posf : posf - posi;
        super.abaixar();
        tarso.levantar(mov);
    }
    
    public void esticar(){
        int posi = super.getServo().getPosicao();
        int posf = super.getServo().getMovMin();
        int mov = posi > posf ? posi - posf : posf - posi;
        super.abaixar(mov/2);
    }
    
    @Override
    public void resetarPosicao(){
        super.resetarPosicao();
        tarso.resetarPosicao();
    }

    public Tarso getTarso() {
        return tarso;
    }

    public void setTarso(Tarso tarso) {
        this.tarso = tarso;
    }
    
    @Override
    public Servo getServo(){
        return super.servo;
    }
    
    
}
