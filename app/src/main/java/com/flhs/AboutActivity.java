/* Written by Drew Gregory: 2/20/2014
*/
package com.flhs;

import com.parse.ParseInstallation;
import com.parse.PushService;

import android.os.Bundle;
import android.widget.Button;

public class AboutActivity extends FLHSActivity {
    Button b1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        SetupNavDrawer();

    }
}
