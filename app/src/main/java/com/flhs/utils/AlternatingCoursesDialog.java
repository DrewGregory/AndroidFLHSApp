package com.flhs.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.flhs.R;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Drew Gregory on 9/25/2014.
 */
public class AlternatingCoursesDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
    Activity myActivity;
    int courseNum;
    View v;
    final String[] Days = {"A","B","C","D","E","1","2","3","4","5"};
    ToggleButton altToggle;
    int[] Alt1Days, Alt2Days;
    int[] PEDays;
    EditText LabName, Course1Name, Course2Name, editText;


    public AlternatingCoursesDialog () {

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                /*Alt1Day == P.E.
                Alt2Day == Lab
                 */
                case 0:    //Day E (2)
                    switch (courseNum) {
                        case 1:

                        case 2:


                    }
                break;
            }

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
        SharedPreferences CoursePreferences = myActivity.getSharedPreferences("CourseNames", myActivity.MODE_PRIVATE);
        builder.setPositiveButton("OK" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences CoursePreferences = myActivity.getSharedPreferences("CourseNames", myActivity.MODE_PRIVATE);
                SharedPreferences.Editor edit = CoursePreferences.edit();
                //if (!gymSwitch.isChecked()) {

                    SparseBooleanArray firstCourseLVCheckedItemPositions = firstCourseLV.getCheckedItemPositions();
                    SparseBooleanArray secondCourseLVCheckedItemPositions = secondCourseLV.getCheckedItemPositions();
                    String firstCourseName = firstCourseNameEditText.getText().toString();
                    String secondCourseName = secondCourseNameEditText.getText().toString();

                    for(int firstCourseItemPosition = 0; firstCourseItemPosition < 10; firstCourseItemPosition++) {
                        if(firstCourseLVCheckedItemPositions.get(firstCourseItemPosition)) {
                            edit.putString("Course " + courseNum + "Day" + ParserA.parseNumToDay(firstCourseItemPosition + 1), firstCourseName);
                        }

                    }

                for(int secondCourseItemPosition = 0; secondCourseItemPosition < 10; secondCourseItemPosition++) {
                    if(secondCourseLVCheckedItemPositions.get(secondCourseItemPosition)) {
                        edit.putString("Course " + courseNum + "Day" + ParserA.parseNumToDay(secondCourseItemPosition + 1), secondCourseName);
                    }


                }
                //}
                edit.commit();
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

        /*gymSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "Checked!", Toast.LENGTH_LONG).show();
                }
                if (!isChecked) {
                    Toast.makeText(getActivity(), "Not Checked!", Toast.LENGTH_LONG).show();
                }
            }
        }); */

        //String[] DayEor5 = {"Day E", "Day 5"};
        //ArrayAdapter<String> LastDayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DayEor5);
        ArrayAdapter<CharSequence> DaysAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Days, android.R.layout.simple_list_item_multiple_choice);
        //PESpinner.setAdapter(LastDayAdapter);
        firstCourseLV.setAdapter(DaysAdapter);
        secondCourseLV.setAdapter(DaysAdapter);
        //PESpinner.setOnItemSelectedListener(this);
        secondCourseLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        firstCourseLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String firstCourseName = CoursePreferences.getString("Course " + courseNum + "Day5", "First Course Name");
        String secondCourseName = firstCourseName;
        int dayIncrement = 0;
        do {

            String courseName = CoursePreferences.getString("Course " + courseNum + "Day" + Days[dayIncrement], firstCourseName);
            if (!courseName.equals(secondCourseName)) {
                secondCourseName = courseName;
            }
            if (dayIncrement ==  9) {
                break;
            }
            dayIncrement++;

        }
        while (secondCourseName.equals(firstCourseName));
        if (!secondCourseName.equals("firstCourseName")) {
            firstCourseNameEditText.setText(firstCourseName);
            secondCourseNameEditText.setText(secondCourseName);
        }
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
