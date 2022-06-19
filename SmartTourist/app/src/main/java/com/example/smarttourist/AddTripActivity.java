package com.example.smarttourist;

import static androidx.constraintlayout.widget.StateSet.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddTripActivity extends AppCompatActivity {

    EditText pickerStartDate, pickerEndDate;
    Button addTrip;
    EditText tripName,tripPrice,tripDuration,tripPlaces, tripDescription;
    Spinner tripType;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Date startDate,endDate;
    String formattedDate,tripTypeText;
    Calendar calendar;
    SimpleDateFormat sdf;
//    public final int PICK_IMAGE_REQUEST=100;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    /// to convert the date to formatted date like a temperature variables
int startDay, startMonth, startYear, endDay, endMonth, endYear;
    private Calendar myCalendar=Calendar.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        /// Initialize activity items
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tripName=findViewById(R.id.trip_name);
        tripPrice=findViewById(R.id.trip_price);
        tripPlaces=findViewById(R.id.trip_Places);
        tripDuration=findViewById(R.id.trip_duration);
        tripDescription=findViewById(R.id.trip_description);
        addTrip=findViewById(R.id.trip_add);
        pickerStartDate= findViewById(R.id.startDateEditText);
        pickerEndDate=findViewById(R.id.end_dateEditText);
        tripType=findViewById(R.id.trip_type);
        calendar = Calendar.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Trips");

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };
        pickerStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddTripActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                sdf = new SimpleDateFormat("dd-MM-yyyy");
       formattedDate = sdf.format(myCalendar.getTime());
        ParsePosition pos = new ParsePosition(0);

         startDate=sdf.parse(formattedDate,pos) ;           }
        });
        pickerEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddTripActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                sdf = new SimpleDateFormat("dd-MM-yyyy");
                formattedDate = sdf.format(myCalendar.getTime());
                ParsePosition pos = new ParsePosition(0);

                endDate=sdf.parse(formattedDate,pos) ;           }

        });

/// Initialize the drop down list fot the trip_type
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.trip_type, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
tripType.setAdapter(adapter);
tripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String items[]=getResources().getStringArray(R.array.trip_type);
            String value= adapterView.getItemAtPosition(i).toString();
            if(value!=items[0]) {
                tripTypeText = value;
            Log.d(TAG,"select:"+value);
            }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tripTypeText="";
            }
        });




// Create a new trip
        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // # Validate the Edit Text not Empty
                if(TextUtils.isEmpty(tripName.getText()))
                    tripName.setError("Required Trip Name", getDrawable(R.drawable.required));
                else if(TextUtils.isEmpty(tripDuration.getText()))
                    tripDuration.setError("required",getDrawable(R.drawable.required));
                else if (TextUtils.isEmpty(tripPlaces.getText()))
                    tripPlaces.setError("required",getDrawable(R.drawable.required));
                else {
                    // # Create an object of type Trip will be send save it to firebase then send it to another activity;

                    // ### get the text of edit text then convert it to string using toString();
                    Trip trip = new Trip();
                    trip.setID(UUID.randomUUID().toString());
                    trip.setName(tripName.getText().toString());
                    trip.setDuration(tripDuration.getText().toString());
                    trip.setPrice(Double.parseDouble(tripPrice.getText().toString()));
                    trip.setDuration(tripDuration.getText().toString());
                    trip.setPlace(tripPlaces.getText().toString());
                    trip.setStartDate(startDate);
                    trip.setEndDate(endDate);
                    trip.setAgentId(Login.SignedInUser.getId());



                    // ## Save the trip into firebase


                    databaseReference.child(trip.getID()).setValue(trip);


                    Intent intent = new Intent(getApplicationContext(),AddTripImage.class);

                    intent.putExtra("tripId",trip.getID());
                    startActivity(intent);

                }


    }

    });


    }

    private void updateLabel() {
    }


}
