package com.flhs;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.flhs.utils.AlternatingCoursesDialog;


public class CourseSelectorActivity extends Activity implements AlternatingCoursesDialog.getEditText, AlternatingCoursesDialog.getToggleButton {
    final String[] Days = {"A","B","C","D","E","1","2","3","4","5"};
    ToggleButton Course1AltToggle, Course2AltToggle, Course3AltToggle, Course4AltToggle, Course5AltToggle, Course6AltToggle, Course7AltToggle, Course8AltToggle;
    EditText[] CourseEditTexts = new EditText[8];
    ToggleButton[] CourseAltToggles = new ToggleButton[8];
    EditText Course1, Course2, Course3, Course4, Course5, Course6, Course7, Course8;
    SharedPreferences CoursePreferences;
    ToggleButton selectedToggleButton;
    private EditText selectedEditText;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                seeSchedule();
                return true;
            case R.id.info_icon:
                Intent aboutActivityExecute = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(aboutActivityExecute);
                return true;

            case R.id.home_icon:
                Intent HomeActivityExecute = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(HomeActivityExecute);
                return true;
            default:
                //Until there is something useful on that Activity....
                Toast.makeText(getApplicationContext(), "Settings...." , Toast.LENGTH_LONG).show();
                return true;
        }
    }
    public void onAltToggleClick (View v) {
        ToggleButton button = (ToggleButton) v;
        int courseNum = 0;
        switch (button.getId()) {
            case R.id.Course1AltToggle:
                courseNum = 1;
                break;
            case R.id.Course2AltToggle:
                courseNum = 2;
                break;
            case R.id.Course3AltToggle:
                courseNum = 3;
                break;
            case R.id.Course4AltToggle:
                courseNum = 4;
                break;
            case R.id.Course5AltToggle:
                courseNum = 5;
                break;
            case R.id.Course6AltToggle:
                courseNum = 6;
                break;
            case R.id.Course7AltToggle:
                courseNum = 7;
                break;
            case R.id.Course8AltToggle:
                courseNum = 8;
                break;
        }
        if(button.isChecked()) {
            selectedToggleButton = button;
            button.setText("Alternating Course " + courseNum );
            selectedEditText = CourseEditTexts[courseNum - 1];
            CourseEditTexts[courseNum - 1].setVisibility(View.GONE);
            Bundle bundle = new Bundle();
            bundle.putInt("CourseNum", courseNum);
            DialogFragment altToggle = new AlternatingCoursesDialog();
            altToggle.setArguments(bundle);
            altToggle.show(getFragmentManager(), "Alternating Courses....");
        } else {
            CourseEditTexts[courseNum - 1].setVisibility(View.VISIBLE);
            button.setText("Alt");
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selector);
        CoursePreferences = getSharedPreferences("CourseNames", MODE_PRIVATE);
        CourseEditTexts[0] = ((EditText) findViewById(R.id.Course1Name));
        CourseEditTexts[1] = ((EditText) findViewById(R.id.Course2Name));
        CourseEditTexts[2] = ((EditText) findViewById(R.id.Course3Name));
        CourseEditTexts[3] = ((EditText) findViewById(R.id.Course4Name));
        CourseEditTexts[4] = ((EditText) findViewById(R.id.Course5Name));
        CourseEditTexts[5] = ((EditText) findViewById(R.id.Course6Name));
        CourseEditTexts[6] = ((EditText) findViewById(R.id.Course7Name));
        CourseEditTexts[7] = ((EditText) findViewById(R.id.Course8Name));
        CourseEditTexts[0].setText(CoursePreferences.getString("Course 1DayA", "Course 1"));
        CourseEditTexts[1].setText(CoursePreferences.getString("Course 2DayA", "Course 2"));
        CourseEditTexts[2].setText(CoursePreferences.getString("Course 3DayA", "Course 3"));
        CourseEditTexts[3].setText(CoursePreferences.getString("Course 4DayA", "Course 4"));
        CourseEditTexts[4].setText(CoursePreferences.getString("Course 5DayA", "Course 5"));
        CourseEditTexts[5].setText(CoursePreferences.getString("Course 6DayA", "Course 6"));
        CourseEditTexts[6].setText(CoursePreferences.getString("Course 7DayA", "Course 7"));
        CourseEditTexts[7].setText(CoursePreferences.getString("Course 8DayA", "Course 8"));
        CourseAltToggles[0] = (ToggleButton) findViewById(R.id.Course1AltToggle);
        CourseAltToggles[1] = (ToggleButton) findViewById(R.id.Course2AltToggle);
        CourseAltToggles[2] = (ToggleButton) findViewById(R.id.Course3AltToggle);
        CourseAltToggles[3] = (ToggleButton) findViewById(R.id.Course4AltToggle);
        CourseAltToggles[4] = (ToggleButton) findViewById(R.id.Course5AltToggle);
        CourseAltToggles[5] = (ToggleButton) findViewById(R.id.Course6AltToggle);
        CourseAltToggles[6] = (ToggleButton) findViewById(R.id.Course7AltToggle);
        CourseAltToggles[7] = (ToggleButton) findViewById(R.id.Course8AltToggle);
        for (int courseIndex = 0; courseIndex < CourseAltToggles.length; courseIndex++ ) {
            if(CoursePreferences.getBoolean("Course"+ (courseIndex + 1) + "Alt", false)) {
                CourseAltToggles[courseIndex].setChecked(true);
                CourseAltToggles[courseIndex].setText("Alternating Course " + (courseIndex + 1) );
                CourseEditTexts[courseIndex].setVisibility(View.VISIBLE);
            }
        }


    }

    public void seeSchedule () {
       new SeeScheduleTask().execute();


    }

    @Override
    public ToggleButton getToggleButton() {
        return selectedToggleButton;
    }

    @Override
    public EditText getEditText() {
        return selectedEditText;
    }


    private class SeeScheduleTask extends AsyncTask<Void, Void, Void> {
        ProgressBar mProgressBar;
        @Override
        protected void onPreExecute() {
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences.Editor mEditor = CoursePreferences.edit();
            for(int courseIndex = 0; courseIndex < CourseAltToggles.length; courseIndex++) {
                if(!CourseAltToggles[courseIndex].isChecked()) {
                    String courseName = CourseEditTexts[courseIndex].getText().toString();
                    mEditor.putBoolean("Course" + (courseIndex + 1) + "Alt", false);
                    for (int day = 0; day < 10; day++) {
                        mEditor.putString("Course " + (courseIndex + 1) + "Day" + Days[day], courseName);
                    }

                } else {
                    mEditor.putBoolean("Course" + (courseIndex + 1) + "Alt", true);
                }
            }
            mEditor.commit();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressBar.setVisibility(View.INVISIBLE);
            startActivity(new Intent(CourseSelectorActivity.this, ScheduleActivity.class));

        }
    }

    public void SeeSchedule(View v) {
        seeSchedule();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.taskbar, menu);
        return true;
    }


}
