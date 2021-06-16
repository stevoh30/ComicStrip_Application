package com.example.comicstrip_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


public class Activity_Template1 extends AppCompatActivity {

    //Declare variables
    private ImageView imageViewCreateText, imageViewCreateBubble2, imageViewCreateGraphics, imageViewChangeBackground, imageViewElements;
    private Button btnFlip, btnDeleteTxt, btnMaximize, btnMinimize, btnScreenshot;
    ImageButton btnNextFragment;
    private Dialog dialog;
    private Context context;
    private String m_Text = "";
    private Boolean delete = false;
    private Boolean flip = false;
    private Boolean maximize = false;
    private Boolean minimize = false;
    private Boolean showElements = true;
    ConstraintLayout layout;
    private byte currentFragment;

    private TextView tv;
    int image_ID = 0;
    int tv_ID = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__template1);

        //Initialize variables
        imageViewElements = findViewById(R.id.imgElements);
        imageViewCreateText = findViewById(R.id.imgCreateText);
        imageViewCreateBubble2 = findViewById(R.id.imgCreateBubble2);
        imageViewCreateGraphics = findViewById(R.id.imgCreateGraphics);
        imageViewChangeBackground = findViewById(R.id.imgCreateBackground);
        btnDeleteTxt = findViewById(R.id.btnDelete);
        btnFlip = findViewById(R.id.btnFlipImage);
        btnMaximize = findViewById(R.id.btnLarge);
        btnMinimize = findViewById(R.id.btnMinimize);
        btnScreenshot=findViewById(R.id.stnSreenshot);
        btnNextFragment = findViewById(R.id.imgNextFragment);
        layout = findViewById(R.id.myLayout);
        context = this;
        dialog = new Dialog(this);

        //display fragment_one at the start of this activity
        if(savedInstanceState == null){
            //create a fragment manager
            FragmentManager fmgr = this.getSupportFragmentManager();
            //create a fragment transaction
            FragmentTransaction ft = fmgr.beginTransaction();
            //create the fragment object
            Fragment_ImageLayout1 f1 = new Fragment_ImageLayout1();
            //add fragment to this activity
            //the FragmentTransaction defines methods: add, replace,remove
            ft.add(R.id.frame1,f1,"FRAGMENT_ONE");
            currentFragment = 1;
            //commit
            ft.commit();
        }

        imageViewElements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showElements == true){
                    HideInterface(view);
                    showElements = false;
                }
                else{
                    ShowInterface(view);
                    showElements = true;
                }
            }
        });
        btnNextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a fragment manager
                FragmentManager fmgr = getSupportFragmentManager();
                //create a fragment transaction
                FragmentTransaction ft = fmgr.beginTransaction();
                switch(currentFragment) {
                    case 1:
                        //create the fragment object
                        Fragment_ImageLayout2 f2 = new Fragment_ImageLayout2();
                        //replace fragment to this activity
                        ft.replace(R.id.frame1, f2, "FRAGMENT_TWO");
                        currentFragment = 2;
                        //commit
                        ft.commit();
                        break;
                    case 2:
                        //create the fragment object
                        Fragment_ImageLayout3 f3 = new Fragment_ImageLayout3();
                        //replace fragment to this activity
                        ft.replace(R.id.frame1, f3, "FRAGMENT_THREE");
                        currentFragment = 3;
                        //commit
                        ft.commit();
                        break;
                    case 3:
                        //create the fragment object
                        Fragment_ImageLayout1 f1 = new Fragment_ImageLayout1();
                        //replace fragment to this activity
                        ft.replace(R.id.frame1, f1, "FRAGMENT_ONE");
                        currentFragment = 1;
                        //commit
                        ft.commit();

                }
            }
        });
        verifyStoragePermission(Activity_Template1.this);
        btnScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenShot(getWindow().getDecorView());
            }
        });


        imageViewCreateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Insert Text to be Generated:");

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
                        tv = new TextView(Activity_Template1.this);
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
                                    //delete = false;
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
        imageViewCreateBubble2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCreateDialogOptions();
            }
        });
        imageViewCreateGraphics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCreateGraphicOptions();
            }
        });
        // Default background color
        layout.setBackground(getResources().getDrawable(R.drawable.background_red));
        imageViewChangeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenChangeBackgroundColorOption();
            }
        });
       btnDeleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                //when clicked, user selects object which is removed from the layout
                if(delete == false) {
                    delete = true;
                    btnDeleteTxt.setBackgroundColor(Color.RED);
                    ResetFlipProperties();
                    ResetMinimizeProperties();
                    ResetMaximimizeProperties();
                }
                else {
                    ResetDeleteProperties();
                }
            }
       });
       btnFlip.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(flip == false){
                    flip = true;
                    btnFlip.setBackgroundColor(Color.RED);
                    ResetMaximimizeProperties();
                    ResetMinimizeProperties();
                    ResetDeleteProperties();
               }
               else{
                   ResetFlipProperties();
               }
           }
        });
       btnMaximize.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(maximize == false){
                   ResetMinimizeProperties();
                   ResetDeleteProperties();
                   ResetFlipProperties();
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
                    ResetDeleteProperties();
                    ResetFlipProperties();
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
    // method that calls a dialog to give the users options for which chatboxes they would
    // like to generate
    public void OpenCreateDialogOptions(){
        dialog.setContentView(R.layout.dialog_boxselection);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        ImageView imageCreateChatBox = dialog.findViewById(R.id.imgCreateDBox);
        ImageView imageCreateChatBubble = dialog.findViewById(R.id.imgCreateDBubble);
        ImageView imageCreateChatFrame = dialog.findViewById(R.id.imgCreateDFrame);

        // setting onClickListeners to imageviews
        imageCreateChatBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.dialog_box2);
                //Creates ID and implements onTouchListener to the object
                GenerateImageView(image);
                layout.addView(image);
                dialog.dismiss();
            }
        });
        imageCreateChatBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.dialog_bubble);
                GenerateImageView(image);
                layout.addView(image);
                dialog.dismiss();
            }
        });
        imageCreateChatFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.dialog_frame);
                GenerateImageView(image);
                layout.addView(image);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void OpenCreateGraphicOptions(){
        dialog.setContentView(R.layout.graphic_boxselection);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ImageView imageCreateLightning = dialog.findViewById(R.id.imgCreateGraphic_Lightning);
        ImageView imageCreateOops = dialog.findViewById(R.id.imgCreateGraphic_Oops);
        ImageView imageCreateCool = dialog.findViewById(R.id.imgCreateGraphic_Cool);
        ImageView imageCreateBoom = dialog.findViewById(R.id.imgCreateGraphic_Boom);
        ImageView imageCreatePow = dialog.findViewById(R.id.imgCreateGraphic_Pow);
        ImageView imageCreateZap = dialog.findViewById(R.id.imgCreateGraphic_Zap);

        imageCreateLightning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_lightningbolt);
                GenerateImageView(image);
                layout.addView(image);
                dialog.dismiss();
            }
        });
        imageCreateOops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_oops2);
                GenerateImageView(image);
                layout.addView(image);
                dialog.dismiss();
            }
        });
        imageCreateCool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_cool);
                GenerateImageView(image);
                layout.addView(image);
                dialog.dismiss();
            }
        });
        imageCreateBoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_boom);
                GenerateImageView(image);
                layout.addView(image);
                dialog.dismiss();
            }
        });
        imageCreatePow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_pow);
                GenerateImageView(image);
                layout.addView(image);
                dialog.dismiss();
            }
        });
        imageCreateZap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_zap);
                GenerateImageView(image);
                layout.addView(image);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void GenerateImageView(ImageView image){
        //increments id for every image created
        image.setId(++image_ID);
        image.setClickable(true);
        //adds ontouchlistener event for dragging object
        image.setOnTouchListener(new MyTouchListener1());
        /* add clickListener that allows object to be deleted */
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckGeneratedObjectsOnClickConditions(view, image);
            }
        });
    }
    //method used in onClick function to check for specific conditions, such as flip toggle
    public void CheckGeneratedObjectsOnClickConditions(View view, ImageView image){
        if(delete == true){
            layout.removeView(view);
        }
        //if btnFlip has been pressed, then flip image after click
        if(flip == true){
            image.setImageBitmap
                    (flipImage(((BitmapDrawable) image.getDrawable()).getBitmap()));
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
    public void OpenChangeBackgroundColorOption(){
        dialog.setContentView(R.layout.background_boxselection);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ImageView imageBackgroundRed = dialog.findViewById(R.id.imgBackgroundRed);
        ImageView imageBackgroundViolet = dialog.findViewById(R.id.imgBackgroundViolet);
        ImageView imageBackgroundTeal = dialog.findViewById(R.id.imgBackgroundTeal);
        imageBackgroundRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackground(getResources().getDrawable(R.drawable.background_red));
                dialog.dismiss();
            }
        });
        imageBackgroundViolet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackground(getResources().getDrawable(R.drawable.background_violet));
                dialog.dismiss();
            }
        });
        imageBackgroundTeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackground(getResources().getDrawable(R.drawable.background_teal));
                dialog.dismiss();
            }
        });
        dialog.show();
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
    public void ResetDeleteProperties(){
        delete = false;
        btnDeleteTxt.setBackgroundColor(Color.BLUE);
    }
    public void ResetFlipProperties(){
        flip = false;
        btnFlip.setBackgroundColor(Color.BLUE);
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


    //screenshot part
    private void takeScreenShot(View view) {

        HideInterface(view);
        imageViewElements.setVisibility(view.INVISIBLE);
        //This is used to provide file name with Date a format
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        //It will make sure to store file to given below Directory and If the file Directory dosen't exist then it will create it.
        try {
            File mainDir = new File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }

            //Providing file name along with Bitmap to capture screenview
            String path = mainDir + "/" + "ComicSrip" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);


            //This logic is used to save file at given location with the given filename and compress the Image Quality.
            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            ShowInterface(view);
            imageViewElements.setVisibility(view.VISIBLE);
            //Create New Method to take ScreenShot with the imageFile.
            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void HideInterface(View view){
        imageViewCreateBubble2.setVisibility(view.INVISIBLE);
        imageViewCreateText.setVisibility(view.INVISIBLE);
        btnScreenshot.setVisibility(view.INVISIBLE);
        btnMinimize.setVisibility(view.INVISIBLE);
        btnMaximize.setVisibility(view.INVISIBLE);
        btnFlip.setVisibility(view.INVISIBLE);
        btnDeleteTxt.setVisibility(view.INVISIBLE);
        imageViewChangeBackground.setVisibility(view.INVISIBLE);
        imageViewCreateGraphics.setVisibility(view.INVISIBLE);
        btnNextFragment.setVisibility(view.INVISIBLE);
    }
    private void ShowInterface(View view){
        imageViewCreateBubble2.setVisibility(view.VISIBLE);
        imageViewCreateText.setVisibility(view.VISIBLE);
        btnScreenshot.setVisibility(view.VISIBLE);
        btnMinimize.setVisibility(view.VISIBLE);
        btnMaximize.setVisibility(view.VISIBLE);
        btnFlip.setVisibility(view.VISIBLE);
        btnDeleteTxt.setVisibility(view.VISIBLE);
        imageViewCreateGraphics.setVisibility(view.VISIBLE);
        imageViewChangeBackground.setVisibility(view.VISIBLE);
        btnNextFragment.setVisibility(view.VISIBLE);
    }

    //Share ScreenShot
    private void shareScreenShot(File imageFile) {

        //Using sub-class of Content provider
        Uri uri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + "." + getLocalClassName() + ".provider",
                imageFile);

        //Explicit intent
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "ComicsTrip ScreenShot captor");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        //It will show the application which are available to share Image; else Toast message will throw.
        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
