package com.example.smarttourist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {
TextView textView;
Button loginBtn, signUpBtn;
Intent loginIntent, signUpIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= findViewById(R.id.textView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        textView.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.bangers_regular));
        loginBtn=findViewById(R.id.main_loginBtn);
        signUpBtn=findViewById(R.id.signUpBtn);

        // Create an action listeners

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  loginIntent=new Intent(MainActivity.this, Login.class);
  startActivity(loginIntent);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpIntent=new Intent(MainActivity.this, SignUp.class);
                startActivity(signUpIntent);
            }
        });

    }



}