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
import java.util.Arrays;
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

       /*  //This code generated my 1 "Config" object. ParseConfig was discontinued in Parse Server. :-(
        ParseObject gameScore = new ParseObject("ConfigObject");
        String[] days = {
                "10/26:3","10/27:Special 5","10/31:4","11/01:5","11/02:A","11/03:B","11/04:C","11/07:D","11/08:E","11/09:1","11/10:Special 2","11/14:3","11/15:4","11/16:Collab 5","11/17:A","11/18:B","11/21:C","11/22:D","11/23:Special E","11/28:1","11/29:2","11/30:3","12/01:4","12/02:5","12/05:Special A","12/06:B","12/07:C","12/08:D","12/09:E","12/12:1","12/13:2","12/14:3","12/15:4","12/16:Collab 5","12/19:A","12/20:B","12/21:C","12:22:D","12/23:Special E"
        };
        gameScore.put("WhatDay", Arrays.asList(days));
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
        });*/


        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
