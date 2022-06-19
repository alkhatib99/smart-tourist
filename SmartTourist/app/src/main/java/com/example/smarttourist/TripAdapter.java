package com.example.smarttourist;


import static androidx.constraintlayout.widget.StateSet.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.Viewholder>implements Filterable
{
//    private         ImageView rImage;
    private List<Trip> mObjects;
    StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();
    StorageReference storageReference=firebaseStorage.child("images");
    Context context;
     ArrayList<Trip> tripArrayList=new ArrayList<>();
    private Filter mFilter;
    //Constructor

    public TripAdapter(Context context, ArrayList<Trip> tripArrayList)
    {
        Log.d(TAG,"Constructor of Trip Adapter- --------------------------");
        this.context=context;
        this.tripArrayList=tripArrayList;

    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d(TAG,"OnCreate View Holder");
View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trip_details,parent,false);
context.notifyAll();
return new Viewholder(view);
    }



        @Override
    public void onBindViewHolder(@NonNull TripAdapter.Viewholder holder, int position) {

        Log.d(TAG,"OnBindViewHolder");
        this.notifyDataSetChanged();
        Trip trip = tripArrayList.get(position);
        holder.tripName.setText(trip.getName());
        holder.tripDuration.setText(trip.getDuration());
        holder.tripPrice.setText(String.valueOf(trip.getPrice()));
//        holder.tripStart.setText(String.valueOf(trip.getStartDate()));
//        holder.tripEnd.setText(String.valueOf(trip.getEndDate()));
        ;
        storageReference.child(Login.SignedInUser.getId()+trip.getID());

        final long ONE_MEGABYTE = 1024 * 1024;
        try {


            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imageView.setImageBitmap(bmp);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Failed" + e.getMessage());
                    Toast.makeText(context, "The trip: " + trip.getName() + " doesn't have an image, No Such file or Path found!!", Toast.LENGTH_SHORT).show();


                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG,"----------------Holder Info--------------");
        Log.d(TAG,holder.tripName.getText().toString()+"\n"+holder.tripDuration.getText().toString());
            holder.describe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_example, null);
                    TextView textViewName=(TextView) popupView.findViewById(R.id.popup_name);
                    TextView textViewDesc=(TextView) popupView.findViewById(R.id.popup_Description);

                    textViewName.setText(trip.getName());

                    textViewDesc.setText(trip.toString());


                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });
                }            }
            );


        }

    @Override
    public int getItemCount() {
        return tripArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new CustomFilter();
        }
        return mFilter;
    }


    public class Viewholder extends  RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tripName,tripPrice,tripDuration,tripStart,tripEnd;
        Button describe;
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
                describe=itemView.findViewById(R.id.descButton);

            layout=itemView.findViewById(R.id.linear);

        }
    }

    private class CustomFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0) {
                ArrayList<Trip> list = new ArrayList<Trip>(tripArrayList);
                results.values = list;
                results.count = list.size();
            } else {
                ArrayList<Trip> newValues = new ArrayList<Trip>();
                for(int i = 0; i < tripArrayList.size(); i++) {
                    Trip trip = tripArrayList.get(i);
                    if(trip.getPrice()<=Double.parseDouble(String.valueOf(constraint))) {
                        newValues.add(trip);
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mObjects = (List<Trip>) results.values;
            Log.d("CustomArrayAdapter", String.valueOf(results.values));
            Log.d("CustomArrayAdapter", String.valueOf(results.count));
            notifyDataSetChanged();
        }

    }

}
