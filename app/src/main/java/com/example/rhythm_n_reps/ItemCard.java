package com.example.rhythm_n_reps;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemCard implements ItemClickListener {

    private final int imageSource; //maybe should be url
    private final String itemName;
    private final String itemDesc;
    private boolean isChecked;
    private final JSONObject jObject;

    //Constructor
    //each item card has an icon, name, description and status if checked
    public ItemCard(int imageSource, String itemName, String itemDesc,boolean isChecked) {
        this.imageSource = imageSource; //gif
        this.itemName = itemName; //name
        this.itemDesc = itemDesc; //body part
        this.isChecked = isChecked;
        jObject = null;
    }

    //maybe first just set jObject to jObject, so each item card is a jObject then
    //fix in setting code holder hat deals the data
    public ItemCard(JSONObject jObject) {
            this.jObject = jObject;
            this.isChecked = isChecked;
            this.imageSource = 0;
            this.itemName = null;
            itemDesc = null;

    }


        //Getters for the imageSource, itemName and itemDesc
    //integer
    public int getImageSource() {
        return imageSource;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public String getItemName() {
        return itemName + (isChecked ? "(checked)" : "");
    }

    public boolean getStatus() {
        return isChecked;
    }

    // click on actual Item Card (is the item currently selected)
    // click on actual check box WITHIN item card
    @Override
    public void onItemClick(int position) {
        isChecked = !isChecked;
    }

    //clicking on actual check box
    @Override
    public void onCheckBoxClick(int position) {
        isChecked = !isChecked;
    }



}
