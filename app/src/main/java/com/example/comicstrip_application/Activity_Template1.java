package com.example.comicstrip_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Activity_Template1 extends AppCompatActivity {

    //declare variables
    ImageView imageView1, imageView2, imageView3;
    byte imageViewSelector = 0;

    // Request code gallery
    private static final int GALLERY_REQUEST = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__template1);

        //Initialize variables
        imageView1 = findViewById(R.id.imageViewPhoto1);
        imageView2 = findViewById(R.id.imageViewPhoto2);
        imageView3 = findViewById(R.id.imageViewPhoto3);

        // create onClick functionality for imageviews to operate as buttons
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewSelector = 1;
                getImageFromGalleryPermissions();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewSelector = 2;
                getImageFromGalleryPermissions();
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewSelector = 3;
                getImageFromGalleryPermissions();
            }
        });
    }

    // Permissions
    //callback from the requestPermissions dialog ===============================================
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check grantResults to find out if user clicked on grant or deny
        if (requestCode == GALLERY_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getImageFromGallery();
//            }else if (requestCode == CAMERA_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                capturePictureFromCamera();
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
    }
}
