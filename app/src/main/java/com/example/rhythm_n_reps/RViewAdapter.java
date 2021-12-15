package com.example.rhythm_n_reps;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Adapter acts as messenger between RViewHolder and the actual data (exercises) that will be displayed.
 * It reads the data and passes/binds it to a RViewHolder instance in the list.
 */

//adapter for slightly different RecyclerView lists since user should be able to delete and check
public class RViewAdapter extends RecyclerView.Adapter<RViewHolder> {

    //Takes in ItemCard View and creates array list if ItemCards

    private final ArrayList<ExerciseRecyclerData> itemList;
    private ItemClickListener listener; //listens for when card is clicked on
    private ArrayList<String> idNumbers;


    //Constructor
    public RViewAdapter(ArrayList<ExerciseRecyclerData> itemList) {
        this.itemList = itemList;
    }

//    listening for click on the item card (holder) itself
    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Passing to ViewHolder.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new RViewHolder(view, listener);
    }

    // For an onBindViewHolder event, SETS all itemCard ImageResource etc. by using getters
    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        ExerciseRecyclerData currentItem = itemList.get(position); //get current item in array of json objs?

        // RViewHolder's image icon, should be still image of Exercise item
        Picasso.get().load(currentItem.getGifUrl()).into(holder.itemGif);
        Log.i("RVIEW ADAPTER bound gifUrl, resulting gif for this specific holder wooo----> ", currentItem.getGifUrl() );
        holder.itemName.setText(currentItem.getName());
        holder.itemBodyPart.setText(currentItem.getBodyPart());
        holder.itemEquip.setText(currentItem.getEquipment());
        holder.itemTarget.setText(currentItem.getTarget());
        holder.checkBox.setChecked(currentItem.getStatus());
        //does not show equipment for smaller icon list


    }

    /**
     * Get number of items that are going to be displayed.
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
