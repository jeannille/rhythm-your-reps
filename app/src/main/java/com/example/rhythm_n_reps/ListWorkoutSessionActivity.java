package com.example.rhythm_n_reps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Random;

// Activity creates and sets up RecyclerView (ViewGroup) containing ItemCard Views
//Makes sure functionality works + handling orientation (any configuration) changes
public class ListWorkoutSessionActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_1 = 1;

    //Adapter provides array list of ItemCard View objects

    //from brows exercises list, create an Intent to open this activity and when user want to add an exercise to this recycler
    //but putExtra the item to be added to this arrayList when sent (or items)
    private ArrayList<ExerciseRecyclerData> itemList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RViewAdapter rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private FloatingActionButton addButton;
    private ArrayList<ExerciseRecyclerData> itemsChecked;
    CheckBox checkBox;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS"; //length of jason array

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        switch (requestCode) {
            case REQUEST_CODE_1: TextView textView = findViewById(R.id.resultDataTextView);
                if (resultCode == RESULT_OK) {
                    String messageReturn = dataIntent.getStringExtra("message_return");
                    textView.setText(messageReturn);
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_workout_session);

        init(savedInstanceState); // call our initialize savedInstanceState



        addButton = findViewById(R.id.addButton); //find floating action button and add click listener to it
        addButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             int pos = 0;
                                             addItem(pos); //floating button (v) gets added to position 0 of our list (6:20)
                                         }
                                     }
        );

//        checkBox = findViewById(R.id.checkbox);
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
//

        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            //if user tries to drag view from position, return false (don't allow dragging of buttons)
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //onSwiped, if item is swiped whichever direction, then display Toast message & delete item from list
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(ListWorkoutSessionActivity.this, "DELETE ITEM", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition(); //which item is being swiped
                itemList.remove(position);

                rviewAdapter.notifyItemRemoved(position); //simply notify that item is removed
                //can use notifyDatasetChange - recalculate everything, better for more complicated items/list

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView); //after setup, attach the ItemTouchHelper to the RecyclerView

    }


    //could also use 'notifyDatasetChange', which is update, recalculates everything, better for more complicated examples
    //but this example does not have to do heavy calculating since only removing one item

    // Handling Orientation Changes on Android (any configuration changes)
    //preaping the bundle to keep track of (object data)
    //Attaches key-value pairs to the Bundle that is passed
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //bundle, contains actual data to be updated, updates outState

        int size = itemList == null ? 0 : itemList.size(); //recreates list by size
        outState.putInt(NUMBER_OF_ITEMS, size); //set bundle's NUMBER_OF_ITEMS from list size (JSON ARRAY)

        // Need to generate unique key for each item
        // This is only a possible way to do, please find your own way to generate the key
        for (int i = 0; i < size; i++) {
            // put bodyPart information id into instance
            outState.putString(KEY_OF_INSTANCE + i + "0", itemList.get(i).getBodyPart()); //generate unique keys for outState
            // put equipment information into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", itemList.get(i).getEquipment());
            // put gifUrl information into instance
            outState.putString(KEY_OF_INSTANCE + i + "2", itemList.get(i).getGifUrl());
            // put id information into instance
            outState.putString(KEY_OF_INSTANCE + i + "3", itemList.get(i).getId());
            // put name information into instance
            outState.putString(KEY_OF_INSTANCE + i + "4", itemList.get(i).getName());
            // put target information into instance
            outState.putString(KEY_OF_INSTANCE + i + "5", itemList.get(i).getTarget());
            // put isChecked information into instance
            outState.putBoolean(KEY_OF_INSTANCE + i + "6", itemList.get(i).getStatus());
        }
        super.onSaveInstanceState(outState);
        //handles configuration change, so we save current state of activity before it goes away
    }



    // initialize our savedInstanceState - for configuration changes eg.
    //deleting and adding elements
    //called in onCreate here, main RView activity
    private void init(Bundle savedInstanceState) {

        initialItemData(savedInstanceState); //get the existing items/data saved
        createRecyclerView(); //sets/links everything up for RView - RViewHolder, Adapter & ItemCard
    }

