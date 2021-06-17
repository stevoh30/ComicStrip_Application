package com.example.comicstrip_application;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link Fragment_ImageLayout1#newInstance} factory method to
// * create an instance of this fragment.
// */
public class Fragment_ImageLayout1 extends Fragment {

    // Request code gallery
    public static final int REQUEST_STORAGE_CODE = 1;
    public static final int REQUEST_STORAGE2_CODE = 3;
    private static final int GALLERY_REQUEST = 9;
    private static final int CAMERA_REQUEST = 11;
    byte imageViewSelector = 0;
    Boolean image1Populated = false;
    Boolean image2Populated = false;
    Boolean image3Populated = false;
    Boolean image1Camera, image2Camera, image3Camera;
    Uri image1Uri, image2Uri, image3Uri;
    private ImageView imageView_Photo1, imageView_Photo2, imageView_Photo3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment__image_layout1, container,false);
        imageView_Photo1 = view.findViewById(R.id.imgPhoto1);
        imageView_Photo2 = view.findViewById(R.id.imgPhoto2);
        imageView_Photo3 = view.findViewById(R.id.imgPhoto3);

        // create onClick functionality for imageviews to operate as buttons
        imageView_Photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewSelector = 1;
                //getImageFromGalleryPermissions();
                if(image1Populated == false) {
                    showImageOptionDialog();
                }
                else{
                    showImageOptionDialog2();
                }
            }
        });
        imageView_Photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewSelector = 2;
                //getImageFromGalleryPermissions();
                if(image2Populated == false) {
                    showImageOptionDialog();
                }
                else{
                    showImageOptionDialog2();
                }
            }
        });
        imageView_Photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewSelector = 3;
                //getImageFromGalleryPermissions();
                if(image3Populated == false) {
                    showImageOptionDialog();
                }
                else{
                    showImageOptionDialog2();
                }
            }
        });
        return view;

    }
    //create image dialog box, allowing user to edit or replace image
    private void showImageOptionDialog2(){
        final String[] options2 = getResources().getStringArray(R.array.image_options2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_image_options)
                .setItems(options2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i){
                            case 0:
                                //edit options
                                SendImage();
                                //imageView_Photo1.setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
                                break;
                            case 1:
                                showImageOptionDialog();
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //send image to activity2 based on user selection
    public void SendImage(){
        BitmapDrawable drawable;
        Bitmap bitmap = null;
        Boolean PictureOptionCamera = false;
        Uri imageUri = null;
        //convert image to bitmap
        switch(imageViewSelector){
            case 1:
                //if picture is taken
                if(image1Camera == true) {
                    imageView_Photo1.invalidate();
                    PictureOptionCamera = true;
                    drawable = (BitmapDrawable) imageView_Photo1.getDrawable();
                    bitmap = drawable.getBitmap();
                }
                //if picture is loaded from gallery
                else{
                    imageUri = image1Uri;
                }
                //imageView number is stored
                break;
            case 2:
                if(image2Camera == true) {
                    imageView_Photo2.invalidate();
                    drawable = (BitmapDrawable) imageView_Photo2.getDrawable();
                    bitmap = drawable.getBitmap();
                    PictureOptionCamera = true;
                }
                else{
                    imageUri = image2Uri;
                }
                break;
            case 3:
                if(image3Camera == true) {
                    imageView_Photo3.invalidate();
                    drawable = (BitmapDrawable) imageView_Photo3.getDrawable();
                    bitmap = drawable.getBitmap();
                    PictureOptionCamera = true;
                }
                else{
                    imageUri = image3Uri;
                }
                break;
        }
        Bundle extras = new Bundle();
        Intent intent = new Intent(getContext(), Activity_Template2.class);
        //if picture is taken:
        if(PictureOptionCamera == true) {
            //sent bitmap via intent to activity_template2
            extras.putParcelable("bitmap", bitmap);
        }
        //if picture is loaded from gallery then:
        else{
            extras.putParcelable("uri", imageUri);
        }
        intent.putExtras(extras);
        startActivityForResult(intent, 100);
    }

    // create image dialog box, allowing user to choose between gallery and taking photo
    private void showImageOptionDialog(){
        final String[] options = getResources().getStringArray(R.array.image_options);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_image_options)
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                                {

                                    getImageFromGallery();

                                }else{
                                    if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                                        Toast.makeText(getActivity(),"grant it this permission will allow you to access storage from this app", Toast.LENGTH_LONG).show();
                                    }
                                    //request it
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                            REQUEST_STORAGE_CODE);
                                }

                                break;
                            case 1:
                                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE )== PackageManager.PERMISSION_GRANTED)
                                {

                                    capturePictureFromCamera();

                                }else{
                                    if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                        Toast.makeText(getContext(),"grant it this permission will allow you to make some change from this app",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    //request it
                                    ActivityCompat.requestPermissions(getActivity(),
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
            }
        }
        if(requestCode ==REQUEST_STORAGE2_CODE){
            //check grantResults to find out if user clicked on grant or deny
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getImageFromGallery();
            }
        }
    }

    //Open phone gallery ==================================================================
    private void getImageFromGalleryPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            getImageFromGallery();
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                Toast.makeText(this, "grant it this permission will allow you to choose pictures", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(getActivity(),
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // onRequest to change image color and filter settings
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
              int color = data.getIntExtra("color", 0);
              Boolean light = data.getBooleanExtra("light", false);
              switch(imageViewSelector){
                  case 1:
                    if (light == false)
                        imageView_Photo1.setColorFilter(color, PorterDuff.Mode.DARKEN);
                    else {
                        imageView_Photo1.setColorFilter(color, PorterDuff.Mode.OVERLAY);
                    }
                  break;
                  case 2:
                      if (light == false)
                          imageView_Photo2.setColorFilter(color, PorterDuff.Mode.DARKEN);
                      else {
                          imageView_Photo2.setColorFilter(color, PorterDuff.Mode.OVERLAY);
                      }
                      break;
                  case 3:
                  if (light == false)
                      imageView_Photo3.setColorFilter(color, PorterDuff.Mode.DARKEN);
                  else {
                      imageView_Photo3.setColorFilter(color, PorterDuff.Mode.OVERLAY);
                  }
              }
        }
        //Check if the intent was to pick image, was successful and an image was picked
        if (requestCode == GALLERY_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            //Get selected image uri here
            Uri imageUri = data.getData();
            // uses imageViewSelector variable to determine what imageview to populate
            switch(imageViewSelector) {
                case 1:
                    image1Uri = imageUri;
                    imageView_Photo1.setImageURI(imageUri);
                    image1Populated = true;
                    image1Camera = false;
                    break;
                case 2:
                    image2Uri = imageUri;
                    imageView_Photo2.setImageURI(imageUri);
                    image2Populated = true;
                    image2Camera = false;
                    break;
                case 3:
                    image3Uri = imageUri;
                    imageView_Photo3.setImageURI(imageUri);
                    image3Populated = true;
                    image3Camera = false;
                    break;
            }
        }
        else if(requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            switch(imageViewSelector) {
                case 1:
                    //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView_Photo1.setImageBitmap(bitmap);
                    image1Camera = true;
                    image1Populated = true;
                    break;
                case 2:
                    //Bitmap bitmap2 = (Bitmap) data.getExtras().get("data");
                    imageView_Photo2.setImageBitmap(bitmap);
                    image2Camera = true;
                    image2Populated = true;
                    break;
                case 3:
                    // Bitmap bitmap3 = (Bitmap) data.getExtras().get("data");
                    imageView_Photo3.setImageBitmap(bitmap);
                    image3Camera = true;
                    image3Populated = true;
                    break;
            }
        }
    }

}
