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

    private PCA9685 modulo;

    private Perna[] pernas;

    public Controlador() throws I2CFactory.UnsupportedBusNumberException {
        modulo = new PCA9685();
        modulo.setPWMFreq(60);

        pernas = new Perna[4];
        pernas[PERNA_1] = new Perna(
                new Tarso(modulo, 0, 275), new Femur(modulo, 4, 375), new Base(modulo, 5, 375));
        pernas[PERNA_1].getTarso().setLimites(150, 400);
        pernas[PERNA_1].getFemur().setLimites(475, 275);
        pernas[PERNA_1].getBase().setLimites(325, 425);

        pernas[PERNA_2] = new Perna(
                new Tarso(modulo, 1, 275), new Femur(modulo, 2, 375), new Base(modulo, 3, 375));
        pernas[PERNA_2].getTarso().setLimites(400, 150);
        pernas[PERNA_2].getFemur().setLimites(275, 475);
        pernas[PERNA_2].getBase().setLimites(325, 425);

        pernas[PERNA_3] = new Perna(
                new Tarso(modulo, 14, 275), new Femur(modulo, 6, 375), new Base(modulo, 7, 375));
        pernas[PERNA_3].getTarso().setLimites(400, 150);
        pernas[PERNA_3].getFemur().setLimites(275, 475);
        pernas[PERNA_3].getBase().setLimites(325, 425);

        pernas[PERNA_4] = new Perna(
                new Tarso(modulo, 15, 275), new Femur(modulo, 9, 375), new Base(modulo, 8, 375));
        pernas[PERNA_4].getTarso().setLimites(150, 400);
        pernas[PERNA_4].getFemur().setLimites(475, 275);
        pernas[PERNA_4].getBase().setLimites(325, 425);
    }

    private void levantarPerna() {

    }
    
    private int[] receberComandos(String msg){                
        
        int[] temp = new int[msg.length() + 1];
        int index = 0;
        String tempNum = "";
        
        for (int i=0; i<msg.length(); i++){                                    
            if (msg.charAt(i) != ' '){
                tempNum = tempNum + msg.charAt(i);                
            }
            else{
                if (msg.charAt(i) == ' ' || i == msg.length()-1){
                    temp[index] = Integer.parseInt(tempNum);
                    index++;
                }
            }
                
        }        
        temp[index] = -1;
        
        return temp;
    }

    public void receberMensagem(String msg) {
                
        int[] comandos = receberComandos(msg);
        int index = 0;
        
        while(comandos[index] != -1){
            
            index++;
            
            switch (comandos[index]) {
                case 5:
                    System.out.println("RESETANDO POSICOES");
                    for (Membro cmp : pernas) {
                        ((Perna) cmp).getTarso().resetarPosicao();
                        ((Perna) cmp).getBase().resetarPosicao();
                        ((Perna) cmp).getFemur().resetarPosicao();
                    }
                    break;
                case 6:
                    System.out.println("PARANDO MOVIMENTO");
                    for (Membro cmp : pernas) {
                        ((Perna) cmp).getTarso().parar();
                        ((Perna) cmp).getBase().parar();
                        ((Perna) cmp).getFemur().parar();
                    }
                    break;
                case 1:
                    System.out.println("LEVANTANDO PERNA 1");
                    pernas[PERNA_1].getFemur().levantar();
                    pernas[PERNA_1].getTarso().levantar();
                    break;
                case 2:
                    System.out.println("LEVANTANDO PERNA 2");
                    pernas[PERNA_2].getFemur().levantar();
                    pernas[PERNA_2].getTarso().levantar();
                    break;
                case 3:
                    System.out.println("LEVANTANDO PERNA 3");
                    pernas[PERNA_3].getFemur().levantar();
                    pernas[PERNA_3].getTarso().levantar();
                    break;
                case 4:
                    System.out.println("LEVANTANDO PERNA 4");
                    pernas[PERNA_4].getFemur().levantar();
                    pernas[PERNA_4].getTarso().levantar();
                    break;
                case 11:
                    System.out.println("ABAIXANDO PERNA 1");
                    pernas[PERNA_1].getFemur().abaixar();
                    pernas[PERNA_1].getTarso().abaixar();
                    break;
                case 22:
                    System.out.println("ABAIXANDO PERNA 2");
                    pernas[PERNA_2].getFemur().abaixar();
                    pernas[PERNA_2].getTarso().abaixar();
                    break;
                case 33:
                    System.out.println("ABAIXANDO PERNA 3");
                    pernas[PERNA_3].getFemur().abaixar();
                    pernas[PERNA_3].getTarso().abaixar();
                    break;
                case 44:
                    System.out.println("ABAIXANDO PERNA 4");
                    pernas[PERNA_4].getFemur().abaixar();
                    pernas[PERNA_4].getTarso().abaixar();
                    break;
            }
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
