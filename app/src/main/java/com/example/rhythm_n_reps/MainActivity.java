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
        }
    }


}