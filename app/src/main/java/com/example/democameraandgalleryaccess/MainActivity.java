package com.example.democameraandgalleryaccess;

import static android.app.Activity.RESULT_OK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView imageProfilePhoto;
    File selectedImage;
    String profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageProfilePhoto = findViewById(R.id.imgProfilePhoto);


        imageProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    PopupMenu popup = new PopupMenu(MainActivity.this, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.nav_gallery:
                                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(pickPhoto, 1);
                                    return true;
                                case R.id.nav_camera:
                                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(takePicture, 2);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.inflate(R.menu.gallery_camera_menu);
                    popup.show();
                } else {
                    // Request permission from the user
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 1: // gallery
                if (resultCode == RESULT_OK) {
                    try {

                            imageProfilePhoto.setImageURI(imageReturnedIntent.getData());

                    } catch (Exception e) {
                        selectedImage = null;
                        e.printStackTrace();
                    }
                }
                break;
            case 2: //camera
                if (resultCode == RESULT_OK) {
                    Bitmap imageBitmap = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    imageProfilePhoto.setImageBitmap(imageBitmap);
                }
                break;
        }
    }

    public void onUpdateClick(View view) {
        if (selectedImage == null) {
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
        } else {
            uploadProfilePhoto();
        }
    }

    void uploadProfilePhoto() {
//        Map<String, RequestBody> map = new HashMap<String, RequestBody>();
//
//        if (selectedImage != null) {
//            map.put("profile_pic\"; filename=\"" + selectedImage.getName() + "\"", RequestBody.create(MediaType.parse("*/*"), selectedImage));
//        }
    }
}


