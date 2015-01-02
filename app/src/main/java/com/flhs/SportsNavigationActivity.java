package com.flhs;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class SportsNavigationActivity extends FLHSActivity {

	String[] Levels = {"Varsity", "Junior Varsity", "Frosh"};
	String[] Seasons = {"Fall", "Winter", "Spring"};
	String[] FallVarsitySports = {
			"Cheerleading", "Cross Country", "Field Hockey", "Football",  "Boys Soccer", "Girls Soccer", "Girls Swim & Dive", "Girls Tennis", "Volleyball"
	};
	String[] FallJuniorVarsitySports = {
			"Cheerleading", "Field Hockey","Football", "Boys Soccer", "Girls Soccer", "Volleyball"	
	};
	String[] WinterVarsitySports = {
			"Boys Basketball", "Girls Basketball", "Cheerleading", "Ice Hockey", "Skiing", "Boys Swimming and Diving", "Indoor Track","Wrestling"
	};
	String[] WinterJuniorVarsitySports = {
			"Boys Basketball", "Girls Basketball", "Cheerleading", "Wrestling"	};
	String[] SpringVarsitySports = {
			"Baseball", "Golf", "Boys Lacrosse", "Girls Lacrosse", "Softball", "Boys Tennis", "Track and Field"
	};
	String[] SpringJuniorVarsitySports = {
			"Baseball", "Boys Lacrosse", "Girls Lacrosse", "Softball",
	};
	String[] SpringFroshSports = {
			"Baseball"	
	};
	ArrayAdapter<String> LevelAdapter;
	ArrayAdapter<String> SeasonAdapter;
	ImageButton backButton;
	ListView lv;
	SharedPreferences SportsPrefs;
	SharedPreferences.Editor SportsPrefsEditor;
	TextView Title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sports_navigation);
		SetupNavDrawer();
		SportsPrefs = getPreferences(MODE_PRIVATE);
		SportsPrefsEditor = SportsPrefs.edit();
		SportsPrefsEditor.putString("Level", "Unselected");
		SportsPrefsEditor.putString("Season", "Unselected");
		SportsPrefsEditor.putString("Sport", "Unselected");
		SportsPrefsEditor.commit();
		/*
		// Show the Up button in the action bar.
		setupActionBar();
		*/
		
		backButton = (ImageButton) findViewById(R.id.BackButton);
		//Hide it..... Obviously you can't GoBack() when you are selecting the Season......
		backButton.setVisibility(View.INVISIBLE);
		lv = (ListView) findViewById(R.id.selector_list_view);
		SeasonAdapter = new ArrayAdapter<String>(SportsNavigationActivity.this, android.R.layout.simple_list_item_1, Seasons);
		lv.setAdapter(SeasonAdapter);
		lv.setOnItemClickListener(new SeasonSelectionListener());
		Title = (TextView) findViewById(R.id.Title);
	}
	public void GoBack(View v) {
		if(Title.getText().equals("Level")) {
			Title.setText("Season");
			lv.setAdapter(SeasonAdapter);
			lv.setOnItemClickListener(new SeasonSelectionListener());
			backButton.setVisibility(View.INVISIBLE);
		}
		if(Title.getText().equals("Sport")) {
			Title.setText("Level");
			lv.setAdapter(LevelAdapter);
			lv.setOnItemClickListener(new LevelSelectionListener());
		}
		
		
			
	}



	private class SeasonSelectionListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
				if (position == 0) {
					SportsPrefsEditor.putString("Season", "Fall");
				}
				if (position == 1) {
					SportsPrefsEditor.putString("Season", "Winter");
				}
				if (position == 2) {
					SportsPrefsEditor.putString("Season", "Spring");
				}
				SportsPrefsEditor.commit();
				LevelAdapter = new ArrayAdapter<String>(SportsNavigationActivity.this, android.R.layout.simple_list_item_1, Levels);
				lv.setAdapter(LevelAdapter);
				lv.setOnItemClickListener(new LevelSelectionListener());
				Title.setText("Level");
				backButton.setVisibility(View.VISIBLE);
			


		}





	}
	private class LevelSelectionListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
				String[] SelectedSports = null;
				String SelectedSeason = SportsPrefs.getString("Season","null"); 
				if (position == 0) {
					SportsPrefsEditor.putString("Level","Varsity");
					if(SelectedSeason.equals("Fall")){
						SelectedSports = FallVarsitySports;
						SportsPrefsEditor.putString("Sport", "Fall Varsity");
					}
					if(SelectedSeason.equals("Winter")) {
						SelectedSports = WinterVarsitySports;
						SportsPrefsEditor.putString("Sport", "Winter Varsity");
					}
					if(SelectedSeason.equals("Spring")) {
						SelectedSports = SpringVarsitySports;
						SportsPrefsEditor.putString("Sport", "Spring Varsity");
					}
				}
				if (position == 1) {
					SportsPrefsEditor.putString("Level","JV");
					if(SelectedSeason.equals("Fall")){
						SelectedSports = FallJuniorVarsitySports;
						SportsPrefsEditor.putString("Sport", "Fall JV");
					}
					if(SelectedSeason.equals("Winter")) {
						SelectedSports = WinterJuniorVarsitySports;
						SportsPrefsEditor.putString("Sport", "Winter JV");
					}
					if(SelectedSeason.equals("Spring")) {
						SelectedSports = SpringJuniorVarsitySports;
						SportsPrefsEditor.putString("Sport", "Spring JV");
					}
				}
				if (position == 2) {
					SportsPrefsEditor.putString("Level", "Frosh");
					if(SelectedSeason.equals("Spring")) {
						SelectedSports = SpringFroshSports;
						SportsPrefsEditor.putString("Sport", "Spring JV");
					} else {
						SelectedSports = new String[1];
						SelectedSports[0] = "Invalid Sport Selection! Hit the Back button to go back!";
					}

				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(SportsNavigationActivity.this, android.R.layout.simple_list_item_1, SelectedSports);
				lv.setAdapter(adapter);
				SportsPrefsEditor.commit();
				Title.setText("Sport");
				lv.setOnItemClickListener(new SportSelectionListener());
			



		}

	}

	private class SportSelectionListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			

		}

	}
}
