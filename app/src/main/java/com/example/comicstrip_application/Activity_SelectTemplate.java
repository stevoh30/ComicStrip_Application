package com.example.comicstrip_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity_SelectTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__select_template);
    }
    public void OpenTemplate1(View v){
        Intent template1Intent = new Intent(this, Activity_Template1.class);
        startActivity(template1Intent);
    }
}