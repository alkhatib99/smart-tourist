package com.example.smarttourist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
EditText username_Input, age_Input, sex_Input, nationality_Input, email_Input, password_Input;
Button signUpBtn;
TextView signText;
Intent signInIntent;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username_Input=findViewById(R.id.username_Input);
        age_Input=findViewById(R.id.age_Input);
        signText=findViewById(R.id.signText);
        signInIntent=new Intent(getApplicationContext(),Login.class);
        sex_Input=findViewById(R.id.sex_Input);
        nationality_Input=findViewById(R.id.nationality_Input);
        email_Input=findViewById(R.id.email_Input);
        password_Input=findViewById(R.id.password_Input);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        signUpBtn=findViewById(R.id.signUpBtn);
        signText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signInIntent);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=email_Input.getText().toString();
                String password=password_Input.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            firebaseUser=  task.getResult().getUser();
                            DatabaseReference newUser = databaseReference.child(firebaseUser.getUid());
                            newUser.child("username").setValue(username_Input.getText().toString());
                            newUser.child("age").setValue(age_Input.getText().toString());
                            newUser.child("sex").setValue(sex_Input.getText().toString());
                            newUser.child("nationality").setValue(nationality_Input.getText().toString());
                            newUser.child("email").setValue(email_Input.getText().toString());
                            newUser.child("password").setValue(password_Input.getText().toString());
                            newUser.child("role").setValue("tourist");
                            Toast.makeText(getApplicationContext(), "Registration Successful",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }
}