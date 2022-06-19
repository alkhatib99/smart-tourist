package com.example.smarttourist;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddAgent extends AppCompatActivity {
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
Button button;
EditText username_input,email_input,pass_input,company_name_input;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agent);
        username_input=findViewById(R.id.username_Input);
        email_input=findViewById(R.id.login_email_Input);
        pass_input=findViewById(R.id.password_Input2);
        company_name_input=findViewById(R.id.company_name);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Users");
    ActionBar actionBar = getSupportActionBar();
    actionBar.hide();

        button=findViewById(R.id.agentSignUpBtn);

        button.setOnClickListener(view -> {
            Log.d(TAG,"Button Clicked");
            User agent = new User();
            agent.setId(UUID.randomUUID().toString());
            agent.setUsername(username_input.getText().toString());
            agent.setEmail(email_input.getText().toString());
            agent.setPassword(pass_input.getText().toString());
            agent.setRole("agent");

            Map<String, User> users = new HashMap<>();

            users.put(agent.getId(),agent);
            databaseReference.child(agent.getId()).setValue(agent).addOnCompleteListener(task -> {

               if (task.isSuccessful()) {
                   Log.d(TAG, "Successful");
                   Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(getApplicationContext(), "UnSuccessfully Inserted", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Failed:"+task.getException());
               }
            });

        });

    }

}