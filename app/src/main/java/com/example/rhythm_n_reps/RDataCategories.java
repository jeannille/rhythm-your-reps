package com.example.rhythm_n_reps;

public class RDataCategories implements ItemClickListener{

    private String title;
    private int imgid;
    private boolean isClicked;

    public RDataCategories(String title, int imgid) {
        this.title = title;
        this.imgid = imgid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }


    @Override
    public void onItemClick(int position) {
        this.isClicked = !isClicked;

    }

    @Override
    public void onCheckBoxClick(int position) {
        this.isClicked = !isClicked;

    }
}


