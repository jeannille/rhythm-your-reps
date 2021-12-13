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
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;
import com.bumptech.glide.Glide;
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


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;
    // creating new array list to store api response results as ExerciseRecyclerData (formatted)
    private ArrayList<ExerciseRecyclerData> recyclerDataArrayList = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;
    private String url0;
    private String queryStringReceived;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_web_service);
        //explicitly inflate the web service view
        LayoutInflater inflater = LayoutInflater.from(WebServiceActivity.this); // 1
        View inflatedView = inflater.inflate(R.layout.activity_web_service, null);
        setContentView(inflatedView);

        Handler handler = new Handler();
//        mURLEditText = findViewById(R.id.URL_editText);
//        mTitleTextView = findViewById(R.id.result_textview);

        // initializing variables, views that will be rendered from this activity
//
//        progressBar = findViewById(R.id.idProgBarWebS);
//        TextView textView = findViewById(R.id.topOfResultsPage);
//        textView.setText("TOP OF CARD LIST RESULTS PAGE");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
//            queryStringReceived = extras.getString("selectedFilter"); //have to get queryString2 and 3
            queryStringReceived = extras.getString("var");
        }

        Log.i("retrieves extras from choose activity", queryStringReceived);

        url0 = "https://exercisedb.p.rapidapi.com/exercises/" + queryStringReceived + apiKeyEndPoint;

        Log.i("Fully built url after getting info and showing in WebService Activity-------", url0);
        //logcat displays this error right after above log print out: W/RecyclerView: No adapter attached; skipping layout

//        queryString = i.getExtras().getString("selectedFilter");
//        textView.setText(queryString).charAt(8)); //test result and font size check

        //where the magic happens
        callWebserviceButtonHandler(url0);

    }

    /**
     * Creates gif image from provided URl and displays to image view.
     *
     * @param view
     * @param gifUrl
     */
    public void showGif(View view, String gifUrl) {
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this).load(gifUrl).into(imageView);
    }

    //when user clicks on button, new Async task created, get url user has entered and execute it
    public void callWebserviceButtonHandler(String url) {
        PingWebServiceTask task = new PingWebServiceTask(); //extends asyncTask

        try {
//            String url = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/equipment/" + CATEGORYNAME FROM DROPDOWN + qString + apiKeyEndPoint);
//            String url = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/" + apiKeyEndPoint);
            url = NetworkUtil.validInput(url);
            task.execute(url);
        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //TODO re-implement url format by which category was chosen

    //did not end up using but will keep for now
    public ExerciseRecyclerData jsonToRData(JSONObject jObject) {
        ExerciseRecyclerData exercise = null; //placeholder for try and catch
        //test intent to add to RView instead, JSONArray

        try {
            //gif only displays when protocol in url is 'https' not 'http' like the raw data url
            String gif = jObject.getString("gifUrl").replace("http", "https");
            exercise = new ExerciseRecyclerData(jObject);
//            exercise = new ItemCard( Integer.parseInt( jObject.getString("id")), jObject.getString("name"), gif, false);

        } catch (JSONException e) {
            Log.i("ERROR trying to create obj----", "Something went wronggggg----");
//            result_view.setText("Something went wrong!");
            Toast.makeText(getApplicationContext(), "Something went wrong WOMPWOMP", Toast.LENGTH_SHORT).show();
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
                //current url to be parsed by NetworkUtil
                URL url0 = new URL(params[0]); //String params of argument, just url - first item

                // Get String response from the url address
                //NetworkUtil creates HttpURLConnection obj & uses GET req method
                //reads response using input stream & returns resp as String
                String resp = NetworkUtil.httpResponse(url0); // full json array of results as a string given the endpoint (only use w/ shorter response results!)
//                Log.i("~~~~~~~~~~~~~~~~~~~~~~json resp of networkUtil.httpResponse(url)~~~~~~~~~~~~", resp);

                //initialize placeholder json array of retrieved results/response from endpoint
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

        //after posting response, retrieve json array, traverse and change to reg ArrayList
        @Override
        protected void onPostExecute(JSONArray jArray) {
            super.onPostExecute(jArray);
            TextView result_view = (TextView) findViewById(R.id.URL_editText);
            ImageView imageView= (ImageView) findViewById(R.id.webServiceImage); //adapter handles this now - for each card/exercise

            //dynamically traverse jArray, convert each to Exercise obj. and populate RecylerDataArrayList
            try {
                String exName = jArray.getJSONObject(0).getString("name");
                result_view.setText(exName);


                String imgStr = jArray.getJSONObject(0).getString("gifUrl").replace("http", "https");
                Glide.with(getApplicationContext()).load(imgStr).into(imageView);


//                for (int i = 0; i < jArray.length(); i++) {
//
//                    ExerciseRecyclerData e = new ExerciseRecyclerData(jArray.getJSONObject(i));
//                    recyclerDataArrayList.add(e);
//                }
//                for (ExerciseRecyclerData i : recyclerDataArrayList) {
//
//                    recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, WebServiceActivity.this);
//                    // store xml's linear layout manager for this class (customizable)
//                    LinearLayoutManager manager = new LinearLayoutManager(WebServiceActivity.this);
//
//                    // set recyclerView's layout manager just created above
//                    recyclerView.setLayoutManager(manager);
//
//                    // finally set adapter for recycler view, will act as middle man for data and its views
//                    recyclerView.setAdapter(recyclerViewAdapter);
//
//                }
//
//                //create RecyclerView
//                recyclerView = findViewById(R.id.idRecyclerViewWebS);
//                //create and set layout manager for recyclerView
//                rLayoutManger = new LinearLayoutManager(WebServiceActivity.this);
//                recyclerView.setLayoutManager(rLayoutManger);
//
//                // create & set adapter to our recycler view
//                recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, WebServiceActivity.this);
//                recyclerView.setAdapter(recyclerViewAdapter);

            } catch (JSONException e) {
                result_view.setText("Something went wrong!");
            }

        }
    }



//                 traverse to add data to Adapter object
//                for (int i = 0; i < recyclerDataArrayList.size(); i++) {
//                }


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


// should use resource strings instead of concatenating when setting text next time
//                result_view.setText(jObject.toString()); //test full json returned
//                result_view.setText( "Exercise Name: " + jObject.getString("name") +
//                        "\n" +"Body part: " + jObject.getString("bodyPart") + "\n" + "Target muscle(s): "
//                        + jObject.getString("target") + "\n" + "Required Equipment: "
//                + jObject.getString("equipment") + "\n" ); //for JSON array of results
//
//                result_view.setText(jArray.getJSONObject(0).getString("name")); // 3/4 abs
    //create ArrayList of JSONObjects for

