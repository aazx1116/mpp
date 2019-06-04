package com.example.mpp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.animation.ValueAnimator;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Weekly extends AppCompatActivity implements TaTCalendarWeekFragment.OnFragmentListener {

    private static final String TAG = "Weekly";
    private static final int COUNT_PAGE = 50;
    private ViewPager viewPager;
    private TaTCalendarWeekAdapter taTCalendarWeekAdapter;

    public final String PREFERENCE = "com.example.mpp.sharedpreference";
    public final String state = "state";

    TextView weeklyDate;
    ListView weeklyListView;

    public void setPreference(String key, int value) {
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly);

        weeklyDate = (TextView) findViewById(R.id.weeklyDate);
        Toolbar tb = (Toolbar) findViewById(R.id.weeklyBar);
        setSupportActionBar(tb);

        viewPager = (ViewPager)findViewById(R.id.calendar_week_pager);
        taTCalendarWeekAdapter = new TaTCalendarWeekAdapter(getSupportFragmentManager());
        viewPager.setAdapter(taTCalendarWeekAdapter);

        taTCalendarWeekAdapter.setOnFragmentListener(this);
        taTCalendarWeekAdapter.setNumOfWeek(COUNT_PAGE);
        viewPager.setCurrentItem(COUNT_PAGE);
        String title = taTCalendarWeekAdapter.getMonthDisplayed(COUNT_PAGE);
        weeklyDate.setText(title);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String title = taTCalendarWeekAdapter.getMonthDisplayed(position);
                weeklyDate.setText(title);

                if (position == 0) {
                    taTCalendarWeekAdapter.addPrev();
                    viewPager.setCurrentItem(COUNT_PAGE, false);
                    Log.d("TaTCalendarActivity","position("+position+") COUNT_PAGE("+COUNT_PAGE+")");
                } else if (position == taTCalendarWeekAdapter.getCount() - 1) {
                    taTCalendarWeekAdapter.addNext();
                    viewPager.setCurrentItem(taTCalendarWeekAdapter.getCount() - (COUNT_PAGE + 1), false);
                    Log.d("TaTCalendarActivity","position("+position+") COUNT_PAGE("+(taTCalendarWeekAdapter.getCount() - (COUNT_PAGE + 1))+")");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
            case R.id.action_list1:
                Intent intentList = new Intent(getApplicationContext(), List.class);
                startActivity(intentList);
                finish();
                return true;
            case R.id.action_monthly1:
                Intent intentMonthly = new Intent(getApplicationContext(), Monthly.class);
                startActivity(intentMonthly);
                finish();
                return true;
            case R.id.action_weekly1:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onFragmentListener(View view) {
        resizeHeight(view);
    }

    public void resizeHeight(View mRootView) {

        if (mRootView.getHeight() < 1) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        if (layoutParams.height <= 0) {
            layoutParams.height = mRootView.getHeight();
            viewPager.setLayoutParams(layoutParams);
            return;
        }
        ValueAnimator anim = ValueAnimator.ofInt(viewPager.getLayoutParams().height, mRootView.getHeight());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
                layoutParams.height = val;
                viewPager.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(200);
        anim.start();
    }


    @Override
    protected void onStop() {
        setPreference(state, 2); // weekly에서 종료시 스테이트 값 2로 반환
        super.onStop();
    }
}




