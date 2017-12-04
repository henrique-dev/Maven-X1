package com.br.phdev;

import com.br.phdev.cmp.Base;
import com.br.phdev.cmp.Componente;
import com.br.phdev.cmp.Femur;
import com.br.phdev.cmp.Membro;
import com.br.phdev.cmp.Perna;
import com.br.phdev.cmp.Tarso;
import com.br.phdev.conexao.Servidor;
import com.br.phdev.driver.PCA9685;

import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//import java.com.br.phdev.driver;
public class Controlador {

    private Controlador controlador;

    private final int PERNA_1 = 0; // PERNA TRASEIRA DIREITA
    private final int PERNA_2 = 1; // PERNA TRASEIRA ESQUERDA
    private final int PERNA_3 = 2; // PERNA DIANTEIRA DIREITA
    private final int PERNA_4 = 3; // PERNA DIANTEIRA ESQUERDA
    public static int velocidade = 1;

    private PCA9685 modulo;

    private Perna[] pernas;

    public Controlador() throws I2CFactory.UnsupportedBusNumberException {
        modulo = new PCA9685();
        modulo.setPWMFreq(60);

        pernas = new Perna[4];
        pernas[PERNA_1] = new Perna(
                new Tarso(modulo, 0, 350), new Femur(modulo, 4, 425), new Base(modulo, 5, 375));
        pernas[PERNA_1].getTarso().setLimites(150, 400);
        pernas[PERNA_1].getFemur().setLimites(475, 275);
        pernas[PERNA_1].getBase().setLimites(280, 470);

        pernas[PERNA_2] = new Perna(
                new Tarso(modulo, 1, 200), new Femur(modulo, 2, 325), new Base(modulo, 3, 375));
        pernas[PERNA_2].getTarso().setLimites(400, 150);
        pernas[PERNA_2].getFemur().setLimites(275, 475);
        pernas[PERNA_2].getBase().setLimites(280, 470);

        pernas[PERNA_3] = new Perna(
                new Tarso(modulo, 14, 200), new Femur(modulo, 6, 325), new Base(modulo, 7, 375));
        pernas[PERNA_3].getTarso().setLimites(400, 150);
        pernas[PERNA_3].getFemur().setLimites(275, 475);
        pernas[PERNA_3].getBase().setLimites(280, 470);

        pernas[PERNA_4] = new Perna(
                new Tarso(modulo, 15, 350), new Femur(modulo, 9, 425), new Base(modulo, 8, 375));
        pernas[PERNA_4].getTarso().setLimites(150, 400);
        pernas[PERNA_4].getFemur().setLimites(475, 275);
        pernas[PERNA_4].getBase().setLimites(280, 470);
    }

    private void levantarPerna() {

    }

