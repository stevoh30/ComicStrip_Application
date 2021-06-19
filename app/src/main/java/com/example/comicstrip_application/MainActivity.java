package com.example.comicstrip_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imgExitApp, imgStartComic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgExitApp = findViewById(R.id.imgCloseApplication);
        imgStartComic = findViewById(R.id.imgCreateComic);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.gb);
        imgExitApp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //sound effect - goodbye sound
                mp.start();

                // closing app
                MainActivity.this.finish();
                moveTaskToBack(true);
                //System.exit(0);
            }
        });
        imgStartComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSelectTemplate(view);
            }
        });
    }

    public void OpenSelectTemplate(View v){
        //sound effect
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.fairysparkle);
        mp.start();
        Intent template1Intent = new Intent(this, Activity_Template1.class);
        startActivity(template1Intent);
    }
}