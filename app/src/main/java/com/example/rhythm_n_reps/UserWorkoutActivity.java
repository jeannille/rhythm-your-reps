package com.example.rhythm_n_reps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User's chosen exercises from original results for their current workout.
 * When this is saved, it can be stored as part of the User's favorites
 */
public class UserWorkoutActivity extends AppCompatActivity {

    private Integer exerciseId; //each exercise has a unique id //for hashmap?
    private ExerciseRecyclerData exercise; //exercise.id
    private ArrayList<ExerciseRecyclerData> userCurrentWorkout;
//    private HashMap<Integer, String> fullWorkout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_workout);
        //        fullWorkout = new HashMap<Integer, String>(); //for ref: https://www.geeksforgeeks.org/initialize-hashmap-in-java/
        //eventually would prefer to have SET object (set of 3 exercises) and have a list of 3 sets be one workout


        //TODO recyclerView for kept items

    }


}