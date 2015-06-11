//Written by Drew Gregory
package com.flhs;

import com.flhs.utils.Formatter;
import com.flhs.utils.ParserA;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;


@SuppressLint("ParserError")
public class CalendarActivity extends FLHSActivity {
    ImageButton PrevButton, NextButton;
    TextView NextMonth, PreviousMonth;
    int SelectedNavDrawerItemIndex = 2;
    Formatter formattingthingy;
    WebView calendarPrintWebpageViewer;
    int selectedYear;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActionBar().setIcon(R.drawable.calendar_icon_red);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        SetupNavDrawer();
        formattingthingy = new Formatter();
        calendarPrintWebpageViewer = (WebView) findViewById(R.id.Calendar_Web_View);
        calendarPrintWebpageViewer.setWebViewClient(new WebViewClient());
        WebSettings settings = calendarPrintWebpageViewer.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);
        calendarPrintWebpageViewer.loadUrl(formattingthingy.getFLHSCalendarURL());
        PrevButton = (ImageButton) findViewById(R.id.previous_month);
        NextButton = (ImageButton) findViewById(R.id.next_month);
        NextMonth = (TextView) findViewById(R.id.next_month_text);
        PreviousMonth = (TextView) findViewById(R.id.previous_month_text);
        selectedYear = formattingthingy.getYear();
        int selectedMonth = formattingthingy.getMonth();
        PreviousMonth.setText(ParserA.integerMonthToString(selectedMonth - 1));
        //Storing the integer value of the month the button is supposed to say in Content Description for both Buttons..... I don't like too many variables....
        PrevButton.setContentDescription(String.valueOf(selectedMonth - 1));
        NextMonth.setText(ParserA.integerMonthToString(selectedMonth + 1));
        NextButton.setContentDescription(String.valueOf(selectedMonth + 1));

        if (selectedMonth == 1) {
            PreviousMonth.setText("December");
            PrevButton.setContentDescription(String.valueOf(12));
        }
        if (selectedMonth == 12) {
            PreviousMonth.setText("November");
            PrevButton.setContentDescription(String.valueOf(11));
        }
        if (selectedMonth != 1 && selectedMonth != 12) {
            PreviousMonth.setText(ParserA.integerMonthToString(selectedMonth - 1));
            PrevButton.setContentDescription(String.valueOf(selectedMonth - 1));

        }

        if (selectedMonth == 12) {
            NextMonth.setText("January");
            NextButton.setContentDescription(String.valueOf(1));

        }
        if (selectedMonth == 1) {
            NextMonth.setText("February");
            NextButton.setContentDescription(String.valueOf(2));
        }
        if (selectedMonth != 12 && selectedMonth != 1) {
            NextMonth.setText(ParserA.integerMonthToString(selectedMonth + 1));
            NextButton.setContentDescription(String.valueOf(selectedMonth + 1));

        }
    }

    public void onPrevButtonClick(View v) {
        int PrevButtonMonthValue = Integer.parseInt((String) PrevButton.getContentDescription());

        if (PrevButtonMonthValue == 1) {
            PreviousMonth.setText("December");
            calendarPrintWebpageViewer.loadUrl(formattingthingy.getFLHSCalendarURL(1, selectedYear));
            PrevButton.setContentDescription(String.valueOf(12));
            NextMonth.setText(ParserA.integerMonthToString(PrevButtonMonthValue + 1) + " ->");
            NextButton.setContentDescription(String.valueOf(PrevButtonMonthValue + 1));
        }
        if (PrevButtonMonthValue == 12) {
            PreviousMonth.setText("November");
            calendarPrintWebpageViewer.loadUrl(formattingthingy.getFLHSCalendarURL(12, selectedYear - 1));
            PrevButton.setContentDescription(String.valueOf(11));
            NextMonth.setText("January");
            NextButton.setContentDescription(String.valueOf(1));
            selectedYear--;
        }
        if (PrevButtonMonthValue != 1 && PrevButtonMonthValue != 12) {
            calendarPrintWebpageViewer.loadUrl(formattingthingy.getFLHSCalendarURL(PrevButtonMonthValue, selectedYear));
            PreviousMonth.setText(ParserA.integerMonthToString(PrevButtonMonthValue - 1));
            PrevButton.setContentDescription(String.valueOf(PrevButtonMonthValue - 1));
            NextMonth.setText(ParserA.integerMonthToString(PrevButtonMonthValue + 1));
            NextButton.setContentDescription(String.valueOf(PrevButtonMonthValue + 1));

        }

    }

    public void onNextButtonClick(View v) {
        int NextButtonMonthValue = Integer.parseInt((String) NextButton.getContentDescription());


        if (NextButtonMonthValue == 12) {
            calendarPrintWebpageViewer.loadUrl(formattingthingy.getFLHSCalendarURL(NextButtonMonthValue, selectedYear));
            NextMonth.setText("January");
            NextButton.setContentDescription(String.valueOf(1));
            PreviousMonth.setText(ParserA.integerMonthToString(NextButtonMonthValue - 1));
            PrevButton.setContentDescription(String.valueOf(NextButtonMonthValue - 1));
        }
        if (NextButtonMonthValue == 1) {
            calendarPrintWebpageViewer.loadUrl(formattingthingy.getFLHSCalendarURL(NextButtonMonthValue, selectedYear + 1));
            NextMonth.setText("February");
            NextButton.setContentDescription(String.valueOf(2));
            PreviousMonth.setText("December");
            PrevButton.setContentDescription(String.valueOf(12));
            selectedYear++;
        }
        if (NextButtonMonthValue != 12 && NextButtonMonthValue != 1) {
            calendarPrintWebpageViewer.loadUrl(formattingthingy.getFLHSCalendarURL(NextButtonMonthValue, selectedYear));
            NextMonth.setText(ParserA.integerMonthToString(NextButtonMonthValue + 1));
            NextButton.setContentDescription(String.valueOf(NextButtonMonthValue + 1));
            PreviousMonth.setText(ParserA.integerMonthToString(NextButtonMonthValue - 1));
            PrevButton.setContentDescription(String.valueOf(NextButtonMonthValue - 1));
        }
    }


}
