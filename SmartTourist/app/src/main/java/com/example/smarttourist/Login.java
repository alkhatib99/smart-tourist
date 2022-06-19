package com.example.smarttourist;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

public static User SignedInUser=new User();
    EditText email_Input, password_Input;
    Button loginBtn;
    TextView textView;
    DatabaseReference databaseReference;
    Intent intent;
    RadioGroup radioRoleGroup;
    FirebaseDatabase firebaseDatabase;
    String role;
   public static ArrayList<User> users;
    FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /// Hide ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ///////// Initialize content


        email_Input = findViewById(R.id.login_email_Input);
        password_Input = findViewById(R.id.login_password);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");
        role = "";
        loginBtn = findViewById(R.id.login_page_loin_button);
        textView = findViewById(R.id.regLink2);
        firebaseAuth=FirebaseAuth.getInstance();
        radioRoleGroup = findViewById(R.id.radioRoleGroup);


        /// Disable edit text till choose the role

        /// Listener for checked radio buttons


        radioRoleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioRoleGroup.getCheckedRadioButtonId();


                switch (selectedId) {
                    case R.id.adminRadio:
                        role = "admin";
                        Log.d(TAG,role);
                        enableItems();
                        break;
                    case R.id.agentRadio:
                        role = "agent";
                        Log.d(TAG,role);
                        enableItems();
                        break;
                    case R.id.touristRadio:
                        role="tourist";
                        Log.d(TAG,role);
                        enableItems();

                        break;

                    default:
                        role = "";
                        break;
                }

            }});

radioRoleGroup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),"Should choose one of roles",Toast.LENGTH_SHORT).show();

    }
});

        ////Get The Data
        users=new ArrayList<User>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    user.setId(ds.getKey());
                    users.add(user);
                    Log.d(TAG, "Added User:"+user.getUsername()+"\nId:"+user.getId());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
Log.d(TAG,"OnCancel");
            }

        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);


        textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Login.this, SignUp.class));
                    finish();
                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "-------------------------------------Login-In Clicked-------------------------------------------");
                    if (radioRoleGroup.getCheckedRadioButtonId() == -1) {
                      Log.d(TAG,"Role not selected");
                        Toast.makeText(getBaseContext(), "Should choose role", Toast.LENGTH_SHORT).show();
                    }
                    else {

                            Log.d(TAG,"---------------Role is---------------------: "+role);
                        String email = email_Input.getText().toString();
                        String password = password_Input.getText().toString();
                        boolean checkEmptyFields=checkEmptyFields(),isValidPassword=isValidPassword(password,password_Input),isValidEmail=isValidEmailAddress(email,email_Input);

                        Log.d(TAG,"-----------------------FieldsValidate--------------------------------");

                        Log.d(TAG,"checkEmpty: "+checkEmptyFields);
                        Log.d(TAG,"Check is pass valid: "+isValidPassword);
                        Log.d(TAG,"Check is email valid: "+isValidEmail);

                        if( checkEmptyFields&&isValidEmail&&isValidPassword)
                        {




                            login(email, password);

                        }
                            else if (!isValidEmail)
                        {
                            email_Input.setError("InvalidEmail");
                        }

                    }

                }   }
            );


            }



    private boolean checkEmptyFields() {

        boolean email=true,password=true;
        if(email_Input.getText().toString().equals("") || TextUtils.isEmpty(email_Input.getText())) {
            email_Input.setError("required");
        email=false;
        }
            if(password_Input.getText().toString().equals("")||TextUtils.isEmpty(password_Input.getText())) {
                password_Input.setError("Password is required");
            password=false;
            }
        return (email&&password);
    }


    private void login(String email, String password) {

        boolean userFound=false;
        Log.d(TAG,"-------------------Login Method-----------------------");
            for(User user: users)
            {

              if(user.getRole().equals(role))
              {
                  Log.d(TAG,"Found user with role: "+role);
                  Log.d(TAG,"----------------User_Info--------------\n");
                  Log.d(TAG,"-----User_Email:--------------\n"+user.getEmail());
                  Log.d(TAG,"-----User_password:--------------\n"+user.getPassword());
                  if(
                          user.getEmail().equals(email) &&
                                  user.getPassword().equals(password))
                  {
                      Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_LONG).show();
                      Log.d(TAG,"---------Login Successful----------");
                      SignedInUser=user;
                      startActivity(checkRole(role));
                      finish();
                  }
                  else
                  {
                      Toast.makeText(Login.this,"Login UnSuccessful, Inputs not valid",Toast.LENGTH_LONG).show();
                      Log.d(TAG,"Login UnSuccessful, Inputs not valid");

                  }
              }
            }

            Log.d(TAG,"End Method");



    }

    public static boolean isValidPassword(@NonNull String password,EditText password_Input) {
        Log.d(TAG,"Check [passwords] Valid");

        boolean isLengthValid=(password.length()>=8);
        boolean isHasUpper=false;


        for (int i = 0 ; i <password.length();i++){

            if(Character.isUpperCase(password.charAt(i)))
                isHasUpper=true;
        }
if(!isLengthValid) {
    password_Input.setError("Password is length should be greater than 8");
Log.d(TAG,"Length Pasword wrong");

}
if(!isHasUpper) {
    password_Input.setError("Password should has a capital letter");
    Log.d(TAG,"Password wrong don't have capital letter");

}
return ((isHasUpper)&&(isLengthValid));
    }

    public static boolean isValidEmailAddress (String email,EditText email_Input){

        Log.d(TAG,"Check Email Valid");
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(email);
        if (m.matches()) {
            return true;
        } else {
            email_Input.setError("Invalid Email");
            return false;
        }

        }

        public void  enableItems(){

            Log.d(TAG,"Enable Items" );
            email_Input.setEnabled(true);
            password_Input.setEnabled(true);
            loginBtn.setEnabled(true);
        }

        public Intent checkRole(String role)
        {
            Intent intent = null;
            if (role.equals("tourist"))
                intent=new  Intent(Login.this,TouristHomeActivity.class);
            else if(role.equals("admin"))
                intent=new Intent(Login.this,AdminHomeActivity.class);
            else if(role.equals("agent"))
                intent=new Intent(Login.this,AgentHomePage.class);

        return  intent;
        }


    }

