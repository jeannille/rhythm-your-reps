package com.example.rhythm_n_reps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

//activity handles the user's input (from WebView), makes (HTTP) request to the web service
// makes request and receives responses from the web service (third party api)
//Android Service runs its processes in the background of app

public class WebServiceActivity extends AppCompatActivity {

    private static final String TAG = "WebServiceActivity";

    private EditText mURLEditText;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        mURLEditText = findViewById(R.id.URL_editText);
        mTitleTextView = findViewById(R.id.result_textview);

    }

    //when user clicks on button, new Async task created, get url user has entered and execute it
    public void callWebserviceButtonHandler(View view) {
        PingWebServiceTask task = new PingWebServiceTask(); //extends asyncTask
        //get & save url user has entered in textbox, make sure its valid
        String rawInput = mURLEditText.getText().toString();
        //remove leading & trailing spaces plus any multiple spacing then replaces spaces with + for query
        String userInput = rawInput.trim().replaceAll(" +", " ");
        String queryString = userInput.replace(" ", "+");
        try {
//            Log.i("---------------------------------------------- input test", queryString);
//            String url = NetworkUtil.validInput("https://www.omdbapi.com/?apikey=21a5afff&s=Bridget+Jones");
            //append user's input as query to api that includes my API key
//            String url = NetworkUtil.validInput("https://www.omdbapi.com/?apikey=21a5afff&plot=full&&t=" + queryString);
            //hardcoded exercise returns
            String url = NetworkUtil.validInput("https://wger.de/api/v2/exerciseinfo/129");
            task.execute(url); //execute updated url w/ user's query appended
        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private class PingWebServiceTask  extends AsyncTask<String, Integer, JSONObject> {
        //asynctask off main thread

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Making progress...");
        }

        //takes in String params are the parameters from what the user has entered
        @Override
        protected JSONObject doInBackground(String... params) {

            //passing back result, pass back two strings as part of an array
            JSONObject result = null;

            //initialize JSONObject to be returned
            JSONObject jObject = new JSONObject();
            try {

                URL url = new URL(params[0]);
                Log.i("--------current url to be parsed by NetworkUtil",url.toString() );
                // Get String response from the url address
                //NetworkUtil  creates HttpURLConnection obj & uses GET req method
                //reads response using input stream & returns resp as String
                String resp = NetworkUtil.httpResponse(url);
                Log.i("String resp",resp);

                jObject = new JSONObject(resp);
//                System.out.print("SUCCESS woooo");
                return jObject;


            } catch (MalformedURLException e) {
                Log.e(TAG,"MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG,"ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG,"IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG,"JSONException");
                e.printStackTrace();
            }

            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject jObject) {
            super.onPostExecute(jObject);
            TextView result_view = (TextView)findViewById(R.id.result_textview);
            ImageView result_img = (ImageView) findViewById(R.id.webServiceImage);

            //should use resourc strings instead of concatenating when setting text next time
            try {
                result_view.setText(jObject.getString("name")); //for JSON array of results
//                String plot = jObject.getString("Plot").replace("\n", "");
//                String actors = jObject.getString("Actors").replace("\n", "");
//                result_view.setText("Title: " + jObject.getString("Title") + "\nPlot: "+ plot + "\nYear: " +
//                        jObject.getString("Year") + "\n"+ "Actors: " + actors);

                //delete below if crash

//                String imgStr = jObject.getString("Poster");
                String imgStr = "https://wger.de/media/exercise-images/129/Standing-biceps-curl-2.png";
                Picasso.get().load(imgStr).into(result_img);


            } catch (JSONException e) {
                result_view.setText("Something went wrong!");
            }

        }
    }



}