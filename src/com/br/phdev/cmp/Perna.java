package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Perna implements Membro {
    
    private Base base;
    private PernaThread thread;

    public Perna(Base base) {        
        this.base = base;        
    }   

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }
    
    public void iniciar(){
        if (thread != null){
            thread.start();
        }
        else{
            thread = new PernaThread();
            thread.start();
        }
    }
    
    public void parar(){
        thread = null;
    }
    
    public void sleep(int tempo){
        try{
            thread.sleep(tempo);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }        
    }
    
    public class PernaThread extends Thread{                
        
        @Override
        public void run(){
            while (true){                
                base.mover();
                base.getFemur().mover();
                base.getFemur().getTarso().mover();
            }
        }
        
    }

}
