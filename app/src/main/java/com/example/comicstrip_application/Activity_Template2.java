package com.example.comicstrip_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Template2 extends AppCompatActivity {


    private ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(50,50);

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
    public static final int REQUEST_STORAGE_CODE = 1;
    public static final int REQUEST_STORAGE2_CODE = 3;
    private static final int GALLERY_REQUEST = 9;
    private static final int CAMERA_REQUEST = 11;

    private TextView tv;
    int image_ID = 0;
    int tv_ID = 1000;
    //private ViewGroup.LayoutParams layoutParams;
    //private android.widget.RelativeLayout.LayoutParams layoutParams;
    //ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.myLayout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__template2);

    
        //Initialize variables
        imageView1 = findViewById(R.id.imageViewPhoto21);
        imageView2 = findViewById(R.id.imageViewPhoto22);
        imageView3 = findViewById(R.id.imageViewPhoto23);
        imageViewCreateText = findViewById(R.id.imgCreateText);
        imageViewCreateBubble = findViewById(R.id.imgCreateBubble);
        btnDeleteTxt = findViewById(R.id.btnDelete);
        btnFlip = findViewById(R.id.btnFlipImage);
        btnMaximize = findViewById(R.id.btnLarge);
        btnMinimize = findViewById(R.id.btnMinimize);
        ConstraintLayout layout = findViewById(R.id.myLayoutT2);
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
                        tv = new TextView(Activity_Template2.this);
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

        // create onClick functionality for imageviews to operate as buttons
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewSelector = 1;
                //getImageFromGalleryPermissions();
                showImageOptionDialog();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewSelector = 2;
                //getImageFromGalleryPermissions();
                showImageOptionDialog();
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewSelector = 3;
                //getImageFromGalleryPermissions();
                showImageOptionDialog();
            }
        });
       // Button btn = findViewById(R.id.btnPractice);

        //findViewById(R.id.btnPractice).setOnTouchListener(new MyTouchListener1());
        //findViewById(R.id.btnPractice).setOnDragListener(new MyDragListener1());
