package com.example.jogoaviao;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    public int x = 0,y = 0; // cordenadas do avião, do jogador
    Bitmap background;
    Background(int eixoXtela, int eixoYtela, Resources res)
    {
        background = BitmapFactory.decodeResource(res,R.drawable.jogo_aviao4);
        background = Bitmap.createScaledBitmap(background,eixoXtela,eixoYtela,false);
    }
}
