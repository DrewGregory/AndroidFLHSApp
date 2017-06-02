package com.flhs.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.flhs.R;

import java.util.ArrayList;

/**
 * Created by Drew Gregory on 6/1/2017.
 */

public class SpecialTrackPickerFragment extends DialogFragment {
    private SpecialTrackPickerFragment.TrackPickerListener mListener;

    int selectedTrack = 0;

    public interface TrackPickerListener {
        void onTrackPickPositiveClick(DialogFragment dialog, int selectedTrack);
        void onDialogNegativeClick(DialogFragment dialog);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please Pick Your Track.");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                mListener.onDialogNegativeClick(SpecialTrackPickerFragment.this);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                mListener.onTrackPickPositiveClick(SpecialTrackPickerFragment.this, selectedTrack);

            }
        });
        Bundle data = getArguments();
        ArrayList<String> tracks = data.getStringArrayList("tracks");
        Log.i("Bundles",tracks.toString());
        String[] trackStrings = new String[tracks.size()];
        for (int i = 0; i < trackStrings.length; i++) {
            trackStrings[i] = tracks.get(i);
        }
        builder.setSingleChoiceItems(trackStrings, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedTrack = which;
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SpecialTrackPickerFragment.TrackPickerListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement TrackPickerListener");
        }
    }
}