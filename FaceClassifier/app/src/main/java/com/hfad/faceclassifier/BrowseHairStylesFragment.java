package com.hfad.faceclassifier;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.faceclassifier.Database.Hairstyle;
import com.hfad.faceclassifier.HelperClasses.HairstyleImagesAdapter;

public class BrowseHairStylesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate with RecyclerView layout
        RecyclerView browseHairStyle_RV = (RecyclerView)inflater.inflate(
                R.layout.fragment_browse_hairstyles, container, false);

        String[] faceshapes = new String[Hairstyle.hairstylesCollection.length];

        for (int i = 0; i < faceshapes.length; i++) {
            faceshapes[i] = Hairstyle.hairstylesCollection[i].getFaceshape();
        }

        int[] hairstyle_images = new int[Hairstyle.hairstylesCollection.length];

        for (int i = 0; i < hairstyle_images.length; i++) {
            hairstyle_images[i] = Hairstyle.hairstylesCollection[i].getImageResourceId();
        }

        // Feed the data to the adapter
        HairstyleImagesAdapter adapter = new HairstyleImagesAdapter(faceshapes, hairstyle_images);
        // Assign the adapter to the RV
        browseHairStyle_RV.setAdapter(adapter);

        // Layout manager arranges views inside RecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        browseHairStyle_RV.setLayoutManager(layoutManager);

        adapter.setListener(new HairstyleImagesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), BrowseHairstyleDetailActivity.class);
                intent.putExtra(BrowseHairstyleDetailActivity.HAIRSTYLE_ID, position);
                getActivity().startActivity(intent);
            }
        });

        return browseHairStyle_RV;
    }

}
