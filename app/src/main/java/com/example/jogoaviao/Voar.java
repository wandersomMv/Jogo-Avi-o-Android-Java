package com.example.jogoaviao;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.jogoaviao.GameView.proporcaoDaTelaX;
import static com.example.jogoaviao.GameView.proporcaoDaTelaY;

public class Voar {

    public int atirar= 0;
    int x, y, largura, altura, contadorAviao = 0, contatorTiro =1;
    Bitmap voar1, voar2,tiro1,tiro2,tiro3,tiro4,tiro5, morreu; // duas imagens para o voador
    boolean subindo = false;
    private GameView gameView;
    Voar(GameView gameView, int eixoY, Resources res)
    {
        this.gameView = gameView;

        voar1 = BitmapFactory.decodeResource(res,R.drawable.fly1);  // imagem do avião
        voar2 = BitmapFactory.decodeResource(res,R.drawable.fly2);

        largura = voar1.getWidth();
        altura = voar1.getHeight();

        largura/=4;
        altura/=4;

        // resosulção da tela
        largura = (int)(largura*proporcaoDaTelaX) ;
        altura = (int)(altura*proporcaoDaTelaY);


        voar1 = Bitmap.createScaledBitmap(voar1,largura,altura,false);
        voar2 = Bitmap.createScaledBitmap(voar2,largura,altura,false);

        tiro1 = BitmapFactory.decodeResource(res,R.drawable.shoot1);
        tiro2 = BitmapFactory.decodeResource(res,R.drawable.shoot2);
        tiro3 = BitmapFactory.decodeResource(res,R.drawable.shoot3);
        tiro4 = BitmapFactory.decodeResource(res,R.drawable.shoot4);
        tiro5 = BitmapFactory.decodeResource(res,R.drawable.shoot5);

        tiro1 = Bitmap.createScaledBitmap(tiro1,largura,altura,false);
        tiro2 = Bitmap.createScaledBitmap(tiro2,largura,altura,false);
        tiro3 = Bitmap.createScaledBitmap(tiro3,largura,altura,false);
        tiro4 = Bitmap.createScaledBitmap(tiro4,largura,altura,false);
        tiro5 = Bitmap.createScaledBitmap(tiro5,largura,altura,false);

        morreu = BitmapFactory.decodeResource(res, R.drawable.dead); // faliceu
        morreu = Bitmap.createScaledBitmap(morreu,largura,altura,false);

        y = eixoY/2;
        x = (int)(64 * proporcaoDaTelaX);
    }


    Bitmap getVoar() // alternar as imagens para dar o efeito de voar
    {


        if(atirar!=0)
        {

            switch (contatorTiro){
                case 1:
                    contatorTiro++;
                    return  tiro1;
                case 2:
                    contatorTiro++;
                    return  tiro2;
                case 3:
                    contatorTiro++;
                    return  tiro3;
                case 4:
                    contatorTiro++;
                    return  tiro4;
                case 5:
                    contatorTiro = 1;
                    atirar--;
                    gameView.novoTiro();
                    return  tiro5;

            }
        }

        if(contadorAviao == 0)
        {
            contadorAviao++;
            return  voar1;
        }
        contadorAviao --;
        return voar2;
    }

    Rect getCocoBatelEmAlgo()
    {
        return new Rect(x,y,x+largura,y+altura);
    }

    Bitmap getMorreu()
    {
        return morreu;
    }

}
