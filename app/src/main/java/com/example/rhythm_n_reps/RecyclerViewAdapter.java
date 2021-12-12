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
    private ArrayList<ExerciseRecyclerData> exerciseRecyclerDataArrayList;
    private Context mcontext;

    // creating a constructor class.
    public RecyclerViewAdapter(ArrayList<ExerciseRecyclerData> recyclerDataArrayList, Context mcontext) {
        this.exerciseRecyclerDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }


    //create the card view for given exercise and info
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    //bind current position in RView to next available slot in RView of data array (cards) - set fields and load image
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview from our modal class.
        ExerciseRecyclerData modal = exerciseRecyclerDataArrayList.get(position);
        holder.courseNameTV.setText(modal.getName());
        holder.courseTracksTV.setText(modal.getTarget());
        holder.courseModeTV.setText(modal.getBodyPart());
        Picasso.get().load(modal.getGifUrl()).into(holder.cardImageView); //make still image not gif for initial results list
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
        return exerciseRecyclerDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private TextView courseNameTV, courseModeTV, courseTracksTV;
        private ImageView cardImageView;

        // what's in an Exercise 'card'
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing the layout with its view ids.
            courseNameTV = itemView.findViewById(R.id.idCardExerciseName); // name
            courseModeTV = itemView.findViewById(R.id.idTVBatch);
            courseTracksTV = itemView.findViewById(R.id.idTVTracks);
            cardImageView = itemView.findViewById(R.id.idCardImageView); //gif image
        }
        //imageView idIVCourse
        //textView idTVCourseName
        //textView idTVBatch
        //textView idTVTracks
    }
}
