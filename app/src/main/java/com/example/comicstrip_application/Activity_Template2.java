package com.example.comicstrip_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Template2 extends AppCompatActivity {


    // declare variables
    ImageView imageEdit, imageReset, imageConfirm;
    Button btnRed, btnGreen, btnBlue, btnCyan, btnYellow, btnBright, btnDarken;
    int color;
    Boolean lighten, darken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__template2);

        // initialization
        lighten = false;
        darken = true;

        imageEdit = findViewById(R.id.imageViewEdit);
        btnRed = findViewById(R.id.btnChangeRed);
        btnBlue = findViewById(R.id.btnChangeBlue);
        btnGreen = findViewById(R.id.btnChangeGreen);
        btnCyan = findViewById(R.id.btnChangeCyan);
        btnYellow = findViewById(R.id.btnChangeYellow);
        btnDarken = findViewById(R.id.btnChangeDarken);
        btnBright = findViewById(R.id.btnChangeBright);
        imageReset = findViewById(R.id.imgReset);
        imageConfirm = findViewById(R.id.imgConfirm);

        // buttons to filter color of imageview ----------------------------------
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = Color.RED;
                SwitchImageColor(color);
            }
        });
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = Color.BLUE;
                SwitchImageColor(color);
            }
        });
        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = Color.GREEN;
                SwitchImageColor(color);
            }
        });
        btnCyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = Color.CYAN;
                SwitchImageColor(color);
            }
        });
        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = Color.YELLOW;
                SwitchImageColor(color);
            }
        });
        btnBright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleFilterSettingsLight();
            }
        });
        btnDarken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleFilterSettingsDark();
            }
        });
        // resets color of imageview to default
        imageReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = Color.WHITE;
                ToggleFilterSettingsDark();
                imageEdit.setColorFilter(Color.WHITE, PorterDuff.Mode.DARKEN);
            }
        });

        //collect data from fragment1 and set file to imageview 
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("bitmap")) {
            Bitmap bitmap = extras.getParcelable("bitmap");
            imageEdit.setImageBitmap(bitmap);
        } else {
            Uri uri = extras.getParcelable("uri");
            imageEdit.setImageURI(uri);
        }

        //pass color properties back to fragment
        imageConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //variable to determine if image was darkened or lightened
                Boolean light;
                if(lighten == true){
                    light = true;
                }
                else {
                    light = false;
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("color", color);
                returnIntent.putExtra("light", light);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
    // image editor functions --------------------------------------------
    private void ToggleFilterSettingsDark(){
        darken = true;
        btnDarken.setForeground(getDrawable(R.drawable.darkclick_icon));
        lighten = false;
        btnBright.setForeground(getDrawable(R.drawable.bright_icon));
    }
    private void ToggleFilterSettingsLight(){
        darken = false;
        btnDarken.setForeground(getDrawable(R.drawable.dark_icon));
        lighten = true;
        btnBright.setForeground(getDrawable(R.drawable.brightclick_icon));
    }
    // function to switch imageColor and change filters depending on selection
    private void SwitchImageColor(int c){
        if(lighten == true){
            imageEdit.setColorFilter(c, PorterDuff.Mode.OVERLAY);
        }
        else if(darken == true) {
            imageEdit.setColorFilter(c, PorterDuff.Mode.DARKEN);
        }
    }

}
