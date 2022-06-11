package com.example.smarttourist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddPlanActivity extends AppCompatActivity {

    Button add;
    EditText planName,planPrice,planDuration,planPlaces;
    FirebaseFirestore db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        sharedPreferences = getSharedPreferences("Travel_Data", Context.MODE_PRIVATE);
        planDuration = findViewById(R.id.plan_Duration);
        planName = findViewById(R.id.plan_name);
        planPlaces = findViewById(R.id.plan_Places);
        planPrice = findViewById(R.id.plan_Price);
        add = findViewById(R.id.plan_add);

        add.setOnClickListener(v -> {
            String guideId = sharedPreferences.getString("guide_id","");
            db = FirebaseFirestore.getInstance();
            Map<String, Object> plans = new HashMap<>();
            plans.put("Plan_name",planName.getText().toString());
            plans.put("Duration",planDuration.getText().toString());
            plans.put("Plan_places",planPlaces.getText().toString());
            plans.put("Price",Integer.parseInt(planPrice.getText().toString()));

            db.collection("Guides").document(guideId).collection("Plans").document()
                    .set(plans)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(),"Plan added Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        Log.w("TEST", "Error writing document", e);
                        Toast.makeText(getApplicationContext(),"Please Try again",Toast.LENGTH_SHORT).show();
                    });
        });




    }
}
