package com.example.rhythm_n_reps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class ListWorkoutSessionActivity extends AppCompatActivity {

    private ArrayList<ItemCard> itemList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RViewAdapter rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private FloatingActionButton addButton;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

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

        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override //if user tries to drag view from position, return false (don't allow dragging of buttons)
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //onSwiped, if item is swiped whichever directioon, then display Toast message & delete item from list
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(ListWorkoutSessionActivity.this, "Delete an item", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition(); //which item is being swiped
                itemList.remove(position);

                rviewAdapter.notifyItemRemoved(position); //just removes one item, simple example

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView); //then attach the touch helper we set up to the recyclerView

    }

    //could also use 'notifyDataChange', which is update, recalculates everything (8:00), better for more complicated examples
    //but this example does not have to do heavy calculating since only removing one item


    // Handling Orientation Changes on Android
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {


        int size = itemList == null ? 0 : itemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        // Need to generate unique key for each item
        // This is only a possible way to do, please find your own way to generate the key
        for (int i = 0; i < size; i++) {
            // put image information id into instance
            outState.putInt(KEY_OF_INSTANCE + i + "0", itemList.get(i).getImageSource()); //generate unique keys
            // put itemName information into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", itemList.get(i).getItemName());
            // put itemDesc information into instance
            outState.putString(KEY_OF_INSTANCE + i + "2", itemList.get(i).getItemDesc());
            // put isChecked information into instance
            outState.putBoolean(KEY_OF_INSTANCE + i + "3", itemList.get(i).getStatus());
        }
        super.onSaveInstanceState(outState);
        //handles configuration change, so we save current state of activity before it goes away

    }


    // init our savedInstanceState - for configuration changes eg. rotation change...
    private void init(Bundle savedInstanceState) {

        initialItemData(savedInstanceState); //get the three pieces of data
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {

        // if Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (itemList == null || itemList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    Integer imgId = savedInstanceState.getInt(KEY_OF_INSTANCE + i + "0");
                    String itemName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    String itemDesc = savedInstanceState.getString(KEY_OF_INSTANCE + i + "2");
                    boolean isChecked = savedInstanceState.getBoolean(KEY_OF_INSTANCE + i + "3");

                    // We need to make sure names such as "XXX(checked)" will not duplicate
                    // Use a tricky way to solve this problem, not the best though
                    if (isChecked) {
                        itemName = itemName.substring(0, itemName.lastIndexOf("("));
                    }
                    ItemCard itemCard = new ItemCard(imgId, itemName, itemDesc, isChecked);

                    itemList.add(itemCard);
                }
            }
        }
        // The first time to opne this Activity
        else {
            ItemCard item1 = new ItemCard(R.drawable.push_up_down, "Gmail", "Example exercise", false);
            itemList.add(item1);

        }

    }

    //create RecyclerView and set up
    //set click listeners, set adapter
    private void createRecyclerView() {


        rLayoutManger = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recycler_view); //get recyclerView and set it to have fixed size (11:30)
        recyclerView.setHasFixedSize(true);
        //items are all fixed size helps recyclerView not worry about recalculating sizes, thus much faster

        rviewAdapter = new RViewAdapter(itemList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //attributions bond to the item has been changed
                itemList.get(position).onItemClick(position);

                rviewAdapter.notifyItemChanged(position);
            }

            @Override
            public void onCheckBoxClick(int position) {
                //attributions bond to the item has been changed
                itemList.get(position).onCheckBoxClick(position);

                rviewAdapter.notifyItemChanged(position);
            }
        };
        rviewAdapter.setOnItemClickListener(itemClickListener);

        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);


    }

    //
    private void addItem(int position) {
        //add temp fake image, but this should be an exercise image... taken from json obj
        itemList.add(position, new ItemCard(R.drawable.ic_android_black_24dp, "No Logo item", "Item id: " + Math.abs(new Random().nextInt(100000)), false));
        Toast.makeText(ListWorkoutSessionActivity.this, "Add an item", Toast.LENGTH_SHORT).show();

        rviewAdapter.notifyItemInserted(position); //notify new item inserted, thus notifying adapter everything must be recalculated
    }



}