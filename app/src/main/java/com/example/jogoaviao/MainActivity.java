package com.example.jogoaviao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    boolean estaMudo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });


        TextView recorde = findViewById(R.id.recorde);
        final SharedPreferences prefs = getSharedPreferences("jogo",MODE_PRIVATE);
        recorde.setText("RECORDE: "+prefs.getInt("pontuacao",0));

        estaMudo = prefs.getBoolean("estaMudo", false);

        final ImageView volumeCtrl  = findViewById(R.id.volumeCtrl);
        if(estaMudo)
            volumeCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
        else
            volumeCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);

        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estaMudo =!estaMudo;
                if(estaMudo)
                    volumeCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
                else
                    volumeCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("estaMudo",estaMudo);
                editor.apply();

            }
        });


    }
}
