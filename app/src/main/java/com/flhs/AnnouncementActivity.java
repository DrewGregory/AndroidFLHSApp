//Written by Drew Gregory;
package com.flhs;

import com.flhs.utils.ConnectionErrorFragment;
import com.flhs.utils.ParserA;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;
import android.app.ProgressDialog;


public class AnnouncementActivity extends FLHSActivity implements ConnectionErrorFragment.AlertDialogListener {
	Button Refresh;
	static TextView date;
	static TextView announcement;
	int SelectedNavDrawerItemIndex = 1;
	ParserA parse;
	ProgressDialog prdi;
	ProgressBar mProgress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announcement);
		SetupNavDrawer();

		mProgress = (ProgressBar) findViewById(R.id.progressBar1);


		prdi = new ProgressDialog(AnnouncementActivity.this);
		Refresh = (Button) findViewById(R.id.switch_lunch);
		date = (TextView) findViewById(R.id.date);
		announcement = (TextView) findViewById(R.id.announcement);
		Refresh.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				tryToConnect();
			}
		});
		tryToConnect();
	}
	void tryToConnect() {
		if (isOnline()) {
			new AnnouncementParser().execute();
		} else {
			ConnectionErrorFragment alert = new ConnectionErrorFragment();
			alert.show(getFragmentManager(), "CONNECTION_ERROR");
			mProgress.setVisibility(ProgressBar.INVISIBLE);
		}

	}

	private class AnnouncementParser extends AsyncTask<Void,Void,Void>{
		String format, dateString = "Loading", announcementString = "Loading";
		int i;

		@Override
		protected void onPreExecute() {

			mProgress.setVisibility(ProgressBar.VISIBLE);
		}
		@Override
		protected Void doInBackground(Void... arg0) {
			String url = "http://www.bcsdny.org/flhs.cfm?subpage=1841";
			Document announce = null;
			try {
				announce = Jsoup.connect(url).get();
				dateString = "After connection";
				announcementString = "After connection";
				Elements annntxt = announce.getElementsByClass("MsoNormal");
				Element datetxt = annntxt.get(0);
				dateString = datetxt.text().trim();
				announcementString = "";
				for (int elementindex = 1; elementindex < (annntxt.size() - 1); elementindex++ ) {
					Element element = annntxt.get(elementindex);
					announcementString = announcementString + element.text() + "\n\n";

				}
			} catch (Exception e1) {
				ConnectionErrorFragment alert = new ConnectionErrorFragment();
				alert.show(getFragmentManager(), "CONNECTION_ERROR");
			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			announcement.setText("postexec");
			date.setText(dateString);
			announcement.setText(announcementString.trim());
			mProgress.setVisibility(ProgressBar.INVISIBLE);
		}


	}

	@Override
	public void onDialogPositiveClick(android.app.DialogFragment dialog) {
		tryToConnect();
		
	}

	@Override
	public void onDialogNegativeClick(android.app.DialogFragment dialog) {
		mProgress.setVisibility(ProgressBar.INVISIBLE);
		
	}
}
