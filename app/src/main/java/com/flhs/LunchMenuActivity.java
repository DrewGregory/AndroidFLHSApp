package com.flhs;

import com.flhs.utils.Formatter;
import com.parse.ParseConfig;

import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LunchMenuActivity extends FLHSActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActionBar().setIcon(R.drawable.lunch_menu_red);
        return super.onCreateOptionsMenu(menu);

    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lunch_menu);
		SetupNavDrawer();
		WebView LunchMenuPDFReader = (WebView) findViewById(R.id.Lunch_Menu_Web_View);

		//I skipped this part.... too lazy to check for Google Drive Viewer Consistencies..... I just put the Google Drive Viewer URL directly in
		String PDFReaderURL = Formatter.googleDriveViewerURLFormat("http://bcsdny.org/files/filesystem/flmsflhslunchmenu1.pdf");
		//Ok... I use the rest of this stuff now.
		LunchMenuPDFReader.setWebViewClient(new WebViewClient());
		WebSettings settings = LunchMenuPDFReader.getSettings();
		settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);
        ParseConfig config = ParseConfig.getCurrentConfig();

		LunchMenuPDFReader.loadUrl("http://docs.google.com/gview?embedded=true&url=" + config.getString("LunchMenuURL"));
	}
/*Just might be useful someday with UP navigation in Action Bar....
	**
	 * Set up the {@link android.app.ActionBar}.
	 **
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home_red:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
*/
}
