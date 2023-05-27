package com.example.photosandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class slideshowController extends AppCompatActivity {
    private ImageView slideshowImageView;
    private Button prevButton, nextButton, slideshowBackButton;

    private Photo photoInImageView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow_page);

        slideshowImageView = findViewById(R.id.slideshowImageView);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        slideshowBackButton = findViewById(R.id.slideshowBackButton);

        photoInImageView = MainActivity.user.getCurrAlbum().getPhotoArrayList().get(0);

        slideshowImageView.setImageURI(photoInImageView.getImage());

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.user.getCurrAlbum().getPhotoArrayList().indexOf(photoInImageView) == 0){
                    Toast.makeText(getApplicationContext(), "You have reached the first photo in the album", Toast.LENGTH_SHORT).show();
                }
                else{
                    int index = MainActivity.user.getCurrAlbum().getPhotoArrayList().indexOf(photoInImageView);
                    slideshowImageView.setImageURI(MainActivity.user.getCurrAlbum().getPhotoArrayList().get(index-1).getImage());
                    photoInImageView = MainActivity.user.getCurrAlbum().getPhotoArrayList().get(index-1);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.user.getCurrAlbum().getPhotoArrayList().indexOf(photoInImageView) == MainActivity.user.getCurrAlbum().getAlbumSize()-1){
                    Toast.makeText(getApplicationContext(), "You have reached the last photo in the album", Toast.LENGTH_SHORT).show();
                }
                else{
                    int index = MainActivity.user.getCurrAlbum().getPhotoArrayList().indexOf(photoInImageView);
                    slideshowImageView.setImageURI(MainActivity.user.getCurrAlbum().getPhotoArrayList().get(index+1).getImage());
                    photoInImageView = MainActivity.user.getCurrAlbum().getPhotoArrayList().get(index+1);
                }
            }
        });

        slideshowBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(slideshowController.this, photoController.class);
                startActivity(change);
            }
        });
    }

}
