package com.example.rhythm_n_reps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/*
class to test connection between user selection and passing to web service
 */

public class TESTActivity extends AppCompatActivity {

    private String selectedTarget;
    private Button targetButton;

    private static final int request_code = 5;

    String[] bodyParts = new String[]{
            "back",
            "cardio",
            "chest",
            "lower arms",
            "lower legs",
            "neck",
            "shoulders",
            "upper arms",
            "upper legs",
            "waist"};

    private String[] target = new String[]{
            "abductors",
            "abs",
            "adductors",
            "biceps",
            "calves",
            "cardiovascular system",
            "delts",
            "forearms",
            "glutes",
            "hamstrings",
            "lats",
            "levator scapulae",
            "pectorals",
            "quads",
            "serratus anterior",
            "spine",
            "traps",
            "triceps",
            "upper back"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testactivity);

        Spinner spinnerBodyParts = (Spinner) findViewById(R.id.testSpinner);
        //equip spinner w/ listener
//        spinnerBodyParts.setOnItemSelectedListener(this);
        ArrayAdapter adapterB = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bodyParts);
        // Specify the default spinner layout for dropdown list of choices
        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply adapter spinners respectively
        spinnerBodyParts.setAdapter(adapterB);
        spinnerBodyParts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterB, View view, int i, long l) {

                //store selcted value, do not do intent, only push intent when target button clicked
                selectedTarget = adapterB.getItemAtPosition(adapterB.getSelectedItemPosition()).toString();
                Toast.makeText(TESTActivity.this, "HAVE selected-------" + selectedTarget, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // not applicable

            }
        });


    }


    public void goToService(View view) {
        switch (view.getId()) {
            case R.id.testGetWebServiceResults:
                Toast.makeText(TESTActivity.this, "HAVE CLICKED TARGET BUTTON--- and val is " + selectedTarget  , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, WebServiceActivity.class);
                String sendOver = "bodyPart/" + selectedTarget;
                Log.i("TESTActivity activity have gotten value of drop down", sendOver);
                intent.putExtra("var", sendOver);

                startActivity(intent);
                break;

        }
    }


//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//        Intent intent = new Intent(this, WebServiceActivity.class);
//        String sendOver = "equipment/rope";
//        intent.putExtra("var", sendOver);
//        startActivity(intent);
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}