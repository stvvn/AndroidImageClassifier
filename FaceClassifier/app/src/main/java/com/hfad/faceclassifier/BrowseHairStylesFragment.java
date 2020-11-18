package com.hfad.faceclassifier;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hfad.faceclassifier.Database.Hairstyle;
import com.hfad.faceclassifier.HelperClasses.HairstyleImagesAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class BrowseHairStylesFragment extends Fragment implements FilterDialog.FilterDialogListener {

    private static final int REQUEST_CODE_SPEECH_INPUT = 9999 ;
    private ImageButton filterBtn;
    private RecyclerView browseHairStyle_RV;
    private EditText searchBar;
    
    private ImageButton searchVoiceBtn;

    private ArrayList<Hairstyle> mHairStyles;

    HairstyleImagesAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate with RecyclerView layout
        View view = inflater.inflate(
                R.layout.fragment_browse_hairstyles, container, false);

        browseHairStyle_RV = view.findViewById(R.id.hairstyle_collection_rv);

        createHairStyleList();

        mAdapter = new HairstyleImagesAdapter(mHairStyles);

        // Assign the adapter to the RV
        browseHairStyle_RV.setAdapter(mAdapter);

        // Layout manager arranges views inside RecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        browseHairStyle_RV.setLayoutManager(layoutManager);

        // Filter
        filterBtn = view.findViewById(R.id.filter_btn);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog filterDialog = new FilterDialog();
                filterDialog.setTargetFragment(BrowseHairStylesFragment.this, 1000);
                filterDialog.show(getActivity().getSupportFragmentManager(), "Filter Dialog");

            }
        });

        // Voice Search
        searchVoiceBtn = view.findViewById(R.id.search_voice_btn);
        searchVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceSearch();
            }
        });
        
        
        // Search Bar Search
        searchBar = view.findViewById(R.id.search_input);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    
        


        return view;
    }

    /****
     *  Starts Google's Voice Recognition
     */
    private void voiceSearch() {
        // Intent to show speech to text dialog
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, say face shape");

        // Start Intent
        try{

            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

        } catch (Exception e) {
            // If there was some error
            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Search RecyclerView based on Voice Search
        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:
                if(resultCode == getActivity().RESULT_OK && null != data){
                    // Get text array from voice intent
                    ArrayList<String> result = data.getStringArrayListExtra
                            (RecognizerIntent.EXTRA_RESULTS);
                    // Set to text view
                    // Toast.makeText(getContext(), "" + result.get(0), Toast.LENGTH_SHORT).show();
                    //filter(result.get(0));
                    searchBar.setText(result.get(0));
                }
                break;
        }
    }

    private void createHairStyleList() {
        mHairStyles = new ArrayList<>();
        mHairStyles.add(new Hairstyle("Heart", R.drawable.heart));
        mHairStyles.add(new Hairstyle("Oblong", R.drawable.oblong));
        mHairStyles.add(new Hairstyle("Oval", R.drawable.oval));
        mHairStyles.add(new Hairstyle("Round", R.drawable.square));
    }


    /*****
     *
     * Filter the RecyclerView based on the search term (Search Logic)
     *
     * @param searchTerm
     */
    private void filter(String searchTerm){

        ArrayList<Hairstyle> filteredList = new ArrayList<>();

        for(Hairstyle hairstyle: mHairStyles){
            String face_shape = hairstyle.getFaceshape().toLowerCase();
            if(face_shape.contains(searchTerm.toLowerCase())){
                filteredList.add(hairstyle);
            }
        }

        mAdapter.filtered(filteredList);
    }

    /******
     *
     * Filter the RecyclerView based on the selected face shape and gender in filter
     * @param selectedFaceShape
     * @param selectedGender
     */

    @Override
    public void applyFilter(String selectedFaceShape, String selectedGender) {

        if (!selectedFaceShape.equals("NONE")){
            searchBar.setText(selectedFaceShape);
        }

        // Toast.makeText(getContext(), selectedFaceShape + "\n" + selectedGender,
        //        Toast.LENGTH_LONG).show();
    }


}

