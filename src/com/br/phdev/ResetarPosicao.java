/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev;

import com.br.phdev.cmp.Base;
import com.br.phdev.cmp.Femur;
import com.br.phdev.cmp.Perna;
import com.br.phdev.cmp.Tarso;
import com.br.phdev.driver.PCA9685;
import com.pi4j.io.i2c.I2CFactory;

/**
 *
 * @author PH
 */
public class ResetarPosicao {

    private final int PERNA_1 = 0; // PERNA TRASEIRA DIREITA
    private final int PERNA_2 = 1; // PERNA TRASEIRA ESQUERDA
    private final int PERNA_3 = 2; // PERNA DIANTEIRA DIREITA
    private final int PERNA_4 = 3; // PERNA DIANTEIRA ESQUERDA

    private Perna[] pernas;

    private PCA9685 moduloPWM;

    public ResetarPosicao() throws I2CFactory.UnsupportedBusNumberException {

        moduloPWM = new PCA9685();
        moduloPWM.setPWMFreq(60);

        for (int i = 0; i < 16; i++) {
            moduloPWM.setPWM(i, 0, 0);
            delay(200);
        }

        pernas = new Perna[4];
        pernas[PERNA_1] = new Perna(new Base(moduloPWM, 2, 385, new Femur(moduloPWM, 5, 428, new Tarso(moduloPWM, 0, 340))));
        pernas[PERNA_1].getBase().getFemur().getTarso().setLimites(200, 420);
        pernas[PERNA_1].getBase().getFemur().setLimites(520, 275);
        pernas[PERNA_1].getBase().setLimites(280, 490);

        pernas[PERNA_2] = new Perna(new Base(moduloPWM, 3, 415, new Femur(moduloPWM, 7, 300, new Tarso(moduloPWM, 1, 195))));
        pernas[PERNA_2].getBase().getFemur().getTarso().setLimites(340, 130);
        pernas[PERNA_2].getBase().getFemur().setLimites(185, 475);
        pernas[PERNA_2].getBase().setLimites(310, 520);

        pernas[PERNA_3] = new Perna(new Base(moduloPWM, 8, 395, new Femur(moduloPWM, 10, 313, new Tarso(moduloPWM, 14, 195))));
        pernas[PERNA_3].getBase().getFemur().getTarso().setLimites(360, 150);
        pernas[PERNA_3].getBase().getFemur().setLimites(220, 465);
        pernas[PERNA_3].getBase().setLimites(300, 490);

        pernas[PERNA_4] = new Perna(new Base(moduloPWM, 12, 390, new Femur(moduloPWM, 13, 448, new Tarso(moduloPWM, 15, 333))));
        pernas[PERNA_4].getBase().getFemur().getTarso().setLimites(185, 420);
        pernas[PERNA_4].getBase().getFemur().setLimites(540, 295);
        pernas[PERNA_4].getBase().setLimites(290, 490);
    }

    public void resetar() {
        for (Perna perna : pernas) {
            perna.getBase().resetarPosicao();
            perna.getBase().mover();
            delay(300);
            perna.getBase().getFemur().resetarPosicao();
            perna.getBase().getFemur().mover();
            delay(300);
            perna.getBase().getFemur().getTarso().resetarPosicao();
            perna.getBase().getFemur().getTarso().mover();
            delay(300);
        }
    }

    private void delay(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws I2CFactory.UnsupportedBusNumberException {

        ResetarPosicao res = new ResetarPosicao();
        res.resetar();

    }

}
