package com.flhs.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.flhs.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Drew Gregory on 9/25/2014.
 */
public class AlternatingCoursesDialog extends DialogFragment {
    Activity myActivity;
    int courseNum;
    View v;
    final String[] Days = {"A","B","C","D","E","1","2","3","4","5"};
    ToggleButton altToggle;
    int[] Alt1Days, Alt2Days;
    int[] PEDays;
    EditText LabName, editText;


    public AlternatingCoursesDialog () {

    }

    public interface getToggleButton {
        ToggleButton getToggleButton();
    }

    public interface getEditText {
        EditText getEditText();
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        courseNum = getArguments().getInt("CourseNum");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater mInflater = getActivity().getLayoutInflater();
        v = mInflater.inflate(R.layout.dialog_alternating_courses, null);
        builder.setView(v);
        builder.setTitle("Alternating Courses");


        //final Switch gymSwitch = (Switch) v.findViewById(R.id.PESwitch);

        //final Spinner PESpinner = (Spinner) v.findViewById(R.id.PESpinner);
        final ListView firstCourseLV = (ListView) v.findViewById(R.id.AlternatingCourse1DaySpinner);
        final ListView secondCourseLV = (ListView) v.findViewById(R.id.AlternatingCourse2DaySpinner);
        final EditText firstCourseNameEditText = (EditText) v.findViewById(R.id.firstAlternatingCourseName);
        final EditText secondCourseNameEditText = (EditText) v.findViewById(R.id.secondAlternatingCourseName);
        SharedPreferences CoursePreferences = myActivity.getSharedPreferences("CourseNames", Activity.MODE_PRIVATE);
        builder.setPositiveButton("OK" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences CoursePreferences = myActivity.getSharedPreferences("CourseNames", Activity.MODE_PRIVATE);
                SharedPreferences.Editor edit = CoursePreferences.edit();
                SparseBooleanArray firstCourseLVCheckedItemPositions = firstCourseLV.getCheckedItemPositions();
                SparseBooleanArray secondCourseLVCheckedItemPositions = secondCourseLV.getCheckedItemPositions();
                String firstCourseName = firstCourseNameEditText.getText().toString();
                String secondCourseName = secondCourseNameEditText.getText().toString();
                Log.i("Checked item positions: ", firstCourseLVCheckedItemPositions.size() + " " + secondCourseLVCheckedItemPositions.size());
                edit.putString("Course" + courseNum + "Alt 1",firstCourseName);
                edit.putString("Course" + courseNum + "Alt 2",secondCourseName);
                    for(int firstCourseItemPosition = 0; firstCourseItemPosition < 10; firstCourseItemPosition++) {
                        Log.i("FLHS_Info", firstCourseItemPosition + "");
                        if(firstCourseLVCheckedItemPositions.get(firstCourseItemPosition)) {
                            Log.i("FLHS_Info", firstCourseItemPosition + "");
                            edit.putString("Course " + courseNum + "Day" + ParserA.parseNumToDay(firstCourseItemPosition + 1), firstCourseName);
                            if (firstCourseItemPosition == 4) {
                                edit.putString("Course " + courseNum + "DayAdv E", firstCourseName);
                                edit.putString("Course " + courseNum + "DayCollab E", firstCourseName);
                            }
                            if (firstCourseItemPosition == 9) {
                                edit.putString("Course " + courseNum + "DayAdv 5", firstCourseName);
                                edit.putString("Course " + courseNum + "DayCollab 5", firstCourseName);
                            }
                        }
                    }
                for(int secondCourseItemPosition = 0; secondCourseItemPosition < 10; secondCourseItemPosition++) {
                    if(secondCourseLVCheckedItemPositions.get(secondCourseItemPosition)) {
                        Log.i("FLHS_Info", "sec" + secondCourseItemPosition + "");
                        edit.putString("Course " + courseNum + "Day" + ParserA.parseNumToDay(secondCourseItemPosition + 1), secondCourseName);
                        if (secondCourseItemPosition == 4) {
                            edit.putString("Course " + courseNum + "DayAdv E", secondCourseName);
                            edit.putString("Course " + courseNum + "DayCollab E", secondCourseName);
                        }
                        if (secondCourseItemPosition == 9) {
                            edit.putString("Course " + courseNum + "DayAdv 5", secondCourseName);
                            edit.putString("Course " + courseNum + "DayCollab 5", secondCourseName);
                        }

                    }


                }
                //}
                edit.apply();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                altToggle.setChecked(false);
                altToggle.setText("Alt");
                editText.setVisibility(View.VISIBLE);

            }
        });



        //String[] DayEor5 = {"Day E", "Day 5"};
        //ArrayAdapter<String> LastDayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DayEor5);
        ArrayAdapter<CharSequence> DaysAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Norm_Days, android.R.layout.simple_list_item_multiple_choice);
        //PESpinner.setAdapter(LastDayAdapter);
        firstCourseLV.setAdapter(DaysAdapter);
        secondCourseLV.setAdapter(DaysAdapter);
        //PESpinner.setOnItemSelectedListener(this);
        secondCourseLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        firstCourseLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String firstCourseName = CoursePreferences.getString("Course" + courseNum + "Alt 1", "First Course Name");
        String secondCourseName = CoursePreferences.getString("Course" + courseNum + "Alt 2", "Second Course Name");
        firstCourseNameEditText.setText(firstCourseName);
        secondCourseNameEditText.setText(secondCourseName);
        return builder.create();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        myActivity = activity;
        getToggleButton active = (getToggleButton) activity;
        getEditText selEditText = (getEditText) activity;
        altToggle = active.getToggleButton();
        editText = selEditText.getEditText();

    }

}
