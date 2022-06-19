package com.example.smarttourist;

import static androidx.constraintlayout.widget.StateSet.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;

public class AddTripImage extends AppCompatActivity {
    ImageView imageView;
    public Uri filePath;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String tripId;
    public final int PICK_IMAGE_REQUEST=100;
    Button addButton,selectButton,uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_image);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        addButton=findViewById(R.id.tripImageAddButton);
        imageView=findViewById(R.id.imageView);
        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();


        Bundle extras=getIntent().getExtras();

        tripId=extras.getString("tripId");

        selectButton=findViewById(R.id.selectButton);
        uploadButton=findViewById(R.id.uploadButton);

selectButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        chooseImage();
    }
});

uploadButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        uploadImage();
    }
});
        addButton.setOnClickListener(view -> {


//                StorageReference SR = storage.getReference("Images").child(tripId+firebaseUser.getUid());

            Intent intent= new Intent(getApplicationContext(),TripActivity.class);
            startActivity(intent);
        });


    }

    private void chooseImage() {
        Log.d(TAG,"Choosing Image");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);

        Log.d(TAG,"----Ending Choose Image ---------------");

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {super.onActivityResult(requestCode, resultCode, data);
Log.d(TAG,"OnActivity Result");
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }

        }
        Log.d(TAG,"------------Ending OnActivity Result");

    }




    private void uploadImage()
    {
        Log.d(TAG,"Upload Image Method");
        if (filePath != null) {

        // Code for showing progressDialog while uploading
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        // Defining the child of storageReference
        StorageReference ref
                = storageReference
                .child("images").child(Login.SignedInUser.getId()+tripId);
        // adding listeners on upload
        // or failure of image

        ref.putFile(filePath)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(

                                    UploadTask.TaskSnapshot taskSnapshot)
                            {
                                Uri uri = filePath;
                                uri.getPath();
StorageReference s=taskSnapshot.getStorage();
                                FirebaseDatabase.getInstance().getReference().child("images").child(Login.SignedInUser.getId()+tripId).setValue(s.getPath());


                                Log.d(TAG,"Success Task putFile");


                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                Toast
                                        .makeText(getApplicationContext(),
                                                "Image Uploaded!!",
                                                Toast.LENGTH_SHORT).show();
                            }
                        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        Log.d(TAG,"OnFailure Task putFile");

                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast
                                .makeText(getApplicationContext(),
                                        "Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .addOnProgressListener(
                        new OnProgressListener<UploadTask.TaskSnapshot>() {

                            // Progress Listener for loading
                            // percentage on the dialog box
                            @Override
                            public void onProgress(

                                    UploadTask.TaskSnapshot taskSnapshot)
                            {
                                Log.d(TAG,"OnProgress Progress Listener");

                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage(
                                        "Uploaded "
                                                + (int)progress + "%");
                            }
                        });
    }
}

}