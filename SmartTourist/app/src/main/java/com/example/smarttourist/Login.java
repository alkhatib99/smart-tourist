package com.example.smarttourist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
EditText email_Input, password_Input;
Button loginBtn;
TextView textView;
Intent SignUpIntent;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
DatabaseReference databaseReference;
Intent intent;
RadioGroup radioRoleGroup;
String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_Input=findViewById(R.id.email_Input);
        password_Input=findViewById(R.id.password_Input);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        role="";
        loginBtn=findViewById(R.id.loginBtn);
        textView=findViewById(R.id.regLink2);

        radioRoleGroup=findViewById(R.id.radioRoleGroup);

        radioRoleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioRoleGroup.getCheckedRadioButtonId();


                switch (selectedId)
                {
                    case R.id.adminRadio:
                        role="admin";

                        break;
                    case R.id.agentRadio:
                        role="agent";
                        break;
                    case R.id.touristRadio:
                        role="tourist";
                        break;

                    default:
                        role="";
                        email_Input.setEnabled(false);
                        password_Input.setEnabled(false);
                        break;
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
                String email=email_Input.getText().toString().trim();
                String password=password_Input.getText().toString().trim();
             if (role.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please select an role",Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {

                                switch (role)
                                {
                                    case "admin":
                                        intent=new Intent(getApplicationContext(), AdminHomeActivity.class);
                                        break;
                                    case "agent":
                                        intent=new Intent(getApplicationContext(), AgentHomePage.class);
                                        break;
                                    case "tourist":
                                        intent=new Intent(getApplicationContext(), TouristHomeActivity.class);
                                        break;

                                    default:
                                        Toast.makeText(Login.this, "Login Successful, But the role are not selected", Toast.LENGTH_SHORT).show();
break;
                                }


                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
            }
        });


    }


}