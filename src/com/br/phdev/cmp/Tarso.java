package com.br.phdev.cmp;

import com.br.phdev.cmp.Perna;
import com.br.phdev.driver.PCA9685;

public class Tarso extends Perna {
  
  public Tarso(int id, PCA9685 modulo, int servoCanal, int posInicial){
    super(id, modulo, servoCanal, posInicial);
  }

}
