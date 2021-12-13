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

    //resulting string values from user selection to parse in url for correct endpoint to web server
    private String selectedTarget;
    private String selectedBodyPart;
    private String selectedEquipment;

    //buttons to submit search
    private Button targetButton;


    private String[] bodyParts = new String[]{
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

    //    /exercises/equipment/{type}
    private String[] equipment = new String[]{
            "assisted",
            "band",
            "barbell",
            "body weight",
            "bosu ball",
            "cable",
            "dumbbell",
            "elliptical machine",
            "ez barbell",
            "hammer",
            "kettlebell",
            "leverage machine",
            "medicine ball",
            "olympic barbell",
            "resistance band",
            "roller",
            "rope",
            "skierg machine",
            "sled machine",
            "smith machine",
            "stability ball",
            "stationary bike",
            "stepmill machine",
            "tire",
            "trap bar",
            "upper body ergometer",
            "weighted",
            "wheel roller"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testactivity);


        Spinner spinnerBodyParts = (Spinner) findViewById(R.id.bodyPart_spinner);
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
                selectedBodyPart = adapterB.getItemAtPosition(adapterB.getSelectedItemPosition()).toString();
                Toast.makeText(TESTActivity.this, "HAVE selected BODYPART -------" + selectedBodyPart, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // not applicable

            }
        });

        //set target Spinner, its adapter and itemClickelistener
        //Target category Spinner
        Spinner spinnerTarget = (Spinner) findViewById(R.id.target_spinner);
        ArrayAdapter adapterT = new ArrayAdapter(this, android.R.layout.simple_spinner_item, target);
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTarget.setAdapter(adapterT);

        spinnerTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterT, View view, int i, long l) {

                //store selected value from drop down, specified target adapter view
                selectedTarget = adapterT.getItemAtPosition(adapterT.getSelectedItemPosition()).toString();
                Toast.makeText(TESTActivity.this, "HAVE selected TARGET -------" + selectedTarget, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // not applicable

            }
        });


    }


    public void goToService(View view) {
        switch (view.getId()) {
            case R.id.selectBodyPartSearchButton:
                Toast.makeText(TESTActivity.this, "HAVE CLICKED BODYPART BUTTON--- and val is " + selectedBodyPart  , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, WebServiceActivity.class);
                String sendOver = "bodyPart/" + selectedBodyPart;
                Log.i("TESTActivity activity have gotten value of drop down", sendOver);
                intent.putExtra("var", sendOver);

                startActivity(intent);
                break;

            case R.id.selectTargetSearchButton:
                Toast.makeText(TESTActivity.this, "HAVE CLICKED TARGET BUTTON--- and val is " + selectedTarget  , Toast.LENGTH_SHORT).show();

                Intent intent2 = new Intent(this, WebServiceActivity.class);
                String sendOver2 = "target/" + selectedTarget;
                Log.i("TARGET BUTTON switch case ----- INTENT----- TESTActivity activity have gotten value of drop down", sendOver2);
                intent2.putExtra("var", sendOver2);

                startActivity(intent2);
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