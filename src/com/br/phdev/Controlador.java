package com.br.phdev;

import com.br.phdev.cmp.Base;
import com.br.phdev.cmp.Femur;
import com.br.phdev.cmp.Membro;
import com.br.phdev.cmp.Perna;
import com.br.phdev.cmp.Tarso;
import com.br.phdev.conexao.Servidor;
import com.br.phdev.driver.MPU9150;
import com.br.phdev.driver.PCA9685;

import com.pi4j.io.i2c.I2CFactory;
import java.util.ArrayList;
import java.util.List;

//import java.com.br.phdev.driver;
public class Controlador {

    private Servidor servidor;
    private ControladorThread thread;

    private final int PERNA_1 = 0; // PERNA TRASEIRA DIREITA
    private final int PERNA_2 = 1; // PERNA TRASEIRA ESQUERDA
    private final int PERNA_3 = 2; // PERNA DIANTEIRA DIREITA
    private final int PERNA_4 = 3; // PERNA DIANTEIRA ESQUERDA
    public static int velocidade = 1;

    private int delayComandos = 150;

    //private Queue<Componente> filaComandos;
    private PCA9685 moduloPWM;
    private MPU9150 moduloMPU;

    private Perna[] pernas;

    private final int algoritmoPasso1[] = new int[]{3, 53, -2, 50, -2, 54, -2, 52, -2, 5, 29, 55, 78, -2, 25, -2, 28, -2,
        27, -2, 75, -2, 79, -2, 77, -2, 53, 80, 4, 30, -2, 0, -2, 3, -2, 2, -1};

    private List<int[]> algPasV2;
    private final int algoritmoPasso2[] = new int[]{3, 53, -2, 50, -2, 57, -2, 58, -2, 55, 52, 78, 5, 32, 33, -2, 25, -2, 28, -2, 27, -2,
        75, -2, 82, -2, 83, -2, 80, 77, 53, 7, 8, 30, -2, 0, -2, 3, -2, 2, -1};

    // "3 53-50-57-58-55 52 78 5 32 33-25-28-27-75-82-83-80 77 53 7 8 30-0-3-2";
    private int movimentoAtual = 0;
    private int movimentoIndex = 0;
    private boolean movimentoParaFrente = false;
    private boolean movimentoParaTras = false;

