package com.example.mpp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class detail extends Activity {

    ArrayList<TextView> component;
    ArrayList<EditText> contents;
    ArrayList<RelativeLayout.LayoutParams> params;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.detail);
        Intent intent = getIntent();
        String noteName = intent.getExtras().getString("noteName");
        String createdTime = intent.getExtras().getString("createdTime");
        User user = new User();
        ArrayList<String> description = user.getHowToUse(noteName);
        ArrayList<String> conetents = user.getMemo(noteName,createdTime);

        RelativeLayout r1 = new RelativeLayout(this);


        params.add(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        r1.setLayoutParams(params.get(0));


        component.add(new TextView(this));
        component.get(0).setText(description.get(1));
        params.add(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        //conetents.add(new EditText(this));
    }

}
