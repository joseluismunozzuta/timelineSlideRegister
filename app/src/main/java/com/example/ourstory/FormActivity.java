package com.example.ourstory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class FormActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private Button uploadButton;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_activity);

        ImageView imageView = findViewById(R.id.imageView);
        uploadButton = findViewById(R.id.submitButton);
        Intent intent2 = getIntent();
        imageUri = Uri.parse(intent2.getStringExtra("imageUri"));
        imageView.setImageURI(imageUri);

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        uploadButton.setOnClickListener(view -> {

            uploadButton.setEnabled(false);

            // Call the method to upload the image
            uploadImageToFirebaseStorage(imageUri).addOnSuccessListener(downloadUrl -> {
                // Handle the download URL here
                String imageUrl = downloadUrl.toString();

                Map<String, Object> data = new HashMap<>();
                TextView title = findViewById(R.id.momenttitle);
                TextView description = findViewById(R.id.momentdesc);
                String momentdesc = description.getText().toString();
                String momentTitle = title.getText().toString();
                data.put("title", momentTitle);
                data.put("description", momentdesc);
                data.put("imgUrl", imageUrl);
                data.put("timestamp", new Timestamp(new Date()));

                db.collection("moments").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        uploadButton.setEnabled(true);
                        Toast.makeText(FormActivity.this, "¡Momento guardado!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(FormActivity.this, "Error guardando momento :(", Toast.LENGTH_LONG).show();
                        uploadButton.setEnabled(true);
                    }
                });

            }).addOnFailureListener(e -> {
                // Handle any errors during the upload
            });
        });
    }

    private Task<Uri> uploadImageToFirebaseStorage(Uri imageUri) {
        // Create a reference to the desired location in Firebase Storage
        StorageReference imageRef = storageRef.child("img/" + UUID.randomUUID().toString());

        // Upload the image
        return imageRef.putFile(imageUri).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            // Return the download URL
            return imageRef.getDownloadUrl();
        }).addOnSuccessListener(uri -> {
            String downloadUrl = uri.toString();
            Log.d("downloadUrl", downloadUrl);
        });
    }


}
