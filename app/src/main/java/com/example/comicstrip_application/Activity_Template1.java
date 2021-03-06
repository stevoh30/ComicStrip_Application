package com.example.comicstrip_application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
    private ImageView imageViewCreateText, imageViewCreateBubble2, imageViewCreateGraphics, imageViewChangeBackground, imageViewElements, imageViewEditTools, imageViewCreateGraphics2;
    private Button btnFlip, btnDeleteTxt, btnMaximize, btnMinimize, btnScreenshot;
    ImageButton btnNextFragment;
    private Dialog dialog;
    private Context context;
    private String m_Text = "";
    Boolean fontChose = false;
    private Boolean delete = false;
    private Boolean flip = false;
    private Boolean maximize = false;
    private Boolean minimize = false;
    private Boolean showElements = true;
    private Boolean minimizeEditTools = false;
    ConstraintLayout layout;
    private byte currentFragment;
    //font
    private Typeface typeAlexBrush, typeChunkFive, typeGrandHotel, typeGreatVibes, typeKaushan, typeLato, typeOswald, typePacifico, typeWingSong, typeDefault;

    private TextView tv;
    int image_ID = 0;
    int tv_ID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__template1);

        //Initialize variables
        imageViewEditTools = findViewById(R.id.imgMinimizeEdits);
        imageViewElements = findViewById(R.id.imgElements);
        imageViewCreateText = findViewById(R.id.imgCreateText);
        imageViewCreateBubble2 = findViewById(R.id.imgCreateBubble2);
        imageViewCreateGraphics = findViewById(R.id.imgCreateGraphics);
        imageViewCreateGraphics2 = findViewById(R.id.imgCreateGraphics2);
        imageViewChangeBackground = findViewById(R.id.imgCreateBackground);
        btnDeleteTxt = findViewById(R.id.btnDelete);
        btnFlip = findViewById(R.id.btnFlipImage);
        btnMaximize = findViewById(R.id.btnLarge);
        btnMinimize = findViewById(R.id.btnMinimize);
        btnScreenshot=findViewById(R.id.btnShare);
        btnNextFragment = findViewById(R.id.imgNextFragment);
        layout = findViewById(R.id.myLayout);
        context = this;
        dialog = new Dialog(this);
        //font resources
        typeDefault= getResources().getFont(R.font.alexbrush_regular);
        typeAlexBrush = getResources().getFont(R.font.alexbrush_regular);
        typeChunkFive = getResources().getFont(R.font.chunkfive_print);
        typeGrandHotel = getResources().getFont(R.font.grandhotel_regular);
        typeGreatVibes = getResources().getFont(R.font.greatvibes_regular);
        typeKaushan = getResources().getFont(R.font.kaushanscript_regular);
        typeLato = getResources().getFont(R.font.lato_semibold);
        typeOswald = getResources().getFont(R.font.oswald_heavy);
        typePacifico = getResources().getFont(R.font.pacifico);
        typeWingSong = getResources().getFont(R.font.windsong);

        //display fragment at the start of this activity
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
        //button hides interface elements
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
        //button switches imageview layout fragment
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
                        Fragment_ImageLayout3 f3 = new Fragment_ImageLayout3();
                        ft.replace(R.id.frame1, f3, "FRAGMENT_THREE");
                        currentFragment = 3;
                        //commit
                        ft.commit();
                        break;
                    case 3:
                        Fragment_ImageLayout1 f1 = new Fragment_ImageLayout1();
                        ft.replace(R.id.frame1, f1, "FRAGMENT_ONE");
                        currentFragment = 1;
                        //commit
                        ft.commit();

                }
            }
        });
        //button calls function to share image to social media platform
        verifyStoragePermission(Activity_Template1.this);
        btnScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenShot(getWindow().getDecorView());
            }
        });
        // button calls upon font selection during generate textview
        imageViewCreateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fontChose == false) {
                    OpenCreateTextDialogOption();
                    OpenTextFontDialogOption();
                    }
                else{
                    OpenCreateTextDialogOption();
                }
            }
        });
        // button generates dialog to create dialog bubbles
        imageViewCreateBubble2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCreateDialogOptions();
            }
        });
        // button generates dialog to create image graphics
        imageViewCreateGraphics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCreateGraphicOptions();
            }
        });
        // button generates dialog to create image graphics2
        imageViewCreateGraphics2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCreateGraphicOptions2();
            }
        });
        // Default background color
        // button allows user to change background image/color
        layout.setBackground(getResources().getDrawable(R.drawable.background_red));
        imageViewChangeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenChangeBackgroundColorOption();
            }
        });
        // button to delete generated objects
       btnDeleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                //when clicked, user selects object which is removed from the layout
                if(delete == false) {
                    delete = true;
                    btnDeleteTxt.setForeground(getDrawable(R.drawable.deleteclick_icon));
                    ResetFlipProperties();
                    ResetMinimizeProperties();
                    ResetMaximimizeProperties();
                }
                else {
                    ResetDeleteProperties();
                }
            }
       });
       // button to mirror generated objects
       btnFlip.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(flip == false){
                    flip = true;
                    //btnFlip.setBackgroundColor(Color.RED);
                   btnFlip.setForeground(getDrawable(R.drawable.flipclick_icon));
                    ResetMaximimizeProperties();
                    ResetMinimizeProperties();
                    ResetDeleteProperties();
               }
               else{
                   ResetFlipProperties();
               }
           }
        });
       // button to increase object scale
       btnMaximize.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(maximize == false){
                   ResetMinimizeProperties();
                   ResetDeleteProperties();
                   ResetFlipProperties();
                   maximize = true;
                   //btnMaximize.setBackgroundColor(Color.RED);
                   btnMaximize.setForeground(getDrawable(R.drawable.plusclick_icon));
               }
               else {
                   ResetMaximimizeProperties();
               }
           }
       });
       // button to decrease object scale
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
                    btnMinimize.setForeground(getDrawable(R.drawable.negativeclick_icon));
                }
                else {
                    ResetMinimizeProperties();
                }
            }
        });
        //button created to minimize edit tool options by making them hidden
        imageViewEditTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(minimizeEditTools == false){
                    btnMinimize.setVisibility(view.INVISIBLE);
                    btnMaximize.setVisibility(view.INVISIBLE);
                    btnFlip.setVisibility(view.INVISIBLE);
                    btnDeleteTxt.setVisibility(view.INVISIBLE);
                    minimizeEditTools = true;
                    imageViewEditTools.setBackground(getDrawable(R.drawable.arrowup_icon));
                    //reset button values
                    ResetFlipProperties();
                    ResetMinimizeProperties();
                    ResetMaximimizeProperties();
                    ResetDeleteProperties();
                }
                else{
                    btnMinimize.setVisibility(view.VISIBLE);
                    btnMaximize.setVisibility(view.VISIBLE);
                    btnFlip.setVisibility(view.VISIBLE);
                    btnDeleteTxt.setVisibility(view.VISIBLE);
                    minimizeEditTools = false;
                    imageViewEditTools.setBackground(getDrawable(R.drawable.arrowdown_icon));
                }
            }
        });
    }
    //dialog box for choosing text font
    private void OpenTextFontDialogOption(){
        final String[] options = getResources().getStringArray(R.array.font_options);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Text Font:").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        typeDefault = getResources().getFont(R.font.alexbrush_regular);
                        fontChose=true;
                        break;
                    case 1:

                        typeDefault = getResources().getFont(R.font.chunkfive_print);
                        fontChose=true;
                        break;
                    case 2:

                        typeDefault = getResources().getFont(R.font.grandhotel_regular);
                        fontChose=true;
                        break;
                    case 3:

                        typeDefault = getResources().getFont(R.font.greatvibes_regular);
                        fontChose=true;
                        break;
                    case 4:

                        typeDefault = getResources().getFont(R.font.kaushanscript_regular);
                        fontChose=true;
                        break;
                    case 5:

                        typeDefault = getResources().getFont(R.font.lato_semibold);
                        fontChose=true;
                        break;
                    case 6:

                        typeDefault = getResources().getFont(R.font.oswald_heavy);
                        fontChose=true;
                        break;
                    case 7:

                        typeDefault = getResources().getFont(R.font.pacifico);
                        fontChose=true;
                        break;
                    case 8:

                        typeDefault = getResources().getFont(R.font.windsong);
                        fontChose=true;
                        break;
                }
            }
        });

        builder.show();
    }
    //dialog box to create a text
    private void OpenCreateTextDialogOption(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Insert Text to be Generated:");

        // Set up the input
        final EditText input = new EditText(context);
        final String[] options = getResources().getStringArray(R.array.font_options);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        // Set up the buttons
        //builder.setOnItemSelectedListener()
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //reset the font
                fontChose = false;
                //create textview object using input dialog value
                m_Text = input.getText().toString();
                tv = new TextView(Activity_Template1.this);
                tv.setX(450);
                tv.setY(700);
                tv.setId(--tv_ID);
                tv.setText(m_Text);
                tv.setTextSize(20);
                tv.setTextColor(Color.BLACK);
                tv.setClickable(true);
                tv.setPadding(20, 10, 0, 0);
                tv.setGravity(Gravity.CENTER);
                tv.setOnTouchListener(new MyTouchListener1());
                tv.setOnClickListener(new View.OnClickListener() {
                    //tv.setTypeface(typeAlexBrush);
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
                //final String[] options = getResources().getStringArray(R.array.image_options);

                tv.setTypeface(typeDefault);
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
    // function to generate dialog boxes onto layout
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
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateChatBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.dialog_bubble);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateChatFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.dialog_frame);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // function to generate graphic objects onto layout
    public void OpenCreateGraphicOptions(){
        dialog.setContentView(R.layout.graphic_boxselection);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ImageView imageCreateLike = dialog.findViewById(R.id.CreateGraphic_Like);
        ImageView imageCreateOops = dialog.findViewById(R.id.imgCreateGraphic_Oops);
        ImageView imageCreateCool = dialog.findViewById(R.id.imgCreateGraphic_Cool);
        ImageView imageCreateBoom = dialog.findViewById(R.id.imgCreateGraphic_Boom);
        ImageView imageCreatePow = dialog.findViewById(R.id.imgCreateGraphic_Pow);
        ImageView imageCreateZap = dialog.findViewById(R.id.imgCreateGraphic_Zap);

        imageCreateLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_like);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateOops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_oops);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateCool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_cool);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateBoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_boom);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreatePow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_pow);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateZap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_zap);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void OpenCreateGraphicOptions2() {
        dialog.setContentView(R.layout.graphic2_boxselection);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ImageView imageCreateEye = dialog.findViewById(R.id.CreateGraphic_Eye);
        ImageView imageCreateGlasses = dialog.findViewById(R.id.imgCreateGraphic_Glasses);
        ImageView imageCreateMoustache = dialog.findViewById(R.id.imgCreateGraphic_Moustache);
        ImageView imageCreateLightning = dialog.findViewById(R.id.imgCreateGraphic_Lightning);
        ImageView imageCreateCloud = dialog.findViewById(R.id.imgCreateGraphic_Cloud);
        ImageView imageCreateBanana = dialog.findViewById(R.id.imgCreateGraphic_Banana);
        ImageView imageCreateBone = dialog.findViewById(R.id.imgCreateGraphic_Bone);
        ImageView imageCreateBow = dialog.findViewById(R.id.imgCreateGraphic_Bow);
        ImageView imageCreateNote = dialog.findViewById(R.id.imgCreateGraphic_Note);

        imageCreateEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_eyeballs);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateGlasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_glasses);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateMoustache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_moustache);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateLightning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_lightningbolt);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_cloud);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateBanana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_banana);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateBone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_bone);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateBow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_bow);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        imageCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = new ImageView(context);
                image.setImageResource(R.drawable.graphic_notes);
                CreateImageView(image);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    // function to generate imageview and place on layout coordinates (center of layout)
    public void CreateImageView(ImageView image){
        GenerateImageView(image);
        layout.addView(image);
        image.setX(400);
        image.setY(700);
    }
    // function that attaches onClick features to generated objects
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
    // function to change background color/image
    public void OpenChangeBackgroundColorOption(){
        dialog.setContentView(R.layout.background_boxselection);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ImageView imageBackgroundRed = dialog.findViewById(R.id.imgBackgroundRed);
        ImageView imageBackgroundViolet = dialog.findViewById(R.id.imgBackgroundViolet);
        ImageView imageBackgroundTeal = dialog.findViewById(R.id.imgBackgroundTeal);
        ImageView imageBackgroundDesignPet = dialog.findViewById(R.id.imgBackgroundDesign1);
        ImageView imageBackgroundDesign1 = dialog.findViewById(R.id.imgBackgroundDesign2);
        ImageView imageBackgroundDesign2 = dialog.findViewById(R.id.imgBackgroundDesign3);
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
        imageBackgroundDesignPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackground(getResources().getDrawable(R.drawable.background_pet2));
                dialog.dismiss();
            }
        });
        imageBackgroundDesign1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackground(getResources().getDrawable(R.drawable.background_design1));
                dialog.dismiss();
            }
        });
        imageBackgroundDesign2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackground(getResources().getDrawable(R.drawable.background_design2));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    // methods that reset the maximize and minimize properties and
    // change button colors back to default
    public void ResetMinimizeProperties(){
        minimize = false;
        //btnMinimize.setBackgroundColor(Color.BLUE);
        btnMinimize.setForeground(getDrawable(R.drawable.negative_icon));
    }
    public void ResetMaximimizeProperties(){
        maximize = false;
        //btnMaximize.setBackgroundColor(Color.BLUE);
        btnMaximize.setForeground(getDrawable(R.drawable.plus_icon));
    }
    public void ResetDeleteProperties(){
        delete = false;
        //btnDeleteTxt.setBackgroundColor(Color.BLUE);
        btnDeleteTxt.setForeground(getDrawable(R.drawable.delete_icon));;
    }
    public void ResetFlipProperties(){
        flip = false;
        //btnFlip.setBackgroundColor(Color.BLUE);
        btnFlip.setForeground(getDrawable(R.drawable.flip_icon));
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
    //method used to save image in path and share on selected social media application
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

            //save file at given location with the given filename and compress the Image Quality.
            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            ShowInterface(view);
            imageViewElements.setVisibility(view.VISIBLE);
            //sharing the image.
            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // function used to hide GUI elements
    private void HideInterface(View view){
        imageViewCreateBubble2.setVisibility(view.INVISIBLE);
        imageViewCreateText.setVisibility(view.INVISIBLE);
        imageViewEditTools.setVisibility(view.INVISIBLE);
        btnScreenshot.setVisibility(view.INVISIBLE);
        btnMinimize.setVisibility(view.INVISIBLE);
        btnMaximize.setVisibility(view.INVISIBLE);
        btnFlip.setVisibility(view.INVISIBLE);
        btnDeleteTxt.setVisibility(view.INVISIBLE);
        imageViewChangeBackground.setVisibility(view.INVISIBLE);
        imageViewCreateGraphics.setVisibility(view.INVISIBLE);
        imageViewCreateGraphics2.setVisibility(view.INVISIBLE);
        btnNextFragment.setVisibility(view.INVISIBLE);
    }
    private void ShowInterface(View view){
        imageViewCreateBubble2.setVisibility(view.VISIBLE);
        imageViewCreateText.setVisibility(view.VISIBLE);
        imageViewEditTools.setVisibility(view.VISIBLE);
        btnScreenshot.setVisibility(view.VISIBLE);
        btnMinimize.setVisibility(view.VISIBLE);
        btnMaximize.setVisibility(view.VISIBLE);
        btnFlip.setVisibility(view.VISIBLE);
        btnDeleteTxt.setVisibility(view.VISIBLE);
        imageViewCreateGraphics.setVisibility(view.VISIBLE);
        imageViewCreateGraphics2.setVisibility(view.VISIBLE);
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

        //this will provide the Sharing GUI.
        //send message if it cannot find any app to share
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
