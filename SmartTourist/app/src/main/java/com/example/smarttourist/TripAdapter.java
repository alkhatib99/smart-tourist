package com.example.smarttourist;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TripAdapter extends FirestoreRecyclerAdapter<Trip,TripAdapter.Trip_holder> {
    private FirebaseFirestore db;
    // private FirebaseAuth mAuth;
    private OnItemClickListener listener;
    // SharedPreferences shared;
    public TripAdapter(FirestoreRecyclerOptions<Trip> options)
    {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final Trip_holder holder, int position, @NonNull Trip model) {
        holder.Trip_name123.setText(model.getName());
        holder.Trip_places.setText(model.getPlace());
        holder.price.setText(model.getPrice().toString());
        holder.duration.setText(model.getDuration());
        //shared = getSharedPreferences("Travel_Data",Context.MODE_PRIVATE);
        holder.delete.setOnClickListener(v -> {
            //mAuth = FirebaseAuth.getInstance();
            //FirebaseUser user = mAuth.getCurrentUser();
            //String guide_id=user.getUid();
            //db=FirebaseFirestore.getInstance();
            //int position = getAdapterPosition();
            int position1 =holder.getBindingAdapterPosition();
            if(position1 != RecyclerView.NO_POSITION && listener !=null)
                listener.onItemClick(getSnapshots().getSnapshot(position1), position1);
            //db.collection("Guides").document(guide_id).collection("Trips").document().delete();
            Log.d("EEEE","Delete button clicked");
        });
    }

    @NonNull
    @Override
    public Trip_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_trip_details,viewGroup,false);
        return new Trip_holder(v);
    }


    class Trip_holder extends RecyclerView.ViewHolder
    {
        public TextView Trip_name123;
        public TextView price;
        public TextView Trip_places;
        public TextView duration;
        public Button delete;
        public Trip_holder(View view) {
            super(view);
            Trip_name123=view.findViewById(R.id.trip_name12);
            price=view.findViewById(R.id.trip_prices12);
            Trip_places=view.findViewById(R.id.trip_places12);
            duration=view.findViewById(R.id.trip_description12);
            delete=view.findViewById(R.id.delete);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position=getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION && listener !=null)
//                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
//                    Log.d("MMMMMM","In view Holder");
//                }
//            });
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION && listener !=null)
//                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
//                }
//            });
        }

    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(TripAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
