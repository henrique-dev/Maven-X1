package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Femur extends Componente {

    private Tarso tarso;

    public Femur(PCA9685 modulo, int servoCanal, int posInicial, Tarso tarso) {
        super(modulo, servoCanal, posInicial);
        super.posInicial = posInicial;
        this.tarso = tarso;
    }

    @Override
    public void levantar() {
        int posi = super.getServo().getPosicao();
        int posf = super.getServo().getMovMax();
        int mov = posi > posf ? posi - posf : posf - posi;
        super.levantar();
        tarso.abaixar(mov);
    }

    @Override
    public void abaixar() {
        int posi = super.getServo().getPosicao();
        int posf = super.getServo().getMovMax();
        int mov = posi > posf ? posi - posf : posf - posi;
        super.abaixar();
        tarso.levantar(mov);
    }

    public void esticar() {        
        boolean invr;
        int posi = super.getServo().getPosicao();
        int posf = super.getServo().getMovMin();
        int mov = posi > posf ? posi - posf : posf - posi;
        invr = (super.getServo().getMovMax() > super.getServo().getMovMin());
        if (!invr)
            super.getServo().setPosicao(super.posInicial + (int)((mov)/3.5));
        else
            super.getServo().setPosicao(super.posInicial - (int)((mov)/3.5));

        invr = (tarso.getServo().getMovMax() > tarso.getServo().getMovMin());
        int valor1 = tarso.getServo().getPosicao();
        int valor2 = tarso.getServo().getMovMax();
        int valor3 = (valor1 > valor2 ? valor1 - valor2 : valor2 - valor1) / 2;
        if (!invr)
            tarso.getServo().setPosicao(tarso.posInicial - valor3);        
        else
            tarso.getServo().setPosicao(tarso.posInicial + valor3);        
    }

    @Override
    public void resetarPosicao() {
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
    public Servo getServo() {
        return super.servo;
    }

}
