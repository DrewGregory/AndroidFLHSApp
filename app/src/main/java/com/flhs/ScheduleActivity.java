package com.flhs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.flhs.utils.DayPickerFragment;
import com.flhs.utils.FLHSDatePicker;
import com.flhs.utils.LunchPickerFragment;
import com.flhs.utils.ParserA;
import com.flhs.utils.ListViewHolderItem;
import com.flhs.utils.SpecialTrackPickerFragment;
import com.parse.ConfigCallback;
import com.parse.ParseConfig;
import com.parse.ParseException;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class ScheduleActivity extends FLHSActivity implements SpecialTrackPickerFragment.TrackPickerListener, DayPickerFragment.DayPickerListener, LunchPickerFragment.LunchPickerListener, DatePickerDialog.OnDateSetListener {
    private static String LUNCH_TYPE = "Lunch Type";
    public static final String DAY_TYPE = "Day Type";
    public static final String DAY_LETTER = "Day Letter";
    String[] twoHourDelay5EarlyLunchTimes = {"9:45 - 10:10", "10:15 - 10:40", "10:45 - 11:10", "11:15 - 11:45", "11:50 - 12:15", "12:20 - 12:45", "12:50 - 1:15", "1:20 - 1:45", "1:50 - 2:15"};
    String[] twoHourDelay5MiddleLunchTimes = {"9:45 - 10:10", "10:15 - 10:40", "10:45 - 11:10", "11:15 - 11:40", "11:45 - 12:15", "12:20 - 12:45", "12:50 - 1:15", "1:20 - 1:45", "1:50 - 2:15"};
    String[] twoHourDelay5LateLunchTimes = {"9:45 - 10:10", "10:15 - 10:40", "10:45 - 11:10", "11:15 - 11:40", "11:45 - 12:10", "12:15 - 12:45", "12:50 - 1:15", "1:20 - 1:45", "1:50 - 2:15"};
    String[] twoHourDelayNormalLunchTimes = {"9:45 - 10:20", "10:25 - 11:00", "11:05 - 11:40", "11:45 - 12:20", "12:25 - 1:00", "1:05 -1:40", "1:45 - 2:20"}; //Works out to be the same regardless of lunch
    String[] oneHourDelay5EarlyLunchTimes = {"8:45 - 9:18", "9:23 - 9:56", "10:01 - 10:34", "10:39 - 11:09", "11:14 - 11:47", "11:52 - 12:25", "12:30 - 1:03", "1:08 - 1:41", "1:46 - 2:20"};
    String[] oneHourDelay5MiddleLunchTimes = {"8:45 - 9:18", "9:23 - 9:56", "10:01 - 10:34", "10:39 - 11:12", "11:17 - 11:47", "11:52 - 12:25", "12:30 - 1:03", "1:08 - 1:41", "1:46 - 2:20"};
    String[] oneHourDelay5LateLunchTimes = {"8:45 - 9:18", "9:23 - 9:56", "10:01 - 10:34", "10:39 - 11:12", "11:17 - 11:50", "11:55 - 12:25", "12:30 - 1:03", "1:08 - 1:41", "1:46 - 2:20"};
    String[] oneHourDelayEarlyLunchTimes = {"8:45 - 9:30", "9:35 - 10:20", "10:25 - 11:00","11:05 - 11:50","11:55 - 12:40","12:45 - 1:30", "1:35 - 2:20"};
    String[] oneHourDelayMiddleLunchTimes = {"8:45 - 9:30", "9:35 - 10:20", "10:25 - 11:10","11:15 - 11:50","11:55 - 12:40","12:45 - 1:30", "1:35 - 2:20"};
    String[] oneHourDelayLateLunchTimes = {"8:45 - 9:30", "9:35 - 10:20", "10:25 - 11:10","11:15 - 12:00","12:05 - 12:40","12:45 - 1:30", "1:35 - 2:20"};
    String[] adv1Lunch2Times = {"7:45 - 8:35", "8:40 - 9:30", "9:35 - 10:00", "10:05 - 10:55", "11:00 - 11:35", "11:40 - 12:30", "12:35 - 1:25", "1:30 - 2:20"};
    String[] adv1Lunch1Times = {"7:45 - 8:35", "8:40 - 9:30", "9:35 - 10:00", "10:05 - 10:40", "10:45 - 11:35", "11:40 - 12:30", "12:35 - 1:25", "1:30 - 2:20"};
    String[] adv1Lunch3Times = {"7:45 - 8:35", "8:40 - 9:30", "9:35 - 10:00", "10:05 - 10:55", "11:00 - 11:50", "11:55 - 12:30", "12:35 - 1:25", "1:30 - 2:20"};
    String[] advLunch1Times = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:30", "9:35 - 10:10", "10:15 - 10:55", "11:00 - 11:35", "11:40 - 12:15", "12:20 - 12:55", "1:00 - 1:35", "1:40 - 2:15"};
    String[] advLunch2Times = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:30", "9:35 - 10:10", "10:15 - 10:50", "10:55 - 11:35", "11:40 - 12:15", "12:20 - 12:55", "1:00 - 1:35", "1:40 - 2:15"};
    String[] advLunch3Times = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:30", "9:35 - 10:10", "10:15 - 10:50", "10:55 - 11:30", "11:35 - 12:15", "12:20 - 12:55", "1:00 - 1:35", "1:40 - 2:15"};
    String[] collabCourses = {"Course 1", "Course 2", "Course 3", "Course 4", "Collab Learning Lunch", "Course 5", "Course 6", "Course 7", "Course 8"};
    String[] collabTimes = {"7:45 - 8:20", "8:25 - 9:00", "9:05 - 9:40", "9:45 - 10:20", "10:25 - 11:35", "11:40 - 12:15", "12:20 - 12:55", "1:00 - 1:35", "1:40 - 2:15"};
    String[] day1Courses = {"Course 1", "Course 2",  "Course 4", "Lunch", "Course 5", "Course 7", "Course 8"};
    String[] Lunch1Times = {"7:45 - 8:40", "8:45 - 9:40", "9:45 - 10:20", "10:25 - 11:20", "11:25 - 12:20", "12:25 - 1:20", "1:25 - 2:20"};
    String[] Lunch2Times = {"7:45 - 8:40", "8:45 - 9:40", "9:45 - 10:40", "10:45 - 11:20", "11:25 - 12:20", "12:25 - 1:20", "1:25 - 2:20"};
    String[] Lunch3Times = {"7:45 - 8:40", "8:45 - 9:40", "9:45 - 10:40", "10:45 - 11:40", "11:45 - 12:20", "12:25 - 1:20", "1:25 - 2:20"};
    String[] day2Courses = {"Course 1", "Course 2", "Course 3", "Lunch", "Course 6", "Course 7", "Course 8"};
    String[] day3Courses = {"Course 2", "Course 3", "Course 4", "Lunch", "Course 5", "Course 6", "Course 7"};
    String[] day4Courses = {"Course 1", "Course 3", "Course 4", "Lunch", "Course 5", "Course 6", "Course 8"};
    String[] day5Courses = {"Course 1", "Course 2", "Course 3", "Course 4", "Lunch", "Course 5", "Course 6", "Course 7", "Course 8"};
    String[] day5Lunch1Times = {"7:45 - 8:25", "8:30 - 9:10", "9:15 - 9:55", "10:00 - 10:30", "10:35 - 11:15", "11:20 - 12:00", "12:05 - 12:45", "12:50 - 1:30", "1:35 - 2:15"};
    String[] day5Lunch2Times = {"7:45 - 8:25", "8:30 - 9:10", "9:15 - 9:55", "10:00 - 10:40", "10:45 - 11:15", "11:20 - 12:00", "12:05 - 12:45", "12:50 - 1:30", "1:35 - 2:15"};
    String[] day5Lunch3Times = {"7:45 - 8:25", "8:30 - 9:10", "9:15 - 9:55", "10:00 - 10:40", "10:45 - 11:25", "11:30 - 12:00", "12:05 - 12:45", "12:50 - 1:30", "1:35 - 2:15"};
    final int EARLY_LUNCH = 0;
    final int MIDDLE_LUNCH = 1;
    final int LATE_LUNCH = 2;
    SharedPreferences.Editor lunchTypeEditor;
    SharedPreferences lunchType, prefs;
    ListView content;
    String scheduleType;
    String dayLetter;
    Button dateButton;
    ParseConfig config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        SetupNavDrawer();
        content = (ListView) findViewById(R.id.contentListView);
        String[] loadScheduleStrings = {"You don't have school today!"};
        ArrayAdapter<String> EmptySchedule = new ArrayAdapter<String>(ScheduleActivity.this, android.R.layout.simple_list_item_1, loadScheduleStrings);
        content.setAdapter(EmptySchedule);
        config = ParseConfig.getCurrentConfig();
        prefs = getSharedPreferences(DAY_TYPE, MODE_PRIVATE);
        dateButton = (Button) findViewById(R.id.ChangeDate);
        String dayType = getDay();
        Button dayTitle = (Button) findViewById(R.id.DayTitle);
        String dayTitleText = dayType;
        dayLetter = dayType.substring(dayType.length() - 1);
        Log.i("Day Type",dayType);
        //"Translate" our day code stuff we put in database to normal text
        if (dayType.equals("Unknown")) {
            dayTitleText = "Day"; //No day set: we're beckoning the user to choose one!
        } else if (dayType.startsWith("1HD")){
            dayType = "1HD";
            dayTitleText = "One Hour Delay " + dayLetter;
        } else if (dayType.startsWith("2HD")) {
            dayType = "2HD";
            dayTitleText = "Two Hour Delay " + dayLetter;
        } else if (dayType.startsWith("~")) {
            //Don't trim SPC because you don't want to lose the name!
            //TODO: Allow for naming of special days
            dayTitleText = dayType.substring(1); //Get rid of "~"
        } else if (dayType.startsWith("ADV")) {
            dayType = "ADV";
            dayTitleText = "Advisory " + dayLetter;
        } else if (dayType.startsWith("CLB")) {
            dayType = "CLB";
            dayTitleText = "Collaborative " + dayLetter;
        }
        dayTitle.setText(dayTitleText);
        int lunchNum = getLunch();
        ParseConfig.getInBackground(new ConfigCallback() {
            @Override
            public void done(ParseConfig config, ParseException e) {
                if (e != null){
                    config = ParseConfig.getCurrentConfig();
                }
                // Get the message from config or fallback to default value
            }
        });
        loadSchedule(dayType, lunchNum);
    }

    public String[] addAdvisory (String[] originalCourses) {
        String[] newCourses = new String[originalCourses.length + 1];
        for (int index = 0; index < newCourses.length; index++) {
            if (index < 2)
                newCourses[index] = originalCourses[index];
             else if (index == 2)
                newCourses[2] = "Advisory";
            else
                newCourses[index] = originalCourses[index - 1];
        }
        return newCourses;
    }

    public int getLunch() {
        lunchType = getSharedPreferences(LUNCH_TYPE, MODE_PRIVATE);
        int lunch = lunchType.getInt(dayLetter, -1);
        if (lunch == -1) {
            LunchPickerFragment LunchSelector = new LunchPickerFragment();
            LunchSelector.show(getFragmentManager(), "Unknown Lunch");
        }
        return lunch;
    }

    public String getDay(){
        String dayType = "Unknown";
        Date theCurrentTime = new Date();
        String mDate = new SimpleDateFormat("dd", Locale.US).format(theCurrentTime);
        String mMonth = new SimpleDateFormat("MM", Locale.US).format(theCurrentTime);
        SharedPreferences.Editor dayTypeEditor = prefs.edit();
        if (prefs.getString("Last Time Date Changed", "0").equals(mDate)) {
            if (prefs.getString("Priority","Date").equals("Day")) {
                return prefs.getString(DAY_TYPE, "A");
            }
        }
        scheduleType = config.getString("ScheduleType");
        dateButton.setText(prefs.getString("selMonth", mMonth) + "/" + prefs.getString("selDate", mDate));
        JSONArray jsonDays = config.getJSONArray("WhatDay", null);
        if (jsonDays != null) {
            for (int index = 0; index < jsonDays.length(); index++) {
                String jsonString = null;
                try {
                    jsonString = jsonDays.get(index).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String date;
                try {
                    date = jsonString.substring(0, jsonString.indexOf(":"));
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    dayTypeEditor.putString("Last Time Day Changed", mDate);
                    dayTypeEditor.apply();
                    break;
                }
                if (date.equals(dateButton.getText())) {
                    dayType = jsonString.substring(jsonString.indexOf(":") + 1);
                    break;
                }
            }
        }
        dayTypeEditor.putString(DAY_TYPE, dayType);
        dayTypeEditor.commit();
        return dayType;
    }

    public void loadSchedule(String dayType, int lunchType) {
        Log.i("Activity Info", dayType + " " + lunchType);
       if (lunchType == -1) {
           lunchType = MIDDLE_LUNCH; //To be able to instantiate schedule.
       }
        String[] courseScheduleToPrint = new String[0];
        String[] timeScheduleToPrint = new String[0];
        //For days that are not merely letters, we are trimming the end letter
        if (dayType.startsWith("1HD")) {
            dayType = "1HD"; //Trim end
        }
        else if (dayType.startsWith("2HD")) {
            dayType = "2HD";
        }
        switch (dayType) {
            case "ADV": {
                if (dayLetter.equals("E") || dayLetter.equals("5")) {
                    courseScheduleToPrint = rearrangeCoursesForLunch(addAdvisory(day5Courses), lunchType);
                    switch (lunchType) {
                        case EARLY_LUNCH:
                            timeScheduleToPrint = advLunch1Times;
                            break;
                        case MIDDLE_LUNCH:
                            timeScheduleToPrint = advLunch2Times;
                            break;
                        case LATE_LUNCH:
                            timeScheduleToPrint = advLunch3Times;
                    }
                } else {
                    switch (lunchType) {
                        case EARLY_LUNCH:
                            timeScheduleToPrint = adv1Lunch1Times;
                            break;
                        case MIDDLE_LUNCH:
                            timeScheduleToPrint = adv1Lunch2Times;
                            break;
                        case LATE_LUNCH:
                            timeScheduleToPrint = adv1Lunch3Times;
                            break;
                    }

                    if (dayLetter.equals("1") || dayLetter.equals("A"))
                        courseScheduleToPrint = rearrangeCoursesForLunch(addAdvisory(day1Courses), lunchType);
                    if (dayLetter.equals("2") || dayLetter.equals("B"))
                        courseScheduleToPrint = rearrangeCoursesForLunch(addAdvisory(day2Courses), lunchType);
                    if (dayLetter.equals("3") || dayLetter.equals("C"))
                        courseScheduleToPrint = rearrangeCoursesForLunch(addAdvisory(day3Courses), lunchType);
                    if (dayLetter.equals("4") || dayLetter.equals("D"))
                        courseScheduleToPrint = rearrangeCoursesForLunch(addAdvisory(day4Courses), lunchType);
                }
                break;
            }
            case "CLB": {
                courseScheduleToPrint = collabCourses;
                timeScheduleToPrint = collabTimes;
                break;
            }
            case "1HD": {
                switch (dayLetter) {
                    case "A":
                    case "1":
                        courseScheduleToPrint = rearrangeCoursesForLunch(day1Courses, lunchType);
                        break;
                    case "B":
                    case "2":
                        courseScheduleToPrint = rearrangeCoursesForLunch(day2Courses, lunchType);
                        break;
                    case "C":
                    case "3":
                        courseScheduleToPrint = rearrangeCoursesForLunch(day3Courses, lunchType);
                        break;
                    case "D":
                    case "4":
                        courseScheduleToPrint = rearrangeCoursesForLunch(day4Courses, lunchType);
                        break;
                    case "5":
                    case "E":
                        courseScheduleToPrint = rearrangeCoursesForLunch(day5Courses, lunchType);
                }
                if (dayLetter.equals("E") || dayLetter.equals("5")) {
                    switch (lunchType) {
                        case EARLY_LUNCH:
                            timeScheduleToPrint = oneHourDelay5EarlyLunchTimes;
                            break;
                        case MIDDLE_LUNCH:
                            timeScheduleToPrint = oneHourDelay5MiddleLunchTimes;
                            break;
                        case LATE_LUNCH:
                            timeScheduleToPrint = oneHourDelay5LateLunchTimes;
                    }
                } else {
                    switch (lunchType) {
                        case EARLY_LUNCH:
                            timeScheduleToPrint = oneHourDelayEarlyLunchTimes;
                            break;
                        case MIDDLE_LUNCH:
                            timeScheduleToPrint = oneHourDelayMiddleLunchTimes;
                            break;
                        case LATE_LUNCH:
                            timeScheduleToPrint = oneHourDelayLateLunchTimes;
                    }
                }
                break;
            }
            case "2HD": {
                if ((dayLetter.equals("E") || dayLetter.equals("5"))) {
                    courseScheduleToPrint = rearrangeCoursesForLunch(day5Courses, lunchType);
                    switch (lunchType) {
                        case EARLY_LUNCH:
                            timeScheduleToPrint = twoHourDelay5EarlyLunchTimes;
                            break;
                        case MIDDLE_LUNCH:
                            timeScheduleToPrint = twoHourDelay5MiddleLunchTimes;
                            break;
                        case LATE_LUNCH:
                            timeScheduleToPrint = twoHourDelay5LateLunchTimes;
                            break;
                    }
                } else {
                    switch (dayLetter) {
                        case "A":
                        case "1":
                            courseScheduleToPrint = rearrangeCoursesForLunch(day1Courses, lunchType);
                            break;
                        case "B":
                        case "2":
                            courseScheduleToPrint = rearrangeCoursesForLunch(day2Courses, lunchType);
                            break;
                        case "C":
                        case "3":
                            courseScheduleToPrint = rearrangeCoursesForLunch(day3Courses, lunchType);
                            break;
                        case "D":
                        case "4":
                            courseScheduleToPrint = rearrangeCoursesForLunch(day4Courses, lunchType);
                            break;
                        case "5":
                        case "E":
                            courseScheduleToPrint = rearrangeCoursesForLunch(day5Courses, lunchType);
                    }
                   timeScheduleToPrint = twoHourDelayNormalLunchTimes; //All the times are the same for normal days; Lunch is the same length as a normal period.
                }
              break;
            }
            case "A":
            case "1":
                courseScheduleToPrint = rearrangeCoursesForLunch(day1Courses, lunchType);
                switch (lunchType) {
                    case EARLY_LUNCH:
                        timeScheduleToPrint = Lunch1Times;
                        break;
                    case MIDDLE_LUNCH:
                        timeScheduleToPrint = Lunch2Times;
                        break;
                    case LATE_LUNCH:
                        timeScheduleToPrint = Lunch3Times;
                        break;
                }
                break;
            case "B":
            case "2":
                courseScheduleToPrint = rearrangeCoursesForLunch(day2Courses, lunchType);
                switch (lunchType) {
                    case EARLY_LUNCH:
                        timeScheduleToPrint = Lunch1Times;
                        break;
                    case MIDDLE_LUNCH:
                        timeScheduleToPrint = Lunch2Times;
                        break;
                    case LATE_LUNCH:
                        timeScheduleToPrint = Lunch3Times;
                        break;
                }
                break;
            case "C":
            case "3":
                courseScheduleToPrint = rearrangeCoursesForLunch(day3Courses, lunchType);
                switch (lunchType) {
                    case EARLY_LUNCH:
                        timeScheduleToPrint = Lunch1Times;
                        break;
                    case MIDDLE_LUNCH:
                        timeScheduleToPrint = Lunch2Times;
                        break;
                    case LATE_LUNCH:
                        timeScheduleToPrint = Lunch3Times;
                        break;
                }
                break;
            case "D":
            case "4":
                courseScheduleToPrint = rearrangeCoursesForLunch(day4Courses, lunchType);
                switch (lunchType) {
                    case EARLY_LUNCH:
                        timeScheduleToPrint = Lunch1Times;
                        break;
                    case MIDDLE_LUNCH:
                        timeScheduleToPrint = Lunch2Times;
                        break;
                    case LATE_LUNCH:
                        timeScheduleToPrint = Lunch3Times;
                        break;
                }
                break;
            case "5":
            case "E":
                courseScheduleToPrint = rearrangeCoursesForLunch(day5Courses, lunchType);
                switch (lunchType) {
                    case EARLY_LUNCH:
                        timeScheduleToPrint = day5Lunch1Times;
                        break;
                    case MIDDLE_LUNCH:
                        timeScheduleToPrint = day5Lunch2Times;
                        break;
                    case LATE_LUNCH:
                        timeScheduleToPrint = day5Lunch3Times;
                        break;
                }
        }
        if (dayType.startsWith("~")) {//SPECIAL CUSTOM DAY
            Button switchLunch = (Button) findViewById(R.id.switch_lunch);

            switchLunch.setVisibility(View.VISIBLE); //Switch this when you figure out lunch compatibility...
            JSONArray scheduleArray = config.getJSONArray(dayType);
            //Determine what the different tracks are...
             ArrayList<String> tracks = new ArrayList<String>();
            ArrayList<Integer> trackIds = new ArrayList<Integer>();
            for (int i = 0; i < scheduleArray.length(); i++) {
                try {
                    String string = scheduleArray.getString(i);
                    if (string.startsWith("/")) {
                        trackIds.add(i);
                        tracks.add(string.substring(1));//Get rid of slash
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            final ArrayList<String> trackFinal = tracks;
            switchLunch.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    SpecialTrackPickerFragment fragment = new SpecialTrackPickerFragment();
                    Bundle args = new Bundle();
                    args.putStringArrayList("tracks",trackFinal);
                    fragment.setArguments(args);
                    fragment.show(getFragmentManager(), "Unknown track");
                }
            });
            //Find which track we should be displaying...
            SharedPreferences specialDayPrefs = getSharedPreferences("specialTrack",MODE_PRIVATE);
            int trackIndex = specialDayPrefs.getInt("index", 0);
            //The first track is the default one.
            //In case this is from a previous special day with more tracks, check if this index exists
            if (trackIds.size() <= trackIndex) //Not room, so let's reset it to 0.
                trackIndex = 0;
            //Update Switch Lunch button with current selection.
            switchLunch.setText(tracks.get(trackIndex));
            /*Indices (in scheduleArray)
            * First Course: trackIds.get(trackIndex) + 1
            * Last Course Time: trackIds.get(trackIndex + 1) - 1
            * */

            int fCIndex = trackIds.get(trackIndex)+1;
            int nextTrackNameIndex;
            try {
                nextTrackNameIndex = trackIds.get(trackIndex + 1);
            } catch (IndexOutOfBoundsException e) {
                //There are no more tracks. Go to last element.
                nextTrackNameIndex = scheduleArray.length();
            }

            timeScheduleToPrint = new String[(nextTrackNameIndex -  fCIndex)/2]; //the courses and times alternate...
            courseScheduleToPrint = new String[(nextTrackNameIndex - fCIndex)/2];
            if ((nextTrackNameIndex - fCIndex) % 2 == 0) { //Check to make sure there is a course for every time
                for (int index = 0; index < (nextTrackNameIndex -  fCIndex); index++) {
                    try {
                        Log.i("Tracks", scheduleArray.getString(fCIndex + index));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (index % 2 == 0) { //The courses and times alternate...
                            courseScheduleToPrint[index/2] = scheduleArray.getString(fCIndex + index);
                        } else if (index % 2 == 1) {
                            timeScheduleToPrint[((int)(index/2))] = scheduleArray.getString(fCIndex  + index);
                        }
                    } catch (JSONException ex) {
                        break;
                    }
                }
            }
        }
        ScheduleAdapter adapter = new ScheduleAdapter(ScheduleActivity.this, courseScheduleToPrint, timeScheduleToPrint);
        content.setAdapter(adapter);
    }

    public String[] rearrangeCoursesForLunch(String[] courses,  int lunchType) {
        int middleIndex = 0;
        for (int index = 1; index < courses.length; index++){
            if(courses[index].equals("Lunch")) {
                middleIndex = index;
                break;
            }
        }
        String courseBefore = courses[middleIndex - 1];
        String courseAfter = courses[middleIndex + 1];
        if (lunchType == EARLY_LUNCH) {
            courses[middleIndex - 1] = "Lunch";
            courses[middleIndex] = courseBefore;
            courses[middleIndex - 1] = "Lunch";
            courses[middleIndex] = courseBefore;
        } else if (lunchType == LATE_LUNCH) {
            courses[middleIndex] = courseAfter;
            courses[middleIndex + 1] = "Lunch";
        }
        return courses;
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
            CourseScheduleToPrint = ParserA.setLunchOrder(day1Courses, Lunch);
        }
        else if (SchoolDay.equals("B") || SchoolDay.equals("2")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day2Courses, Lunch);
        }
        else if (SchoolDay.equals("C") || SchoolDay.equals("3")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day3Courses, Lunch);
        }
        else if (SchoolDay.equals("D") || SchoolDay.equals("4")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day4Courses, Lunch);
        }
        else if (SchoolDay.equals("E") || SchoolDay.equals("5")) {
            CourseScheduleToPrint = ParserA.setLunchOrder(day5Courses, Lunch);
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
        }
    }

    @Override
    public void onLunchPickPositiveClick(DialogFragment dialog, int selectedLunch) {

        lunchTypeEditor = lunchType.edit();
        lunchTypeEditor.putInt(dayLetter, selectedLunch);
        lunchTypeEditor.apply();
        startActivity(new Intent(ScheduleActivity.this, ScheduleActivity.class));
    }

    @Override
    public void onTrackPickPositiveClick(DialogFragment dialog, int selectedTrack) {
        SharedPreferences specialTrackPrefs = getSharedPreferences("specialTrack", MODE_PRIVATE);
        SharedPreferences.Editor editor = specialTrackPrefs.edit();
        editor.putInt("index", selectedTrack);
        editor.apply();
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
        editor.putString("Priority", "Day");
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
        edit.putString("Last Time Day Changed", new SimpleDateFormat("dd", Locale.US).format(new Date()));
        edit.putString("Priority", "Date");
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

            Log.i("Courses:", Arrays.toString(Courses));
            Log.i("Times:" , Arrays.toString(Times));
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
            mItem.courseName.setText(CoursePreferences.getString(courses[position] + "Day" + dayLetter, courses[position]));
            mItem.courseTime.setText(times[position]);
            if (isDuringTime(times[position]))
                convertView.setBackgroundColor(Color.YELLOW);
            else
                convertView.setBackgroundColor(0);
            return convertView;
        }
    }
}