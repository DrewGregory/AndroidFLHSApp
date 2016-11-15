package com.flhs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.flhs.utils.DayPickerFragment;
import com.flhs.utils.FLHSDatePicker;
import com.flhs.utils.LunchPickerFragment;
import com.flhs.utils.ParserA;
import com.flhs.utils.ListViewHolderItem;
import com.parse.ConfigCallback;
import com.parse.GetCallback;
import com.parse.ParseConfig;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class ScheduleActivity extends FLHSActivity implements DayPickerFragment.DayPickerListener, LunchPickerFragment.LunchPickerListener, DatePickerDialog.OnDateSetListener {
    private static String LUNCH_TYPE = "Lunch Type";
    public static final String DAY_TYPE = "Day Type";
    String[] twoHourDelay5EarlyLunchTimes = {"9:45 - 10:10", "10:15 - 10:40", "10:45 - 11:10", "11:15 - 11:45", "11:50 - 12:15", "12:20 - 12:45", "12:50 - 1:15", "1:20 - 1:45", "1:50 - 2:15"};
    String[] twoHourDelay5MiddleLunchTimes = {"9:45 - 10:10", "10:15 - 10:40", "10:45 - 11:10", "11:15 - 11:40", "11:45 - 12:15", "12:20 - 12:45", "12:50 - 1:15", "1:20 - 1:45", "1:50 - 2:15"};
    String[] twoHourDelay5LateLunchTimes = {"9:45 - 10:10", "10:15 - 10:40", "10:45 - 11:10", "11:15 - 11:40", "11:45 - 12:10", "12:15 - 12:45", "12:50 - 1:15", "1:20 - 1:45", "1:50 - 2:15"};
    String[] twoHourDelayNormalTimes = {"9:45 - 10:20", "10:25 - 11:00", "11:05 - 11:40", "11:45 - 12:20", "12:25 - 1:00", "1:05 -1:40", "1:45 - 2:20"};
    String[] oneHourDelayEarlyLunchTimes = {"8:45 - 9:18", "9:23 - 9:56", "10:01 - 10:34", "10:39 - 11:09", "11:14 - 11:47", "11:52 - 12:25", "12:30 - 1:03", "1:08 - 1:41", "1:46 - 2:20"};
    String[] oneHourDelayMiddleLunchTimes = {"8:45 - 9:18", "9:23 - 9:56", "10:01 - 10:34", "10:39 - 11:12", "11:17 - 11:47", "11:52 - 12:25", "12:30 - 1:03", "1:08 - 1:41", "1:46 - 2:20"};
    String[] oneHourDelayLateLunchTimes = {"8:45 - 9:18", "9:23 - 9:56", "10:01 - 10:34", "10:39 - 11:12", "11:14 - 11:50", "11:55 - 12:25", "12:30 - 1:03", "1:08 - 1:41", "1:46 - 2:20"};
    String[] advLunch1Courses = {"Course 1", "Course 2", "Advisory", "Course 3", "Lunch", "Course 4", "Course 5", "Course 6", "Course 7", "Course 8"};
    String[] advLunch2Courses = {"Course 1", "Course 2", "Advisory", "Course 3", "Course 4", "Lunch", "Course 5", "Course 6", "Course 7", "Course 8"};
    String[] advLunch3Courses = {"Course 1", "Course 2", "Advisory", "Course 3", "Course 4", "Course 5", "Lunch", "Course 6", "Course 7", "Course 8"};
    String[] advLunch1Times = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:30", "9:35 - 10:10", "10:15 - 10:55", "11:00 - 11:35", "11:40 - 12:15", "12:20 - 12:55", "1:00 - 1:35", "1:40 - 2:15"};
    String[] advLunch2Times = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:30", "9:35 - 10:10", "10:15 - 10:50", "10:55 - 11:35", "11:40 - 12:15", "12:20 - 12:55", "1:00 - 1:35", "1:40 - 2:15"};
    String[] advLunch3Times = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:30", "9:35 - 10:10", "10:15 - 10:50", "10:55 - 11:30", "11:35 - 12:15", "12:20 - 12:55", "1:00 - 1:35", "1:40 - 2:15"};
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
    String[] day5Lunch1Times = {"7:45 - 8:25", "8:30 - 9:10", "9:15 - 9:55", "10:00 - 10:30", "10:35 - 11:15", "11:20 - 12:00", "12:05 - 12:45", "12:50 - 1:30", "1:35 - 2:15"};
    String[] day5Lunch2Times = {"7:45 - 8:25", "8:30 - 9:10", "9:15 - 9:55", "10:00 - 10:40", "10:45 - 11:15", "11:20 - 12:00", "12:05 - 12:45", "12:50 - 1:30", "1:35 - 2:15"};
    String[] day5Lunch3Times = {"7:45 - 8:25", "8:30 - 9:10", "9:15 - 9:55", "10:00 - 10:40", "10:45 - 11:25", "11:30 - 12:00", "12:05 - 12:45", "12:50 - 1:30", "1:35 - 2:15"};
    final int EARLY_LUNCH = 0;
    final int MIDDLE_LUNCH = 1;
    final int LATE_LUNCH = 2;
    SharedPreferences.Editor lunchTypeEditor;
    SharedPreferences lunchType, prefs;
    ListView content;
    Button dateButton;
    ParseObject config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        SetupNavDrawer();
        prefs = getSharedPreferences(DAY_TYPE, MODE_PRIVATE);
        content = (ListView) findViewById(R.id.contentListView);
        String[] loadScheduleStrings = {"You don't have school today!"};
        ArrayAdapter<String> EmptySchedule = new ArrayAdapter<String>(ScheduleActivity.this, android.R.layout.simple_list_item_1, loadScheduleStrings);
        content.setAdapter(EmptySchedule);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ConfigObject");

        query.getInBackground("HTvx9KQMmf", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    config = object;
                    // object will be your game score
                    Log.i("IT WORKED!", object.getBoolean("MAGIC") + " dat");
                    loadDateSchedule();
                    //Reason why I am running new void method is that it is in separate thread... so if I didn't put all the methods and/or resulting code here,
                    //the other code would run asynchronously and reach a nullpointer exception.
                } else {
                    // something went wrong
                    Log.i("IT DIDNT WORK!", "Whhyyyyyyy?");
                }
            }
        });
    }


     public void loadDateSchedule() {
         Date theCurrentTime = new Date();
         String mDate = new SimpleDateFormat("dd", Locale.US).format(theCurrentTime);
         String mMonth = new SimpleDateFormat("MM", Locale.US).format(theCurrentTime);
         dateButton = (Button) findViewById(R.id.ChangeDate);
         SharedPreferences.Editor dayTypeEditor = prefs.edit();
         if (!prefs.getString("Last Time Date Changed", "0").equals(mDate)) {
             dayTypeEditor.putString("selMonth", mMonth);
             dayTypeEditor.putString("selDate", mDate);
         }
         lunchType = getSharedPreferences(LUNCH_TYPE, MODE_PRIVATE);
        Button DayTitle = (Button) findViewById(R.id.DayTitle);
        if (!(prefs.getString("Last Time Day Changed", "0").equals(mDate))) {
            dateButton.setText(prefs.getString("selMonth", mMonth) + "/" + prefs.getString("selDate", mDate));
            boolean foundDate = false;
            JSONArray jsonDays = config.getJSONArray("WhatDay");
            if (jsonDays != null) {
                for (int index = 0; index < jsonDays.length(); index++) {
                    String jsonString = null;
                    try {
                        jsonString = jsonDays.get(index).toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dayTypeEditor.putString(DAY_TYPE, "Unknown");
                    }
                    String date = null;
                    try {
                        date = jsonString.substring(0, jsonString.indexOf(":"));
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                        dayTypeEditor.putString("Last Time Day Changed", mDate);
                        dayTypeEditor.apply();
                        break;
                    }
                    if (date.equals(dateButton.getText())) {
                        dayTypeEditor.putString(DAY_TYPE, jsonString.substring(jsonString.indexOf(":") + 1));
                        dayTypeEditor.commit();
                        foundDate = true;
                        break;
                    }
                }
                if (!foundDate) {
                    dayTypeEditor.putString(DAY_TYPE, "Unknown");
                    dayTypeEditor.commit();
                }
            }
        }
        String dayType = prefs.getString(DAY_TYPE, "Unknown");
        if (dayType.equals("Unknown")) {
            DayTitle.setText("Day");
        } else {
            DayTitle.setText("Day: " + dayType);
        }
        if (dayType.startsWith("1HD")) {
            dayTypeEditor.putString(DAY_TYPE, dayType.substring(dayType.length() - 1));
            dayTypeEditor.commit();
            dayType = "One Hour Delay";
        }
        else if (dayType.startsWith("2HD")) {
            dayTypeEditor.putString(DAY_TYPE, dayType.substring(dayType.length() - 1));
            dayTypeEditor.commit();
            dayType = "Two Hour Delay";
        }
        else if (dayType.startsWith("SPC")) {
            dayTypeEditor.putString(DAY_TYPE, dayType.substring(dayType.length() - 1));
            dayTypeEditor.commit();
            dayType = "Special";
        }
        switch (dayType) {
            case "ADVE":
            case "ADV5": {
                /*Switch day to E and 5 temporarily so that I can get the correct lunch setting in "lunchType.getInt(dayType, -1)"*/
                String[] CourseScheduleToPrint = {"Unknown Schedule."};
                String[] TimeScheduleToPrint = {"Unknown Times"};
                int selectedLunch = lunchType.getInt(dayType, -1);
                if (selectedLunch != -1) {
                    switch (selectedLunch) {
                        case EARLY_LUNCH:
                            CourseScheduleToPrint = advLunch1Courses;
                            TimeScheduleToPrint = advLunch1Times;
                            break;
                        case MIDDLE_LUNCH:
                            CourseScheduleToPrint = advLunch2Courses;
                            TimeScheduleToPrint = advLunch2Times;
                            break;
                        case LATE_LUNCH:
                            CourseScheduleToPrint = advLunch3Courses;
                            TimeScheduleToPrint = advLunch3Times;
                    }
                } else {
                    LunchPickerFragment LunchSelector = new LunchPickerFragment();
                    LunchSelector.show(getFragmentManager(), "Unknown Lunch");
                }
                ScheduleAdapter adapter = new ScheduleAdapter(ScheduleActivity.this, CourseScheduleToPrint, TimeScheduleToPrint);
                content.setAdapter(adapter);
                break;
            }
            case "CLBE":
            case "CLB5": {
                String[] CourseScheduleToPrint = collabCourses;
                String[] TimeScheduleToPrint = collabTimes;
                ScheduleAdapter adapter = new ScheduleAdapter(ScheduleActivity.this, CourseScheduleToPrint, TimeScheduleToPrint);
                content.setAdapter(adapter);
                DayTitle.setText("Day: " + dayType);
                Button switchLunch = (Button) findViewById(R.id.switch_lunch);
                switchLunch.setVisibility(View.INVISIBLE);
                break;
            }
            /* Old Special Day code.... for ParseConfig. You need to change this with the new mLab ParseObject.
            case "Special": {
                DayTitle.setText("Special Day " + prefs.getString(DAY_TYPE, "Unknown"));
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
                break;
            }*/
            case "One Hour Delay": {
                DayTitle.setText("One Hour Delay");
                String[] CourseScheduleToPrint = {"Unknown Schedule."};
                String[] TimeScheduleToPrint = {"Unknown Times"};
                String day = prefs.getString(DAY_TYPE, "Unknown");
                if (day.equals("A") || day.equals("B") || day.equals("C") || day.equals("D")) {
                    dayTypeEditor.putString(DAY_TYPE, "E");
                    dayTypeEditor.apply();
                }
                else if (day.equals("1") || day.equals("2") || day.equals("3") || day.equals("4")) {
                    dayTypeEditor.putString(DAY_TYPE, "5");
                    dayTypeEditor.apply();
                }
                int lunch = lunchType.getInt(prefs.getString(DAY_TYPE, "Unknown"), -1);
                switch (lunch) {
                    case EARLY_LUNCH:
                        CourseScheduleToPrint = ParserA.setLunchOrder(day5Lunch1Courses, lunch);
                        TimeScheduleToPrint = oneHourDelayEarlyLunchTimes;
                        break;
                    case MIDDLE_LUNCH:
                        CourseScheduleToPrint = ParserA.setLunchOrder(day5Lunch1Courses, lunch);
                        TimeScheduleToPrint = oneHourDelayMiddleLunchTimes;
                        break;
                    case LATE_LUNCH:
                        CourseScheduleToPrint = ParserA.setLunchOrder(day5Lunch1Courses, lunch);
                        TimeScheduleToPrint = oneHourDelayLateLunchTimes;
                        break;
                    default:
                        LunchPickerFragment LunchSelector = new LunchPickerFragment();
                        LunchSelector.show(getFragmentManager(), "Unknown Lunch");
                }
                ScheduleAdapter adapter = new ScheduleAdapter(this, CourseScheduleToPrint, TimeScheduleToPrint);
                content.setAdapter(adapter);
                break;
            }
            case "Two Hour Delay": {
                DayTitle.setText("Two Hour Delay");
                String[] CourseScheduleToPrint = {"Unknown Schedule."};
                String[] TimeScheduleToPrint = {"Unknown Times"};
                String day = prefs.getString(DAY_TYPE, "Unknown");
                int lunch = lunchType.getInt(day, -1);
                if (lunch != EARLY_LUNCH && lunch != MIDDLE_LUNCH && lunch != LATE_LUNCH) {
                    LunchPickerFragment LunchSelector = new LunchPickerFragment();
                    LunchSelector.show(getFragmentManager(), "Unknown Lunch");
                }
                if ((day.equals("E") || day.equals("5"))) {
                    switch (lunch) {
                        case EARLY_LUNCH:
                            TimeScheduleToPrint = twoHourDelay5EarlyLunchTimes;
                            CourseScheduleToPrint = ParserA.setLunchOrder(day5Lunch1Courses, lunch);
                            break;
                        case MIDDLE_LUNCH:
                            TimeScheduleToPrint = twoHourDelay5MiddleLunchTimes;
                            CourseScheduleToPrint = ParserA.setLunchOrder(day5Lunch1Courses, lunch);
                            break;
                        case LATE_LUNCH:
                            TimeScheduleToPrint = twoHourDelay5LateLunchTimes;
                            CourseScheduleToPrint = ParserA.setLunchOrder(day5Lunch1Courses, lunch);
                            break;
                    }
                } else {
                    switch (day) {
                        case "A":
                        case "1":
                            CourseScheduleToPrint = ParserA.setLunchOrder(day1Lunch1Courses, lunch);
                            break;
                        case "B":
                        case "2":
                            CourseScheduleToPrint = ParserA.setLunchOrder(day2Lunch1Courses, lunch);
                            break;
                        case "C":
                        case "3":
                            CourseScheduleToPrint = ParserA.setLunchOrder(day3Lunch1Courses, lunch);
                            break;
                        case "D":
                        case "4":
                            CourseScheduleToPrint = ParserA.setLunchOrder(day4Lunch1Courses, lunch);
                            break;
                    }
                    TimeScheduleToPrint = twoHourDelayNormalTimes; //All the times are the same for normal days; Lunch is the same length as a normal period.z
                }

                ScheduleAdapter adapter = new ScheduleAdapter(this, CourseScheduleToPrint, TimeScheduleToPrint);
                content.setAdapter(adapter);
                break;
            }
            default: {
                int lunch = lunchType.getInt(dayType, -1);
                if (lunch != -1) {
                    loadNormalSchedule(lunch, dayType);
                } else {
                    LunchPickerFragment LunchSelector = new LunchPickerFragment();
                    LunchSelector.show(getFragmentManager(), "Unknown Lunch");
                }
                break;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedule_content_menu, menu);
        getActionBar().setIcon(R.drawable.schedule_red);
        getActionBar().show();
        return true;
    }

    public void changeDate(View v) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("Last Time Day Changed", "0");
        edit.putString("Last Time Date Changed", new SimpleDateFormat("dd", Locale.US).format(new Date()));
        FLHSDatePicker dialog = new FLHSDatePicker();
        dialog.show(getFragmentManager(), "Date Picker");
        edit.apply();

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
        else if (SchoolDay.equals("B") || SchoolDay.equals("2")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day2Lunch1Courses, Lunch);
        }
        else if (SchoolDay.equals("C") || SchoolDay.equals("3")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day3Lunch1Courses, Lunch);
        }
        else if (SchoolDay.equals("D") || SchoolDay.equals("4")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day4Lunch1Courses, Lunch);
        }
        else if (SchoolDay.equals("E") || SchoolDay.equals("5")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day5Lunch1Courses, Lunch);
        }
        if (!(SchoolDay.equals("E") && SchoolDay.equals("5"))) {
            switch (Lunch) {
                case EARLY_LUNCH:
                    TimeScheduleToPrint = Lunch1Times;
                    break;
                case MIDDLE_LUNCH:
                    TimeScheduleToPrint = Lunch2Times;
                    break;
                case LATE_LUNCH:
                    TimeScheduleToPrint = Lunch3Times;
                    break;
            }
        }
        if (SchoolDay.equals("E") || SchoolDay.equals("5")) {
            switch (Lunch) {
                case EARLY_LUNCH:
                    TimeScheduleToPrint = day5Lunch1Times;
                    break;
                case MIDDLE_LUNCH:
                    TimeScheduleToPrint = day5Lunch2Times;
                    break;
                case LATE_LUNCH:
                    TimeScheduleToPrint = day5Lunch3Times;
                    break;
            }
        }
        if (CourseScheduleToPrint != null) {
            ScheduleAdapter adapter = new ScheduleAdapter(ScheduleActivity.this, CourseScheduleToPrint, TimeScheduleToPrint);
            content.setAdapter(adapter);
        } else {

        }
    }

    @Override
    public void onLunchPickPositiveClick(DialogFragment dialog, int selectedLunch) {

        lunchTypeEditor = lunchType.edit();
        lunchTypeEditor.putInt(prefs.getString(DAY_TYPE, "Unknown"), selectedLunch);
        lunchTypeEditor.apply();
        startActivity(new Intent(ScheduleActivity.this, ScheduleActivity.class));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }


    @Override
    public void onDayPickPositiveClick(int DayNum) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(DAY_TYPE, ParserA.parseNumToDay(DayNum));
        editor.putString("Last Time Day Changed", new SimpleDateFormat("dd", Locale.US).format(new Date()));
        editor.apply();
        startActivity(new Intent(ScheduleActivity.this, ScheduleActivity.class));
    }

    public void switch_day(View v) {
        DayPickerFragment mFragment = new DayPickerFragment();
        mFragment.show(getFragmentManager(), "Unknown Day");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String sMonth = String.format("%02d", ++month);
        String sDate = String.format("%02d", day);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("selMonth", sMonth + "");
        edit.putString("selDate", sDate + "");
        edit.apply();
        startActivity(new Intent(this, ScheduleActivity.class));
    }

    private class ScheduleAdapter extends ArrayAdapter<String> {
        Context context;
        private String[] courses;
        private String[] times;
        Calendar myCalendar;
        SharedPreferences CoursePreferences;
        ListViewHolderItem mItem;

        public ScheduleAdapter(Context context,
                               String[] Courses, String[] Times) {
            super(context, R.layout.schedule_lv_item, Courses);
            this.context = context;
            this.courses = Courses;
            this.times = Times;
            myCalendar = Calendar.getInstance();
            CoursePreferences = getSharedPreferences("CourseNames", MODE_PRIVATE);
        }


        boolean isDuringTime(String timeRange) {
            if (timeRange.contains("-")) {
                String firstTime = timeRange.substring(0, timeRange.indexOf("-")).trim();
                String secondTime = timeRange.substring(timeRange.indexOf("-") + 1).trim();
                int firstTimeInMin = Integer.parseInt(firstTime.substring(0, firstTime.indexOf(":"))) * 60 // Hours
                        + Integer.parseInt(firstTime.substring(firstTime.indexOf(":") + 1)); //Minutes
                if (firstTimeInMin < 240) { //240 means 4:00...... add 12 hours to so we handle am/pm issues.
                    firstTimeInMin += 12 * 60;
                }
                int secondTimeInMin = Integer.parseInt(secondTime.substring(0, secondTime.indexOf(":"))) * 60 //Hours
                        + Integer.parseInt(secondTime.substring(secondTime.indexOf(":") + 1)); //Minutes
                if (secondTimeInMin < 240) {
                    secondTimeInMin += 12 * 60;
                }
                int currentTime = myCalendar.get(Calendar.HOUR_OF_DAY) * 60
                        + myCalendar.get(Calendar.MINUTE);
                return currentTime <= secondTimeInMin && currentTime >= firstTimeInMin;
            } else {
                return false;
            }
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.schedule_lv_item, parent, false);
                mItem = new ListViewHolderItem();
                mItem.courseName = (TextView) convertView.findViewById(R.id.courseName);
                mItem.courseTime = (TextView) convertView.findViewById(R.id.times);
                convertView.setTag(mItem);
            } else {
                mItem = (ListViewHolderItem) convertView.getTag();

            }
            mItem.courseName.setText(CoursePreferences.getString(courses[position] + "Day" + prefs.getString(DAY_TYPE, "Unknown"), courses[position]));
            mItem.courseTime.setText(times[position]);
            if (isDuringTime(times[position]))
                convertView.setBackgroundColor(Color.YELLOW);
            else
                convertView.setBackgroundColor(0);
            return convertView;
        }
    }
}