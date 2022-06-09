package com.example.smarttourist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
EditText username_Input, age_Input, sex_Input, nationality_Input, email_Input, password_Input;
Button signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username_Input=findViewById(R.id.username_Input);
        age_Input=findViewById(R.id.age_Input);
        sex_Input=findViewById(R.id.sex_Input);
        nationality_Input=findViewById(R.id.nationality_Input);
        email_Input=findViewById(R.id.email_Input);
        password_Input=findViewById(R.id.password_Input);

        signUpBtn=findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}