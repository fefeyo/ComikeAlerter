package com.fefeyo.android.comicmarkertalerter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.parse.Parse;
import com.parse.ParseInstallation;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(this, "ECw1iymeuOuX5XyK4I8H0AmHge4XpLLMndgiKhEi", "IVNQ1wmkG6RtrpclNdXGdDT7ChefGHYAHpcyeQhT");
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
