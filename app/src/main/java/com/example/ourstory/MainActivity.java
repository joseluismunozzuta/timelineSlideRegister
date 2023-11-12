package com.example.ourstory;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout rootLayout = findViewById(R.id.rootLayout);

        Random random = new Random();
        int randomNumber = random.nextInt(5) + 1;
        //rootLayout.setBackgroundResource(R.drawable.image1);
        switch (randomNumber) {
            case 1:
                rootLayout.setBackgroundResource(R.drawable.image1);
                break;
            case 2:
                rootLayout.setBackgroundResource(R.drawable.image2);
                break;
            case 3:
                rootLayout.setBackgroundResource(R.drawable.image3);
                break;
            case 4:
                rootLayout.setBackgroundResource(R.drawable.image4);
                break;
            case 5:
                rootLayout.setBackgroundResource(R.drawable.image5);
                break;
        }

        Button openSpotifyButton = findViewById(R.id.openSpotifyButton);
        openSpotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSpotifyPlaylist();
            }
        });

    }

    public void openSpotifyPlaylist() {

        final String spotifyContent = "https://open.spotify.com/playlist/23URihMBXfJG9tvxpS9Bqs?si=bf6d9bfdcbae4206";
        final String branchLink = "https://spotify.link/content_linking?~campaign=com.spotify.music&$deeplink_path=" + spotifyContent + "&$fallback_url=" + spotifyContent;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(branchLink));
        startActivity(intent);
    }

    public void changeBackground(View view){
        ConstraintLayout rootLayout = findViewById(R.id.rootLayout);
        Random random = new Random();
        int randomNumber = random.nextInt(5) + 1;
        //rootLayout.setBackgroundResource(R.drawable.image1);
        switch (randomNumber) {
            case 1:
                rootLayout.setBackgroundResource(R.drawable.image1);
                break;
            case 2:
                rootLayout.setBackgroundResource(R.drawable.image2);
                break;
            case 3:
                rootLayout.setBackgroundResource(R.drawable.image3);
                break;
            case 4:
                rootLayout.setBackgroundResource(R.drawable.image4);
                break;
            case 5:
                rootLayout.setBackgroundResource(R.drawable.image5);
                break;
        }
    }


    public void goToTimeline(View view){
        Intent intent = new Intent(this, TimeLine.class);
        startActivity(intent);
    }

    // Launch the gallery when a button is clicked
    public void selectImage(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    // Handle the result from the gallery selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                // Get the image URI
                Uri selectedImageUri = data.getData();
                Intent formIntent = new Intent(this, FormActivity.class);
                formIntent.putExtra("imageUri", selectedImageUri.toString());
                startActivity(formIntent);
                // Now, you can upload the image to Firebase Storage using selectedImageUri
                /*uploadImageToFirebaseStorage(selectedImageUri).addOnSuccessListener(downloadUrl -> {
                    String imageUrl = downloadUrl.toString();
                }).addOnFailureListener(e->{
                    Toast.makeText(MainActivity.this,"Error subiendo imagen. Pruebe luego.", Toast.LENGTH_LONG).show();
                });*/
            }
        }
    }

}