    public Controlador() throws I2CFactory.UnsupportedBusNumberException {

        moduloPWM = new PCA9685();
        moduloPWM.setPWMFreq(60);
        //moduloMPU = new MPU9150();

        inicializarPernas();

        servidor = new Servidor(this);
        servidor.start();

        thread = new ControladorThread();
        thread.start();

        algPasV2 = new ArrayList<>();
        algPasV2.add(new int[]{3, 53, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{50, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{57, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{58, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{-2, 55, 52, 78, 5, 32, 33, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{25, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{28, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{27, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{75, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{82, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{83, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{-2, 80, 77, 53, 7, 8, 30 - 1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{0, -1});
        algPasV2.add(new int[]{-2, -1});
        algPasV2.add(new int[]{3, -1});
        algPasV2.add(new int[]{2, -1});
        algPasV2.add(new int[]{-2, -1});

        System.out.println(algPasV2.size());

        //filaComandos = new LinkedList<>();
    }

    private void inicializarPernas() {
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

    private int[] receberComandos(String msg) {

        if (msg == null) {
            return null;
        }

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

        temp[index] = -1;

        return temp;
    }

    public void receberMensagem(String msg, int[] cmds) {

        //System.out.println("recebendo comandos");
        int[] comandos;
        if (cmds == null) {
            //System.out.println("Vetor nulo");
            comandos = receberComandos(msg);
            if (comandos == null) {
                //System.out.println("String nula");
                return;
            }
        } else {
            comandos = cmds;
        }

        int index = 0;

        while (comandos[index] != -1) {
            executarComando(comandos[index++]);            
        }
    }

    public synchronized void executarComando(int comando) {
        switch (comando) {
                case -2:
                    delay(delayComandos);
                    break;
                case 0:
                    //System.out.println("LEVANTANDO PERNA 1");
                    pernas[PERNA_1].getBase().getFemur().levantar();
                    //filaComandos.add(pernas[PERNA_1].getBase().getFemur().getInstance());
                    //pernas[PERNA_1].getTarso().levantar();
                    break;
                case 1:
                    //System.out.println("ABAIXANDO PERNA 1");
                    pernas[PERNA_1].getBase().getFemur().abaixar();
                    //filaComandos.add(pernas[PERNA_1].getBase().getFemur().getInstance());
                    //pernas[PERNA_1].getTarso().abaixar();
                    break;
                case 2:
                    //System.out.println("ABAIXANDO PERNA 1 PARA POSICAO INICIAL");
                    //pernas[PERNA_1].getTarso().resetarPosicao();
                    pernas[PERNA_1].getBase().getFemur().resetarPosicao();
                    //filaComandos.add(pernas[PERNA_1].getBase().getFemur().getInstance());
                    break;
                case 3:
                    //System.out.println("ABRINDO BASE DA PERNA 1");
                    pernas[PERNA_1].getBase().levantar();
                    break;
                case 4:
                    //System.out.println("FECHANDO BASE DA PERNA 1");
                    pernas[PERNA_1].getBase().abaixar();
                    break;
                case 5:
                    //System.out.println("BASE DA PERNA 1 INDO PRA POSICAO INICIAL");
                    pernas[PERNA_1].getBase().resetarPosicao();
                    break;
                case 6:
                    //System.out.println("ABRINDO BASE DA PERNA 1 PELA METADE");
                    pernas[PERNA_1].getBase().levantarMetade();
                    break;
                case 7:
                    //System.out.println("FECHANDO BASE DA PERNA 1 PELA METADE");
                    pernas[PERNA_1].getBase().abaixarMetade();
                    break;
                case 8:
                    //System.out.println("ESTICANDO PERNA 1");
                    pernas[PERNA_1].getBase().getFemur().esticar();
                    break;
                case 25:
                    //System.out.println("LEVANTANDO PERNA 2");
                    pernas[PERNA_2].getBase().getFemur().levantar();
                    //pernas[PERNA_2].getTarso().levantar();
                    break;
                case 26:
                    //System.out.println("ABAIXANDO PERNA 2");
                    pernas[PERNA_2].getBase().getFemur().abaixar();
                    //pernas[PERNA_2].getTarso().abaixar();
                    break;
                case 27:
                    //System.out.println("ABAIXANDO PERNA 2 PARA POSICAO INICIAL");
                    //pernas[PERNA_2].getTarso().resetarPosicao();
                    pernas[PERNA_2].getBase().getFemur().resetarPosicao();
                    break;
                case 28:
                    //System.out.println("ABRINDO BASE DA PERNA 2");
                    pernas[PERNA_2].getBase().abaixar();
                    break;
                case 29:
                    //System.out.println("FECHANDO BASE DA PERNA 2");
                    pernas[PERNA_2].getBase().levantar();
                    break;
                case 30:
                    //System.out.println("BASE DA PERNA 2 INDO PRA POSICAO INICIAL");
                    pernas[PERNA_2].getBase().resetarPosicao();
                    break;
                case 31:
                    //System.out.println("ABRINDO BASE DA PERNA 2 PELA METADE");
                    pernas[PERNA_2].getBase().abaixarMetade();
                    break;
                case 32:
                    //System.out.println("FECHANDO BASE DA PERNA 2 PELA METADE");
                    pernas[PERNA_2].getBase().levantarMetade();
                    break;
                case 33:
                    //System.out.println("ESTICANDO PERNA 2");
                    pernas[PERNA_2].getBase().getFemur().esticar();
                    break;
                case 50:
                    //System.out.println("LEVANTANDO PERNA 3");
                    pernas[PERNA_3].getBase().getFemur().levantar();
                    //pernas[PERNA_3].getTarso().levantar();
                    break;
                case 51:
                    //System.out.println("ABAIXANDO PERNA 3");
                    pernas[PERNA_3].getBase().getFemur().abaixar();
                    //pernas[PERNA_3].getTarso().abaixar();
                    break;
                case 52:
                    //System.out.println("ABAIXANDO PERNA 3 PARA POSICAO INICIAL");
                    //pernas[PERNA_3].getTarso().resetarPosicao();
                    pernas[PERNA_3].getBase().getFemur().resetarPosicao();
                    break;
                case 53:
                    //System.out.println("ABRINDO BASE DA PENRA 3");
                    pernas[PERNA_3].getBase().abaixar();
                    break;
                case 54:
                    //System.out.println("FECHANDO BASE DA PERNA 3");
                    pernas[PERNA_3].getBase().levantar();
                    break;
                case 55:
                    //System.out.println("BASE DA PERNA 3 INDO PRA POSICAO INICIAL");
                    pernas[PERNA_3].getBase().resetarPosicao();
                    break;
                case 56:
                    //System.out.println("ABRINDO BASE DA PERNA 3 PELA METADE");
                    pernas[PERNA_3].getBase().abaixarMetade();
                    break;
                case 57:
                    //System.out.println("FECHANDO BASE DA PERNA 3 PELA METADE");
                    pernas[PERNA_3].getBase().levantarMetade();
                    break;
                case 58:
                    //System.out.println("ESTICANDO PERNA 3");
                    pernas[PERNA_3].getBase().getFemur().esticar();
                    break;
                case 75:
                    //System.out.println("LEVANTANDO PERNA 4");
                    pernas[PERNA_4].getBase().getFemur().levantar();
                    //pernas[PERNA_4].getTarso().levantar();
                    break;
                case 76:
                    //System.out.println("ABAIXANDO PERNA 4");
                    pernas[PERNA_4].getBase().getFemur().abaixar();
                    //pernas[PERNA_4].getTarso().abaixar();
                    break;
                case 77:
                    //System.out.println("ABAIXANDO PERNA 4 PARA POSICAO INICIAL");
                    //pernas[PERNA_4].getTarso().resetarPosicao();
                    pernas[PERNA_4].getBase().getFemur().resetarPosicao();
                    break;
                case 78:
                    //System.out.println("ABRINDO BASE D APERNA 4");
                    pernas[PERNA_4].getBase().levantar();
                    break;
                case 79:
                    //System.out.println("FECHANDO BASE DA PERNA 4");
                    pernas[PERNA_4].getBase().abaixar();
                    break;
                case 80:
                    //System.out.println("BASE DA PERNA 4 INDO PRA POSICAO INICIAL");
                    pernas[PERNA_4].getBase().resetarPosicao();
                    break;
                case 81:
                    //System.out.println("ABRINDO BASE DA PERNA 4 PELA METADE");
                    pernas[PERNA_4].getBase().levantarMetade();
                    break;
                case 82:
                    //System.out.println("FECHANDO BASE DA PERNA 4 PELA METADE");
                    pernas[PERNA_4].getBase().abaixarMetade();
                    break;
                case 83:
                    //System.out.println("ESTICANDO PERNA 4");
                    pernas[PERNA_4].getBase().getFemur().esticar();
                    break;
                case 315: // Botão X
                case 101:
                    //System.out.println("RESETANDO POSICOES");
                    movimentoIndex = 0;
                    for (Membro cmp : pernas) {
                        ((Perna) cmp).getBase().getFemur().resetarPosicao();
                        ((Perna) cmp).getBase().resetarPosicao();
                        ((Perna) cmp).getBase().getFemur().getTarso().resetarPosicao();
                    }
                    break;
                case 102:
                    //System.out.println("PARANDO MOVIMENTO");
                    for (Membro cmp : pernas) {
                        ((Perna) cmp).getBase().pararMovimento();
                        ((Perna) cmp).getBase().getFemur().pararMovimento();
                        ((Perna) cmp).getBase().getFemur().getTarso().pararMovimento();
                    }
                    break;
                case 103:
                    //System.out.println("RESETANDO POSICOES");
                    for (Membro cmp : pernas) {
                        ((Perna) cmp).getBase().getFemur().resetarPosicao();
                        delay(100);
                        ((Perna) cmp).getBase().resetarPosicao();
                        delay(100);
                        ((Perna) cmp).getBase().getFemur().getTarso().resetarPosicao();
                        delay(100);
                    }
                    break;
                case 105:
                    //System.out.println("DELAY DE COMANDOS ALTERADO PARA 150ms");
                    delayComandos = 150;
                    break;
                case 106:
                    //System.out.println("DELAY DE COMANDOS ALTERADO PARA 300ms");
                    delayComandos = 300;
                    break;
                case 107:
                    //System.out.println("DELAY DE COMANDOS ALTERADO PARA 450ms");
                    delayComandos = 450;
                    break;
                case 108:
                    //System.out.println("DELAY DE COMANDOS ALTERADO PARA 600ms");
                    delayComandos = 600;
                    break;
                case 110:
                    //System.out.println("VELOCIDADE 1");
                    velocidade = 1;
                    break;
                case 111:
                    //System.out.println("VELOCIDADE 0.5");
                    velocidade = 2;
                    break;
                case 112:
                    //System.out.println("VELOCIDADE 0.2");
                    velocidade = 5;
                    break;
                case 113:
                    //System.out.println("VELOCIDADE 0.1");
                    velocidade = 10;
                    break;
                case 115:
                    //System.out.println("INICIANDO PERNAS");
                    //thread = new ControladorThread();
                    //thread.start();
                    //pernas[PERNA_1].iniciar();
                    //pernas[PERNA_2].iniciar();
                    //pernas[PERNA_3].iniciar();
                    //pernas[PERNA_4].iniciar();
                    break;
                case 301: // Botão A
                case 125:
                    //System.out.println("ELEVANDO BASE");
                    pernas[PERNA_1].setElevandobase(true);
                    pernas[PERNA_2].setElevandobase(true);
                    pernas[PERNA_3].setElevandobase(true);
                    pernas[PERNA_4].setElevandobase(true);
                    break;
                case 321: // Botão G
                case 126:
                    //System.out.println("DESCENDO BASE");
                    pernas[PERNA_1].setDescendobase(true);
                    pernas[PERNA_2].setDescendobase(true);
                    pernas[PERNA_3].setDescendobase(true);
                    pernas[PERNA_4].setDescendobase(true);
                    break;
                case 311: // Botão E
                case 127:
                    //System.out.println("PARANDO ELEVAR/DESCER");
                    pernas[PERNA_1].setElevandobase(false);
                    pernas[PERNA_2].setElevandobase(false);
                    pernas[PERNA_3].setElevandobase(false);
                    pernas[PERNA_4].setElevandobase(false);
                    pernas[PERNA_1].setDescendobase(false);
                    pernas[PERNA_2].setDescendobase(false);
                    pernas[PERNA_3].setDescendobase(false);
                    pernas[PERNA_4].setDescendobase(false);
                    break;
                case 150:
                    //System.out.println("ALGORITMO DE PASSO V1");
                    //preMsg = "3 53-50-54-52-5 29 55 78-25-28-27-75-79-77-53 80 4 30-0-3-2";                    
                    receberMensagem(null, algoritmoPasso1);
                    break;
                case 151:
                    //System.out.println("ALGORITMO DE PASSO V2");
                    //preMsg = "3 53-50-57-58-55 52 78 5 32 33-25-28-27-75-82-83-80 77 53 7 8 30-0-3-2";
                    receberMensagem(null, algoritmoPasso2);
                    break;
                case 303: // Botão B
                    switch (delayComandos) {
                        case 150:
                            delayComandos = 300;
                            break;
                        case 300:
                            delayComandos = 450;
                            break;
                        case 450:
                            delayComandos = 600;
                            break;
                        case 600:
                            delayComandos = 150;
                            break;
                    }
                    break;
                case 304: // Botãao cima                
                    movimentoParaFrente = true;
                    break;
                case 305: // Botão baixo
                    movimentoParaFrente = false;
                    break;
            }
    }

    public void parar() {        
        pernas = null;
        inicializarPernas();
        delay(300);
        for (Membro cmp : pernas) {
            ((Perna) cmp).getBase().pararMovimento();
            ((Perna) cmp).getBase().getFemur().pararMovimento();
            ((Perna) cmp).getBase().getFemur().getTarso().pararMovimento();
        }               
        //servidor = new Servidor(this);
        //servidor.start();        
    }

    private void delay(int tempo) {
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
            long targetTime = 1000 / 120;                        

            while (rodando) {
                startTime = System.nanoTime();

                try {
                    for (Perna perna : pernas) {
                        perna.getBase().mover();
                        perna.getBase().getFemur().mover();
                        perna.getBase().getFemur().getTarso().mover();

                        if (perna.estaElevandobase()) {
                            perna.elevarBase(Perna.ATE_O_LIMITE);
                        }
                        if (perna.estaDescendobase()) {
                            perna.descerBase(Perna.ATE_O_LIMITE);
                        }
                        if (movimentoParaFrente) {                                                        
                            //receberMensagem(null, algPasV2.get(movimentoIndex++));    
                            //int comando = algPasV2.get(movimentoIndex)[index];                            
                            int index = 0;
                            int[] mov = algPasV2.get(movimentoIndex++);
                            while (mov[index] != -1){
                                System.out.println("index " + index);
                                executarComando(algPasV2.get(movimentoIndex)[index++]);                                
                            }                                                                         
                            System.out.println("movimentoindex " + movimentoIndex);
                            delay(delayComandos/5);
                            if (movimentoIndex == algPasV2.size() - 1) {
                                movimentoIndex = 0;
                            }
                            //delay(delayComandos);                            
                        }
                    }

                } catch (Exception e) {
                    //e.printStackTrace();
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
                if (tickCount == 120) {
                    averageTick = (int) (1000 / ((totalTime / tickCount) / 1000000));
                    tickCount = 0;
                    totalTime = 0;
                }
            }
        }

        public void setRodando(boolean rodando) {
            this.rodando = rodando;
        }

        public boolean estaRodando() {
            return this.rodando;
        }

    }

    public ControladorThread getThread() {
        return thread;
    }        

    public static void main(String[] args) throws I2CFactory.UnsupportedBusNumberException {

        Controlador controlador = new Controlador();

    }

}
