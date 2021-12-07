package com.example.rhythm_n_reps;

//represents one exercise returned from JSONArray requests

import org.json.JSONException;
import org.json.JSONObject;

public class Exercise {

    private String bodyPart;
    private String equipment;
    private String gifUrl;
    private String id;
    private String name;
    private String target;


//constructor Exercise onkect if given JSON object retrieved from API
    public Exercise(JSONObject jObject){
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

    public Exercise(String bodyPart, String equipment, String gifUrl, String id, String name, String target) {
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

    //create overide toString to display the exercise json obj nicely
}
