package com.smarttersstudio.crimeadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CrimeDetailsActivity extends AppCompatActivity {
    private TextView titleText,descText,statusText,dateText,pinText;
    private Button changeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_details);
        titleText=findViewById(R.id.crime_details_title);
        descText=findViewById(R.id.crime_details_desc);
    }
}
