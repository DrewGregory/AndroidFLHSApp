package com.flhs.utils;

import com.flhs.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class LunchPickerFragment extends DialogFragment {

    private LunchPickerListener mListener;

    int selectedLunch = 0;

    public interface LunchPickerListener {
        public void onLunchPickPositiveClick(DialogFragment dialog, int selectedLunch);

        public void onDialogNegativeClick(DialogFragment dialog);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please Pick Your Lunch.");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                mListener.onDialogNegativeClick(LunchPickerFragment.this);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                mListener.onLunchPickPositiveClick(LunchPickerFragment.this, selectedLunch);

            }
        });
        builder.setSingleChoiceItems(R.array.lunch_items, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedLunch = which;
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (LunchPickerListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement LunchPickerListener");
        }
    }


}
