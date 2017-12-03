package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Servo{

	private PCA9685 modulo;
	private int canal;
	private int posicao;
	private int movMax;
	private int movMin;
	
	public Servo(PCA9685 modulo, int canal, int posInicial){
		this.canal = canal;
		this.posicao = posInicial;
		this.modulo = modulo;
	}
        
        public void mover(){
            modulo.setPWM(canal, 0, (int)(posicao*2.5) + 150);
        }
	
	public void mover(int pos){
		if (pos < 0 || pos >180)
			return;
                if (pos == 0){
                    modulo.setPWM(canal, 0, 0);
                    return;
                }
		this.posicao = pos;
		modulo.setPWM(canal, 0, (int)(pos*2.5) + 150);
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

}
