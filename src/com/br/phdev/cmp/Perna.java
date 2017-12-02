package com.br.phdev.cmp;

import com.br.phdev.driver.PCA9685;

public class Perna implements Membro{

        private Tarso tarso;
        private Femur femur;
        private Base base;
	
	public Perna(Tarso tarso, Femur femur, Base base){
            this.tarso = tarso;
            this.femur = femur;
            this.base = base;                               
	}

}
