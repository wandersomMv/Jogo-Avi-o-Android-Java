package com.example.jogoaviao;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.jogoaviao.GameView.proporcaoDaTelaX;
import static com.example.jogoaviao.GameView.proporcaoDaTelaY;

public class Tiro {

    int x,y,largura,altura;
    Bitmap tiro;
    Tiro(Resources res){

        tiro = BitmapFactory.decodeResource(res,R.drawable.bullet);

        largura = tiro.getWidth();
        altura = tiro.getHeight();

        largura/=4;
        altura/=4;

        largura = (int)(proporcaoDaTelaX*largura);
        altura =(int)(proporcaoDaTelaY * altura);

        tiro = Bitmap.createScaledBitmap(tiro,largura,altura,false);

    }
    Rect getAcertouTiro()
    {
        return new Rect(x,y,x+largura,y+altura);
    }
}
