package com.example.rhythm_n_reps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        mURLEditText = findViewById(R.id.URL_editText);
        mTitleTextView = findViewById(R.id.result_textview);
    }

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
    public void callWebserviceButtonHandler(View view) {
        PingWebServiceTask task = new PingWebServiceTask(); //extends asyncTask
        //get & save url user has entered in textbox, make sure its valid
        String rawInput = mURLEditText.getText().toString();
        //remove leading & trailing spaces plus any multiple spacing then replaces spaces with + for query
        String userInput = rawInput.trim().replaceAll(" +", " ");
        String queryString = userInput.replace(" ", "+");
//        String apiKeyEndPoint = "?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f";
        //keep array of possible target, if user does not enter correct target, display options
        //when user enters target, display with userInput as target in URL and return
        try {
//            Log.i("---------------------------------------------- input test", queryString);
            //append user's input as query to api that includes my API key
            //hardcoded exercise returns
            //returns array of results for exercises that focus on whatever user has entered
            //if button/view clicked is for target, use this URL endpoint
            //TO DO: would probably better to offer the user a drop down for each category :/
            //less room for error
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

//            Log.i("USER INPUT-------------- after editing", queryString); // check expected format
//            String url = NetworkUtil.validInput("https://exercisedb.p.rapidapi.com/exercises/target/abs?rapidapi-key=3dc44b11e1msh1ffd3a2125bf15fp1f0c21jsn1a1dde786b0f");

        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    //TO DO schema: bodyPart, equipment, gifUrl, id (string), name, target

    public ItemCard testJSONToItemCard (JSONObject jObject){
        ItemCard exercise = null; //placeholder for try and catch
        //test intent to add to RView instead, JSONArray
        //or function to make one JSON obj from array into item card for now w/ just name & gif
        //getDesc is gifurl for now
        try {
            String gif = jObject.getString("gifUrl").replace("http", "https");
            exercise = new ItemCard( Integer.parseInt( jObject.getString("id")), jObject.getString("name"), gif,
            false);

        }
        catch (JSONException e) {
            Log.i("ERROR trying to create obj", "Something went wronggggg");
//            result_view.setText("Something went wrong!");
        }
        return exercise;
    }

    private class PingWebServiceTask extends AsyncTask<String, Integer, JSONArray> {
        //asynctask off main thread

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Making progress...");
        }

        //takes in String params are the parameters from what the user has entered
        @Override
        protected JSONArray doInBackground(String... params) {

            //passing back result, pass back two strings as part of an array
            ItemCard test = null;

            //initialize JSONObject to be returned
//            JSONObject jObject = new JSONObject();
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
//                test = testJSONToItemCard(jObject);
//                Integer blah = test.getImageSource();
//                Log.i("TESTING IF JSON is ITEMCARD now ---------", blah.toString()); //obj not array

//                System.out.print("SUCCESS woooo");
                return jArray;


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
            TextView result_view = (TextView) findViewById(R.id.result_textview);
            ImageView imageView= (ImageView) findViewById(R.id.webServiceImage);
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

                for(int i = 0; i< jArray.length(); i++){
                    itemList.add(jArray.getJSONObject(i));
                }
                Log.i("------------------------ itemList!!!!", itemList.toString());
                result_view.setText(itemList.get(3).toString());

                //replace gif url with https in order to load
                String imgStr = jArray.getJSONObject(0).getString("gifUrl").replace("http", "https");
//                Log.i("-------------GIF URL obtained----------------",imgStr); //double check result
//                Picasso.get().load(imgStr).into(imageView); //works

//                Glide.with(this).load(imgStr).into(result_img);
                Glide.with(getApplicationContext()).load(imgStr).into(imageView);

                //get value of id for specific jsonObj, use this when user clicks on the exercise to add
//                Log.i("test", String.valueOf(jObject.get("id"))); //{id}

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