//Written by Drew Gregory..... 2/5/2014
package com.flhs;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.flhs.utils.ConnectionErrorFragment;
import com.flhs.utils.ParserA;
import com.flhs.utils.SportEvent;
import com.flhs.utils.eventobject;
import com.parse.ConfigCallback;
import com.parse.ParseConfig;
import com.parse.ParseException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class HomeActivity extends FLHSActivity implements ConnectionErrorFragment.AlertDialogListener {
	ProgressBar mProgress;
	private static ParserA parser = new ParserA();
	public static ListView eventstodaylv;
	public static TextView txtdate;
	public static ListView sporteventstodaylv;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		SetupNavDrawer();
        mProgress = (ProgressBar) findViewById(R.id.progress_bar);
		//Sets listviews as "Loading...."		
		eventstodaylv = (ListView) findViewById(R.id.eventsTodayListView);
		eventstodaylv.setScrollContainer(false);
		eventstodaylv.setAdapter(loadingAdapter);

		sporteventstodaylv = (ListView) findViewById(R.id.sporteventsTodayListView);
		sporteventstodaylv.setAdapter(loadingAdapter);




		//Date Settings
		Date dateconversion = new Date();//Set Date object as Calendar time.
		SimpleDateFormat sf = new SimpleDateFormat("EE MMMM d, yyyy");//Day Of Week Month Date, Year
		String datestring = sf.format(dateconversion);

        String[] lvEventsArray = {"Event 1", "Event 2"};

		//Current Date
		txtdate = (TextView) findViewById(R.id.dateHeader);
		txtdate.setText(datestring);
		tryToConnect();

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

	}

	void tryToConnect() {
		if (isOnline()) {
			//Runs the loader thread!
			new EventsLoaderThread().execute();
		} else {
			ConnectionErrorFragment errorAlert = new ConnectionErrorFragment();
			errorAlert.show(getFragmentManager(), "Connection Error");
		}


	}

	class EventsLoaderThread extends AsyncTask<Void, Void, Void> {

		ProgressDialog pr = new ProgressDialog(HomeActivity.this);
		ArrayList<eventobject> todaysevents;
		ArrayList<SportEvent> sportEventsToday;
		@Override
		protected void onPreExecute() {
			mProgress.setVisibility(ProgressBar.VISIBLE);


		}


		@Override
		protected Void doInBackground(Void... arg0) {
			//Events Today Section
			todaysevents = parser.parseEventsToday();

			//Sports News Today Section
			sportEventsToday = parser.printSportsToday();

			return null;
		}


		@Override
		protected void onPostExecute(Void result) {
			//Events Today Format
			String[] lvEventsArray = {"Couldn't connect to bcsdny.org......."};
			if(todaysevents != null) {
				if (todaysevents.size() != 0) {

					lvEventsArray = new String[todaysevents.size()];
					for(int lvEventsArrayIndex = 0; lvEventsArrayIndex < todaysevents.size(); lvEventsArrayIndex++) {
						lvEventsArray[lvEventsArrayIndex] = todaysevents.get(lvEventsArrayIndex).eventsDesc[0] + "\n" + todaysevents.get(lvEventsArrayIndex).Date;
					}
				} if(todaysevents.size() == 0) {
					lvEventsArray = new String[1];
					lvEventsArray[0] = "No Events Today!";
				}
			}
			if(todaysevents == null) {
                if(getApplicationContext().equals(HomeActivity.this)) {
                    ConnectionErrorFragment errorAlert = new ConnectionErrorFragment();
                    errorAlert.show(getFragmentManager(), "Connection Error");
                }
			}


			//Sports News Today Format
			String[] lvSportEventsArray = {"Couldn't connect to sportspak.swboces.org....."};
			if (sportEventsToday != null) {
				if (sportEventsToday.size() == 0) {
					lvSportEventsArray = new String[1];
					lvSportEventsArray[0] = "No Games Today!";
				} else {
					lvSportEventsArray = new String[sportEventsToday.size()];
					for(int lvSportEventsArrayIndex = 0; lvSportEventsArrayIndex < sportEventsToday.size(); lvSportEventsArrayIndex++) {
						SportEvent indexedgame = sportEventsToday.get(lvSportEventsArrayIndex);
						lvSportEventsArray[lvSportEventsArrayIndex] = indexedgame.getTime() + "\n"
								+ indexedgame.getSport() + "\n" + indexedgame.getHomeTeam() + " vs " + indexedgame.getVisitingTeam();
					}
				}
			} else {
                if(getApplicationContext().equals(HomeActivity.this)) {
                    ConnectionErrorFragment errorAlert = new ConnectionErrorFragment();
                    errorAlert.show(getFragmentManager(), "Connection Error");
                }
			}


			//Events Today ListView
			EventsAdapter eventsTodayLvAdapter = new EventsAdapter(HomeActivity.this, lvEventsArray);

			eventstodaylv.setAdapter(eventsTodayLvAdapter);

			//SportsNews ListView

			EventsAdapter sporteventstodayadapter = new EventsAdapter(HomeActivity.this, lvSportEventsArray);
			sporteventstodaylv.setAdapter(sporteventstodayadapter);


            /*View homeBox1 = findViewById(R.id.homebox1);
            View homeBox2 = findViewById(R.id.homebox2);
            TextView eventsToday = (TextView) findViewById(R.id.eventsToday);
            ViewGroup.LayoutParams params = homeBox1.getLayoutParams();
            params.height = (int) (eventsToday.getTextSize() + eventsTodayLvAdapter.listViewHeight);
            homeBox1.setLayoutParams(params);
            TextView sportEventsToday = (TextView) findViewById(R.id.sporteventsToday);
            ViewGroup.LayoutParams params2 = homeBox2.getLayoutParams();
            params2.height = (int) (sportEventsToday.getTextSize() + sporteventstodayadapter.listViewHeight);
            homeBox2.setLayoutParams(params2); */

            eventstodaylv.setClickable(false);
            sporteventstodaylv.setClickable(false);
			mProgress.setVisibility(ProgressBar.INVISIBLE);







		}


	}

    private class EventsAdapter extends ArrayAdapter<String> {
        String[] events;
        Context context;
        int listViewHeight = 1;
        EventsAdapter (Context context, String[] Events) {
            super(context, R.layout.events_today_list_view_item, Events);
            this.events = Events;
            this.context = context;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.events_today_list_view_item, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.eventsListTextView);
            textView.setText(events[position]);
            //listViewHeight += textView.getMeasuredHeight() + textView.getHeight() + textView.getMinimumHeight();                                      // textView.getTextSize() * textView.getLineCount() + 172; //android:layout_margin=88
            return rowView;
        }
    }

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		tryToConnect();
		
	}



	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		mProgress.setVisibility(ProgressBar.INVISIBLE);
		
	}
	
	
	
	

}



