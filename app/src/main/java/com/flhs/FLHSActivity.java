package com.flhs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ConfigCallback;
import com.parse.ParseConfig;
import com.parse.ParseException;

/**
 * Created by Drew on 10/23/2014.
 */
public class FLHSActivity extends Activity {
    public final String DATE = "DATE";
    ActionBarDrawerToggle mDrawerToggle;
    int[][] FLHSDays = new int[13][32];
    final String[] Days = {"A","B","C","D","E","1","2","3","4","5"};
    final String[] Courses = {"Course 1", "Course 2", "Course 3", "Course 4", "Course 5", "Course 6", "Course 7", "Course 8"};



    CharSequence title = getTitle();
    class DrawerItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    Intent announcementsActivityExecute = new Intent(getApplicationContext(), AnnouncementActivity.class);
                    startActivity(announcementsActivityExecute);
                    break;
                case 1:
                    Intent calendarActivityExecute = new Intent(getApplicationContext(), CalendarActivity.class);
                    startActivity(calendarActivityExecute);
                    break;
                case 2:
                    Intent LunchMenuActivityExecute = new Intent(getApplicationContext(), LunchMenuActivity.class);
                    startActivity(LunchMenuActivityExecute);
                    break;
                case 3:
                    Intent SportsNavigationActivityExecute = new Intent(getApplicationContext(), TempSportsActivity.class);
                    startActivity(SportsNavigationActivityExecute);
                    break;
                case 4:
                    ParseConfig.getInBackground(new ConfigCallback() {
                        @Override
                        public void done(ParseConfig config, ParseException e) {
                            if (e == null) {
                            } else {
                                config = ParseConfig.getCurrentConfig();
                            }

                            // Get the message from config or fallback to default value

                        }
                    });
                    Intent schedulesActivityExecute = new Intent(getApplicationContext(), ScheduleActivity.class);
                    startActivity(schedulesActivityExecute);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "I don't know what happened....", Toast.LENGTH_LONG).show();

            }
        }
    }
    public class NavDrawerArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        int SelectedItemIndex;
        private final String[] values;

        public NavDrawerArrayAdapter(Context context, String[] values) {
            super(context, R.layout.nav_drawer_lv_item, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.nav_drawer_lv_item, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            textView.setText(values[position]);
            switch (position)
            {
                case 0:
                    if (getTitle().equals("Announcements")) {
                        imageView.setImageResource(R.drawable.announcements_icon_red);
                        textView.setTextColor(Color.RED);
                    } else {
                        imageView.setImageResource(R.drawable.announcements_icon_black);
                    }
                    break;
                case 1:
                    if(getTitle().equals("Calendar")) {
                        textView.setTextColor(Color.RED);
                        imageView.setImageResource(R.drawable.calendar_icon_red);
                    } else {
                        imageView.setImageResource(R.drawable.calendar_icon_black);
                    }
                    break;
                case 2:
                    if(getTitle().equals("Lunch Menu")) {
                        imageView.setImageResource(R.drawable.lunch_menu_red);
                        textView.setTextColor(Color.RED);
                    } else {
                        imageView.setImageResource(R.drawable.lunch_menu_black);
                    }
                    break;
                case 3:
                    if(getTitle().equals("Sports")) {
                        imageView.setImageResource(R.drawable.sports_icon_red);
                        textView.setTextColor(Color.RED);
                    } else {
                        imageView.setImageResource(R.drawable.sports_icon_black);
                    }
                    break;
                case 4:
                    if(getTitle().equals("Bell Schedules")) {
                        imageView.setImageResource(R.drawable.schedule_red);
                        textView.setTextColor(Color.RED);
                    } else {
                        imageView.setImageResource(R.drawable.schedule_black);
                    }
                    break;
                default:
                    imageView.setImageResource(R.drawable.ic_launcher);
            }
            return rowView;
        }
    }
    public static final String appname = "FLHS Info";
    ArrayAdapter<String> loadingAdapter;



    String[] MainActivities = {
            "Announcements", "Calendar", "Lunch Menu", "Sports", "Bell Schedules"
    };
    NavDrawerArrayAdapter navDrawerLvAdapter;



    ListView navlv;

    int SelectedNavDrawerItemIndex;




    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] LoadingStrings = {"Loading events from the internet....."};
        loadingAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, LoadingStrings );

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.taskbar, menu);
        getActionBar().show();
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()) {
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

    void SetupNavDrawer() {
        title = getTitle();
        navlv = (ListView) findViewById(R.id.left_drawer);
        navDrawerLvAdapter = new NavDrawerArrayAdapter(getApplicationContext(), MainActivities);
        navlv.setAdapter(navDrawerLvAdapter);
        navlv.setOnItemClickListener(new DrawerItemClickListener());


        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.hello_world  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(title);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("FLHS Menu");
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
