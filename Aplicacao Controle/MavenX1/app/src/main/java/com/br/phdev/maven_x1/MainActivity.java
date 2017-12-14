package com.br.phdev.maven_x1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class MainActivity extends Activity {

    private Cliente cliente;

    private TextView labelInfo;
    private Button botaoConectar;
    private EditText textPorta;
    private EditText textIp;
    private CheckBox checkComandos;
    private CheckBox checkBotoes;

    private EditText textMsg;
    private Button botaoEnviar;

    public class TelaBotoes extends GridLayout{

        private Button botao1;
        private Button botao2;
        private Button botao3;
        private Button botao4;
        private Button botao5;
        private Button botao6;
        private Button botao7;
        private Button botao8;
        private Button botao9;
        private Button botao10;
        private Button botao11;
        private Button botao12;
        private Button botao13;
        private Button botao14;
        private Button botao15;

        @SuppressLint("ClickableViewAccessibility")
        public TelaBotoes(Context context) {
            super(context);

            LinearLayout linha1 = new LinearLayout(context);
            LinearLayout linha2 = new LinearLayout(context);
            LinearLayout linha3 = new LinearLayout(context);
            LinearLayout coluna = new LinearLayout(context);
            linha1.setOrientation(LinearLayout.HORIZONTAL);
            linha1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            linha2.setOrientation(LinearLayout.HORIZONTAL);
            linha2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            linha3.setOrientation(LinearLayout.HORIZONTAL);
            linha3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            coluna.setOrientation(LinearLayout.VERTICAL);
            coluna.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


            int largura = linha1.getWidth();
            Log.d("LARGURA", largura + "");

            linha1.setBaselineAligned(true);

            botao1 = new Button(context);
            botao1.setText("A");
            botao1.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));

            botao1.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("300");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("301");
                            break;
                    }
                    return false;
                }

            });

            botao2 = new Button(context);
            botao2.setText("B");
            botao2.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));

            botao2.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("302");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("303");
                            break;
                    }
                    return false;
                }

            });

            botao3 = new Button(context);
            botao3.setText("↑");
            botao3.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao3.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("304");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("305");
                            break;
                    }
                    return false;
                }

            });

            botao4 = new Button(context);
            botao4.setText("C");
            botao4.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao4.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("306");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("307");
                            break;
                    }
                    return false;
                }

            });

            botao5 = new Button(context);
            botao5.setText("D");
            botao5.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao5.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("308");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("309");
                            break;
                    }
                    return false;
                }

            });

            botao6 = new Button(context);
            botao6.setText("E");
            botao6.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao6.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("310");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("311");
                            break;
                    }
                    return false;
                }

            });

            botao7 = new Button(context);
            botao7.setText("←");
            botao7.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao7.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("312");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("313");
                            break;
                    }
                    return false;
                }

            });

            botao8 = new Button(context);
            botao8.setText("X");
            botao8.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao8.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("314");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("315");
                            break;
                    }
                    return false;
                }

            });

            botao9 = new Button(context);
            botao9.setText("→");
            botao9.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao9.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("316");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("317");
                            break;
                    }
                    return false;
                }

            });

            botao10 = new Button(context);
            botao10.setText("F");
            botao10.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao10.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("318");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("319");
                            break;
                    }
                    return false;
                }

            });

            botao11 = new Button(context);
            botao11.setText("G");
            botao11.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao11.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("320");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("321");
                            break;
                    }
                    return false;
                }

            });

            botao12 = new Button(context);
            botao12.setText("H");
            botao12.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao12.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("322");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("323");
                            break;
                    }
                    return false;
                }

            });

            botao13 = new Button(context);
            botao13.setText("↓");
            botao13.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao13.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("324");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("325");
                            break;
                    }
                    return false;
                }

            });

            botao14 = new Button(context);
            botao14.setText("I");
            botao14.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao14.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("326");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("327");
                            break;
                    }
                    return false;
                }

            });

            botao15 = new Button(context);
            botao15.setText("J");
            botao15.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            botao15.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getActionMasked();
                    switch (event){
                        case MotionEvent.ACTION_DOWN:
                            cliente.enviarMensagem("328");
                            break;
                        case MotionEvent.ACTION_UP:
                            cliente.enviarMensagem("329");
                            break;
                    }
                    return false;
                }

            });

            linha1.addView(botao1);
            linha1.addView(botao2);
            linha1.addView(botao3);
            linha1.addView(botao4);
            linha1.addView(botao5);
            linha2.addView(botao6);
            linha2.addView(botao7);
            linha2.addView(botao8);
            linha2.addView(botao9);
            linha2.addView(botao10);
            linha3.addView(botao11);
            linha3.addView(botao12);
            linha3.addView(botao13);
            linha3.addView(botao14);
            linha3.addView(botao15);

            coluna.addView(linha1);
            coluna.addView(linha2);
            coluna.addView(linha3);

            addView(coluna);


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        final LinearLayout telaPrincipal = new LinearLayout(this);
        final LinearLayout telaComandos = new LinearLayout(this);
        final TelaBotoes telaBotoes = new TelaBotoes(this);

        telaPrincipal.setOrientation(LinearLayout.VERTICAL);
        telaComandos.setOrientation(LinearLayout.VERTICAL);

        labelInfo = new TextView(this);
        labelInfo.setText("CONECTAR AO MAVEN");
        labelInfo.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);

        textPorta = new EditText(this);
        textPorta.setText("12345");
        textIp = new EditText(this);
        textIp.setText("192.168.2.124");

        botaoConectar = new Button(this);
        botaoConectar.setText("Conectar");
        botaoConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ip = textIp.getText().toString();
                final String porta = textPorta.getText().toString();

                Thread tempThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        cliente = new Cliente(ip, porta);
                    }
                });
                tempThread.start();
                try{
                    tempThread.join();
                }
                catch (Exception e){

                }

                if (cliente != null)
                    if (cliente.estaConectado()) {
                        if (checkBotoes.isChecked())
                            setContentView(telaBotoes);
                        else
                            setContentView(telaComandos);
                    }

            }
        });

        checkComandos = new CheckBox(this);
        checkComandos.setText("MODO COMANDOS");
        checkComandos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBotoes.isChecked())
                    checkBotoes.setChecked(false);
                checkComandos.setChecked(true);
            }
        });

        checkBotoes = new CheckBox(this);
        checkBotoes.setText("MODO BOTÕES");
        checkBotoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkComandos.isChecked())
                    checkComandos.setChecked(false);
                checkBotoes.setChecked(true);
            }
        });

        checkBotoes.setChecked(true);

        telaPrincipal.addView(labelInfo);
        telaPrincipal.addView(botaoConectar);
        telaPrincipal.addView(textIp);
        telaPrincipal.addView(textPorta);
        telaPrincipal.addView(checkComandos);
        telaPrincipal.addView(checkBotoes);

        textMsg = new EditText(this);

        botaoEnviar = new Button(this);
        botaoEnviar.setText("ENVIAR");
        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cliente != null){
                    cliente.enviarMensagem(textMsg.getText().toString());
                    textMsg.setText("");
                }
                else {
                    if (cliente != null)
                        cliente.sair();
                    setContentView(telaPrincipal);
                }
            }
        });


        telaComandos.addView(textMsg);
        telaComandos.addView(botaoEnviar);

        telaBotoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cliente == null)
                    setContentView(telaPrincipal);
                else
                    if (!cliente.estaConectado())
                        setContentView(telaPrincipal);
            }
        });

        setContentView(telaPrincipal);


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (cliente != null)
            cliente.sair();
        Log.d("MAVEN", "DESTRUIU");
    }

    private class Cliente{

        private Socket socket;
        private OutputStream ou;
        private Writer ouw;
        private BufferedWriter bfw;

        Cliente(String ip, String porta){

            Log.d("MAVEN", "Conectando:  " + ip + " - " + porta);

            conectar(ip, 12345);
        }

        private void conectar(String ip, int porta){

            try{
                socket = new Socket(ip, porta);
                ou = socket.getOutputStream();
                ouw = new OutputStreamWriter(ou);
                bfw = new BufferedWriter(ouw);
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }

        public void enviarMensagem(String msg){
            try {
                bfw.write(msg + '\n');
                bfw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean estaConectado(){
            if (socket != null)
                return socket.isConnected();
            else
                return false;
        }

        public void sair(){
            try {
                bfw.close();
                ouw.close();
                ou.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
