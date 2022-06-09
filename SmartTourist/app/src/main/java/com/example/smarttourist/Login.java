package com.example.smarttourist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {
ImageView logoLoginPage;
EditText emailLoginPage, passwordLoginPage;
Button loginButtonLoginPage;
TextView textView;
    Intent SignUpIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logoLoginPage=findViewById(R.id.logoLoginPage);
        emailLoginPage=findViewById(R.id.emailLoginPage);
        passwordLoginPage=findViewById(R.id.passwordLoginPage);

        loginButtonLoginPage=findViewById(R.id.loginButtonLoginPage);
        textView=findViewById(R.id.textView);
        SignUpIntent = new Intent(Login.this,SignUp.class);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(SignUpIntent);
            }
        });

        loginButtonLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


    }

    public void Login(View view)
    {

    }

}