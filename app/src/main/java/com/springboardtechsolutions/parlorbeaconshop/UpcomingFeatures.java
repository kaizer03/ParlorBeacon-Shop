package com.springboardtechsolutions.parlorbeaconshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UpcomingFeatures extends AppCompatActivity {

    String backslash = "\u2022";
    String features[] = {
            backslash+" Tablet Support",
            backslash+" Online Booking System",
            backslash+" Booking Cancellation",
            backslash+" Upload Profile Picture"};
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_features);

        listView = (ListView)findViewById(R.id.UF);
        ArrayAdapter adapter = new ArrayAdapter(UpcomingFeatures.this, android.R.layout.simple_list_item_1,android.R.id.text1,features);
        listView.setAdapter(adapter);
    }



}