//    don't have to use this as we're getting data from API
    private void initialItemData(Bundle savedInstanceState) {

        // if Not the first time to open this Activity, then create the defaulted items
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (itemList == null || itemList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS); //set how many items will be listed
                //String bodyPart, String equipment, String gifUrl, String id, String name, String target

                // Retrieve keys we stored in the instance
                //saving a unique value for each item so they remain in list
                for (int i = 0; i < size; i++) {
                    //maybe should be id number of exercise
                    String bodyPart = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String equipment = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    String gifUrl = savedInstanceState.getString(KEY_OF_INSTANCE + i + "2");
                    String id = savedInstanceState.getString(KEY_OF_INSTANCE + i + "3");
                    String name = savedInstanceState.getString(KEY_OF_INSTANCE + i + "4");
                    String target = savedInstanceState.getString(KEY_OF_INSTANCE + i + "5");
                    boolean isChecked = savedInstanceState.getBoolean(KEY_OF_INSTANCE + i + "6");

                    // We need to make sure names such as "XXX(checked)" will not duplicate
                    // Use a tricky way to solve this problem, not the best though
//                    if (isChecked) {
////                        itemName = itemName.substring(0, itemName.lastIndexOf("("));
//                        name = name.substring(1, name.lastIndexOf("("));
//                    }

                    //create ItemCard using values and add to existing arrayList of ItemCards
                    //re add the exercise with its attibutes we saves
                    ExerciseRecyclerData itemCard = new ExerciseRecyclerData(bodyPart, equipment, gifUrl, id, name, target, isChecked);
                    itemList.add(itemCard);
                }
            }
        }
        // The first time to open this Activity, add default items
        else {

            JSONObject e1 = new JSONObject();
            try {
                e1.put("bodyPart", "upper arms");
                e1.put("equipment" ,"barbell");
                e1.put("gifUrl", "http://d205bpvrqc9yn1.cloudfront.net/0038.gif"); //make sure getGifUrl works with http
                e1.put("id", "0038");
                e1.put("name", "barbell drag curl");
                e1.put("target", "biceps");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ExerciseRecyclerData item1 = new ExerciseRecyclerData(e1);
            ExerciseRecyclerData item2 = new ExerciseRecyclerData("upper arms", "cable",
                    "http://d205bpvrqc9yn1.cloudfront.net/0190.gif",
                    "0190", "cable one arm curl", "biceps", false);


            ExerciseRecyclerData item3 = new ExerciseRecyclerData("upper arms", "barbell", "https://d205bpvrqc9yn1.cloudfront.net/0038.gif",
                    "0038", "barbell drag curl", "biceps", false);
//            ItemCard item3 = new ItemCard( new Media(new URI("http://d205bpvrqc9yn1.cloudfront.net/0007.gif"),  "test", "false");
            itemList.add(item1);
            itemList.add(item2);
            itemList.add(item3);

        }

    }

    //create RecyclerView and set up
    //set click listeners, set adapter
    private void createRecyclerView() {

        rLayoutManger = new LinearLayoutManager(this); //create LinearLayout for RView

        recyclerView = findViewById(R.id.recycler_view); //get recyclerView from resource (xml) and set as fixed size
        recyclerView.setHasFixedSize(true);
        //all items are fixed size, much faster - helps recyclerView not worry about recalculating sizes

        rviewAdapter = new RViewAdapter(itemList); //initialize rViewAdapter given array list of items

        //Initialize / set up the ItemClickListeners for both clicks
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //attributions bond to the item has been changed
                itemList.get(position).onItemClick(position); //get the item based off of position & call onItemClick given that position

                rviewAdapter.notifyItemChanged(position); //notify adapter items have changed
            }

            @Override
            public void onCheckBoxClick(int position) {
                //changes STATUS of exerciseData item to isChecked
                itemList.get(position).onCheckBoxClick(position); //get the item based off of position & call onCheckBoxClick given that position
//                String id = " id-----  "+ itemList.get(position).getId(); //actual id
//                itemsChecked.add(itemList.get(position)); //get the item that was clicked and add to araylist
//                Toast.makeText(getApplicationContext(), "CHECKBOX CLICKED " , Toast.LENGTH_SHORT).show();

                //attributions bond to the item has been changed
                rviewAdapter.notifyItemChanged(position);
            }
        };
        //after callbacks are set, set the initialized itemClickListener for rViewAdapter instance
        rviewAdapter.setOnItemClickListener(itemClickListener);

        recyclerView.setAdapter(rviewAdapter); //set this adapter for RecyclerView
        recyclerView.setLayoutManager(rLayoutManger); //set layoutManager for RecyclerView

    }

    //Add a new (generic info for now) item to the full itemList (no logo)
    //maybe just pass id when they want to add something , and use id to add url and get exer and add it here
    //add item can take in the id or something? or a new Exer. obj

    @SuppressWarnings("deprecation")
    private void addItem(int position) {
        //when user clicks add, open intent and send current itemsList
        //TESTActivity (spinner dropdowns) user selects stuff - connected to Choosecategory already
        // --> ChooseCategoryActivity getIntent(), with current string AND itemsList (from original this Act)
        //In choose category, then can select something to add, which then sends back to Choose
        //the TESTActivity is not revisited on the way back, ChooseA just sends the new item & list
        //

        //after receiving activityResult from ChooseAct (bodyPart/etc..)
        //we add the new Exercise.
        //send back json as string maybe, and just create it here?

//        Intent intentToGetExercise = new Intent(this, TESTActivity.class); //spinners
//        intentToGetExercise.putExtra("message", "This message comes from the LIST WORKOUT TO ADD ONE");
//        startActivityForResult(intentToGetExercise, REQUEST_CODE_1);

        //String url2 = "https://exercisedb.p.rapidapi.com/exercises/bodypart/" + trim.toLowerCase() + apiKeyEndPoint;
//


        itemList.add(position, new ExerciseRecyclerData("upper arms", "cable", "http://d205bpvrqc9yn1.cloudfront.net/1636.gif",
                "1636",  "cable overhead curl", "biceps", false));

//        Toast.makeText(ListWorkoutSessionActivity.this, "Add an item---", Toast.LENGTH_SHORT).show();

        rviewAdapter.notifyItemInserted(position); //notify adapter new item was inserted, so everything gets recalculated
    }


}