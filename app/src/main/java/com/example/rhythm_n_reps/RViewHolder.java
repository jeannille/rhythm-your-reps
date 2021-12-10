package com.example.rhythm_n_reps;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
//Given a itemView View instance (with its data) and a ItemClickListener from the RViewAdapater,
//generates, displays and "holds" the View within entire list of Views in the main activity,
//and handles events when the this generated View/ItemCard object is clicked

public class RViewHolder extends RecyclerView.ViewHolder {

// One card view displays the card's image icon, name, text description and checkbox
    public ImageView itemIcon; //image view of exercise, use Picasso
    public TextView itemName; //description, name
    public TextView itemDesc;
    public CheckBox checkBox; //add to workout list


    // Constructor finds each field by id, MUST call super first
    //should only find IDs once, when creating the item card
    public RViewHolder(View itemView, final ItemClickListener listener) {
        super(itemView); //passed to view holder, actual item card on list
        itemIcon = itemView.findViewById(R.id.item_icon); //
        itemName = itemView.findViewById(R.id.item_name);
        itemDesc = itemView.findViewById(R.id.item_desc);
        checkBox = itemView.findViewById(R.id.checkbox);


        //below, set on click listener for itemViews



        //both itemView listeners do the same thing ('checked'), may separate the ITEM click event
        // vs. checkbox (is useful in case you want them to do different things if clicked)
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the listener is not null, save this clicked item's position
                if (listener != null) {
                    int position = getLayoutPosition(); //tracks item's position # w/in array that was clicked
                    if (position != RecyclerView.NO_POSITION) {
                        //if onClick occurs before RecyclerView was able to calculate position, ignore click
                        //Only save position if it has been determined (suggestion from docs)

                        listener.onItemClick(position); //call onItemClick w calculated position of click
                    }
                }
            }
        });

        //set onClick listener for check box within ItemCard View
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

//maybe when clicking on item card, its just to view the exercise in a new screen
//could be to add to other List (or keep)