    private int[] receberComandos(String msg) {

        int[] temp = new int[msg.length() + 1];
        int index = 0;
        String tempNum = "";

        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) != ' ' && msg.charAt(i) != '-') {                                
                tempNum = tempNum + msg.charAt(i);
                if (i == msg.length() - 1) {
                    temp[index] = Integer.parseInt(tempNum);
                    index++;
                    
                }
            } else {
                if (msg.charAt(i) == '-'){
                    temp[index] = Integer.parseInt(tempNum);
                    index++;
                    tempNum = "";
                    temp[index] = -2;
                    index++;
                }
                else{
                    temp[index] = Integer.parseInt(tempNum);
                    index++;
                    tempNum = "";
                }
            }

        }

        System.out.println("Numero de comandos: " + temp.length);

        temp[index] = -1;

        return temp;
    }

    public void receberMensagem(String msg) {

        int[] comandos = receberComandos(msg);
        int index = 0;                
        String preMsg = "";

        while (comandos[index] != -1) {

            

            switch (comandos[index++]) {
                case -2:
                    sleep(150);
                    pernas[PERNA_1].sleep(150);
                    pernas[PERNA_2].sleep(150);
                    pernas[PERNA_3].sleep(150);
                    pernas[PERNA_4].sleep(150);                    
                    break;
                case 0:
                    System.out.println("LEVANTANDO PERNA 1");
                    pernas[PERNA_1].getFemur().levantar();
                    //pernas[PERNA_1].getTarso().levantar();
                    break;
                case 1:
                    System.out.println("ABAIXANDO PERNA 1");
                    pernas[PERNA_1].getFemur().abaixar();
                    //pernas[PERNA_1].getTarso().abaixar();
                    break;
                case 2:
                    System.out.println("ABAIXANDO PERNA 1 PARA POSICAO INICIAL");
                    //pernas[PERNA_1].getTarso().resetarPosicao();
                    pernas[PERNA_1].getFemur().resetarPosicao();
                    break;
                case 3:
                    System.out.println("ABRINDO BASE DA PERNA 1");
                    pernas[PERNA_1].getBase().levantar();
                    break;
                case 4:
                    System.out.println("FECHANDO BASE DA PERNA 1");
                    pernas[PERNA_1].getBase().abaixar();
                    break;
                case 5:
                    System.out.println("BASE DA PERNA 1 INDO PRA POSICAO INICIAL");
                    pernas[PERNA_1].getBase().resetarPosicao();
                    break;
                case 25:
                    System.out.println("LEVANTANDO PERNA 2");
                    pernas[PERNA_2].getFemur().levantar();
                    //pernas[PERNA_2].getTarso().levantar();
                    break;
                case 26:
                    System.out.println("ABAIXANDO PERNA 2");
                    pernas[PERNA_2].getFemur().abaixar();
                    //pernas[PERNA_2].getTarso().abaixar();
                    break;
                case 27:
                    System.out.println("ABAIXANDO PERNA 2 PARA POSICAO INICIAL");
                    //pernas[PERNA_2].getTarso().resetarPosicao();
                    pernas[PERNA_2].getFemur().resetarPosicao();
                    break;
                case 28:
                    System.out.println("ABRINDO BASE DA PERNA 2");
                    pernas[PERNA_2].getBase().abaixar();
                    break;
                case 29:
                    System.out.println("FECHANDO BASE DA PERNA 2");
                    pernas[PERNA_2].getBase().levantar();
                    break;
                case 30:
                    System.out.println("BASE DA PERNA 2 INDO PRA POSICAO INICIAL");
                    pernas[PERNA_2].getBase().resetarPosicao();
                    break;
                case 50:
                    System.out.println("LEVANTANDO PERNA 3");
                    pernas[PERNA_3].getFemur().levantar();
                    //pernas[PERNA_3].getTarso().levantar();
                    break;
                case 51:
                    System.out.println("ABAIXANDO PERNA 3");
                    pernas[PERNA_3].getFemur().abaixar();
                    //pernas[PERNA_3].getTarso().abaixar();
                    break;
                case 52:
                    System.out.println("ABAIXANDO PERNA 3 PARA POSICAO INICIAL");
                    //pernas[PERNA_3].getTarso().resetarPosicao();
                    pernas[PERNA_3].getFemur().resetarPosicao();
                    break;
                case 53:
                    System.out.println("ABRINDO BASE DA PENRA 3");
                    pernas[PERNA_3].getBase().abaixar();
                    break;
                case 54:
                    System.out.println("FECHANDO BASE DA PERNA 3");
                    pernas[PERNA_3].getBase().levantar();
                    break;
                case 55:
                    System.out.println("BASE DA PERNA 3 INDO PRA POSICAO INICIAL");
                    pernas[PERNA_3].getBase().resetarPosicao();
                    break;
                case 75:
                    System.out.println("LEVANTANDO PERNA 4");
                    pernas[PERNA_4].getFemur().levantar();
                    //pernas[PERNA_4].getTarso().levantar();
                    break;
                case 76:
                    System.out.println("ABAIXANDO PERNA 4");
                    pernas[PERNA_4].getFemur().abaixar();
                    //pernas[PERNA_4].getTarso().abaixar();
                    break;
                case 77:
                    System.out.println("ABAIXANDO PERNA 4 PARA POSICAO INICIAL");
                    //pernas[PERNA_4].getTarso().resetarPosicao();
                    pernas[PERNA_4].getFemur().resetarPosicao();
                    break;
                case 78:
                    System.out.println("ABRINDO BASE D APERNA 4");
                    pernas[PERNA_4].getBase().levantar();
                    break;
                case 79:
                    System.out.println("FECHANDO BASE DA PERNA 4");
                    pernas[PERNA_4].getBase().abaixar();
                    break;
                case 80:
                    System.out.println("BASE DA PERNA 4 INDO PRA POSICAO INICIAL");
                    pernas[PERNA_4].getBase().resetarPosicao();
                    break;
                case 101:
                    System.out.println("RESETANDO POSICOES");
                    for (Membro cmp : pernas) {
                        ((Perna) cmp).getTarso().resetarPosicao();
                        ((Perna) cmp).getBase().resetarPosicao();
                        ((Perna) cmp).getFemur().resetarPosicao();
                    }
                    break;
                case 102:
                    System.out.println("PARANDO MOVIMENTO");
                    for (Membro cmp : pernas) {
                        ((Perna) cmp).getTarso().parar();
                        ((Perna) cmp).getBase().parar();
                        ((Perna) cmp).getFemur().parar();
                    }
                    break;
                case 103:
                    System.out.println("RESETANDO POSICOES");
                    for (Membro cmp : pernas) {
                        ((Perna) cmp).getTarso().resetarPosicao();
                        sleep(100);
                        ((Perna) cmp).getBase().resetarPosicao();
                        sleep(100);
                        ((Perna) cmp).getFemur().resetarPosicao();
                        sleep(100);
                    }
                    break;
                case 110:
                    System.out.println("VELOCIDADE 1");
                    velocidade = 1;
                    break;
                case 111:
                    System.out.println("VELOCIDADE 0.5");
                    velocidade = 2;
                    break;
                case 112:
                    System.out.println("VELOCIDADE 0.2");
                    velocidade = 5;
                    break;
                case 113:
                    System.out.println("VELOCIDADE 0.1");
                    velocidade = 10;
                    break;
                case 115:
                    System.out.println("INICIANDO PERNAS");
                    pernas[PERNA_1].iniciar();
                    pernas[PERNA_2].iniciar();
                    pernas[PERNA_3].iniciar();
                    pernas[PERNA_4].iniciar();
                    break;
                case 150:
                    System.out.println("ANDANDO");
                    preMsg = "3 53-50-54-52-5 29 55 78-25-28-27-75-79-77-53 80 4 30-0-3-2";
                    receberMensagem(preMsg);                    
                    break;
                case 151:                                         
                    preMsg = "3 53-50-54-52-5 29 55 78-25-28-27-75-79-77-53 80 4 30-0-3-2-3 53-50-54-52-5 29 55 78-25-28-27-75-79-77-53 80 4 30-0-3-2"
                            + "-3 53-50-54-52-5 29 55 78-25-28-27-75-79-77-53 80 4 30-0-3-2-3 53-50-54-52-5 29 55 78-25-28-27-75-79-77-53 80 4 30-0-3-2"
                            + "-3 53-50-54-52-5 29 55 78-25-28-27-75-79-77-53 80 4 30-0-3-2-3 53-50-54-52-5 29 55 78-25-28-27-75-79-77-53 80 4 30-0-3-2"
                            + "-3 53-50-54-52-5 29 55 78-25-28-27-75-79-77-53 80 4 30-0-3-2-3 53-50-54-52-5 29 55 78-25-28-27-75-79-77-53 80 4 30-0-3-2";
                    receberMensagem(preMsg);                    
                    break;
            }
        }
    }

    public void parar() {
        for (Membro cmp : pernas) {
            ((Perna) cmp).getTarso().parar();
            ((Perna) cmp).getBase().parar();
            ((Perna) cmp).getFemur().parar();
        }
    }

    private void sleep(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws I2CFactory.UnsupportedBusNumberException {

        Controlador controlador = new Controlador();

        //controlador.sleep(1000);
        //controlador.alinhar();
        Servidor servidor = new Servidor(controlador);
        servidor.start();

    }

}
