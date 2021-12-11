package com.example.rhythm_n_reps;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONObject;
import java.util.ArrayList;

public class ListViewResults extends AppCompatActivity {

    //display results of user querying exercises returned from APi
    //will receive bundle containing array list of JSON Objects (exercises)
    //and display the cardViews

//    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_results);

        //get query results - array from WebService when user clicks on get results
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        String JResults = extras.getString("jArray");
        Log.i("Returned jArray result", JResults.toString());

        final TextView textView = findViewById(R.id.result_LISTVIEW);
        textView.setText(JResults.charAt(8));

//        Log.i("bundle result---", JResults.toString());


//        for (JSONObject j : jArray = extras.getParcelableArray("jArray")) {
//            recyclerDataArrayList.add(j);
//        };

    }

    public void createRecyclerView(){

//        courseRV.setLayoutManager(manager);
        //hook adapter to recylerView




    }



}