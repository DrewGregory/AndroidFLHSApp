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

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("rxnQYcc4cGE16XzZEzkjLbobtqscs8xt7bqxj40g")
                .server("https://flhsappmigration.herokuapp.com/parse")
                .clientKey("MeF6JiZBBMcqqw6dxHNNpsoGFSPB9FIhiKQfZMFJ")
                .build()
        );

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
