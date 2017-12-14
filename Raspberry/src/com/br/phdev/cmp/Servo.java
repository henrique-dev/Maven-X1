package com.br.phdev.cmp;

import com.br.phdev.Controlador;
import com.br.phdev.driver.PCA9685;

public class Servo {
    
    private PCA9685 modulo;
    private int canal;
    private int posicaoAntiga;
    private int posicao;
    private int movMax;
    private int movMin;
    private int posicaoInicial;

    public Servo(PCA9685 modulo, int canal, int posInicial) {
        this.canal = canal;
        this.posicao = posInicial;
        this.modulo = modulo;
        this.posicaoInicial = posInicial;
    }

    public synchronized void mover() {                    
        //if (posicao == posicaoAntiga)
          //  return;
        
        if (posicao == 0) {
            modulo.setPWM(canal, 0, 0);            
            return;
        }
        if (Controlador.velocidade == 1) {
            modulo.setPWM(canal, 0, posicao);
            posicaoAntiga = posicao;
        } else {
            int passo = (posicao > posicaoAntiga ? posicao - posicaoAntiga : posicaoAntiga - posicao) / Controlador.velocidade;           

            if (movMax > movMin) {
                if (posicaoAntiga < posicao) {
                    for (int i = posicaoAntiga; i < posicao; i += passo) {
                        modulo.setPWM(canal, 0, i);                        
                    }
                } else {
                    for (int i = posicaoAntiga; i > posicao; i -= passo) {
                        modulo.setPWM(canal, 0, i);                        
                    }
                }
                modulo.setPWM(canal, 0, posicao);                
            } else {
                if (posicaoAntiga > posicao) {
                    for (int i = posicaoAntiga; i > posicao; i -= passo) {
                        modulo.setPWM(canal, 0, i);                        
                    }
                } else {
                    for (int i = posicaoAntiga; i < posicao; i += passo) {
                        modulo.setPWM(canal, 0, i);                        
                    }
                }
                modulo.setPWM(canal, 0, posicao);                
            }
            posicaoAntiga = posicao;
        }            
        //delay();
    }

    public void mover(int pos) {
        if (pos == 0) {
            modulo.setPWM(canal, 0, 0);
            return;
        }
        if (pos < 150 || pos > 600) {
            return;
        }
        this.posicao = pos;
        modulo.setPWM(canal, 0, pos);
        posicaoAntiga = posicao;
        //delay();
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }

    public int getCanal() {
        return this.canal;
    }

    public void setPosicao(int posicao) {
        if (movMax > movMin) {
            if (posicao != 0) {
                if (posicao < movMin) {
                    posicao = movMin;
                    System.out.println("POSICAO EXAGERADA");
                } else if (posicao > movMax) {
                    posicao = movMax;
                    System.out.println("POSICAO EXAGERADA");
                }
            }
        } else {
            if (posicao != 0) {
                if (posicao > movMin) {
                    posicao = movMin;
                    System.out.println("POSICAO EXAGERADA");
                } else if (posicao < movMax) {
                    posicao = movMax;
                    System.out.println("POSICAO EXAGERADA");
                }
            }
        }
        this.posicao = posicao;
    }

    public int getPosicao() {
        return this.posicao;
    }

    public void setMovMax(int valor) {
        this.movMax = valor;
    }

    public int getMovMax() {
        return this.movMax;
    }

    public void setMovMin(int valor) {
        this.movMin = valor;
    }

    public int getMovMin() {
        return this.movMin;
    }

    public int getPosicaoInicial() {
        return posicaoInicial;
    }

    public void setPosicaoInicial(int posicaoInicial) {
        this.posicaoInicial = posicaoInicial;
    }        

    private void delay() {
        try {
            wait(1000);
            //Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
