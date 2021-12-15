package com.example.rhythm_n_reps;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.ArrayList;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

//Adapter class sets data to out Recycler Item
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    // creating a variable for our array list and context.
    private ArrayList<ExerciseRecyclerData> exerciseRecyclerDataArrayList;
    private Context mcontext;

    private ItemClickListener listener;

    // creating a constructor class.
    public RecyclerViewAdapter(ArrayList<ExerciseRecyclerData> recyclerDataArrayList, Context mcontext) {
        this.exerciseRecyclerDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    //listening for click, listener sent from RViewHolder
    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }


    //create the card view for given exercise and info
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
//        String vStr = view.toString();
//        Log.i("-----------------testing if we've reached adapter", vStr);
        return new RecyclerViewHolder(view, listener);
    }

    //bind current position in RView to next available slot in RView of data array (cards) - set fields and load image
    //set gif url only once, when we bind RecyclerViewHolder to each value of Exercise object
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview from our modal class.
        ExerciseRecyclerData modal = exerciseRecyclerDataArrayList.get(position);
        holder.cardExerciseName.setText("Exercise: " +  modal.getName() );
        holder.cardTargetView.setText("Target: " + modal.getTarget());
        holder.cardBodyPart.setText("Body part: " + modal.getBodyPart() + "\n" +"Target: " + modal.getTarget());
        holder.cardEquipmentView.setText("Equipment required: " + modal.getEquipment() );
        holder.cardCheckBoxView.setChecked(modal.getStatus());

//        Picasso.get().load(modal.getGifUrl()).into(holder.cardImageView); //make still image not gif for initial results list
//        ImageView imageView= (ImageView) ;
        String imgStr = modal.getGifUrl();
        Glide.with(holder.cardImageView.getContext()).load(imgStr).into(holder.cardImageView);

    }


    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return exerciseRecyclerDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private TextView cardExerciseName, cardBodyPart, cardTargetView, cardEquipmentView ;
        private ImageView cardImageView;

        public CheckBox cardCheckBoxView; //add to workout list

        // what's in an Exercise 'card'
        public RecyclerViewHolder(@NonNull View itemView, final ItemClickListener listener) {
            super(itemView);
            // initializing the layout with its view ids.
            cardExerciseName = itemView.findViewById(R.id.idCardExerciseName); // name
            cardBodyPart = itemView.findViewById(R.id.idCardBodypart);
            cardTargetView = itemView.findViewById(R.id.idTargetCardView);
            cardImageView = itemView.findViewById(R.id.idCardImageView); //gif image
            cardEquipmentView = itemView.findViewById(R.id.idTargetCardView);
            cardCheckBoxView = itemView.findViewById(R.id.checkboxSearchResultsList);

//            Intent intent= new Intent(this, RecyclerViewAdapter.class);

            cardCheckBoxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        //if the listener is not null, save this clicked item's position
                        //don't need to worry about position for results page of exercises - user will select from here
                        if (listener != null) {
                            int position = getLayoutPosition();
//                            Toast.makeText(mcontext.getApplicationContext(), "clicked checkbox", Toast.LENGTH_SHORT).show();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onCheckBoxClick(position);

                            }
                        }
                        if(cardCheckBoxView.isChecked()) {
                            //chechbox getContent is WebServiceActivity
//                            String t = checkBox.getContext().toString();
//                            Intent intent= new Intent(this, ListWorkoutSessionActivity.class);
/*
                            intent.putExtra("message","added item");
                            startActivity(intent);
*/
                            Toast.makeText(mcontext.getApplicationContext(), "not clickedddd" + cardExerciseName.getText(), Toast.LENGTH_SHORT).show();
                            Log.i("Checkbox clicked ------ result of checkbox context --- ", String.valueOf(itemView.getId()));
                        }
                    }
                }
            });

        }

    }
}
