package com.example.smarttourist;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class SignInAnnonymous {
  public FirebaseAuth firebase;
  FirebaseApp firebaseApp;
    public abstract FirebaseAuth getFirebase(Context context);
    public Context context;
    public FirebaseUser firebaseUser;
    public SignInAnnonymous(Context context)
    {
        this.context=context;
        firebaseApp=FirebaseApp.getInstance();
        firebase=FirebaseAuth.getInstance();
         firebase.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
             @Override
             public void onSuccess(AuthResult authResult) {
            firebaseUser=authResult.getUser();
             }
         });

    }

    public FirebaseUser getAuth()
    {
     return firebaseUser;
    }


}
