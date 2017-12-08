package com.br.phdev;

import com.br.phdev.cmp.Base;
import com.br.phdev.cmp.Femur;
import com.br.phdev.cmp.Perna;
import com.br.phdev.cmp.Tarso;
import com.br.phdev.driver.PCA9685;

import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;
import java.util.Scanner;

public class Ajustar {

    private final int PERNA_1 = 0; // PERNA TRASEIRA DIREITA
    private final int PERNA_2 = 1; // PERNA TRASEIRA ESQUERDA
    private final int PERNA_3 = 2; // PERNA DIANTEIRA DIREITA
    private final int PERNA_4 = 3; // PERNA DIANTEIRA ESQUERDA

    private Perna[] pernas;

    private PCA9685 modulo;

    public Ajustar() throws I2CFactory.UnsupportedBusNumberException {

        modulo = new PCA9685();
        modulo.setPWMFreq(60);

        for (int i = 0; i < 16; i++) {
            modulo.setPWM(i, 0, 0);
            delay(200);
        }

        pernas = new Perna[4];
        pernas[PERNA_1] = new Perna(new Base(modulo, 5, 385, new Femur(modulo, 4, 428, new Tarso(modulo, 0, 340))));
        pernas[PERNA_1].getBase().getFemur().getTarso().setLimites(200, 420);
        pernas[PERNA_1].getBase().getFemur().setLimites(520, 275);
        pernas[PERNA_1].getBase().setLimites(280, 490);

        pernas[PERNA_2] = new Perna(new Base(modulo, 3, 415, new Femur(modulo, 2, 300, new Tarso(modulo, 1, 195))));
        pernas[PERNA_2].getBase().getFemur().getTarso().setLimites(340, 130);
        pernas[PERNA_2].getBase().getFemur().setLimites(185, 475);
        pernas[PERNA_2].getBase().setLimites(310, 520);

        pernas[PERNA_3] = new Perna(new Base(modulo, 7, 395, new Femur(modulo, 6, 313, new Tarso(modulo, 14, 195))));
        pernas[PERNA_3].getBase().getFemur().getTarso().setLimites(360, 150);
        pernas[PERNA_3].getBase().getFemur().setLimites(220, 465);
        pernas[PERNA_3].getBase().setLimites(300, 490);

        pernas[PERNA_4] = new Perna(new Base(modulo, 8, 390, new Femur(modulo, 9, 448, new Tarso(modulo, 15, 333))));
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

    public void movimentar() {
        for (Perna perna : pernas) {
            perna.getBase().mover();
            perna.getBase().getFemur().mover();
            perna.getBase().getFemur().getTarso().mover();
        }
    }

    public void setPos(int ch, int pos) {
        for (Perna perna : pernas) {
            if (perna.getBase().getServo().getCanal() == ch) {
                perna.getBase().getServo().setPosicao(pos);
            } else if (perna.getBase().getFemur().getServo().getCanal() == ch) {
                perna.getBase().getFemur().getServo().setPosicao(pos);
            } else if (perna.getBase().getFemur().getTarso().getServo().getCanal() == ch) {
                perna.getBase().getFemur().getTarso().getServo().setPosicao(pos);
            }
        }
    }

    private void delay(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public class MainThread extends Thread {

        @Override
        public void run() {
            while (true) {
                for (Perna perna : pernas) {
                    perna.getBase().mover();
                    perna.getBase().getFemur().mover();
                    perna.getBase().getFemur().getTarso().mover();
                }
            }
        }

    }

    public static void main(String[] args) throws I2CFactory.UnsupportedBusNumberException {

        Scanner entrada = new Scanner(System.in);

        Ajustar aj = new Ajustar();

        int servoChannel = 0;
        int servoPos = 0;

        aj.resetar();

        while (true) {
            System.out.println("AJUSTANDO SERVO\n\n");
            System.out.println("Informe o canal do servo: ");
            servoChannel = entrada.nextInt();
            System.out.println("Informe a posicao para o servo: ");
            servoPos = entrada.nextInt();
            System.out.println("Movendo para " + servoPos);
            aj.setPos(servoChannel, servoPos);            

        }

        // 375 servo middle
    }

}
