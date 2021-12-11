package com.example.rhythm_n_reps;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

//Adapter class sets data to out Recycler Item
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    // creating a variable for our array list and context.
    private ArrayList<ExerciseRecyclerData> courseDataArrayList;
    private Context mcontext;

    // creating a constructor class.
    public RecyclerViewAdapter(ArrayList<ExerciseRecyclerData> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview from our modal class.
        ExerciseRecyclerData modal = courseDataArrayList.get(position);
        holder.courseNameTV.setText(modal.getName());
        holder.courseTracksTV.setText(modal.getTarget());
        holder.courseModeTV.setText(modal.getBodyPart());
        Picasso.get().load(modal.getGifUrl()).into(holder.courseIV);
    }

    //private String bodyPart;
    //    private String equipment;
    //    private String gifUrl;
    //    private String id;
    //    private String name;
    //    private String target;

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private TextView courseNameTV, courseModeTV, courseTracksTV;
        private ImageView courseIV;

        // what's in the ItemCard
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName); // name
            courseModeTV = itemView.findViewById(R.id.idTVBatch);
            courseTracksTV = itemView.findViewById(R.id.idTVTracks);
            courseIV = itemView.findViewById(R.id.idIVCourse); //gif image
        }
        //imageView idIVCourse
        //textView idTVCourseName
        //textView idTVBatch
        //textView idTVTracks
    }
}
