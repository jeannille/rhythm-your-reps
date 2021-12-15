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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//activity handles the user's input (from WebView), makes (HTTP) request to the web service
// makes request and receives responses from the web service (third party api)
//Android Service runs its processes in the background of app
//uses RecyclerViewAdapter - which has RecyclerHolder class defined in same java file
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
//    private ArrayList<ExerciseRecyclerData> randomList = new ArrayList<>();
    private String url0;
    private String queryStringReceived; //sent via intent (user selection)
    CheckBox addToWorkout;
    EditText userInput;

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
        addToWorkout = findViewById(R.id.checkboxSearchResultsList);
        userInput = findViewById(R.id.userEditBox);

//        String test = userInput.toString();

//        progressBar = findViewById(R.id.idProgBarWebS);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
////            queryStringReceived = extras.getString("selectedFilter"); //have to get queryString2 and 3
//            queryStringReceived = extras.getString("var");
//        }

//        Log.i("successfully retrieves extras from choose activity", queryStringReceived);

//        String choice = queryStringReceived.substring(queryStringReceived.lastIndexOf("/") + 1); //get the selected after category
//        String[] queryArr = queryStringReceived.split("/"); // category / choice
//        Log.i("~~~~~~~~~ use for result view ~~~~~~~~~~~~~~  retrieves extras from choose activity", queryArr[0]);
//        Log.i("~~~~~~~~~ CATEGORY use for result view ~~~~~~~~~~~~~~  retrieves extras from choose activity", queryArr[1]);
//
//        TextView result_view = (TextView)findViewById(R.id.URL_editText); //get category and choice for query results view
//        resultTextPage(queryArr[0], queryArr[1], result_view);


        //wanted user to see the gif when cliked on image instead of having gif by default

//        url0 = "https://exercisedb.p.rapidapi.com/exercises/" + queryStringReceived + apiKeyEndPoint;
//        url0 = "https://exercisedb.p.rapidapi.com/exercises/target/" + "delts" + apiKeyEndPoint;
//        Log.i("Fully concatenated url after getting info and showing in WebService Activity-------", url0);

        //where the magic happens
//        callWebserviceButtonHandler(url0);

    }

    public void selectItems(View view) {

        String trim = userInput.getText().toString();
        String url2 = "https://exercisedb.p.rapidapi.com/exercises/target/" + trim.toLowerCase() + apiKeyEndPoint;
        Log.i("URL with user input", url2);
        callWebserviceButtonHandler(url2);

        Log.i("-----", trim);

    }

//        ArrayList<ExerciseRecyclerData> test = new ArrayList<ExerciseRecyclerData>();
//
////        callWebserviceButtonHandler(url0);
//        for (ExerciseRecyclerData e: recyclerDataArrayList){
//            if(e.getStatus()){
//                test.add(e);
//            }
//        }
//        Log.i("Added the items ------- ", test.toString());


    //generate different responses according to selection
    public static void resultTextPage(String category, String selection, TextView textView){

        switch (category) {
            case "bodyPart":
                textView.setText("Bodypart focus : " + selection);
                break;
            case "target":
                textView.setText("Targeted muscles : " + selection);
                break;
            case "equipment":
                textView.setText("Equipment required : " + selection);
                break;

        }

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
            //validate url and execute endpoint to get json response
            url = NetworkUtil.validInput(url);
            task.execute(url);
        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //did not end up using but will keep for now, crated additonal constructer that takes in json
    public ExerciseRecyclerData jsonToRData(JSONObject jObject) {
        ExerciseRecyclerData exercise = null; //placeholder for try and catch
        //test intent to add to RView instead, JSONArray

        try {
            //gif only displays when protocol in url is 'https' not 'http' like the raw data url
            String gif = jObject.getString("gifUrl").replace("http", "https");
            exercise = new ExerciseRecyclerData(jObject);

        } catch (JSONException e) {
            Log.i("ERROR trying to create obj----", "Something went WRONG----");
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
                //NetworkUtil creates HttpURLConnection obj, uses GET req method reads response using input stream & returns resp as String
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
//            ImageView imageView= (ImageView) findViewById(R.id.webServiceImage); //adapter handles this now - for each card/exercise

            //dynamically traverse jArray, convert each to Exercise obj. and populate RecylerDataArrayList
            try {
//                String exName = jArray.getJSONObject(0).getString("name");
//                result_view.setText(exName);
//                String imgStr = jArray.getJSONObject(0).getString("gifUrl").replace("http", "https");
//                Glide.with(getApplicationContext()).load(imgStr).into(imageView);

                for (int i = 0; i < jArray.length(); i++) {

                    ExerciseRecyclerData e = new ExerciseRecyclerData(jArray.getJSONObject(i));
                    recyclerDataArrayList.add(e);
                }


                ArrayList<ExerciseRecyclerData> randomList = new ArrayList<>();
                //just for ranomizing 3

                for(int i=0; i <3; i++){
                    Random random = new Random();
                    int one = random.nextInt(recyclerDataArrayList.size() - 0 + 1) + 0;
//                    int value = random.nextInt(jArray.length());
                    randomList.add(recyclerDataArrayList.get(one));
//                    Log.i("---", recyclerDataArrayList.get(value).getItemName() );
                }





                //create RecyclerView
                recyclerView = findViewById(R.id.idRecyclerViewWebS);
                //create and set layout manager for recyclerView
                rLayoutManger = new LinearLayoutManager(WebServiceActivity.this);
                recyclerView.setLayoutManager(rLayoutManger);

                // create & set adapter to our recycler view
                recyclerViewAdapter = new RecyclerViewAdapter(randomList, WebServiceActivity.this);
                recyclerView.setAdapter(recyclerViewAdapter);

//                if (!recyclerDataArrayList.get(2).getStatus()){
//                    Toast.makeText(getApplicationContext(), "NOT CLICKED", Toast.LENGTH_SHORT).show();
//                }

            } catch (JSONException e) {
                result_view.setText("Something went wrong!");
            }
//            }

        }
    }

//    public void testRequest(View view) throws IOException { //string id then put in ur endpoint
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://exercisedb.p.rapidapi.com/exercises/exercise/0936" + apiKeyEndPoint)
//                .get()
//                .addHeader("x-rapidapi-host", "exercisedb.p.rapidapi.com")
//                .addHeader("x-rapidapi-key", "3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f")
//                .build();
//
//        try (Response response = client.newCall(request).execute()){
//            String test = response.body().string();
//
//            Toast.makeText(getApplicationContext(), "result from select test" + test, Toast.LENGTH_SHORT).show();
//
////            return test;
//        }
//
//
//    }

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

class RandomNumber {
    public static void main(String[] args) {
        RandomNumber r = new RandomNumber();
//        System.out.println(r.random(5, 10));
    }
    public int random(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
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

