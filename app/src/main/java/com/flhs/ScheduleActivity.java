package com.flhs;
//Exam Dif

import java.text.SimpleDateFormat;
import java.util.Date;

import com.flhs.utils.DayPickerFragment;
import com.flhs.utils.LunchPickerFragment;
import com.flhs.utils.ParserA;
import com.parse.ParseConfig;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class ScheduleActivity extends FLHSActivity implements DayPickerFragment.DayPickerListener, LunchPickerFragment.LunchPickerListener {
    private static String LUNCH_TYPE = "Lunch Type";
    /* Here is where I store the dates for the upcoming school days in the calendar........ I'll have to update this frequently....
                *   Day A == 1
                *   Day B == 2
                *   Day C == 3
                *   Day D == 4
                *   Day E == 5
                *   Day 1 == 6
                *   Day 2 == 7
                *   Day 3 == 8
                *   Day 4 == 9
                *   Day 5 == 10
                *   So far only 1st quarter.... UPDATE!
                 */

    public static final String DAY_TYPE = "Day Type";
    String[] advLunch1Courses = {"Course 1", "Course 2", "Advisory", "Course 3", "Lunch", "Course 4", "Course 5", "Course 6", "Course 7", "Course 8"};
    String[] advLunch2Courses = {"Course 1", "Course 2", "Advisory", "Course 3", "Course 4", "Lunch", "Course 5", "Course 6", "Course 7", "Course 8"};
    String[] advLunch3Courses = {"Course 1", "Course 2", "Advisory", "Course 3", "Course 4", "Course 5", "Lunch", "Course 6", "Course 7", "Course 8"};
    String[] advLunch1Times = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:30", "9:35 -10:10", "10:15 - 10:55", "11:00 - 11:35", "11:40 - 12:15", "12:20 -12:55", "1:00 -1:35", "1:40 - 2:15"};
    String[] advLunch2Times = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:30", "9:35 -10:10", "10:15 - 10:50", "10:55 - 11:35", "11:40 - 12:15", "12:20 -12:55", "1:00 -1:35", "1:40 - 2:15"};
    String[] advLunch3Times = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:30", "9:35 -10:10", "10:15 - 10:50", "10:55 - 11:30", "11:35 - 12:15", "12:20 -12:55", "1:00 -1:35", "1:40 - 2:15"};
    String[] collabCourses = {"Course 1", "Course 2", "Course 3", "Course 4", "Collab Learning Lunch", "Course 5", "Course 6", "Course 7", "Course 8"};
    String[] collabTimes = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:40", "9:45 - 10:20", "10:25 - 11:35", "11:40 - 12:15", "12:20 - 12:55", "1:00 - 1:35", "1:40 - 2:15"};
    String[] day1Lunch1Courses = {"Course 1", "Course 2", "Lunch", "Course 4", "Course 5", "Course 7", "Course 8"};
    String[] Lunch1Times = {"7:45 - 8:40", "8:45 - 9:40", "9:45 - 10:20", "10:25 - 11:20", "11:25 - 12:20", "12:25 - 1:20", "1:25 - 2:20"};
    String[] Lunch2Times = {"7:45 - 8:40", "8:45 - 9:40", "9:45 - 10:40", "10:45 - 11:20", "11:25 - 12:20", "12:25 - 1:20", "1:25 - 2:20"};
    String[] Lunch3Times = {"7:45 - 8:40", "8:45 - 9:40", "9:45 - 10:40", "10:45 - 11:40", "11:45 - 12:20", "12:25 - 1:20", "1:25 - 2:20"};
    String[] day2Lunch1Courses = {"Course 1", "Course 2", "Lunch", "Course 3", "Course 6", "Course 7", "Course 8"};
    String[] day3Lunch1Courses = {"Course 2", "Course 3", "Lunch", "Course 4", "Course 5", "Course 6", "Course 7"};
    String[] day4Lunch1Courses = {"Course 1", "Course 3", "Lunch", "Course 4", "Course 5", "Course 6", "Course 8"};
    String[] day5Lunch1Courses = {"Course 1", "Course 2", "Course 3", "Lunch", "Course 4", "Course 5", "Course 6", "Course 7", "Course 8"};
    String[] day5Lunch1Times = {"7:45 - 8:25", "8:30 - 9:10", "9:15 - 9:55", "10:00 - 10:40", "10:45 - 11:15", "11:20 - 12:00", "12:05 - 12:45", "12:50 - 1:30", "1:35 - 2:15"};
    String[] day5Lunch2Times = {"7:45 - 8:25", "8:30 - 9:10", "9:15 - 9:55", "10:00 - 10:40", "10:45 - 11:25", "11:30 - 12:00", "12:05 - 12:45", "12:50 - 1:30", "1:35 - 2:15"};
    String[] day5Lunch3Times = {"7:45 - 8:25", "8:30 - 9:10", "9:15 - 9:55", "10:00 - 10:40", "10:45 - 11:25", "11:30 - 12:10", "12:15 - 12:45", "12:50 - 1:30", "1:35 - 2:15"};
    String[] halfDayTimes = {"7:45 - 7:59", "8:04 - 8:18", "8:23 - 8:37", "8:42 - 8:56", "9:01 - 9:15", "9:20 - 9:34", "9:39 - 9:53", "9:58 - 10:12"};
    String[] halfDayCourses = {"Course 1", "Course 2", "Course 3", "Course 4", "Course 5", "Course 6", "Course 7", "Course 8"};
    SharedPreferences.Editor lunchTypeEditor;
    SharedPreferences lunchType, prefs;
    ListView content;
    String scheduleType;

    //Button DateButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /* Lunch Types are 0, 1, and 2, while Day Types are 1, 2, 3, 4, 5, 6, 7, 8, 9, and 10 */
        setContentView(R.layout.activity_schedule);
        SetupNavDrawer();
        content = (ListView) findViewById(R.id.contentListView);
        String[] loadScheduleStrings = {"You don't have school today!"};
        ArrayAdapter<String> EmptySchedule = new ArrayAdapter<String>(ScheduleActivity.this, android.R.layout.simple_list_item_1, loadScheduleStrings);
        content.setAdapter(EmptySchedule);
        //DateButton = (Button) findViewById(R.id.dateButton);
        ParseConfig config = ParseConfig.getCurrentConfig();
        scheduleType = config.getString("ScheduleType");
        Date theCurrentTime = new Date();
        int mDate = Integer.parseInt(new SimpleDateFormat("dd").format(theCurrentTime));
        prefs = getSharedPreferences(DAY_TYPE, MODE_PRIVATE);
        int mMonth = Integer.parseInt(new SimpleDateFormat("MM").format(theCurrentTime));
        //DateButton.setText(mMonth + "/" + mDate);
        lunchType = getSharedPreferences(LUNCH_TYPE, MODE_PRIVATE);
        Button DayTitle = (Button) findViewById(R.id.DayTitle);
        SharedPreferences.Editor dayTypeEditor = prefs.edit();
        if (!(prefs.getInt("Last Time Changed", 0) == (mDate))) {
            JSONArray jsonDays = config.getJSONArray("WhatDay", null);
            for (int index = 0; index < jsonDays.length(); index++) {
                String jsonString = null;
                try {
                    jsonString = jsonDays.get(index).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                    dayTypeEditor.putString(DAY_TYPE, "Unknown");
                }
                String date = jsonString.substring(0, jsonString.indexOf(":"));
                if (date.equals(mMonth + "/" + mDate)) {
                    dayTypeEditor.putString(DAY_TYPE, jsonString.substring(jsonString.indexOf(":") + 1));
                    dayTypeEditor.commit();
                    break;
                }
            }
        }
        if (scheduleType.equals("Normal")) {
            String dayType = prefs.getString(DAY_TYPE, "Unknown");
            DayTitle.setText("Day: " + dayType);
            if (!dayType.equals("Unknown")) {
                if (lunchType.getInt(dayType, -1) != -1) {
                    loadNormalSchedule(lunchType.getInt(dayType, -1), dayType);
                } else {
                    LunchPickerFragment LunchSelector = new LunchPickerFragment();
                    LunchSelector.show(getFragmentManager(), "Unknown Lunch");
                }
            }
        }
        if (scheduleType.equals("Collaborative")) {
            String[] CourseScheduleToPrint = collabCourses;
            String[] TimeScheduleToPrint = collabTimes;
            ScheduleAdapter adapter = new ScheduleAdapter(ScheduleActivity.this, CourseScheduleToPrint, TimeScheduleToPrint);
            content.setAdapter(adapter);
            DayTitle.setText("Day: Collab");
            Button switchLunch = (Button) findViewById(R.id.switch_lunch);
            switchLunch.setVisibility(View.INVISIBLE);
        }
        if (scheduleType.equals("Advisory")) {
            String[] CourseScheduleToPrint = {"Unknown Schedule."};
            String[] TimeScheduleToPrint = {"Unknown Times"};
            int selectedLunch = lunchType.getInt(prefs.getString(DAY_TYPE, "Unknown"), -1);
            DayTitle.setText("Day: Advisory");
            if (selectedLunch != -1) {
                if (selectedLunch == 0) {
                    CourseScheduleToPrint = advLunch1Courses;
                    TimeScheduleToPrint = advLunch1Times;
                }
                if (selectedLunch == 1) {
                    CourseScheduleToPrint = advLunch2Courses;
                    TimeScheduleToPrint = advLunch2Times;
                }
                if (selectedLunch == 2) {
                    CourseScheduleToPrint = advLunch3Courses;
                    TimeScheduleToPrint = advLunch3Times;
                }
            } else {
                LunchPickerFragment LunchSelector = new LunchPickerFragment();
                LunchSelector.show(getFragmentManager(), "Unknown Lunch");
            }
            ScheduleAdapter adapter = new ScheduleAdapter(ScheduleActivity.this, CourseScheduleToPrint, TimeScheduleToPrint);
            content.setAdapter(adapter);
        }
        if (scheduleType.equals("Half Day")) {
            String[] TimeScheduleToPrint = halfDayTimes;
            String[] CourseScheduleToPrint = halfDayCourses;
            DayTitle.setText("Day: Half Day");
            ScheduleAdapter adapter = new ScheduleAdapter(ScheduleActivity.this, CourseScheduleToPrint, TimeScheduleToPrint);
            content.setAdapter(adapter);
        }
        if (scheduleType.equals("Special Day")) {
            DayTitle.setText("Special Day " + ParserA.parseNumToDay(prefs.getInt(DAY_TYPE, 0)));
            Button switchLunch = (Button) findViewById(R.id.switch_lunch);
            switchLunch.setVisibility(View.INVISIBLE); //Switch this when you figure out lunch compatibility....
            JSONArray jsonTimes = config.getJSONArray("SpecialDayTimes", null);
            String[] TimeScheduleToPrint = new String[jsonTimes.length()];
            JSONArray jsonCourses = config.getJSONArray("SpecialDayCourses", null);
            String[] CourseScheduleToPrint = new String[jsonCourses.length()];
            for (int index = 0; index < jsonTimes.length(); index++) {
                try {
                    TimeScheduleToPrint[index] = jsonTimes.getString(index);
                    CourseScheduleToPrint[index] = jsonCourses.getString(index);
                } catch (JSONException ex) {
                    break;
                }
            }
            ScheduleAdapter adapter = new ScheduleAdapter(ScheduleActivity.this, CourseScheduleToPrint, TimeScheduleToPrint);
            content.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedule_content_menu, menu);
        getActionBar().show();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.info_icon:
                Intent aboutActivityExecute = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(aboutActivityExecute);
                return true;

            case R.id.home_icon:
                Intent HomeActivityExecute = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(HomeActivityExecute);
                return true;
            case R.id.customize_icon:
                Intent Course_SelectorActivityExecute = new Intent(getApplicationContext(), CourseSelectorActivity.class);
                startActivity(Course_SelectorActivityExecute);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void switch_lunch(View v) {
        LunchPickerFragment LunchSelector = new LunchPickerFragment();
        LunchSelector.show(getFragmentManager(), "Unknown Lunch");
    }


    public void loadNormalSchedule(int Lunch, String SchoolDay) {
        String[] TimeScheduleToPrint = Lunch1Times;

        String[] CourseScheduleToPrint = null;
        if ((SchoolDay.equals("A")) || (SchoolDay.equals("1"))) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day1Lunch1Courses, Lunch);
        }
        if (SchoolDay.equals("B") || SchoolDay.equals("2")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day2Lunch1Courses, Lunch);
        }
        if (SchoolDay.equals("C") || SchoolDay.equals("3")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day3Lunch1Courses, Lunch);
        }
        if (SchoolDay.equals("D") || SchoolDay.equals("4")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day4Lunch1Courses, Lunch);
        }
        if (SchoolDay.equals("E") || SchoolDay.equals("5")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day5Lunch1Courses, Lunch);
        }
        if (!(SchoolDay.equals("E") && SchoolDay.equals("5"))) {
            switch (Lunch) {
                case 0:
                    TimeScheduleToPrint = Lunch1Times;
                    break;
                case 1:
                    TimeScheduleToPrint = Lunch2Times;
                    break;
                case 2:
                    TimeScheduleToPrint = Lunch3Times;
                    break;

            }
        }
        if (SchoolDay.equals("E") || SchoolDay.equals("5")) {
            switch (Lunch) {
                case 0:
                    TimeScheduleToPrint = day5Lunch1Times;
                    break;
                case 1:
                    TimeScheduleToPrint = day5Lunch2Times;
                    break;
                case 2:
                    TimeScheduleToPrint = day5Lunch3Times;
                    break;
            }
        }
        if (CourseScheduleToPrint != null) {
            ScheduleAdapter adapter = new ScheduleAdapter(ScheduleActivity.this, CourseScheduleToPrint, TimeScheduleToPrint);
            content.setAdapter(adapter);
        } else {
            Toast.makeText(ScheduleActivity.this, "You are in the weekend....", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLunchPickPositiveClick(DialogFragment dialog, int selectedLunch) {
        lunchTypeEditor = lunchType.edit();
        lunchTypeEditor.putInt(prefs.getString(DAY_TYPE, "Unknown"), selectedLunch);
        lunchTypeEditor.commit();
        startActivity(new Intent(ScheduleActivity.this, ScheduleActivity.class));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }


    @Override
    public void onDayPickPositiveClick(int DayNum) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(DAY_TYPE, ParserA.parseNumToDay(DayNum));
        editor.putInt("Last Time Changed", Integer.parseInt(new SimpleDateFormat("d").format(new Date())));
        editor.commit();
        startActivity(new Intent(ScheduleActivity.this, ScheduleActivity.class));
    }

    public void switch_day(View v) {
        DayPickerFragment mFragment = new DayPickerFragment();
        mFragment.show(getFragmentManager(), "Unknown Day");
    }

    private class ScheduleAdapter extends ArrayAdapter<String> {
        Context context;
        private String[] courses;
        private String[] times;

        public ScheduleAdapter(Context context,
                               String[] Courses, String[] Times) {
            super(context, R.layout.schedule_lv_item, Courses);
            this.context = context;
            this.courses = Courses;
            this.times = Times;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SharedPreferences CoursePreferences = getSharedPreferences("CourseNames", MODE_PRIVATE);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.schedule_lv_item, parent, false);
            TextView CourseSpot = (TextView) rowView.findViewById(R.id.courseName);
            CourseSpot.setText(CoursePreferences.getString(courses[position] + "Day" + prefs.getString(DAY_TYPE, "Unknown"), courses[position]));
            TextView TimeSpot = (TextView) rowView.findViewById(R.id.times);
            TimeSpot.setText(times[position]);
            if (position % 2 == 0) {
                rowView.setBackgroundColor(0);//Transparent
            }
            return rowView;

        }
    }
}