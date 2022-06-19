package com.example.smarttourist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class TouristHomeActivity extends AppCompatActivity {
Button findPlan, myPlan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_home);
        ActionBar actionBar = getSupportActionBar();
actionBar.hide();
        findPlan=findViewById(R.id.findPlanButton);
    myPlan=findViewById(R.id.touristtripButton);

    findPlan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),TripActivity.class);
            startActivity(intent);
        }
    });

 myPlan.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent intent= new Intent(getApplicationContext(),MyTrips.class);
         startActivity(intent);
     }
 });
    }
}