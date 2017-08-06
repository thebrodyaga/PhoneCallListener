package com.example.win10.phonecalllistener;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String ttsPakcange = "com.google.android.tts";
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        boolean isAppInstalled = appInstalledOrNot(ttsPakcange);

        if (isAppInstalled) {
            //This intent will help you to launch if the package is already installed
            /*Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.check.application");
            startActivity(LaunchIntent);*/

            Log.d("Mylog", "Application is already installed.");
        } else {
            // Do whatever we want to do if application not installed
            // For example, Redirect to play store
            textView.setText("Необходимо утановить и настроить синтезатор речи");
            button.setVisibility(View.VISIBLE);
            Log.d("Mylog", "Application is not currently installed.");
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;

    }

    @Override
    public void onClick(View v) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + ttsPakcange)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + ttsPakcange)));
        }
    }
}
