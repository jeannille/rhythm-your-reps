package com.example.rhythm_n_reps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
//https://developer.android.com/guide/topics/ui/controls/spinner
// all endpoints https://exercisedb.p.rapidapi.com/exercises/
/**
 * Where User will choose to search for exercises by either target, bodyPart, or equipment.
 * The selection event handler for the spinner (of choices) is implemented by using AdapterView OnItemSelectedListener.
 * Registr a callback to be invoked when an item in this AdapterView has been clicked.
 */
public class ChooseCategoryActivity extends AppCompatActivity implements OnItemSelectedListener {

    private static final String TAG = "ChooseCategoryActivity";
//    private static final String API_KEY_ENDPOINT= "?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f";
//    private String queryString;


    //set lists of items for each category to search the exercises API by, endpoints for reference


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

    // /exercises/target/{target}
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
        setContentView(R.layout.activity_choose_category);

        //First Spinner for bodyPart category
        //set associated spinners from layout
        Spinner spinnerBodyParts = (Spinner) findViewById(R.id.bodyPart_spinner);
        //equip spinner w/ listener
        spinnerBodyParts.setOnItemSelectedListener(this);
        ArrayAdapter adapterB = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bodyParts);
        // Specify the default spinner layout for dropdown list of choices
        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply adapter spinners respectively
        spinnerBodyParts.setAdapter(adapterB);
    }



//        spinnerBodyParts.setOnItemSelectedListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view = null;
//            }
//        });

/*
        //Target category Spinner
        Spinner spinnerTarget = (Spinner) findViewById(R.id.target_spinner);
        spinnerTarget.setOnItemSelectedListener(this);
        ArrayAdapter adapterT = new ArrayAdapter(this, android.R.layout.simple_spinner_item, target);
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTarget.setAdapter(adapterT);

        //Equipment spinner
       Spinner spinnerEquipment = (Spinner) findViewById(R.id.equipment_spinner);
        spinnerEquipment.setOnItemSelectedListener(this);
        ArrayAdapter adapterE = new ArrayAdapter(this, android.R.layout.simple_spinner_item, equipment);
        adapterE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEquipment.setAdapter(adapterE);

     */

        //now set resulting recyclerViews for after spinner value has been selected
        //new intent for web handler



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//        String chosenOption = (String) parent.getItemAtPosition(position);



        //create intent and launch WebServiceActivity
        //TODO uncomment after testing
       /*

        String userSelection = adapterView.getItemAtPosition(adapterView.getSelectedItemPosition()).toString();

        Log.i("resulting userSelection text from spinner in ChooseCat Activity---------", userSelection);

        String queryString1 = "bodyPart/" + userSelection;

       Intent i = new Intent(this, WebServiceActivity.class);
        // send over both 'bodypart' and {userSelection} values for url
        i.putExtra( "selectedFilter", queryString1);

        Log.i("------ switch case body part spinner-------", queryString1 );
        startActivity(i);
        */




        //for each send over "category/{selectedItem}" format for url to web service
       /*switch(adapterView.getId()){
           case R.id.bodyPart_spinner:
               //get name of array, ie. bodyParts for url
               String queryString1 = "bodyPart/" + userSelection;
               //create intent and send over both 'bodypart' and {userSelection} values for url
               Intent intent = new Intent(this, WebServiceActivity.class );
               intent.putExtra( "selectedFilter", queryString);
               Log.i("------ switch cas body part spinner", queryString1);
               startActivity(intent);
               break;

           case R.id.target_spinner:
               String queryString2 = "target/" + userSelection;
               Intent intent2 = new Intent(this, WebServiceActivity.class );
               intent2.putExtra( "selectedFilter", queryString2);
               startActivity(intent2);
               break;

           case R.id.equipment_spinner:
               String queryString3 = "equipment/" + userSelection;
               Intent intent3 = new Intent(this, WebServiceActivity.class );
               intent3.putExtra( "selectedFilter", queryString3);
               startActivity(intent3);
               break;

        */

       }



        //take in var and launch activity
//        Intent intent = new Intent(this, WebServiceActivity.class );
//        intent.putExtra( "selectedFilter", userSelection );


        //start WebService activity - pass variable used to parse the GET url and render data results for views
//        this.startActivity(intent);

//        Toast.makeText(getApplicationContext(), userSelection, Toast.LENGTH_LONG).show();

        //example url request if user chose bodypart: https://exercisedb.p.rapidapi.com/exercises/bodyPart/userSelection?MYAPIKEY

        //once selected, a button below this spinner will call webServiceHandler w/ url above
        //and new recyclerview will open etc

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getApplicationContext(), "No selection made.", Toast.LENGTH_LONG).show();

    }

