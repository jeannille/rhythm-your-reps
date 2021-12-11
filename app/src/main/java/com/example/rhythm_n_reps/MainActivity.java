package com.example.rhythm_n_reps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.workshopButton:
                Intent workshopIntent = new Intent(this, WWorkshopActivity.class);
                startActivity(workshopIntent);
                break;
                //button to open up web service - exercise
            case R.id.activityWebService:
                Intent webIntent = new Intent(this, WebServiceActivity.class);
                startActivity(webIntent);
                break;
            case R.id.buttonToSessionLIst:
                Intent listSessionIntent = new Intent(this, ListWorkoutSessionActivity.class);
                startActivity(listSessionIntent);
                break;

            //testing
            case R.id.chooseCategoryActivity:
                Intent spinnersIntent = new Intent(this, ChooseCategoryActivity.class);
                startActivity(spinnersIntent);
        }
    }


}