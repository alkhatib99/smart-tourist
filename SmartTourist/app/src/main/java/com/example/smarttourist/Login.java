package com.example.smarttourist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Login extends AppCompatActivity {
EditText email_Input, password_Input;
Button loginBtn;
TextView textView;
Intent SignUpIntent;
RadioGroup radioRoleGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_Input=findViewById(R.id.email_Input);
        password_Input=findViewById(R.id.password_Input);

        loginBtn=findViewById(R.id.loginBtn);
        textView=findViewById(R.id.regLink2);

        radioRoleGroup=findViewById(R.id.radioRoleGroup);

        radioRoleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioRoleGroup.getCheckedRadioButtonId();

                switch (selectedId)
                {
                    case R.id.adminRadio:

                        break;
                    case R.id.agentRadio:
                        break;
                    case R.id.touristRadio:
                        break;

                    default:
                        email_Input.setEnabled(false);
                        password_Input.setEnabled(false);
                 }

            }
        });
        SignUpIntent = new Intent(Login.this,SignUp.class);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(SignUpIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


    }


}