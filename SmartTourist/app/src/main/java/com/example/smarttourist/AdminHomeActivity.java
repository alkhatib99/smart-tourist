package com.example.smarttourist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

Button agentButton, tripButton;
//FirebaseUser firebaseUser;
//FirebaseAuth firebaseAuth;
@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    agentButton=findViewById(R.id.admin_add_agent_button);
    tripButton=findViewById(R.id.admin_trip_button);

    tripButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent= new Intent(AdminHomeActivity.this,TripActivity.class);
            startActivity(intent);
            finish();

        }
    });
    agentButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent in = new Intent(AdminHomeActivity.this,AddAgent.class);
            startActivity(in);
            finish();
        }
    });
    }
}