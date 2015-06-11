package com.flhs.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.flhs.R;

/**
 * Created by Drew Gregory on 9/1/2014.
 */
public class DayPickerFragment extends DialogFragment {
    int selectedDay;
    private DayPickerListener mDayPickerListener;

    public interface DayPickerListener {
        public void onDayPickPositiveClick(int DayNum);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please Pick Your Day.");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                mDayPickerListener.onDayPickPositiveClick(selectedDay);

            }
        });
        builder.setSingleChoiceItems(R.array.Days, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedDay = which + 1;
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mDayPickerListener = (DayPickerListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DayPickerListener");
        }
    }


}
