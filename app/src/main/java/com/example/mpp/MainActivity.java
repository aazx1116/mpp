package com.example.mpp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public final String PREFERENCE = "com.example.mpp.sharedpreference";
    public final String state = "state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button btnList = (Button) findViewById(R.id.btnList);
        Button btnMonthly = (Button) findViewById(R.id.btnMonthly);
        Button btnWeekly = (Button) findViewById(R.id.btnWeekly);

        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);

        if (pref.getInt(state, 0) == 0) {
            Intent intentList = new Intent(getApplicationContext(), List.class);
            startActivity(intentList);
            finish();
        }

        else if (pref.getInt(state, 0) == 1) {
            Intent intentMonthly = new Intent(getApplicationContext(), Monthly.class);
            startActivity(intentMonthly);
            finish();
        }

        else if (pref.getInt(state, 0) == 2) {
            Intent intentWeekly = new Intent(getApplicationContext(), Weekly.class);
            startActivity(intentWeekly);
            finish();
        }


        btnList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentList = new Intent(getApplicationContext(), List.class);
                startActivity(intentList);
                finish();
            }
        });

        btnMonthly.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentMonthly = new Intent(getApplicationContext(), Monthly.class);
                startActivity(intentMonthly);
                finish();
            }
        });

        btnWeekly.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentWeekly = new Intent(getApplicationContext(), Weekly.class);
                startActivity(intentWeekly);
                finish();
            }
        });

    }
}


