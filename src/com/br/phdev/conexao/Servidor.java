package com.br.phdev.conexao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread{
    
    private final int PORTA = 12345;
    
    private static ServerSocket server;
    private Socket con;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;
    
    public Servidor(Socket con){        
        this.con = con;
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
            server = new ServerSocket(PORTA);
            while(true){
                Socket con = server.accept();
                System.out.println("Conectado");
                return con;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return iniciar();
    }
    
    @Override
    public void run(){
        char msg = ' ';
        try{
            while(!('s' == msg)){
                msg = (char)bfr.read();
                System.out.println(msg);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
