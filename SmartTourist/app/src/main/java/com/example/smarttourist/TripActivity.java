package com.example.smarttourist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TripActivity extends AppCompatActivity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    RecyclerView mRecyclerview2;
    String guide_id,guide_name;
    TextView guideData;
    FirebaseUser firebaseUser;
    SharedPreferences shared;
    private TripAdapter adapter;
    Button addTrip;

//    DatabaseReference datbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
//    d
        mRecyclerview2=findViewById(R.id.mRecyclerView2);
        mRecyclerview2.setLayoutManager(new LinearLayoutManager(this));

        addTrip = findViewById(R.id.addButton);
        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddTripActivity.class);
                startActivity(i);

            }
        });

        guide_id=shared.getString("guide_id","Error");
        guide_name=shared.getString("guide_name","Error");
        guideData =findViewById(R.id.guideData);
        guideData.setText("Trips of "+guide_name+":");

        FirebaseUser checkUser = FirebaseAuth.getInstance().getCurrentUser();
        if(checkUser == null){
            Intent SelectionPage = new Intent(getApplicationContext(),Login.class);
            startActivity(SelectionPage);
            finish();
            return;
        }

        Query query=FirebaseFirestore.getInstance()
                .collection("Guides")
                .document(guide_id)
                .collection("Trips")
                .limit(50);

        FirestoreRecyclerOptions<Trip> options = new FirestoreRecyclerOptions.Builder<Trip>()
                .setQuery(query,Trip.class)
                .build();

        adapter=new TripAdapter(options);
        mRecyclerview2.setAdapter(adapter);
        adapter.startListening();

        adapter.setOnItemClickListener(new TripAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //Trip Trip=documentSnapshot.toObject(Trip.class);
                String id = documentSnapshot.getId();
                DocumentReference path = documentSnapshot.getReference();
                path.delete();

//                Toast.makeText(TripActivity.this,
//                        "Position: " + position + "  ID:  " + id + "  \nPath " + path, Toast.LENGTH_SHORT).show();


                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                //i.putExtra("Trip_id",id);
                //i.putExtra("city",value);
                //i.putExtra("Value2", "Simple Tutorial");
                // Set the request code to any code you like, you can identify the
                // callback via this code
                startActivity(i);
            }
        });

    }
}
