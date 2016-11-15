package com.flhs;

import com.parse.ConfigCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseConfig;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FLHSApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Solely for setting up Push Notifications Receiver for Parse......
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("dRechuv2drap")
                .clientKey("t74traBeFuTr")
                .server("https://flhs-info-app.herokuapp.com/parse")
                .build()
        );

        /* This code generated my 1 "Config" object. ParseConfig was discontinued in Parse Server. :-(
        ParseObject gameScore = new ParseObject("Config");
        gameScore.put("WhatDay", "[]");
        gameScore.put("LunchMenuURL", "http://www.bcsdny.org/files/filesystem/Nov%20HS%20MS%20Lunch.pdf");
        gameScore.put("MAGIC", true);
        gameScore.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Parse", "Save Succeeded");
                } else {
                    Log.i("Parse", "Save Failed");
                }
            }
        });  */

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Config");
        query.getInBackground("ErmEX6MwFz", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    Log.i("IT WORKED!", object.getString("WhatDay"));
                } else {
                    // something went wrong
                }
            }
        });

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
