package com.example.rhythm_n_reps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//https://developer.android.com/guide/topics/ui/controls/spinner
// all endpoints https://exercisedb.p.rapidapi.com/exercises/

/**
 * Where User will choose to search for exercises by either target, bodyPart, or equipment.
 * The selection event handler for the spinner (of choices) is implemented by using AdapterView OnItemSelectedListener.
 * Registr a callback to be invoked when an item in this AdapterView has been clicked.
 */
public class ChooseCategoryActivity extends AppCompatActivity {

    private static final String TAG = "ChooseCategoryActivity";
    //    private static final String API_KEY_ENDPOINT= "?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f";
//    private String queryString;
    ArrayList<ExerciseRecyclerData> exercisesList = new ArrayList<ExerciseRecyclerData>();
    private static final String apiKeyEndPoint = "?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f";
    private String queryStringReceived; //sent via intent (user selection)
    private ArrayList<String> idNumbers = new ArrayList<String>();

    private ArrayList<ExerciseRecyclerData> listToKeep = new ArrayList<>();

    private Button selectExercisesButton;


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



//        String url0 = "https://exercisedb.p.rapidapi.com/exercises/target/abs" + apiKeyEndPoint;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
//            queryStringReceived = extras.getString("selectedFilter"); //have to get queryString2 and 3
            queryStringReceived = extras.getString("var");
        }

        String[] queryArr = queryStringReceived.split("/"); // category / choice
        Log.i("~~~~~~~~~ use for result view ~~~~~~~~~~~~~~  retrieves extras from choose activity", queryArr[0]);
        Log.i("~~~~~~~~~ CATEGORY use for result view ~~~~~~~~~~~~~~  retrieves extras from choose activity", queryArr[1]);

        TextView result_view = (TextView)findViewById(R.id.textUserListView); //get category and choice for query results view
        WebServiceActivity.resultTextPage(queryArr[0], queryArr[1], result_view);

        String url0 = "https://exercisedb.p.rapidapi.com/exercises/" + queryStringReceived + apiKeyEndPoint;

        callWebserviceButtonHandler(url0);

        selectExercisesButton = findViewById(R.id.addSelected);

        selectExercisesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendItems = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
                //toKeepList should be maintained, anytime the status of one click is changed to +

                Bundle bundle = new Bundle();

                startActivity(sendItems);
            }
        });





    }


    //when user clicks on button, new Async task created, get url user has entered and execute it
    public void callWebserviceButtonHandler(String url) {
        BackTask task = new BackTask(); //extends asyncTask

        try {
            //validate url and execute endpoint to get json response
            url = NetworkUtil.validInput(url);
            task.execute(url);
        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public class BackTask extends AsyncTask<String, Integer, JSONArray> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Making progress...");
        }

        @Override
        protected JSONArray doInBackground(String... params) {

            JSONArray jArray = new JSONArray(); //placeholder for response array

            try {
                //current url to be parsed by NetworkUtil
                URL url0 = new URL(params[0]); //String params of argument, just url - first item
                String resp = NetworkUtil.httpResponse(url0); // full json array of results as a string given the endpoint (only use w/ shorter response results!)
                jArray = new JSONArray(resp);

                return jArray; //json array of returned exercises

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

        //simple list view and adaper for returned array results
        @Override
        protected void onPostExecute(JSONArray jArray) {
            super.onPostExecute(jArray);

//            TextView textView = findViewById(R.id.textUserListView);
            JSONObject t = null;
//            ArrayList<String> myStringArray = new ArrayList<String>();

            try {
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jsonobject = jArray.getJSONObject(i);

                    ExerciseRecyclerData exe = new ExerciseRecyclerData(jsonobject);
//                    exe.setName(jsonobject.getString("name"));
                    exercisesList.add(exe); //add this Exe item to our resulting exercises list
//                    myStringArray.add(exe.getName() + "  " + exe.getTarget());

                } //end of for loop, in try

                //now create adapter for our list of returned results, simple_list item is a formatted layout for each item

                CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(), R.layout.activity_choose_category, exercisesList);

                ItemClickListener itemClickListener = new ItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //attributions bond to the item has been changed
                        exercisesList.get(position).onItemClick(position); //get the item based off of position & call onItemClick given that position

//                        adapter.notifyItemChanged(position); //notify adapter items have changed
                    }

                    @Override
                    public void onCheckBoxClick(int position) {
                        //changes STATUS of exerciseData item to isChecked
                        exercisesList.get(position).onCheckBoxClick(position); //get the item based off of position & call onCheckBoxClick given that position
                        ExerciseRecyclerData clickedExercise = exercisesList.get(position); //get actual object that was clicked

                        String exerciseClicked = clickedExercise.getItemName();
                        String id = " id-----  "+ clickedExercise.getId(); //actual id
                        if(clickedExercise.getStatus()) {
                            listToKeep.add(exercisesList.get(position)); //get the item that was clicked and add to araylist
                            idNumbers.add(clickedExercise.getId());
                            Toast.makeText(getApplicationContext(), "CHECKBOX CLICKED " + exerciseClicked + id, Toast.LENGTH_SHORT).show();
                        }
//                        Toast.makeText(getApplicationContext(), "CHECKBOX CLICKED " + exerciseClicked + id, Toast.LENGTH_SHORT).show();

                        //attributions bond to the item has been changed
//                        adapter.notifyItemChanged(position);
                    }
                };

                adapter.setOnItemClickListener(itemClickListener);
                // now set adapter to list view

                ListView listView = (ListView) findViewById(R.id.userListView);
                listView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    /*
    For future reference this code is faster and from the API documentation... wish i had seen this... sigh
    OkHttpClient client = new OkHttpClient();

Request request = new Request.Builder()
	.url("https://exercisedb.p.rapidapi.com/exercises")
	.get()
	.addHeader("x-rapidapi-host", "exercisedb.p.rapidapi.com")
	.addHeader("x-rapidapi-key", "3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f")
	.build();

Response response = client.newCall(request).execute();
     */


//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//        String chosenOption = (String) parent.getItemAtPosition(position);


    //create intent and launch WebServiceActivity

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

//       }


    //take in var and launch activity
//        Intent intent = new Intent(this, WebServiceActivity.class );
//        intent.putExtra( "selectedFilter", userSelection );


    //start WebService activity - pass variable used to parse the GET url and render data results for views
//        this.startActivity(intent);

//        Toast.makeText(getApplicationContext(), userSelection, Toast.LENGTH_LONG).show();

    //example url request if user chose bodypart: https://exercisedb.p.rapidapi.com/exercises/bodyPart/userSelection?MYAPIKEY

    //once selected, a button below this spinner will call webServiceHandler w/ url above
    //and new recyclerview will open etc

//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//        Toast.makeText(getApplicationContext(), "No selection made.", Toast.LENGTH_LONG).show();
//
//    }

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

