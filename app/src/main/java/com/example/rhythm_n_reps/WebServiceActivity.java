package com.example.rhythm_n_reps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

//activity handles the user's input (from WebView), makes (HTTP) request to the web service
// makes request and receives responses from the web service (third party api)
//Android Service runs its processes in the background of app

public class WebServiceActivity extends AppCompatActivity {

    private static final String TAG = "WebServiceActivity";

    //API key will be used for all exercise API access endpoints
    private static final String apiKeyEndPoint = "?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f";

    private EditText mURLEditText;
    private TextView mTitleTextView;
    private ArrayList resultListExe;
    private Button mExerciseByTarget;

    private RecyclerView courseRV;
//    private JSONArray recyclerDataArrayList;
    private ArrayList<ExerciseRecyclerData> recyclerDataArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
//        mURLEditText = findViewById(R.id.URL_editText);
//        mTitleTextView = findViewById(R.id.result_textview);

        // initializing our variables.
        courseRV = findViewById(R.id.idRVCourse);
        progressBar = findViewById(R.id.idPBLoading);

        // creating new array list.
        recyclerDataArrayList = new ArrayList<>();

        callWebserviceButtonHandler();


    }

//    //send over results data to obj (maybe string is json array)
//    //call when the target button (view based here) clicked
//    public void sendData(ArrayList aList){
//
//        Intent i = new Intent(this, ListViewResults.class);
////        i.putParcelableArrayListExtra("jArray", aList);
//        i.putExtra("jArray", aList);
//        startActivity(i);
//        //since target button is linked to webCallService, maybe make splash screen
//        //to send data
//
//    }

    /**
     * Creates gif image from provided URl and displays to image view.
     * @param view
     * @param gifUrl
     */
    public void showGif(View view, String gifUrl) {
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this).load(gifUrl).into(imageView);
    }

    //when user clicks on button, new Async task created, get url user has entered and execute it
    public void callWebserviceButtonHandler() {
        PingWebServiceTask task = new PingWebServiceTask(); //extends asyncTask
//        //get & save url user has entered in textbox, make sure its valid
//        String rawInput = mURLEditText.getText().toString();
//        //remove leading & trailing spaces plus any multiple spacing then replaces spaces with + for query
//        String userInput = rawInput.trim().replaceAll(" +", " ");
//        String queryString = userInput.replace(" ", "+");
        //will change to display options for user (target, bodyPart, and equipment) display options
        //when user enters target, display with userInput as target in URL and return
        try {
            //append user's input as query to api that includes my API key
            //hardcoded exercise returns
            //returns array of results for exercises that focus on whatever user has entered
            //if button/view clicked is for target, use this URL endpoint


/*
            switch (view.getId()) {
                case R.id.searchByTargetButton:
                    Log.i("R ID clicked on IS", "TARGET");
                    String url = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/target/" + queryString + apiKeyEndPoint);
                    task.execute(url);
                    break;
                case R.id.searchByBodypartButton:
                    Log.i("R ID clicked on BODYPART", "BODY PART");
                    String url2 = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/bodyPart/" + queryString + apiKeyEndPoint);
                    task.execute(url2);
                    break;
                case R.id.searchByEquipButton:
                    Log.i("R ID clicked on EQUIPMENT", "EQUIPMENT");
                    String urlEquip = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/equipment/" + queryString + apiKeyEndPoint);
                    task.execute(urlEquip);
                    break;
            }

 */

            Log.i("R ID clicked on IS", "TARGET");
            // TESTING DEC 10 - returns JSON ARRAY OF ABS exercises
            String url = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/target/abs?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f");
//            String url2 = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/bodyPart/" + queryString + apiKeyEndPoint);
            task.execute(url);

        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public ExerciseRecyclerData jsonToRData (JSONObject jObject){
        ExerciseRecyclerData exercise = null; //placeholder for try and catch
        //test intent to add to RView instead, JSONArray
        //or function to make one JSON obj from array into item card for now w/ just name & gif
        //getDesc is gifurl for now
        try {
            //gif only displays when protocol in url is 'https' not 'http' like the raw data url
            String gif = jObject.getString("gifUrl").replace("http", "https");
            exercise = new ExerciseRecyclerData(jObject);
//            exercise = new ItemCard( Integer.parseInt( jObject.getString("id")), jObject.getString("name"), gif, false);

        }
        catch (JSONException e) {
            Log.i("ERROR trying to create obj", "Something went wronggggg");
//            result_view.setText("Something went wrong!");
        }
        return exercise;
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
                // Get String response from the url address
                //NetworkUtil  creates HttpURLConnection obj & uses GET req method
                //reads response using input stream & returns resp as String
                String resp = NetworkUtil.httpResponse(url); // full json array of results as a string given the endpoint
                Log.i("~~~~~~~~~~~~~~~~~~~~~~String resp of networkUtil.httpResponse(url)~~~~~~~~~~~~", resp);

                //get first jsonObj from JSONArray
                jArray = new JSONArray(resp);
//                Integer arrLen = jArray.length();

//                Log.i("DEC 9 - RESULTING array from query: \n", jArray.toString(3));
//                Log.i("length of array -------", arrLen.toString());

//                jObject = jArray.getJSONObject(0);
//                Log.i("TESTING IF JSON is ITEMCARD now ---------", blah.toString()); //obj not array

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
////                result_view.setText(jObject.toString()); //test full json returned
//                result_view.setText( "Exercise Name: " + jObject.getString("name") +
//                        "\n" +"Body part: " + jObject.getString("bodyPart") + "\n" + "Target muscle(s): "
//                        + jObject.getString("target") + "\n" + "Required Equipment: "
//                + jObject.getString("equipment") + "\n" ); //for JSON array of results
//
//                result_view.setText(jArray.getJSONObject(0).getString("name")); // 3/4 abs
                //create ArrayList of JSONObjects for

                //open new intent, and send array from service
                for(int i = 0; i< jArray.length(); i++){
                    //'traverse' jArray by length, convert to Exe obj. and store as ArrayList to pass
                    ExerciseRecyclerData e = new ExerciseRecyclerData(jArray.getJSONObject(i));
                    recyclerDataArrayList.add(e);
                }

                for (ExerciseRecyclerData i : recyclerDataArrayList) {

                    recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, WebServiceActivity.this);

                    // below line is to set layout manager for our recycler view.
                    LinearLayoutManager manager = new LinearLayoutManager(WebServiceActivity.this);

                    // setting layout manager for our recycler view.
                    courseRV.setLayoutManager(manager);

                    // below line is to set adapter to our recycler view.
                    courseRV.setAdapter(recyclerViewAdapter);

                }
                ;

                // below line we are running a loop to add data to our adapter class.
                for (int i = 0; i < recyclerDataArrayList.size(); i++){

                    recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, WebServiceActivity.this);

                    // below line is to set layout manager for our recycler view.
                    LinearLayoutManager manager = new LinearLayoutManager(WebServiceActivity.this);

                    // setting layout manager for our recycler view.
                    courseRV.setLayoutManager(manager);

                    // below line is to set adapter to our recycler view.
                    courseRV.setAdapter(recyclerViewAdapter);

                }



//                result_view.setText(itemList.get(3).toString()); //get position 3 exercise from results for now


                //testing that concersion to E object worked as expected, yayyyy
//                ExerciseRecyclerData test = (ExerciseRecyclerData) itemList.get(3);
//                result_view.setText(test.getName());

                //replace gif url with https in order to load
//                String imgStr = jArray.getJSONObject(0).getString("gifUrl").replace("http", "https");
//                Glide.with(getApplicationContext()).load(imgStr).into(imageView);

//                Picasso.get().load(imgStr).into(imageView); //works for still image, may be good for icon view

//                result_view.setText("did not get error-------- after getting jsonArray");
//                sendData(itemList);



            } catch (JSONException e) {
                result_view.setText("Something went wrong!");
            }

        }
    }

    //tester method for keeping gif as drawable, stackoverflow question on getting from URL path
    //should return drawable
    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }


}