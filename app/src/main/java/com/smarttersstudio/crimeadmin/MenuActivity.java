package com.smarttersstudio.crimeadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void viewCrimes(View view) {
        startActivity(new Intent(this,CrimeActivity.class));
    }

    public void viewMissings(View view) {
        startActivity(new Intent(this,MissingActivity.class));
    }

    public void viewCriminals(View view) {
        startActivity(new Intent(this,ComplainActivity.class));
    }
}
