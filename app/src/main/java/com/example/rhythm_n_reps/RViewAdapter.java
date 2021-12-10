package com.example.rhythm_n_reps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter acts as messenger between RViewHolder and the actual data (exercises) that will be displayed.
 * It reads the data and passes it to ViewHolder
 */


public class RViewAdapter extends RecyclerView.Adapter<RViewHolder> {

    //Takes in ItemCard View and creates array list if ItemCards

    private final ArrayList<ItemCard> itemList;
    private ItemClickListener listener; //listens for when card is clicked on

    //Constructor
    public RViewAdapter(ArrayList<ItemCard> itemList) {
        this.itemList = itemList;
    }

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
        ItemCard currentItem = itemList.get(position); //get current item in array of json objs?

        // RViewHolder's image icon
        holder.itemIcon.setImageResource(currentItem.getImageSource());
        holder.itemName.setText(currentItem.getItemName());
        holder.itemDesc.setText(currentItem.getItemDesc());
        holder.checkBox.setChecked(currentItem.getStatus());
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
