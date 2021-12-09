package com.example.rhythm_n_reps;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
// RecyclerView that 'holds' each ItemCard ('view') object
//handles click events (click listeners for itemCard)
public class RViewHolder extends RecyclerView.ViewHolder {

    public ImageView itemIcon; //image view of exercise, use Picasso
    public TextView itemName; //description, name
    public TextView itemDesc;
    public CheckBox checkBox; //add to workout


    // Constructor finds each field by id, MUST call super first
    public RViewHolder(View itemView, final ItemClickListener listener) {
        super(itemView);
        itemIcon = itemView.findViewById(R.id.item_icon);
        itemName = itemView.findViewById(R.id.item_name);
        itemDesc = itemView.findViewById(R.id.item_desc);
        checkBox = itemView.findViewById(R.id.checkbox);


        //set on click listener for itemView
        //if the listener is not null, save position of click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        //in case onclick occurs before RecyclerView calculates position, ignore click
                        //only save position if it has been determined (doc)
                        //

                        listener.onItemClick(position); //call onItemClick w calculated position of click
                    }
                }
            }
        });

        //set on click listener for check box within item
        //this does the same as itemView's listener, but having them separated
        //is useful in case you want them to do different things if clicked
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onCheckBoxClick(position);
                    }
                }
            }
        });
    }
}