package com.example.smarttourist;

import static androidx.constraintlayout.widget.StateSet.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

public class MyTripsAdapter extends RecyclerView.Adapter<MyTripsAdapter.Viewholder> {
    //    private         ImageView rImage;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Context context;
    Bitmap bitmap = null;
    ArrayList<Trip> myTripsArrayList ;
    private Filter mFilter;
    //Constructor

    public MyTripsAdapter(Context context, ArrayList<Trip> tripArrayList) {
        Log.d(TAG, "Constructor of Trip Adapter- --------------------------");
        this.context = context;
        myTripsArrayList = tripArrayList;
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "OnCreate View Holder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trip_details, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Trip trip = myTripsArrayList.get(position);

        Log.d(TAG, "OnBindViewHolder");

//        DatabaseReference tripsRef =
//                FirebaseDatabase.getInstance().getReference().child("Users").child(Login.SignedInUser.getId()).child("Trips");
//
//        tripsRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot s : snapshot.getChildren()) {
//                        Trip t = s.getValue(Trip.class);
//                        myTripsArrayList.add(t);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        storageReference.child("images/" + Login.SignedInUser.getId() + trip.getID()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Thr trip " + trip.getName() + " doesn't have image");
            }
        });


        if (bitmap != null) {
            holder.imageView.setImageBitmap(bitmap);

        } else {
            holder.imageView.setImageResource(R.drawable.error);
        }

        holder.tripName.setText(trip.getName());
        holder.tripDuration.setText(trip.getDuration());
        holder.tripPrice.setText(String.valueOf(trip.getPrice()));

        Log.d(TAG, "----------------Holder Info--------------");
        Log.d(TAG, holder.tripName.getText().toString() + "\n" + holder.tripDuration.getText().toString());


        holder.describe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_example, null);
                TextView textViewName = (TextView) popupView.findViewById(R.id.popup_name);
                TextView textViewDesc = (TextView) popupView.findViewById(R.id.popup_Description);

                textViewName.setText(String.format("%s\n", trip.getName()));

                textViewDesc.setText(
                        String.format("tripId: %s\ntrip Places:%s\ntrip Price: %s\ntrip Duration: %s\n trip Description %s", trip.getID(), trip.getPlace(), trip.getPrice(), trip.getDuration(), trip.getDescription())
                );


                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });


    }


    @Override
    public int getItemCount() {
        return myTripsArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tripName, tripPrice, tripDuration, tripStart, tripEnd;
        Button describe, addButton;
        LinearLayout layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder Constructor");

            imageView = itemView.findViewById(R.id.holdertripImageView);
            tripName = itemView.findViewById(R.id.holderTripName);
            tripPrice = itemView.findViewById(R.id.holderTripPrice);
            tripDuration = itemView.findViewById(R.id.holderTripDuration);
            describe = itemView.findViewById(R.id.descButton);
            layout = itemView.findViewById(R.id.linear);

        }
    }


}
