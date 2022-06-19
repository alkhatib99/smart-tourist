package com.example.smarttourist;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TripActivity extends AppCompatActivity {
    RecyclerView Recyclerview;
    private TripAdapter adapter;
    Button addTrip;
    private String role;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
private ArrayList<Trip> tripArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
//
        Recyclerview = findViewById(R.id.mRecyclerView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tripArrayList=new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
    role=Login.SignedInUser.getRole();
        Log.d(TAG,"-------------Role is----------------"+"\n"+role);
//        if(firebaseDatabase.getReference().child("Users").child(Login.SignedInUser.getId()).child("role").equals("tourist"))
        databaseReference=firebaseDatabase.getReference().child("Trips");

        // If the user was an tourist the addButton will disable

        addTrip = findViewById(R.id.tripActivity_AddButton);

        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddTripActivity.class);
                startActivity(i);
                finish();

            }
        });
        adapter = new TripAdapter(TripActivity.this,tripArrayList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(TripActivity.this);

        Recyclerview.setLayoutManager(linearLayoutManager);

        Recyclerview.setAdapter(adapter);
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.d(TAG,"Start Listening--------------------------------------");
            Log.d(TAG,"Start iterating");

            for(DataSnapshot ds : snapshot.getChildren()) {
                Trip trip = ds.getValue(Trip.class);
                trip.setID(ds.getKey());
                tripArrayList.add(trip);
                Log.d(TAG, "Added Trip:"+"\n"+trip.toString());


            }
            adapter.notifyDataSetChanged();
            Log.d(TAG,"Finish Iteration");


            Log.d(TAG,"----------------ARRayLIST:"+tripArrayList.toString());


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });




    }



        }
