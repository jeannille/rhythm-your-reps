package com.example.rhythm_n_reps;

//interface for listening for when an item card is clicked
//planning on item cards being list of exercises

public interface ItemClickListener {

    void onItemClick(int position);

    void onCheckBoxClick(int position);
}
