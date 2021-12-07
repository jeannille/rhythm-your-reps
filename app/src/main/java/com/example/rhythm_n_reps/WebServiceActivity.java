package com.example.rhythm_n_reps;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public void ShowGif(View view, String gifUrl) {
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this).load(gifUrl).into(imageView);
    }

    //when user clicks on button, new Async task created, get url user has entered and execute it
    //api key - 2ee82f0cca6abd8fce24191c8174a521a04c1e7e
    //switched api - exercisedb
    public void callWebserviceButtonHandler(View view) {
        PingWebServiceTask task = new PingWebServiceTask(); //extends asyncTask
        //get & save url user has entered in textbox, make sure its valid
        String rawInput = mURLEditText.getText().toString();
        //remove leading & trailing spaces plus any multiple spacing then replaces spaces with + for query
        String userInput = rawInput.trim().replaceAll(" +", " ");
        String queryString = userInput.replace(" ", "+");
        String apiKeyEndPoint = "?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f";
        try {
//            Log.i("---------------------------------------------- input test", queryString);
            //append user's input as query to api that includes my API key
//            String url = NetworkUtil.validInput("https://www.omdbapi.com/?apikey=21a5afff&plot=full&&t=" + queryString);
            //hardcoded exercise returns
//            String url = NetworkUtil.validInput("https://wger.de/api/v2/exerciseinfo/129");
            //returns array of results for exercises that focus on abs
            String url = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/target/abs?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f");
            task.execute(url); //execute updated url w/ user's query appended
        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    //TO DO schema: bodyPart, equipment, gifurl, id (string), name, target

    private class PingWebServiceTask extends AsyncTask<String, Integer, JSONObject> {
        //asynctask off main thread

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Making progress...");
        }

        //takes in String params are the parameters from what the user has entered
        @Override
        protected JSONObject doInBackground(String... params) {

            //passing back result, pass back two strings as part of an array
//            JSONObject result = null;

            //initialize JSONObject to be returned
            JSONObject jObject = new JSONObject();
            JSONArray jArray = new JSONArray();
            try {

                URL url = new URL(params[0]);
                Log.i("--------current url to be parsed by NetworkUtil", url.toString());
                // Get String response from the url address
                //NetworkUtil  creates HttpURLConnection obj & uses GET req method
                //reads response using input stream & returns resp as String
                String resp = NetworkUtil.httpResponse(url);
                Log.i("String resp", resp);

                //get first jsonObj from JSONArray
                jArray = new JSONArray(resp);
//                JSONObject test = jArray.getJSONObject(0);

                jObject = jArray.getJSONObject(2);

//                System.out.print("SUCCESS woooo");
                return jObject;


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

            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject jObject) {
            super.onPostExecute(jObject);
            TextView result_view = (TextView) findViewById(R.id.result_textview);
            ImageView imageView= (ImageView) findViewById(R.id.webServiceImage);

            //should use resourc strings instead of concatenating when setting text next time
            try {
                //result_view.setText(jObject.toString()); //test full json returned

                result_view.setText( "Exercise Name: " + jObject.getString("name") +
                        "\n" +"Body part: " + jObject.getString("bodyPart") + "\n" + "Target muscle(s): "
                        + jObject.getString("target") + "\n" + "Required Equipment: "
                + jObject.getString("equipment")); //for JSON array of results


//                String plot = jObject.getString("Plot").replace("\n", "");
//                String actors = jObject.getString("Actors").replace("\n", "");
//                Log.i("-------------GIF URL obtained----------------",imgStr);
                //replace gif url with https in order to load
                String imgStr = jObject.getString("gifUrl").replace("http", "https");
                Picasso.get().load(imgStr).into(imageView); //works


//                Glide.with(this).load(imgStr).into(result_img);
                Glide.with(getApplicationContext()).load(imgStr).into(imageView);





            } catch (JSONException e) {
                result_view.setText("Something went wrong!");
            }

        }
    }


}