package com.hfad.faceclassifier.Database;

import com.hfad.faceclassifier.R;

/**
 * This class represents the data that needs to be fed into Recycler View
 */
public class Hairstyle {

    private String faceshape;
    private int imageResourceId;

    public Hairstyle(String faceshape, int imageResourceId) {
        this.faceshape = faceshape;
        this.imageResourceId = imageResourceId;
    }

    public String getFaceshape() { return faceshape; }

    public int getImageResourceId() { return imageResourceId; }


}
