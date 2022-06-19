package com.example.smarttourist;


import static androidx.constraintlayout.widget.StateSet.TAG;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.Viewholder>
{
//    private         ImageView rImage;
     Context context;
     ArrayList<Trip> tripArrayList=new ArrayList<>();
    //Constructor

    public TripAdapter(Context context, ArrayList<Trip> tripArrayList)
    {
        Log.d(TAG,"Constrctor of tRip ADapter- --------------------------");
        this.context=context;
        this.tripArrayList=tripArrayList;
    }


    @NonNull
    @Override
    public TripAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d(TAG,"OnCreate View Holder");
View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trip_details,parent,false);

return new TripAdapter.Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.Viewholder holder, int position) {
Log.d(TAG,"OnBindViewHolder");
        Trip trip = tripArrayList.get(position);
        holder.tripName.setText(trip.getName());
        holder.tripDuration.setText(trip.getDuration());
        holder.tripPrice.setText(String.valueOf(trip.getPrice()));
//        holder.tripStart.setText(String.valueOf(trip.getStartDate()));
//        holder.tripEnd.setText(String.valueOf(trip.getEndDate()));
        ;
        FileDownloadTask fileDownloadTask = FirebaseStorage.getInstance().getReference().getFile(Uri.parse("images/"+Login.SignedInUser.getId()+trip.getID()));
        fileDownloadTask.addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {

//                    holder.imageView.setImageBitmap();
                }
            }
        });
     holder.imageView.setImageBitmap(trip.getImage());

        Log.d(TAG,"----------------Holder Info--------------");
        Log.d(TAG,holder.tripName.getText().toString()+"\n"+holder.tripDuration.getText().toString());
    }

    @Override
    public int getItemCount() {
        return tripArrayList.size();
    }


    public class Viewholder extends  RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tripName,tripPrice,tripDuration,tripStart,tripEnd;
LinearLayout layout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG,"ViewHolder Constructor");

            imageView=itemView.findViewById(R.id.holdertripImageView);
            tripName=itemView.findViewById(R.id.holderTripName);
            tripPrice=itemView.findViewById(R.id.holderTripPrice);
            tripDuration=itemView.findViewById(R.id.holderTripDuration);
//            tripStart=itemView.findViewById(R.id.holderStartDate);
//            tripEnd=itemView.findViewById(R.id.holderEndDate);


            layout=itemView.findViewById(R.id.linear);

        }
    }
}