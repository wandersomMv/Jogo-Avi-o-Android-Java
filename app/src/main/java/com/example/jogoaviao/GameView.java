package com.example.jogoaviao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    Thread thread; // olha o bixo vindo
    boolean isPlaying; //(status do jogo) variável  para saber se o jogo está pausado ou rodando
    private Background background1, background2;
    private int tamanhoX, tamanhoy, pontuacao = 0,sound; // pegar todos os tamanhos da tela do dispositivo, por exemplo 16:9
    private  Voar voar;
    private Random random;
    private SoundPool soundPool;
    private SharedPreferences dadosUsuarioPontos;
    private List<Tiro> tiros;
    private PassarinhoDeBosta[] passarinhoDeBosta;
    private  GameActivity activity;
    public static float proporcaoDaTelaX, proporcaoDaTelaY;
    private Paint desenhar;
    private boolean perdeu = false;


    public GameView(GameActivity activity,int tamanhoX, int tamanhoY) { // tamanho da tela que será usado no background
        super(activity);

        this.activity = activity;

        this.tamanhoX =tamanhoX;
        this.tamanhoy = tamanhoY;

        dadosUsuarioPontos = activity.getSharedPreferences("jogo",Context.MODE_PRIVATE);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(
                    AudioAttributes.CONTENT_TYPE_MUSIC
            ).setUsage(AudioAttributes.USAGE_GAME).build();

            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
        }
        else
        {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }
        sound = soundPool.load(activity,R.raw.shoot,1);

        proporcaoDaTelaX = 1920f/tamanhoX;
        proporcaoDaTelaY = 1080f/tamanhoY;


        background1 = new Background(tamanhoX,tamanhoY,getResources());
        background2 = new Background(tamanhoX,tamanhoY,getResources());

        voar = new Voar( this,this.tamanhoy,getResources());
        tiros = new ArrayList<>();

        background2.x= tamanhoX;


        desenhar = new Paint();
        desenhar.setTextSize(128);
        desenhar.setColor(Color.GRAY);


        passarinhoDeBosta = new PassarinhoDeBosta[4];
        for(int i =0; i <4; i++)
        {
            PassarinhoDeBosta bostinha = new PassarinhoDeBosta(getResources());
            passarinhoDeBosta[i] = bostinha;
        }
        random = new Random();

    }

    @Override
    public void run() {

            while (this.isPlaying)
            {
                atualizar();
                contruir();
                esperar();
            }


    }
    public void atualizar(){
        // tamanho do pixel será igual a 10
        background1.x -=10 * proporcaoDaTelaX;
        background2.x -=10 * proporcaoDaTelaY;

        if (background1.x + background1.background.getWidth() < 0)
        {
            background1.x = tamanhoX;
        }
        if (background2.x + background2.background.getWidth() < 0)
        {
            background2.x = tamanhoX;
        }

//        voar.y = (voar.subindo)?  voar.y - 30 *(int) proporcaoDaTelaY : voar.y +30 *(int) proporcaoDaTelaX;

        if(voar.subindo)
            voar.y -=30 * proporcaoDaTelaY;
        else
            voar.y += 30 *proporcaoDaTelaY;

        if(voar.y < 0)
            voar.y= 0;
        if(voar.y > tamanhoy - voar.altura)
            voar.y = tamanhoy - voar.altura;


        List<Tiro> listaDeBOSTA = new ArrayList<>();

        for(Tiro tiro: tiros)
        {
            if(tiro.x>tamanhoX)
            {
                listaDeBOSTA.add(tiro);
            }
            tiro.x += 50*proporcaoDaTelaX;

            for(PassarinhoDeBosta moscandoBird: passarinhoDeBosta){

                if(Rect.intersects(moscandoBird.getCocoBatelEmAlgo(),tiro.getAcertouTiro()))
                {
                    pontuacao++;
                    moscandoBird.x=-500;
                    tiro.x = tamanhoX + 500;
                    moscandoBird.morreuDeVacilao = true;
                }
            }

        }

        for(Tiro coco:listaDeBOSTA)
        {
            this.tiros.remove(coco);
        }
        for (PassarinhoDeBosta vacilao: passarinhoDeBosta)
        {
            vacilao.x -= vacilao.velocidadeDoCocoVoando;
            if((vacilao.x + vacilao.largura)<0)
            {

                if(!vacilao.morreuDeVacilao)
                {
                    perdeu = true;
                    return;
                }
                int limiteDaBostaVoar = (int)(30*proporcaoDaTelaX);
                vacilao.velocidadeDoCocoVoando =random.nextInt(limiteDaBostaVoar);

                if(vacilao.velocidadeDoCocoVoando < (10*proporcaoDaTelaX))
                {
                    vacilao.velocidadeDoCocoVoando = (int)(10*proporcaoDaTelaX);
                }
                vacilao.x = tamanhoX;
                vacilao.y = random.nextInt(tamanhoy - vacilao.altura);

                vacilao.morreuDeVacilao = false;

            }
            if(Rect.intersects(vacilao.getCocoBatelEmAlgo(), voar.getCocoBatelEmAlgo())) //passarinho bateu  no aviao
            {
                this.perdeu = true;
                return;
            }


        }






    }
    public void contruir(){

        if(getHolder().getSurface().isValid())
        {
            Canvas canvas = getHolder().lockCanvas(); // desenhar a tela com cavas
            canvas.drawBitmap(background1.background,background1.x,background1.y,desenhar);
            canvas.drawBitmap(background2.background,background2.x,background2.y,desenhar);


            for(PassarinhoDeBosta bostinhaVoadora: passarinhoDeBosta)
            {
                canvas.drawBitmap(bostinhaVoadora.getPassaroBosta(),bostinhaVoadora.x,bostinhaVoadora.y,desenhar);
            }
            canvas.drawText(pontuacao+"",tamanhoX/2f,164,desenhar);
            if(perdeu)
            {
                this.isPlaying = false ; // está rodando o jogo
                canvas.drawBitmap(voar.getMorreu(),voar.x,voar.y,desenhar);
                getHolder().unlockCanvasAndPost(canvas);
                salvarSePontuacaoForMaior();
                esperarParaSair();

                return;

            }





            // mostrar a tela para o usuário, desenhar a tela do jogo

            canvas.drawBitmap(voar.getVoar(),voar.x,voar.y,desenhar);

            for(Tiro tiro:tiros)
            {
                canvas.drawBitmap(tiro.tiro,tiro.x,tiro.y,desenhar);
            }

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void esperarParaSair() {

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity,MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void esperar(){ // atualizar os quadros a cada 17 milisegundos
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void resume(){ // resumir o jogo caso o jogador esteja pausado ou iniciado o jogo

        this.isPlaying = true ; // está rodando o jogo
        thread = new Thread(this);
        thread.start();
    }
    public void pause(){// pausar a theard  refencia: https://www.guj.com.br/t/duvida-metodo-join-em-thread-respondido-pelo-menos-eu-entendi/211909/3
        try {
            this.isPlaying = false;
            thread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //verificar quando o usuário vai para cima ou para baixo

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: // caso querer ir para baixo
                if(event.getX() < (int)(this.tamanhoX/2))
                {
                    voar.subindo =  true;
                }
                break;
            case MotionEvent.ACTION_UP: // caso querer ir para cima

                voar.subindo = false;
                if(event.getX()>tamanhoX/2)
                {
                    voar.atirar++;
                }

                break;
        }

        return true;

    }

    public void novoTiro() {

        if(!dadosUsuarioPontos.getBoolean("estaMudo",false))
        {
            soundPool.play(sound,1,1,0,0,1);
        }

        Tiro tiro = new Tiro(getResources());
        tiro.x = voar.x + voar.largura;
        tiro.y = voar.y + (voar.largura/2);
        this.tiros.add(tiro);



    }
    private void salvarSePontuacaoForMaior() {

        if(dadosUsuarioPontos.getInt("pontuacao",0)<pontuacao)
        {
            SharedPreferences.Editor editor = dadosUsuarioPontos.edit();
            editor.putInt("pontuacao",pontuacao);
            editor.apply();
        }
    }
}