//        btn.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                float xDown =0, yDown=0;
//                switch(motionEvent.getActionMasked()){
//                    //user pressed down on object
//                    case MotionEvent.ACTION_DOWN:
//                        xDown = motionEvent.getX();
//                        yDown = motionEvent.getY();
//                        break;
//                    //user moves object
//                    case MotionEvent.ACTION_MOVE:
//                        float movedX, movedY;
//                        movedX = motionEvent.getX();
//                        movedY = motionEvent.getY();
//
//                        //calculates distance from down to move
//                        float distanceX = movedX - xDown;
//                        float distanceY = movedY - yDown;
//
//                        //move view to position
//                        btn.setX(btn.getX()+distanceX);
//                        btn.setY(btn.getY()+distanceY);
//
//                        //set values for next move
//                        xDown=movedX;
//                        yDown=movedY;
//
//                        break;
//
//                }
//                return false;
//            }
//        });

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

    // create image dialog box, allowing user to choose between gallery and taking photo
    private void showImageOptionDialog(){
        final String[] options = getResources().getStringArray(R.array.image_options);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.dialog_image_options)
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                if(ContextCompat.checkSelfPermission(Activity_Template2.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                                {

                                    getImageFromGallery();

                                }else{
                                    if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                                        /**
                                         return false if user asked click on "Don't ask me again" box
                                         or the permission is dissable on the device
                                         your dialog with user won't be displayed
                                         */
                                        /**
                                         * returns true, if the user rejected before but is trying to access again
                                         * then your dialog to convince the user why they should grant it, will show
                                         */
                                        Toast.makeText(Activity_Template2.this,"grant it this permission will allow you to access storage from this app", Toast.LENGTH_LONG).show();
                                    }
                                    /**
                                     * permission is not already granted. request it
                                     */
                                    //request it
                                    ActivityCompat.requestPermissions(Activity_Template2.this,
                                            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                            REQUEST_STORAGE_CODE);
                                }

                                break;
                            case 1:
                                if(ContextCompat.checkSelfPermission(Activity_Template2.this, Manifest.permission.WRITE_EXTERNAL_STORAGE )== PackageManager.PERMISSION_GRANTED)
                                {

                                    capturePictureFromCamera();

                                }else{
                                    if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                        /**
                                         return false if user asked click on "Don't ask me again" box
                                         or the permission is dissable on the device
                                         your dialog with user won't be displayed
                                         */
                                        /**
                                         * returns true, if the user rejected before but is trying to access again
                                         * then your dialog to convince the user why they should grant it, will show
                                         */
                                        Toast.makeText(Activity_Template2.this,"grant it this permission will allow you to make some change from this app",
                                                Toast.LENGTH_LONG).show();
                                    }

                                    /**
                                     * permission is not already granted. request it
                                     */

                                    //request it
                                    ActivityCompat.requestPermissions(Activity_Template2.this,
                                            new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                            REQUEST_STORAGE2_CODE);
                                }
                                break;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Permissions
    //callback from the requestPermissions dialog ===============================================
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check grantResults to find out if user clicked on grant or deny
        if(requestCode ==REQUEST_STORAGE_CODE){
            //check grantResults to find out if user clicked on grant or deny
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getImageFromGallery();
            }else{
                Toast.makeText(this,"permission denied. won't be able to use the phone app",Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode ==REQUEST_STORAGE2_CODE){
            //check grantResults to find out if user clicked on grant or deny
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getImageFromGallery();
            }else{
                Toast.makeText(this,"permission denied. won't be able to use the phone app",Toast.LENGTH_LONG).show();
            }
        }
    }

    //Open phone gallery ==================================================================
    private void getImageFromGalleryPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            getImageFromGallery();
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "grant it this permission will allow you to choose pictures",
                        Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    GALLERY_REQUEST);
        }
    }

    private void getImageFromGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }
    private void capturePictureFromCamera(){
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
    // Activity Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Check if the intent was to pick image, was successful and an image was picked
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            //Get selected image uri here
            Uri imageUri = data.getData();
            // uses imageViewSelector variable to determine what imageview to populate
            switch(imageViewSelector) {
                case 1:
                    imageView1.setImageURI(imageUri);
                    break;
                case 2:
                    imageView2.setImageURI(imageUri);
                    break;
                case 3:
                    imageView3.setImageURI(imageUri);
                    break;
            }
        }
        else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            switch(imageViewSelector) {
                case 1:
                    //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView1.setImageBitmap(bitmap);
                    break;
                case 2:
                    //Bitmap bitmap2 = (Bitmap) data.getExtras().get("data");
                    imageView2.setImageBitmap(bitmap);
                    break;
                case 3:
                    // Bitmap bitmap3 = (Bitmap) data.getExtras().get("data");
                    imageView3.setImageBitmap(bitmap);
                    break;
            }
        }
    }
}
//class MyTouchListener1 implements View.OnTouchListener {
//    //creates shadowbox when dragging object
//    @Override
//    public boolean onTouch(View v, MotionEvent e) {
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
//                v.startDrag(null, shadowBuilder, v, 0);
//                v.setVisibility((v.INVISIBLE));
//                return true;
//        }
//        return false;
//    }
//}
//creates coordinates for targeted drop for object upon release click

class MyDragListener1 implements View.OnDragListener {
    private static final String msg = "Works";
    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(50,50);
    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch(event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                layoutParams = (ConstraintLayout.LayoutParams)v.getLayoutParams();
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");
                // Do nothing
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED");
                int x_cord = (int) event.getX();
                int y_cord = (int) event.getY();
                break;

            case DragEvent.ACTION_DRAG_EXITED :
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED");
                x_cord = (int) event.getX();
                y_cord = (int) event.getY();
                layoutParams.leftMargin = x_cord;
                layoutParams.topMargin = y_cord;
                v.setLayoutParams(layoutParams);
                break;

            case DragEvent.ACTION_DRAG_LOCATION  :
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION");
                x_cord = (int) event.getX();
                y_cord = (int) event.getY();
                break;

            case DragEvent.ACTION_DRAG_ENDED   :
                Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");
                v.setVisibility(View.VISIBLE);
                // Do nothing
                break;

            case DragEvent.ACTION_DROP:
                Log.d(msg, "ACTION_DROP event");

                // Do nothing
                break;
            default: break;
        }
        return true;
    }



}
