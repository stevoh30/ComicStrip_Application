package com.example.comicstrip_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnExitApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExitApp = (Button) findViewById(R.id.btnCloseApplication);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.gb);
        btnExitApp.setOnClickListener(new View.OnClickListener() {

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
    }
    public void OpenSelectTemplate(View v){
        //sound effect
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.fairysparkle);
        mp.start();
        Intent selectTemplateIntent = new Intent(this, Activity_SelectTemplate.class);
        startActivity(selectTemplateIntent);
    }
    public void CloseApplication(){

    }
}