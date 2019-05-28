package com.example.mpp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Weekly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly);


        Toolbar tb = (Toolbar) findViewById(R.id.weeklyBar);
        setSupportActionBar(tb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater barInflater = getMenuInflater();
        barInflater.inflate(R.menu.appbar_action, menu);
        menu.getItem(4).setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                // 추가를 눌렀을때
                return true;
            case R.id.action_search:
                // 검색을 눌렀을때
                return true;
            case R.id.action_list:
                Intent intentList = new Intent(getApplicationContext(), List.class);
                startActivity(intentList);
                finish();
                return true;
            case R.id.action_monthly:
                Intent intentMonthly = new Intent(getApplicationContext(), Monthly.class);
                startActivity(intentMonthly);
                finish();
                return true;
            case R.id.action_weekly:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
