package com.flhs;

import com.parse.ConfigCallback;
import com.parse.Parse;
import com.parse.ParseConfig;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;

import android.app.Application;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FLHSApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Solely for setting up Push Notifications Receiver for Parse......
        Parse.initialize(this, "rxnQYcc4cGE16XzZEzkjLbobtqscs8xt7bqxj40g", "83Gx5MS2NkfDeagqKsj0f6hTdVt1yzftQkIJyROF");

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                } else {
                }
            }
        });

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
        SharedPreferences TodaysDate = getSharedPreferences("DATE", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = TodaysDate.edit();
        Date date = new Date();
        mEditor.putInt("DATE", Integer.parseInt(new SimpleDateFormat("d").format(date)));
        mEditor.apply();
    }
}
