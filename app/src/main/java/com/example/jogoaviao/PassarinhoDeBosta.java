package com.example.jogoaviao;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.jogoaviao.GameView.proporcaoDaTelaX;
import static com.example.jogoaviao.GameView.proporcaoDaTelaY;

public class PassarinhoDeBosta {


    public int velocidadeDoCocoVoando = 20;
    public boolean morreuDeVacilao = true;
    int x = 0,y, largura,altura,contCocoPassaro=1;
    Bitmap passaroBosta1,passaroBosta2,passaroBosta3,passaroBosta4;

    PassarinhoDeBosta(Resources res)
    {
        passaroBosta1 = BitmapFactory.decodeResource(res,R.drawable.bird1);
        passaroBosta2 = BitmapFactory.decodeResource(res,R.drawable.bird2);
        passaroBosta3 = BitmapFactory.decodeResource(res,R.drawable.bird3);
        passaroBosta4 = BitmapFactory.decodeResource(res,R.drawable.bird4);

        largura = passaroBosta1.getWidth();
        altura = passaroBosta1.getHeight();

        largura/=6;
        altura/=6;

        altura*=(int)proporcaoDaTelaX;
        largura *=(int)proporcaoDaTelaY;


        passaroBosta1 = Bitmap.createScaledBitmap(passaroBosta1,largura,altura,false);
        passaroBosta2 = Bitmap.createScaledBitmap(passaroBosta2,largura,altura,false);
        passaroBosta3 = Bitmap.createScaledBitmap(passaroBosta3,largura,altura,false);
        passaroBosta4 = Bitmap.createScaledBitmap(passaroBosta4,largura,altura,false);

        y = -altura;
    }
    Bitmap getPassaroBosta()
    {


        if(contCocoPassaro == 1)
        {
            contCocoPassaro++;
            return passaroBosta1;
        }
        if(contCocoPassaro == 2)
        {
            contCocoPassaro++;
            return passaroBosta2;
        }
        if(contCocoPassaro == 3)
        {
            contCocoPassaro++;
            return passaroBosta3;
        }

        contCocoPassaro =1;
        return  passaroBosta4;

    }
    Rect getCocoBatelEmAlgo()
    {
        return new Rect(x,y,x+largura,y+altura);
    }
}
