package com.example.rhythm_n_reps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import java.net.URI;
import java.util.ArrayList;
import java.util.Random;

// Activity creates and sets up RecyclerView (ViewGroup) containing ItemCard Views
//Makes sure functionality works + handling orientation (any configuration) changes
public class ListWorkoutSessionActivity extends AppCompatActivity {

    //Adapter provides array list of ItemCard View objects
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

        init(savedInstanceState); // call our initialized savedInstanceState

        addButton = findViewById(R.id.addButton); //find/save floating action button & set clickListener below
        addButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             int pos = 0;
                                             addItem(pos); //floating button adds item to position 0 of our list
                                         }
                                     }
        );

        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override //if user tries to drag view from position, return false (don't allow dragging of buttons)
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
    //Attaches key-value pairs to the Bundle that is passed
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //bundle, contains actual data to be updated, updates outState

        int size = itemList == null ? 0 : itemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size); //set bundle's NUMBER_OF_ITEMS from list size (JSON ARRAY)

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


    // initialize our savedInstanceState - for configuration changes eg. rotation change...
    //called in onCreate here, main RView activity
    private void init(Bundle savedInstanceState) {

        initialItemData(savedInstanceState); //get the three pieces of data (bundle)
        createRecyclerView(); //sets/links everything up for RView - RViewHolder, Adapter & ItemCard
    }

    private void initialItemData(Bundle savedInstanceState) {

        // if Not the first time to open this Activity, then create the defaulted items
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (itemList == null || itemList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS); //set how many items will be listed

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    //maybe should be id number of exercise
                    Integer imgId = savedInstanceState.getInt(KEY_OF_INSTANCE + i + "0");
                    String itemName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    String itemDesc = savedInstanceState.getString(KEY_OF_INSTANCE + i + "2");
                    boolean isChecked = savedInstanceState.getBoolean(KEY_OF_INSTANCE + i + "3");

                    // We need to make sure names such as "XXX(checked)" will not duplicate
                    // Use a tricky way to solve this problem, not the best though
                    if (isChecked) {
                        itemName = itemName.substring(0, itemName.lastIndexOf("("));
                    }

                    //create ItemCard using values and add to existing arrayList of ItemCards
                    ItemCard itemCard = new ItemCard(imgId, itemName, itemDesc, isChecked);
                    itemList.add(itemCard);
                }
            }
        }
        // The first time to open this Activity, add default items
        else {
//            ImageView itemIcon = itemView.findViewById(R.id.item_icon);
            ItemCard item1 = new ItemCard(R.drawable.pic_gmail_01, "Gmail", "Example description", false);
            ItemCard item2 = new ItemCard(R.drawable.pic_google_01, "Google", "Example description", false);
            ItemCard item3 = new ItemCard(R.drawable.cable_pulldown, "Push Up", "Body part: Chest", false);
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
                //attributions bond to the item has been changed
                itemList.get(position).onCheckBoxClick(position); //get the item based off of position & call onCheckBoxClick given that position

                rviewAdapter.notifyItemChanged(position);
            }
        };
        //after callbacks are set, set the initialized itemClickListener for rViewAdapter instance
        rviewAdapter.setOnItemClickListener(itemClickListener);

        recyclerView.setAdapter(rviewAdapter); //set this adapter for RecyclerView
        recyclerView.setLayoutManager(rLayoutManger); //set layoutManager for RecyclerView

    }

    //Add a new (generic info for now) item to the full itemList (no logo,
    private void addItem(int position) {
        //add temp fake image, but this should be an exercise image... taken from json obj
        itemList.add(position, new ItemCard(R.drawable.ic_android_black_24dp, "No Logo item", "Item id: "
                + Math.abs(new Random().nextInt(100000)), false));
        Toast.makeText(ListWorkoutSessionActivity.this, "Add an item", Toast.LENGTH_SHORT).show();


        rviewAdapter.notifyItemInserted(position); //notify adapter new item was inserted, so everything gets recalculated
    }



}