//
//    public void callWebserviceButtonHandler(String queryString) {
//        PingWebServiceTask task = new ChooseCategoryActivity.PingWebServiceTask(); //extends asyncTask
//
//        try{
////            String url = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/target/abs?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f");
//            String url = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/bodyPart/" + queryString + API_KEY_ENDPOINT);
//            task.execute(url);
//        }
//        catch (NetworkUtil.MyException e){
//            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
//        }
//
//        }
//
//    private class PingWebServiceTask extends AsyncTask<String, Integer, JSONArray> {
//        //Asynctask off main thread
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            Log.i(TAG, "Making progress...");
//        }
//
//        //takes in String params are the parameters from what the user has entered
//        @Override
//        protected JSONArray doInBackground(String... params) {
//
//            //initialize JSONArray to be returned
//            JSONArray jArray = new JSONArray(); //placeholder for response array
//            try {
//
//                URL url = new URL(params[0]); //String params of argument, just url first item
//                Log.i("-------------------current url to be parsed by NetworkUtil", url.toString());
//                String resp = NetworkUtil.httpResponse(url); // full json array of results as a string given the endpoint
//
//                //get first jsonObj from JSONArray
//                jArray = new JSONArray(resp);
//
//                return jArray; //json array of abs excerises
//
//
//            } catch (MalformedURLException e) {
//                Log.e(TAG, "MalformedURLException");
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                Log.e(TAG, "ProtocolException");
//                e.printStackTrace();
//            } catch (IOException e) {
//                Log.e(TAG, "IOException");
//                e.printStackTrace();
//            } catch (JSONException e) {
//                Log.e(TAG, "JSONException");
//                e.printStackTrace();
//            }
//
//            return jArray;
//        }

//        @Override
//        protected void onPostExecute(JSONArray jArray) {
//            super.onPostExecute(jArray);
//            TextView result_view = (TextView) findViewById(R.id.result_LISTVIEW);
////            ImageView imageView= (ImageView) findViewById(R.id.webServiceImage);
//
//            ArrayList itemList = new ArrayList<>();
//
//            //should use resource strings instead of concatenating when setting text next time
//            try {
//                //open new intent, and send array from service
//                for(int i = 0; i< jArray.length(); i++){
//                    //'traverse' jArray by length, convert to Exe obj. and store as ArrayList to pass
//                    ExerciseRecyclerData e = new ExerciseRecyclerData(jArray.getJSONObject(i));
//                    recyclerDataArrayList.add(e);
//                }
//
//                for (ExerciseRecyclerData i : recyclerDataArrayList) {
//
//                    recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, ChooseCategoryActivity.this);
//
//                    // below line is to set layout manager for our recycler view.
//                    LinearLayoutManager manager = new LinearLayoutManager(ChooseCategoryActivity.this);
//
//                    // setting layout manager for our recycler view.
//                    recyclerView.setLayoutManager(manager);
//
//                    // below line is to set adapter to our recycler view.
//                    recyclerView.setAdapter(recyclerViewAdapter);
//
//                }
//
//                // below line we are running a loop to add data to our adapter class.
//                for (int i = 0; i < recyclerDataArrayList.size(); i++){
//
//                    recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, ChooseCategoryActivity.this);
//
//                    // below line is to set layout manager for our recycler view.
//                    LinearLayoutManager manager = new LinearLayoutManager(ChooseCategoryActivity.this);
//
//                    // setting layout manager for our recycler view.
//                    recyclerView.setLayoutManager(manager);
//
//                    // below line is to set adapter to our recycler view.
//                    recyclerView.setAdapter(recyclerViewAdapter);
//
//                }
////                sendData(itemList); //could send data to webservice activity via intents, cleaner
//
//
//            } catch (JSONException e) {
//                result_view.setText("Something went wrong!");
//            }
//
//        }
    }





//}


//in order to handle item click events, must use AdapterView's OnItemSelectedListener


//Choices provided to the spinner are provided through a SpinnerAdapter like an ArrayAdapter

    /*
    other option to add string arrays to a spinner
//specify spinner item
cityArea = (Spinner) findViewById(R.id.cityArea);

//create array of the values for the spinner
    ArrayList<String> area = new ArrayList<>();
//add values in area arrayList
cityArea.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, area));

     */

