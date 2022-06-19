package com.example.smarttourist;

import static androidx.constraintlayout.widget.StateSet.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class SignUp extends AppCompatActivity {
    EditText username_Input, age_Input, sex_Input, nationality_Input, email_Input, password_Input, health_condition;
    Button signUpBtn;
    TextView signText;
    Intent signInIntent;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("server/saving-data/fireblog");
        username_Input=findViewById(R.id.username_Input);
        age_Input=findViewById(R.id.age_Input);
        signText=findViewById(R.id.signText);
        signInIntent=new Intent(getApplicationContext(),Login.class);
        sex_Input=findViewById(R.id.sex_Input);
        nationality_Input=findViewById(R.id.nationality_Input);
        email_Input=findViewById(R.id.login_email_Input);
        password_Input=findViewById(R.id.password_Input2);
        health_condition=findViewById(R.id.login_password);
       final String role="tourist";


        //// Firebase Auth and FirebaseDatabase
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference().child("Users");

        signUpBtn=findViewById(R.id.signUpBtn);
        signText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signInIntent);
            }
        });

        /// Name, Email, Password, Sex are required*

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                ////Set Error for required fields.
                if(TextUtils.isEmpty(email_Input.getText())) /// if(email_input.getText.toString().equals(""))
                    email_Input.setError("Email is required");
                if(TextUtils.isEmpty(password_Input.getText()))
                    password_Input.setError("Password is required");
                boolean isValidEmail=Login.isValidEmailAddress(email_Input.getText().toString(),email_Input), isValidPassword=Login.isValidPassword(password_Input.getText().toString(),password_Input);
//                Log.d(TAG,"isValidEmail"+isValidEmail);
//                Log.d(TAG,"isValidPassword"+isValidPassword);

                if(TextUtils.isEmpty(username_Input.getText()))
                    username_Input.setError("Username is required");
                if(TextUtils.isEmpty(sex_Input.getText()))
                    sex_Input.setError("Sex is required");


                else if(
isValidEmail&&
            isValidPassword    ) {

                    if(TextUtils.isEmpty(age_Input.getText()))
                        age_Input.setText(null);
                    if(TextUtils.isEmpty(nationality_Input.getText()))
                        nationality_Input.setText(null);

                    if(TextUtils.isEmpty(health_condition.getText()))
                        health_condition.setText(null);
                    Log.d(TAG,"Sign up ---------------------------------");

                    User user =
                            new User(
                            username_Input.getText().toString(),
                            email_Input.getText().toString(),
                            password_Input.getText().toString(),
                            age_Input.getText().toString(),
                            sex_Input.getText().toString(),
                            nationality_Input.getText().toString(),
                            role,
                            health_condition.getText().toString()
                                    );

                    user.setId(UUID.randomUUID().toString());

                    databaseReference.child(user.getId()).setValue(user);



Intent in = new Intent(getApplicationContext(),MainActivity.class);
startActivity(in);
finish();
                }


            }
        });

    }






    }



