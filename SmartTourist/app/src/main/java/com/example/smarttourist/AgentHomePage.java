package com.example.smarttourist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AgentHomePage extends AppCompatActivity {
Button addTrip,myTrip,allTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_home_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        addTrip=findViewById(R.id.agentAddButton);
        myTrip=findViewById(R.id.agentMyTripsButton);
        allTrip=findViewById(R.id.agentripsButton);


        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddTripActivity.class);
                        startActivity(intent);
                        finish();
            }
        });

        myTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MyTrips.class);
                startActivity(intent);
            }
        });

        allTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),TripActivity.class);
                startActivity(intent);
            }
        });

    }
}