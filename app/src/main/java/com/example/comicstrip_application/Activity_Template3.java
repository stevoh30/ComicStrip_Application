package com.example.comicstrip_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Template3 extends AppCompatActivity {
    //Declare variables
    private ImageView imageView1, imageView2, imageView3,
            imageViewCreateText, imageViewCreateBubble;
    private Button btnFlip, btnDeleteTxt, btnMaximize, btnMinimize;
    byte imageViewSelector = 0;
    private Context context;
    private String m_Text = "";
    private Boolean delete = false;
    private Boolean flip = false;
    private Boolean maximize = false;
    private Boolean minimize = false;

    Float scalingFactor = 0.75f; // scale down variable


    // Request code gallery


    private TextView tv;
    int image_ID = 0;
    int tv_ID = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__template3);
        //Initialize variables
        imageViewCreateText = findViewById(R.id.imgCreateText);
        imageViewCreateBubble = findViewById(R.id.imgCreateBubble);
        btnDeleteTxt = findViewById(R.id.btnDelete);
        btnFlip = findViewById(R.id.btnFlipImage);
        btnMaximize = findViewById(R.id.btnLarge);
        btnMinimize = findViewById(R.id.btnMinimize);
        ConstraintLayout layout = findViewById(R.id.myLayout);
        context = this;


        imageViewCreateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Insert your text here:");

                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                // Set up the buttons
                //builder.setOnItemSelectedListener()
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //create textview object using input dialog value
                        m_Text = input.getText().toString();
                        tv = new TextView(Activity_Template3.this);
                        tv.setId(--tv_ID);
                        tv.setText(m_Text);
                        tv.setTextSize(18);
                        tv.setTextColor(Color.BLACK);
                        tv.setClickable(true);
                        tv.setPadding(20, 10, 0, 0);
                        tv.setGravity(Gravity.CENTER);
                        tv.setOnTouchListener(new MyTouchListener1());
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(delete == true){
                                    layout.removeView(view);
                                    delete = false;
                                }
                                if(maximize == true){
                                    tv.setScaleX(tv.getScaleX()+.075f);
                                    tv.setScaleY(tv.getScaleY()+.075f);
                                }
                                if(minimize == true){
                                    tv.setScaleX(tv.getScaleX()-.075f);
                                    tv.setScaleY(tv.getScaleY()-.075f);
                                }
                            }
                        });

                        layout.addView(tv);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        // Creates dialogimage with click of button
        imageViewCreateBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(getApplicationContext());
                image.setImageResource(R.drawable.dialog_box2);
                //increments id for every image created
                image.setId(++image_ID);
                image.setClickable(true);
                //image.set
                //adds ontouchlistener event for dragging object
                image.setOnTouchListener(new MyTouchListener1());
                /* add clickListener that allows object to be deleted */
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(delete == true){
                            layout.removeView(view);
                            delete = false;
                        }
                        //if btnFlip has been pressed, then flip image after click
                        if(flip == true){
                            image.setImageBitmap
                                    (flipImage(((BitmapDrawable) image.getDrawable()).getBitmap()));
                            flip = false;
                        }
                        if(maximize == true){
                            image.setScaleX(image.getScaleX()+.075f);
                            image.setScaleY(image.getScaleY()+.075f);
                        }
                        if(minimize == true){
                            image.setScaleX(image.getScaleX()-.075f);
                            image.setScaleY(image.getScaleY()-.075f);
                        }
                    }
                });
                layout.addView(image);
            }
        });
        btnDeleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when clicked, user selects object which is removed from the layout
                delete = true;
            }
        });
        btnFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip = true;
                ResetMaximimizeProperties();
                ResetMinimizeProperties();

            }
        });
        btnMaximize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(maximize == false){
                    ResetMinimizeProperties();
                    maximize = true;
                    btnMaximize.setBackgroundColor(Color.RED);
                }
                else {
                    ResetMaximimizeProperties();
                }
            }
        });
        btnMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(minimize == false){
                    //toggle maximize off
                    //set maximize background color back to blue
                    ResetMaximimizeProperties();
                    //toggles minimize on and background color to red
                    minimize = true;
                    btnMinimize.setBackgroundColor(Color.RED);
                }
                else {
                    ResetMinimizeProperties();
                }
            }
        });

        
    }
    // methods that reset the maximize and minimize properties and
    // change button colors back to default
    public void ResetMinimizeProperties(){
        minimize = false;
        btnMinimize.setBackgroundColor(Color.BLUE);
    }
    public void ResetMaximimizeProperties(){
        maximize = false;
        btnMaximize.setBackgroundColor(Color.BLUE);
    }
    // method that allows for imageviews to be flipped horizontally
    private Bitmap flipImage(Bitmap image_bitmap) {

        // create new matrix for transformation
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);

        Bitmap flipped_bitmap =
                Bitmap.createBitmap
                        (image_bitmap, 0, 0, image_bitmap.getWidth(), image_bitmap.getHeight(), matrix, true);

        return flipped_bitmap;
    }




}