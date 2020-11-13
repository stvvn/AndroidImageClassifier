package com.hfad.faceclassifier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfad.faceclassifier.Database.Hairstyle;

public class BrowseHairstyleDetailActivity extends AppCompatActivity {

    public static final String HAIRSTYLE_ID = "hairstyleId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_hairstyle_detail);

        //Display details of the HairStyle Clicked
        int hairstyleId = (Integer)getIntent().getExtras().get(HAIRSTYLE_ID);
        String faceShape = Hairstyle.hairstylesCollection[hairstyleId].getFaceshape();
        TextView textView = (TextView)findViewById(R.id.faceshapeInfo);
        textView.setText("Face Shape: " + faceShape);

        int hairStyleImage = Hairstyle.hairstylesCollection[hairstyleId].getImageResourceId();
        ImageView imageView = (ImageView)findViewById(R.id.hairstyleImage);
        imageView.setImageDrawable(ContextCompat.getDrawable(this, hairStyleImage));
        imageView.setContentDescription(faceShape);
    }
}