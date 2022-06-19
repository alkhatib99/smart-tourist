package com.example.smarttourist;


import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
public class MyTrips extends AppCompatActivity {
    private TripAdapter adapter;
    Button addTrip;
    private String role;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<Trip> myTripsArrayList;
    RecyclerView recyclerView;
    public static MyTripsAdapter myTripsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
        recyclerView = findViewById(R.id.myTripsRecyclerView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        myTripsArrayList = new ArrayList<Trip>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(Login.SignedInUser.getId()).child("Trips");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d(TAG,"start Listening");
                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG,ds.toString());
                    Trip trip = ds.getValue(Trip.class);
                    trip.setID(ds.getKey());
                    myTripsArrayList.add(trip);
                    Log.d(TAG, "Added Trip:"+"\n"+trip.toString());


                }

                myTripsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myTripsAdapter = new MyTripsAdapter(getApplicationContext(), myTripsArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myTripsAdapter);


    }
    public ArrayList<Trip> getMyTripsArrayList()
    {
        return myTripsArrayList;
    }

}

