package com.example.smarttourist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    EditText username_Input, age_Input, sex_Input, nationality_Input, email_Input, password_Input, health_condition;
    Button signUpBtn;
    TextView signText;
    Intent signInIntent;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
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
        email_Input=findViewById(R.id.email_Input);
        password_Input=findViewById(R.id.password_Input2);
        health_condition=findViewById(R.id.health_condition);
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


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                ////Set Error for required fields.
                if(TextUtils.isEmpty(email_Input.getText()))
                    email_Input.setError("Email is required");
                if(TextUtils.isEmpty(password_Input.getText()))
                    password_Input.setError("Password is required");
                if(TextUtils.isEmpty(username_Input.getText()))
                    username_Input.setError("Username is required");
                if(TextUtils.isEmpty(sex_Input.getText()))
                    sex_Input.setError("Sex is required");
                else if(!( TextUtils.isEmpty(email_Input.getText()) && TextUtils.isEmpty(password_Input.getText()) && TextUtils.isEmpty(username_Input.getText()) && TextUtils.isEmpty(sex_Input.getText()))) {
                    if(TextUtils.isEmpty(age_Input.getText()))
                        age_Input.setText(null);
                    if(TextUtils.isEmpty(nationality_Input.getText()))
                        nationality_Input.setText(null);
                    if(TextUtils.isEmpty(health_condition.getText()))
                        health_condition.setText(null);
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

                    firebaseAuth.createUserWithEmailAndPassword(email_Input.getText().toString(), password_Input.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseUser = task.getResult().getUser();
//                                Map<String, User> users = new HashMap<>();
                                user.setId(firebaseUser.getUid());
//                                users.put(firebaseUser.getUid(),user);
                                databaseReference.child("tourists").child(firebaseUser.getUid()).setValue(user);
                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });

    }






    }



