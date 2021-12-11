package com.example.rhythm_n_reps;

//represents one exercise returned from JSONArray requests

import org.json.JSONException;
import org.json.JSONObject;

//CardView item within list of Exercises
//each card view will contain ImageVIew (exercise gif), Text view (description), checkbox
//to potentially add this particular exercise to a working session list
public class ExerciseRecyclerData {
    // variable names are the same as values we get from key value from json file
    // "must be similar to that of key value which we are getting from our json file"
    private String bodyPart;
    private String equipment;
    private String gifUrl;
    private String id;
    private String name;
    private String target;


//constructor Exercise onkect if given JSON object retrieved from API
    public ExerciseRecyclerData(JSONObject jObject){
        try {
            this.bodyPart = jObject.getString("bodyPart");
            this.equipment = jObject.getString("equipment");
            this.gifUrl = jObject.getString("gifUrl");
            this.id = jObject.getString("id");
            this.name = jObject.getString("name");
            this.target = jObject.getString("target");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ExerciseRecyclerData(String bodyPart, String equipment, String gifUrl, String id, String name, String target) {
        this.bodyPart = bodyPart;
        this.equipment = equipment;
        this.gifUrl = gifUrl;
        this.id = id;
        this.name = name;
        this.target = target;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getGifUrl() {
        //ensure https so image displays
        this.gifUrl = gifUrl.replace("http", "https");
// gif url fixed when sending JSON obj as Exe. objs
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

//    @Override
//    public void onItemClick(int position) {
//
//    }
//
//    @Override
//    public void onCheckBoxClick(int position) {
//
//    }

    //create overide toString to display the exercise json obj nicely
}
