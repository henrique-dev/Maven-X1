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

    private final int PERNA_1 = 0;
    private final int PERNA_2 = 1;
    private final int PERNA_3 = 2;
    private final int PERNA_4 = 3;

    private PCA9685 modulo;

    private Perna[] pernas;

    public Controlador() throws I2CFactory.UnsupportedBusNumberException {
        modulo = new PCA9685();
        modulo.setPWMFreq(60);

        pernas = new Perna[4];
        pernas[PERNA_1] = new Perna(
                new Tarso(modulo, 0, 275), new Femur(modulo, 4, 375), new Base(modulo, 5, 375));
        pernas[PERNA_2] = new Perna(
                new Tarso(modulo, 1, 275), new Femur(modulo, 2, 375), new Base(modulo, 3, 375));
        pernas[PERNA_3] = new Perna(
                new Tarso(modulo, 14, 275), new Femur(modulo, 6, 375), new Base(modulo, 7, 375));
        pernas[PERNA_4] = new Perna(
                new Tarso(modulo, 15, 275), new Femur(modulo, 9, 375), new Base(modulo, 8, 375));
    }

    private void resetarPosicao() {                
                
        for (Membro cmp : pernas) {
            ((Perna)cmp).getTarso().resetarPosicao();
            ((Perna)cmp).getBase().resetarPosicao();
            ((Perna)cmp).getFemur().resetarPosicao();
            ((Perna)cmp).getTarso().mover();            
            ((Perna)cmp).getBase().mover();
            ((Perna)cmp).getFemur().mover();
        }
    }
    
    private void pararMovimento() {                
                
        for (Membro cmp : pernas) {
            ((Perna)cmp).getTarso().parar();
            ((Perna)cmp).getBase().resetarPosicao();
            ((Perna)cmp).getFemur().resetarPosicao();           
        }
    }
    
    public void receberMensagem(char opc){
        switch (opc){
            case 'a':
                resetarPosicao();
                break;
            case 'b':
                pararMovimento();
                break;
        }
    }
    
    private void sleep(int tempo){
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
