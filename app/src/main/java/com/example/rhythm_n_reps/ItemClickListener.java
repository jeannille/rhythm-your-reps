package com.example.rhythm_n_reps;

//interface for listening for when an item card is clicked
//planning on item cards being list of exercises

//an onclick that has multiple options when clicked - should do this with spinners & textboxes
public interface ItemClickListener {

    void onItemClick(int position);

    void onCheckBoxClick(int position);

//    ExerciseRecyclerData onCheckBoxClick(int position);
}
