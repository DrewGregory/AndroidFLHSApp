package com.flhs.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;
import com.flhs.R;

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
                DaysAdapter firstAdapter = (DaysAdapter) firstCourseLV.getAdapter();
                DaysAdapter secondAdapter = (DaysAdapter) secondCourseLV.getAdapter();
                String firstCourseName = firstCourseNameEditText.getText().toString();
                String secondCourseName = secondCourseNameEditText.getText().toString();
                edit.putString("Course" + courseNum + "Alt 1",firstCourseName);
                edit.putString("Course" + courseNum + "Alt 2",secondCourseName);
                String firstCourseValues = "";
                String secondCourseValues = "";
                    for(int firstCourseItemPosition = 0; firstCourseItemPosition < 10; firstCourseItemPosition++) {
                        if(firstAdapter.mItems[firstCourseItemPosition].isChecked) {
                            String day = ParserA.parseNumToDay(firstCourseItemPosition + 1);
                            edit.putString("Course " + courseNum + "Day" + day, firstCourseName);
                            firstCourseValues += " " + day;
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
                    if(secondAdapter.mItems[secondCourseItemPosition].isChecked) {
                        String day = ParserA.parseNumToDay(secondCourseItemPosition + 1);
                        edit.putString("Course " + courseNum + "Day" + day, secondCourseName);
                        secondCourseValues += " " + day;
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
                edit.putString("Course" + courseNum + "Alt1DayValues", firstCourseValues);
                edit.putString("Course" + courseNum + "Alt2DayValues", secondCourseValues);
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
        //Old Adapter .....ArrayAdapter<CharSequence> DaysAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Norm_Days, android.R.layout.simple_list_item_multiple_choice);
        SharedPreferences prefs = myActivity.getSharedPreferences("CourseNames", Activity.MODE_PRIVATE);
        String[] firstDayValues = prefs.getString("Course" + courseNum + "Alt1DayValues","").split("");
        String[] secondDayValues = prefs.getString("Course" + courseNum + "Alt2DayValues","").split("");
        DaysAdapter mFirstDaysAdapter = new DaysAdapter(getActivity(), firstDayValues, Days);
        DaysAdapter mSecondDaysAdapter = new DaysAdapter(getActivity(), secondDayValues, Days);
        firstCourseLV.setAdapter(mFirstDaysAdapter);
        secondCourseLV.setAdapter(mSecondDaysAdapter);
        secondCourseLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); //Not sure if this does anything.....
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

    private class DaysAdapter extends ArrayAdapter<String> {
        String[] normDays = {"A", "B", "C", "D", "E", "1", "2", "3", "4", "5"};
        String[] values;
        ListViewHolderItem[] mItems = new ListViewHolderItem[10];
        Context context;
        public DaysAdapter (Context context, String[] alreadyStoredDayValues, String[] normDays) {
            super(context, R.layout.alternating_courses_lv_item, normDays);
            this.context = context;
            this.values = alreadyStoredDayValues;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.alternating_courses_lv_item, parent, false);
                mItems[position] = new ListViewHolderItem();
                mItems[position].checkBox = (CheckBox) convertView.findViewById(R.id.alt_checkbox);
                mItems[position].checkBox.setChecked(false);
                for(String day : values)
                    if (day.equals(normDays[position])) {
                        mItems[position].checkBox.setChecked(true);
                        mItems[position].isChecked = true;
                    }
                convertView.setTag(mItems[position]);
            } else {
                mItems[position] = (ListViewHolderItem) convertView.getTag();
                mItems[position].checkBox.setChecked(mItems[position].isChecked);
            }
            mItems[position].checkBox.setText("Day " + normDays[position]);
            return convertView;
        }


    }

}
