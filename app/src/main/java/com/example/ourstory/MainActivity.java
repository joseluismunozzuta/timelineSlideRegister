package com.example.ourstory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                TextView uriText = findViewById(R.id.uriText);
                uriText.setText(selectedImageUri.toString());
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
