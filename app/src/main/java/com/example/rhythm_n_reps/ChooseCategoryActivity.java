package com.example.rhythm_n_reps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private static final String API_KEY_ENDPOINT= "?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f";
    private String queryString;

    private RecyclerView courseRV;
    //    private JSONArray recyclerDataArrayList;
    private ArrayList<ExerciseRecyclerData> recyclerDataArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;

    //set lists of items for each category to search the exercises API by, endpoints for reference

    // /exercises/bodyPart/{bodyPart}
    String[] bodyParts = {
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
    String[] target = {
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
    String[] equipment = {
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

        //Supply the bodyPart spinner with array of choices (from strings) using ArrayAdapter instance
        Spinner spinner = (Spinner) findViewById(R.id.bodyPart_spinner);
        spinner.setOnItemSelectedListener(this); //equip spinner w/ listener
        // Create an ArrayAdapter instance using the string array and default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.bodyPart_array, android.R.layout.simple_spinner_item);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bodyParts);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //now set resulting recyclerViews for after spinner value has been selected
        courseRV = findViewById(R.id.idRVCourse);
        progressBar = findViewById(R.id.idPBLoading);

        // creating new array list.
        recyclerDataArrayList = new ArrayList<>();

    }

    ; //specify interface implementation by calling setOnItemSelectedListener


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//        String chosenOption = (String) parent.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(), bodyParts[position], Toast.LENGTH_LONG).show();

        String queryString = bodyParts[position]; //eg. 'back'

        callWebserviceButtonHandler(queryString);
        //endpoint would be https://exercisedb.p.rapidapi.com/exercises/bodyPart/bodyPartChosen
        // the display
        //once selected, a button below this spinner will call webServiceHandler w/ url above
        //and new recyclerview will open etc

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void callWebserviceButtonHandler(String queryString) {
        PingWebServiceTask task = new ChooseCategoryActivity.PingWebServiceTask(); //extends asyncTask

        try{
//            String url = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/target/abs?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f");
            String url = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/bodyPart/" + queryString + API_KEY_ENDPOINT);
            task.execute(url);
        }
        catch (NetworkUtil.MyException e){
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        }

    private class PingWebServiceTask extends AsyncTask<String, Integer, JSONArray> {
        //Asynctask off main thread

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Making progress...");
        }

        //takes in String params are the parameters from what the user has entered
        @Override
        protected JSONArray doInBackground(String... params) {

            //initialize JSONArray to be returned
            JSONArray jArray = new JSONArray(); //placeholder for response array
            try {

                URL url = new URL(params[0]); //String params of argument, just url first item
                Log.i("-------------------current url to be parsed by NetworkUtil", url.toString());
                String resp = NetworkUtil.httpResponse(url); // full json array of results as a string given the endpoint

                //get first jsonObj from JSONArray
                jArray = new JSONArray(resp);

                return jArray; //json array of abs excerises


            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, "IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "JSONException");
                e.printStackTrace();
            }

            return jArray;
        }

        @Override
        protected void onPostExecute(JSONArray jArray) {
            super.onPostExecute(jArray);
            TextView result_view = (TextView) findViewById(R.id.result_LISTVIEW);
//            ImageView imageView= (ImageView) findViewById(R.id.webServiceImage);

            ArrayList itemList = new ArrayList<>();

            //should use resource strings instead of concatenating when setting text next time
            try {
                //open new intent, and send array from service
                for(int i = 0; i< jArray.length(); i++){
                    //'traverse' jArray by length, convert to Exe obj. and store as ArrayList to pass
                    ExerciseRecyclerData e = new ExerciseRecyclerData(jArray.getJSONObject(i));
                    recyclerDataArrayList.add(e);
                }

                for (ExerciseRecyclerData i : recyclerDataArrayList) {

                    recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, ChooseCategoryActivity.this);

                    // below line is to set layout manager for our recycler view.
                    LinearLayoutManager manager = new LinearLayoutManager(ChooseCategoryActivity.this);

                    // setting layout manager for our recycler view.
                    courseRV.setLayoutManager(manager);

                    // below line is to set adapter to our recycler view.
                    courseRV.setAdapter(recyclerViewAdapter);

                }

                // below line we are running a loop to add data to our adapter class.
                for (int i = 0; i < recyclerDataArrayList.size(); i++){

                    recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, ChooseCategoryActivity.this);

                    // below line is to set layout manager for our recycler view.
                    LinearLayoutManager manager = new LinearLayoutManager(ChooseCategoryActivity.this);

                    // setting layout manager for our recycler view.
                    courseRV.setLayoutManager(manager);

                    // below line is to set adapter to our recycler view.
                    courseRV.setAdapter(recyclerViewAdapter);

                }
//                sendData(itemList); //could send data to webservice activity via intents, cleaner


            } catch (JSONException e) {
                result_view.setText("Something went wrong!");
            }

        }
    }





}


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

