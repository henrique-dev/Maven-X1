package com.br.phdev.cmp;

import com.br.phdev.Controlador;
import com.br.phdev.driver.PCA9685;

public class Servo{

	private PCA9685 modulo;
	private int canal;
        private int posicaoAntiga;
	private int posicao;
	private int movMax;
	private int movMin;        
	
	public Servo(PCA9685 modulo, int canal, int posInicial){
		this.canal = canal;
		this.posicao = posInicial;
		this.modulo = modulo;
	}
        
        public void mover(){
            
            if (movMax > movMin){
                if (posicao < movMin || posicao > movMax){
                    System.out.println("POSICAO EXAGERADA");
                    return;
                }
            }
            else{
                if (posicao > movMin || posicao < movMax){
                    System.out.println("POSICAO EXAGERADA");
                    return;
                }
            }
            
            if (Controlador.velocidade == 1){
                modulo.setPWM(canal, 0, posicao);
                posicaoAntiga = posicao;
            }
            else{
                int passo = (posicao > posicaoAntiga ? posicao - posicaoAntiga : posicaoAntiga - posicao);
                
                if (movMax > movMin){
                    if (posicaoAntiga < posicao)
                        for (int i=posicaoAntiga; i<=posicao; i+=passo){
                            modulo.setPWM(canal, 0, i);
                            System.out.println("loop1");
                            delay();
                        }                    
                    else
                        for (int i=posicaoAntiga; i>=posicao; i-=passo){
                            modulo.setPWM(canal, 0, i);
                            System.out.println("loop2");
                            delay();
                        }
                    modulo.setPWM(canal, 0, posicao);
                    delay();
                }
                else{
                    if (posicaoAntiga > posicao)
                        for (int i=posicaoAntiga; i>=posicao; i-=passo){
                            modulo.setPWM(canal, 0, i);
                            System.out.println("loop3");
                            delay();
                        }                    
                    else
                        for (int i=posicaoAntiga; i<=posicao; i+=passo){
                            modulo.setPWM(canal, 0, i);
                            System.out.println("loop4");
                            delay();
                        }
                    modulo.setPWM(canal, 0, posicao);
                    delay();
                }
                
                /*
                if (posicaoAntiga < posicao)
                    for (int i=posicaoAntiga; i<=posicao; i+=passo){
                        modulo.setPWM(canal, 0, i);
                        System.out.println("loop1");
                        delay();
                    }
                else
                    for (int i=posicaoAntiga; i>=posicao; i+=passo){
                        modulo.setPWM(canal, 0, i);
                        System.out.println("loop2");
                        delay();
                    }
                modulo.setPWM(canal, 0, posicao);*/
                posicaoAntiga = posicao;
            }                        
            delay();
        }
	
	public void mover(int pos){
		if (pos < 150 || pos > 600)
			return;
                if (pos == 0){
                    modulo.setPWM(canal, 0, 0);
                    return;
                }
		this.posicao = pos;
		modulo.setPWM(canal, 0, pos);
                posicaoAntiga = posicao;
                delay();
	}
	
	public void setCanal(int canal){
		this.canal = canal;
	}
	
	public int getCanal(){
		return this.canal;
	}
	
	public void setPosicao(int posicao){
		this.posicao = posicao;
	}
	
	public int getPosicao(){
		return this.posicao;
	}
	
	public void setMovMax(int valor){
		this.movMax = valor;
	}
	
	public int getMovMax(){
		return this.movMax;
	}
	
	public void setMovMin(int valor){
		this.movMin = valor;
	}
        
        public int getMovMin(){
            return this.movMin;
        }
        
        private void delay(){
            try{
                Thread.sleep(200);
            }
            catch(InterruptedException e){
                e.printStackTrace();                
            }
        }

}
