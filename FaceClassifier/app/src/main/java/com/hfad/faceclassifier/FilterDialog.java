package com.hfad.faceclassifier;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class FilterDialog extends AppCompatDialogFragment {
    private static final String TAG = "FilterDialog";
    // Stores selected face shapes
   private String selectedFaceShape = null;

    // Stores selected gender
    private String selectedGender = null;

    // UI elements
    private Chip heart, oblong, oval, round, square, male, female;

    private FilterDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.filter_layout, null);

        heart = view.findViewById(R.id.heart_chip);
        oblong = view.findViewById(R.id.oblong_chip);
        oval = view.findViewById(R.id.oval_chip);
        round = view.findViewById(R.id.round_chip);
        square = view.findViewById(R.id.square_chip);
        male = view.findViewById(R.id.male_chip);
        female = view.findViewById(R.id.female_chip);

        AlertDialog.Builder filterBuilder = new AlertDialog.Builder(getActivity());
        filterBuilder
                .setView(view)

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyFilter(selectedFaceShape, selectedGender);

                    }
                });

        CompoundButton.OnCheckedChangeListener checkedFaceShape = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    selectedFaceShape = buttonView.getText().toString();
                }
                else {
                    selectedFaceShape = "NONE";
                }
            }
        };

        CompoundButton.OnCheckedChangeListener checkedGender = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    selectedGender = buttonView.getText().toString();
                }
                else {
                    selectedGender = "NONE";
                }
            }
        };

        heart.setOnCheckedChangeListener(checkedFaceShape);
        oblong.setOnCheckedChangeListener(checkedFaceShape);
        oval.setOnCheckedChangeListener(checkedFaceShape);
        round.setOnCheckedChangeListener(checkedFaceShape);
        square.setOnCheckedChangeListener(checkedFaceShape);

        male.setOnCheckedChangeListener(checkedGender);
        female.setOnCheckedChangeListener(checkedGender);


        return filterBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FilterDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }

    public interface FilterDialogListener {
        void applyFilter(String selectedFaceShape, String selectedGender);
    }
}
