package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Perna implements Membro {
    
    private Base base;
    //private PernaThread thread;

    public Perna(Base base) {        
        this.base = base;        
    }   

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }        
    
    public void parar(){
        //thread = null;
    }        
    
    
    public void iniciar(){
        base.iniciar();
        base.getFemur().iniciar();
        base.getFemur().getTarso().iniciar();
    }
    
    /*
    
    public class PernaThread extends Thread{                
        
        @Override
        public void run(){
            while (true){                
                base.mover();
                delay(400);
                base.getFemur().mover();
                delay(400);
                base.getFemur().getTarso().mover();
                delay(400);
            }
        }
        
        public void delay(int tempo){
        try{
            thread.sleep(tempo);            
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }        
    }

        
    }
    */

}
