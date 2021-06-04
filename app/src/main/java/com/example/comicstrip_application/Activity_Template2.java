package com.example.comicstrip_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Activity_Template2 extends AppCompatActivity {


    private ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(50,50);


    //private ViewGroup.LayoutParams layoutParams;
    //private android.widget.RelativeLayout.LayoutParams layoutParams;
    //ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.myLayout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__template2);
    }


}
