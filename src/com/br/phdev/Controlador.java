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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

//import java.com.br.phdev.driver;
public class Controlador {

    private ControladorThread thread;    

    private final int PERNA_1 = 0; // PERNA TRASEIRA DIREITA
    private final int PERNA_2 = 1; // PERNA TRASEIRA ESQUERDA
    private final int PERNA_3 = 2; // PERNA DIANTEIRA DIREITA
    private final int PERNA_4 = 3; // PERNA DIANTEIRA ESQUERDA
    public static int velocidade = 1;
    
    private Queue<Componente> filaComandos;

    private PCA9685 modulo;

    private Perna[] pernas;

    public Controlador() throws I2CFactory.UnsupportedBusNumberException {
        modulo = new PCA9685();
        modulo.setPWMFreq(60);

        pernas = new Perna[4];
        pernas[PERNA_1] = new Perna(new Base(modulo, 5, 385, new Femur(modulo, 4, 398, new Tarso(modulo, 0, 310))));
        pernas[PERNA_1].getBase().getFemur().getTarso().setLimites(200, 420);
        pernas[PERNA_1].getBase().getFemur().setLimites(520, 275);
        pernas[PERNA_1].getBase().setLimites(280, 490);

        pernas[PERNA_2] = new Perna(new Base(modulo, 3, 415, new Femur(modulo, 2, 330, new Tarso(modulo, 1, 225))));
        pernas[PERNA_2].getBase().getFemur().getTarso().setLimites(330, 120);
        pernas[PERNA_2].getBase().getFemur().setLimites(185, 475);
        pernas[PERNA_2].getBase().setLimites(310, 520);

        pernas[PERNA_3] = new Perna(new Base(modulo, 7, 395, new Femur(modulo, 6, 343, new Tarso(modulo, 14, 225))));
        pernas[PERNA_3].getBase().getFemur().getTarso().setLimites(360, 150);
        pernas[PERNA_3].getBase().getFemur().setLimites(220, 465);
        pernas[PERNA_3].getBase().setLimites(300, 490);

        pernas[PERNA_4] = new Perna(new Base(modulo, 8, 390, new Femur(modulo, 9, 418, new Tarso(modulo, 15, 303))));
        pernas[PERNA_4].getBase().getFemur().getTarso().setLimites(185, 420);
        pernas[PERNA_4].getBase().getFemur().setLimites(540, 295);
        pernas[PERNA_4].getBase().setLimites(290, 490);
        
        filaComandos = new LinkedList<>();
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
                if (msg.charAt(i) == '-') {
                    temp[index] = Integer.parseInt(tempNum);
                    index++;
                    tempNum = "";
                    temp[index] = -2;
                    index++;
                } else {
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
                    sleep(400);
                    //pernas[PERNA_1].delay(150);
                    //pernas[PERNA_2].delay(150);
                    //pernas[PERNA_3].delay(150);
                    //pernas[PERNA_4].delay(150);                    
                    break;
                case 0:
                    System.out.println("LEVANTANDO PERNA 1");
                    pernas[PERNA_1].getBase().getFemur().levantar();   
                    filaComandos.add(pernas[PERNA_1].getBase().getFemur().getInstance());
                    //pernas[PERNA_1].getTarso().levantar();
                    break;
                case 1:
                    System.out.println("ABAIXANDO PERNA 1");
                    pernas[PERNA_1].getBase().getFemur().abaixar();
                    filaComandos.add(pernas[PERNA_1].getBase().getFemur().getInstance());
                    //pernas[PERNA_1].getTarso().abaixar();
                    break;
                case 2:
                    System.out.println("ABAIXANDO PERNA 1 PARA POSICAO INICIAL");
                    //pernas[PERNA_1].getTarso().resetarPosicao();
                    pernas[PERNA_1].getBase().getFemur().resetarPosicao();
                    filaComandos.add(pernas[PERNA_1].getBase().getFemur().getInstance());
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
                    pernas[PERNA_2].getBase().getFemur().levantar();
                    //pernas[PERNA_2].getTarso().levantar();
                    break;
                case 26:
                    System.out.println("ABAIXANDO PERNA 2");
                    pernas[PERNA_2].getBase().getFemur().abaixar();
                    //pernas[PERNA_2].getTarso().abaixar();
                    break;
                case 27:
                    System.out.println("ABAIXANDO PERNA 2 PARA POSICAO INICIAL");
                    //pernas[PERNA_2].getTarso().resetarPosicao();
                    pernas[PERNA_2].getBase().getFemur().resetarPosicao();
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
                    pernas[PERNA_3].getBase().getFemur().levantar();
                    //pernas[PERNA_3].getTarso().levantar();
                    break;
                case 51:
                    System.out.println("ABAIXANDO PERNA 3");
                    pernas[PERNA_3].getBase().getFemur().abaixar();
                    //pernas[PERNA_3].getTarso().abaixar();
                    break;
                case 52:
                    System.out.println("ABAIXANDO PERNA 3 PARA POSICAO INICIAL");
                    //pernas[PERNA_3].getTarso().resetarPosicao();
                    pernas[PERNA_3].getBase().getFemur().resetarPosicao();
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
                    pernas[PERNA_4].getBase().getFemur().levantar();
                    //pernas[PERNA_4].getTarso().levantar();
                    break;
                case 76:
                    System.out.println("ABAIXANDO PERNA 4");
                    pernas[PERNA_4].getBase().getFemur().abaixar();
                    //pernas[PERNA_4].getTarso().abaixar();
                    break;
                case 77:
                    System.out.println("ABAIXANDO PERNA 4 PARA POSICAO INICIAL");
                    //pernas[PERNA_4].getTarso().resetarPosicao();
                    pernas[PERNA_4].getBase().getFemur().resetarPosicao();
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
                        ((Perna) cmp).getBase().getFemur().resetarPosicao();
                        ((Perna) cmp).getBase().resetarPosicao();
                        ((Perna) cmp).getBase().getFemur().getTarso().resetarPosicao();
                    }
                    break;
                case 102:
                    System.out.println("PARANDO MOVIMENTO");
                    for (Membro cmp : pernas) {
                        ((Perna) cmp).getBase().pararMovimento();
                        ((Perna) cmp).getBase().getFemur().pararMovimento();
                        ((Perna) cmp).getBase().getFemur().getTarso().pararMovimento();
                    }
                    break;
                case 103:
                    System.out.println("RESETANDO POSICOES");
                    for (Membro cmp : pernas) {
                        ((Perna) cmp).getBase().getFemur().resetarPosicao();
                        sleep(100);
                        ((Perna) cmp).getBase().resetarPosicao();
                        sleep(100);
                        ((Perna) cmp).getBase().getFemur().getTarso().resetarPosicao();
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
                    thread = new ControladorThread();
                    thread.start();                    
                    //pernas[PERNA_1].iniciar();
                    //pernas[PERNA_2].iniciar();
                    //pernas[PERNA_3].iniciar();
                    //pernas[PERNA_4].iniciar();
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
            ((Perna) cmp).getBase().pararMovimento();
            ((Perna) cmp).getBase().getFemur().pararMovimento();
            ((Perna) cmp).getBase().getFemur().getTarso().pararMovimento();
        }
    }

    private void sleep(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public class ControladorThread extends Thread {
        
        private int averageTick;
        private boolean rodando = true;

        @Override
        public void run() {            

            long startTime;
            long timeMillis;
            long waitTime;
            long totalTime = 0;
            long tickCount = 0;
            long targetTime = 1000 / 60;

            while (rodando) {
                startTime = System.nanoTime();
                
                try{
                    
                    filaComandos.poll().mover();
                    
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                timeMillis = (System.nanoTime() - startTime) / 1000000;
                waitTime = targetTime - timeMillis;
                try {
                    sleep(waitTime);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                totalTime += System.nanoTime() - startTime;
                tickCount++;
                if (tickCount == 60){
                    averageTick = (int)(1000/((totalTime/tickCount)/1000000));
                    tickCount = 0;
                    totalTime = 0;
                }
            }
        }
        
        public void setRodando(boolean rodando){
            this.rodando = rodando;
        }
        
        public boolean estaRodando(){
            return this.rodando;
        }

    }

    public static void main(String[] args) throws I2CFactory.UnsupportedBusNumberException {

        Controlador controlador = new Controlador();
        Servidor servidor = new Servidor(controlador);
        servidor.start();        
        

    }

}
