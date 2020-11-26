package com.hfad.faceclassifier.Database;

import com.hfad.faceclassifier.R;

import java.io.Serializable;

/**
 * This class represents the data that needs to be fed into Recycler View
 */
public class Hairstyle implements Serializable {

    private String faceshape;
    private int imageResourceId;
    private float rating;

    public Hairstyle(String faceshape, int imageResourceId) {
        this.faceshape = faceshape;
        this.imageResourceId = imageResourceId;
        this.rating = 0.0f;
    }

    public void setRating(float rating) { this.rating = rating; }

    public String getFaceshape() { return faceshape; }

    public int getImageResourceId() { return imageResourceId; }

}
