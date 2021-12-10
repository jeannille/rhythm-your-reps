package com.example.rhythm_n_reps;

import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtil {

    public static class MyException extends Exception {
        public MyException() {
        }

        public MyException(String message) {
            super(message);
        }
    }

    //checks to see of the url input is valid URL format
    public static String validInput(String url) throws MyException {
        if (Patterns.WEB_URL.matcher(url).matches() || URLUtil.isValidUrl(url)) {
            if(!(url.startsWith("https://")||url.startsWith("http://"))){
                return "https://" + url;
            }
            return url;
        }

        throw new MyException("Invalid Input");
    }


    //Java's stream api and string builder
    public static String convertStreamToString(InputStream inputStream){
        StringBuilder stringBuilder=new StringBuilder();
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String len;
            while((len=bufferedReader.readLine())!=null){
                stringBuilder.append(len);
            }
            bufferedReader.close();
            return stringBuilder.toString().replace(",", ",\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String httpResponse(URL url) throws IOException {
        //open a connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //method of request is GET
        conn.setRequestMethod("GET");
        //DoInput means retrieving from server where output would outputting to server
        conn.setDoInput(true);

        //tell connection to connect
        conn.connect();

        // Read response.
        InputStream inputStream = conn.getInputStream();
        //helper function to convert Java Stream into String
        String resp = NetworkUtil.convertStreamToString(inputStream);

        return resp;
    }


    @Deprecated
    public static void print(Object o){
        Log.e("log",String.valueOf(o));
    }


}
