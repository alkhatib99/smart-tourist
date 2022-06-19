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
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.Viewholder>implements Filterable
{
//    private         ImageView rImage;
    private List<Trip> mObjects;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Context context;
    Bitmap bitmap=null;
    private ArrayList<Trip> myTripsList=new ArrayList<Trip>();
     ArrayList<Trip> tripArrayList=new ArrayList<>();
    private Filter mFilter;
    //Constructor

    public TripAdapter(Context context, ArrayList<Trip> tripArrayList)
    {
        Log.d(TAG,"Constructor of Trip Adapter- --------------------------");
        this.context=context;
        this.tripArrayList=tripArrayList;
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        MyTrips myTrips = new MyTrips();

        FirebaseDatabase.getInstance().getReference().child("Users").child(Login.SignedInUser.getId()).child("Trips").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren())
                {
                    Trip t = s.getValue(Trip.class);
                    myTripsList.add(t);
                }
//                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        myTripsList

//        myTripsList=myTrips.getMyTripsArrayList();
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d(TAG,"OnCreate View Holder");
View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trip_details,parent,false);

return new Viewholder(view);
    }



        @Override
    public void onBindViewHolder(@NonNull TripAdapter.Viewholder holder, int position) {

        Log.d(TAG,"OnBindViewHolder");
        Trip trip = tripArrayList.get(position);

            if(!Login.SignedInUser.getRole().equals("tourist")) {
                Log.d(TAG, "Role is "+Login.SignedInUser.getRole());
                holder.addButton.setVisibility(View.GONE);
                holder.addButton.setEnabled(false);
            }else
            {
                Log.d(TAG,"Role is "+Login.SignedInUser.getRole());
                holder.addButton.setEnabled(true);
                holder.addButton.setVisibility(View.VISIBLE);

            }

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"Add button Clicked");
                  if(myTripsList.isEmpty())
                  {
                      OnCompleteListener onCompleteListener = new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              if (task.isSuccessful())
                            Log.d(TAG,"Success addedto firebasse");
else
    Log.d(TAG,"error "+task.getException());
                          }
                      };
                      FirebaseDatabase.getInstance().getReference().child("Users").child(Login.SignedInUser.getId()).child("Trips").child(trip.getID()).setValue(trip).addOnCompleteListener(onCompleteListener);

                      myTripsList.add(trip);
                      Toast.makeText(context,"Added Success",Toast.LENGTH_LONG).show();

                  }else if(myTripsList.contains(trip))
                      Toast.makeText(context, "This teip already exists",Toast.LENGTH_SHORT).show();
                else
                  {
                      boolean can=false;
                      for (Trip t : myTripsList)
                      {
                          try {

                              if (t.getEndDate().before(trip.getStartDate()))
                                  can = true;
                              else if (t.getEndDate().after(trip.getStartDate()))
                                  can = false;
                          }
                          catch (Exception e)
                          {
                              e.printStackTrace();
                              Toast.makeText(context,"There are wrong with ond of trip's date",Toast.LENGTH_SHORT).show();
                          }
                      }
                      if(can) {
                          FirebaseDatabase.getInstance().getReference().child("Users").child(Login.SignedInUser.getId()).child("Trips").setValue(trip);

                          myTripsList.add(trip);
                      Toast.makeText(context,"Added Success",Toast.LENGTH_LONG).show();
                      }  else
                          Toast.makeText(context,"You can't add two trips at same period time ",Toast.LENGTH_LONG).show();
                  }
                }
            });

            storageReference.child("images/"+Login.SignedInUser.getId()+trip.getID()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null) {
                    try {
                            uri.getPath();
                        bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Thr trip "+trip.getName()+" doesn't have image");
            }
        });


if (bitmap!=null)
{
    holder.imageView.setImageBitmap(bitmap);

}

else{
    holder.imageView.setImageResource(R.drawable.error);
}

            holder.tripName.setText(trip.getName());
            holder.tripDuration.setText(trip.getDuration());
            holder.tripPrice.setText(String.valueOf(trip.getPrice()));
            Log.d(TAG,"----------------Holder Info--------------");
        Log.d(TAG,holder.tripName.getText().toString()+"\n"+holder.tripDuration.getText().toString());


            holder.describe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_example, null);
                    TextView textViewName=(TextView) popupView.findViewById(R.id.popup_name);
                    TextView textViewDesc=(TextView) popupView.findViewById(R.id.popup_Description);

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
        Button describe,addButton;
LinearLayout layout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG,"ViewHolder Constructor");

            imageView=itemView.findViewById(R.id.holdertripImageView);
            tripName=itemView.findViewById(R.id.holderTripName);
            tripPrice=itemView.findViewById(R.id.holderTripPrice);
            tripDuration=itemView.findViewById(R.id.holderTripDuration);
            describe=itemView.findViewById(R.id.descButton);
addButton=itemView.findViewById(R.id.tripDetailsAddButton);
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
