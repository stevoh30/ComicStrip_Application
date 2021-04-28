package com.example.comicstrip_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void OpenSelectTemplate(View v){
        Intent selectTemplateIntent = new Intent(this, Activity_SelectTemplate.class);
        startActivity(selectTemplateIntent);
    }
}