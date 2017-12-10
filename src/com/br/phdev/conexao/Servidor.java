package com.br.phdev.conexao;

import com.br.phdev.Controlador;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread{
    
    private final int PORTA = 12345;
    
    private boolean rodando = false;
    
    private static ServerSocket server;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;
    private Controlador controlador;
    
    public Servidor(Controlador controlador){        
        this.controlador = controlador;
        
        Socket con = iniciar();
        
        try{
            in = con.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private Socket iniciar(){
        
        try{          
            if (server == null)
                server = new ServerSocket(PORTA);
            while(true){
                Socket con = server.accept();
                System.out.println("Conectado");
                rodando = true;
                return con;
            }
        }
        catch(IOException e){
            e.printStackTrace();                 
        }
        
        return null;
    }
    
    @Override
    public void run(){
        String msg = "";
        try{
            while(rodando){                
                msg = bfr.readLine();                                
                controlador.receberMensagem(msg, null);                       
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                System.out.println("Fechando servidor");
                controlador.parar();     
                rodando = false;
                iniciar();
                //server.close();                
                //server = null;                
            }
            catch(Exception e){
                e.printStackTrace();
            }            
        }
    }
    
}
