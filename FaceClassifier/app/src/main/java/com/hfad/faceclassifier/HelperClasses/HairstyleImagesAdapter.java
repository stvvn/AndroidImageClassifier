package com.hfad.faceclassifier.HelperClasses;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.faceclassifier.R;

public class HairstyleImagesAdapter extends RecyclerView.Adapter<HairstyleImagesAdapter.ViewHolder> {

    // Each view has face shape of hairstyle and image
    private String[] faceshapes;
    private int[] imageIds;

    private Listener listener;
    public interface Listener {
        void onClick(int position);
    }

    // Activities and Fragments use this method to registers a listener
    public void setListener(Listener listener){
        this.listener = listener;
    }

    // Constructor
    public HairstyleImagesAdapter(String[] faceshapes, int[] imageIds) {
        this.faceshapes = faceshapes;
        this.imageIds = imageIds;
    }

    // Defines ViewHolder as inner class (Specify which view should be used for each data item)
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    // Tells adapter the number of data items in RecyclerView
    @Override
    public int getItemCount() { return faceshapes.length; }

    // Gets called when RV requires a new view holder (i.e Tells how to construct our view holders)
    @Override
    public HairstyleImagesAdapter.ViewHolder onCreateViewHolder(
            // parent is RV itself
            ViewGroup parent, int viewType){
        // Get a LayoutInflater object and use it to turn the layout into a CardView.
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_hairstyle_image, parent, false);
        return new ViewHolder(cv);
    }

    // The RV calls this method when it wants to use(or reuse) a view holder for a new piece of
    // data
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        CardView cardView = holder.cardView;

        // Set the image view in Cardview
        ImageView imageView = (ImageView) cardView.findViewById(R.id.hairstyle_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(faceshapes[position]);

        // Set the text view in Cardview
        TextView textView = (TextView) cardView.findViewById(R.id.face_shape_info);
        textView.setText(faceshapes[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

    }
}
