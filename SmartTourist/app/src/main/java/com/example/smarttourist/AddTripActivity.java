package com.example.smarttourist;

import static androidx.constraintlayout.widget.StateSet.TAG;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddTripActivity extends AppCompatActivity {
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    DatePicker pickerStartDate, pickerEndDate;
    Button addTrip;
    byte[] ImageByte;
    EditText tripName,tripPrice,tripDuration,tripPlaces, tripDescription;
    Spinner tripType;

    Date startDate,endDate;
    String formattedDate,tripTypeText;
    ImageView imageView;
    Calendar calendar;
    SimpleDateFormat sdf;


int startDay, startMonth, startYear, endDay, endMonth, endYear;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        /// Initialize activity items
        tripName=findViewById(R.id.trip_name);
        tripPrice=findViewById(R.id.trip_price);
        tripPlaces=findViewById(R.id.trip_Places);
        tripDuration=findViewById(R.id.trip_duration);
        tripDescription=findViewById(R.id.trip_description);
        addTrip=findViewById(R.id.trip_add);
        pickerStartDate=findViewById(R.id.start_date);
        pickerEndDate=findViewById(R.id.end_date);
        tripType=findViewById(R.id.trip_type);
        imageView=findViewById(R.id.imageView);
        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        calendar = Calendar.getInstance();


//        checkFilePermissions();
pickerStartDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        startDay= i2;
        startMonth=i1;
        startYear=i;
        calendar.set(startYear, startMonth, startDay);

       sdf = new SimpleDateFormat("dd-MM-yyyy");
       formattedDate = sdf.format(calendar.getTime());
        ParsePosition pos = new ParsePosition(0);

         startDate=sdf.parse(formattedDate,pos);

    }
});

pickerEndDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
       endDay= i2;
        endMonth=i1;
        endYear=i;
        calendar.set(endYear, endMonth, endDay);

        sdf = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = sdf.format(calendar.getTime());
        ParsePosition pos = new ParsePosition(0);

       endDate=sdf.parse(formattedDate,pos);
    }
});

    String[] items= getResources().getStringArray(R.array.trip_type);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_add_trip,items){
            @Override
            public boolean isEnabled(int position) {
                return position!=0;

            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
              TextView view= (TextView) super.getDropDownView(position,convertView,parent);
              if (position==0)
                  view.setTextColor(Color.GRAY);

              else
              {

              }
            return view;
            }
        };

        arrayAdapter.setDropDownViewResource(R.layout.activity_add_trip);
        tripType.setAdapter(arrayAdapter);
        tripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String value= adapterView.getItemAtPosition(i).toString();
            if(value != items[0])
                tripTypeText=value;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tripTypeText="";
            }
        });

        final int PICK_IMAGE = 100;

imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

 choosePicture();
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        photoPickerIntent.setType("image/*");
        try {
            startActivityForResult(photoPickerIntent,PICK_IMAGE);
        }
    catch(Exception e)
        {
            Log.i("Error",e.toString());

        }
    }
});



// Create a new trip

        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /// Create object of trip
                Trip trip = new Trip();
                trip.setName(tripName.getText().toString());
                trip.setPlace(tripPlaces.getText().toString());
                trip.setStartDate(startDate);
                trip.setEndDate(endDate);
                tripTypeText=(tripTypeText!="")? tripTypeText: "Not Selected";
                trip.setType(tripTypeText);
                trip.setPrice((Long)(Long.parseLong(tripPrice.getText().toString())));
                trip.setImage(ImageByte);
                trip.setDescription(tripDescription.getText().toString());
                trip.setDuration(tripDuration.getText().toString());

              /// Save the trip into firebase

                Map<String, Object> trips = new HashMap<>();
                trips.put("Name",trip.getName());
                trips.put("Place",trip.getPlace());
                trips.put("StartDate",trip.getStartDate());
                trips.put("EndDate",trip.getEndDate());
                trips.put("Type",trip.getType());
                trips.put("Price",trip.getPrice());
                trips.put("Image",trip.getImage());
                trips.put("Duration",trip.getDuration());
                trips.put("Description",trip.getDescription());







    }

    private void choosePicture() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100 && data!=null  && data.getData()!=null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploadPicture();

            getImageAsByteFromImageView(imageView);
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap=((BitmapDrawable) imageView.getDrawable()).getBitmap();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
             ImageByte = baos.toByteArray();
                  }
    }

    private void uploadPicture() {

    final ProgressDialog progressDialog= new ProgressDialog(this);
    progressDialog.setTitle("Uploading image");
    progressDialog.show();

        final String randomKey= UUID.randomUUID().toString();

        storageReference=storageReference.child("images/");

    storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            progressDialog.dismiss();
Toast.makeText(getApplicationContext(),"The Image was not uploaded Exception"+e,Toast.LENGTH_SHORT).show();
        }
    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            double progressPercent=(100.00* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
            progressDialog.setMessage("Percentage: "+(int) progressPercent+"%");
        }
    });
    }

    }
}
