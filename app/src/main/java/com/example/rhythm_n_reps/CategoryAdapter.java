package com.example.rhythm_n_reps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<ExerciseRecyclerData> {

    private ArrayList<ExerciseRecyclerData> exercises;
    private Context mcontext;


    public CategoryAdapter(@NonNull Context context, int resource, ArrayList<ExerciseRecyclerData> exercises) {

        super(context, resource, exercises);
        this.mcontext = context;
        this.exercises = exercises;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        ExerciseRecyclerData exercise = exercises.get(position);

        if(v == null){
            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v=inflater.inflate(R.layout.card_view_layout,null);
        }

        TextView cardExerciseName = (TextView)v.findViewById(R.id.idCardExerciseName); //top of list
        TextView cardBodyPart = v.findViewById(R.id.idCardBodypart);
        TextView cardTargetView = v.findViewById(R.id.idTargetCardView);
        ImageView cardImageView = v.findViewById(R.id.idCardImageView); //gif image
        TextView cardEquipmentView = v.findViewById(R.id.idTargetCardView);
//        CheckBox cardCheckBoxView = v.findViewById(R.id.checkboxSearchResultsList);

        if (cardExerciseName != null) {
            //set each item for each card

//            cardExerciseName.setText(exercise.getName());
            cardExerciseName.setText("Exercise: " +  exercise.getName() );
            cardTargetView.setText("Target: " + exercise.getTarget());
            cardBodyPart.setText("Body part: " + exercise.getBodyPart() + "\n\n" +"Target: " + exercise.getTarget());
            cardEquipmentView.setText("Equipment required: " + exercise.getEquipment() );


            //insert still image of gif for now
            Picasso.get().load(exercise.getGifUrl()).into(cardImageView); //make still image not gif for initial results list
//        ImageView imageView= (ImageView) ;
//            String imgStr = exercise.getGifUrl();
//            Glide.with(cardImageView.getContext()).load(imgStr).into(cardImageView);
        }

        return v;

    }



}
