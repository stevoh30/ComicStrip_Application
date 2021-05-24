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
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class Activity_Template1 extends AppCompatActivity {

    //Declare variables
    private ImageView imageView1, imageView2, imageView3,
            imageViewCreateText, imageViewCreateBubble;
    private Button btnCreateTxt, btnDeleteTxt;
    byte imageViewSelector = 0;
    private Context context;
    private String m_Text = "";
    private Boolean delete = false;


    // Request code gallery
    public static final int REQUEST_STORAGE_CODE = 1;
    public static final int REQUEST_STORAGE2_CODE = 3;
    private static final int GALLERY_REQUEST = 9;
    private static final int CAMERA_REQUEST = 11;

    private TextView tv;
    int image_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__template1);

        //Initialize variables
        imageView1 = findViewById(R.id.imageViewPhoto1);
        imageView2 = findViewById(R.id.imageViewPhoto2);
        imageView3 = findViewById(R.id.imageViewPhoto3);
        imageViewCreateText = findViewById(R.id.imgCreateText);
        imageViewCreateBubble = findViewById(R.id.imgCreateBubble);
        btnDeleteTxt = findViewById(R.id.btnDelete);
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
                        tv = new TextView(Activity_Template1.this);
                        tv.setText(m_Text);
                        tv.setTextSize(18);
                        tv.setTextColor(Color.BLACK);
                        tv.setClickable(true);
                        tv.setPadding(20, 10, 0, 0);
                        tv.setGravity(Gravity.CENTER);
                        tv.setOnTouchListener(new MyTouchListener());

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
                image.setImageResource(R.drawable.dialogbox);
                //increments id for every image created
                image.setId(++image_ID);
                //image.set
                //adds ontouchlistener event for dragging object
                image.setOnTouchListener(new MyTouchListener());
                layout.addView(image);
            }
        });
       btnDeleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
            tv.setText("");

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
    }
    private void removeSelectedObject(){
        //when clicked, user selects object which is removed from the layout

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
                                if(ContextCompat.checkSelfPermission(Activity_Template1.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
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
                                        Toast.makeText(Activity_Template1.this,"grant it this permission will allow you to access storage from this app", Toast.LENGTH_LONG).show();
                                    }
                                    /**
                                     * permission is not already granted. request it
                                     */
                                    //request it
                                    ActivityCompat.requestPermissions(Activity_Template1.this,
                                            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                            REQUEST_STORAGE_CODE);
                                }

                                break;
                            case 1:
                                if(ContextCompat.checkSelfPermission(Activity_Template1.this, Manifest.permission.WRITE_EXTERNAL_STORAGE )== PackageManager.PERMISSION_GRANTED)
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
                                        Toast.makeText(Activity_Template1.this,"grant it this permission will allow you to make some change from this app",
                                                Toast.LENGTH_LONG).show();
                                    }

                                    /**
                                     * permission is not already granted. request it
                                     */

                                    //request it
                                    ActivityCompat.requestPermissions(Activity_Template1.this,
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
class MyTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float xDown =0, yDown=0;
        switch(motionEvent.getActionMasked()){
            //user pressed down on object
            case MotionEvent.ACTION_DOWN:
                xDown = motionEvent.getX();
                yDown = motionEvent.getY();
                break;
            //user moves object
            case MotionEvent.ACTION_MOVE:
                float movedX, movedY;
                movedX = motionEvent.getX();
                movedY = motionEvent.getY();

                //calculates distance from down to move
                float distanceX = movedX - xDown;
                float distanceY = movedY - yDown;

                //move view to position
                view.setX(view.getX()+distanceX);
                view.setY(view.getY()+distanceY);

                //set values for next move
                xDown=movedX;
                yDown=movedY;

                break;
        }
        return false;
    }
}
