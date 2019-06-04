package com.example.mpp;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class List extends AppCompatActivity {

    View dialogView;
    EditText dataContent;
    EditText dataTitle;
    TextView dataDate;
    ArrayList titleList;
    ArrayList contentList;
    ArrayAdapter adapter;
    ListView listListView;

    public final String PREFERENCE = "com.example.mpp.sharedpreference";
    public final String state = "state";

    public void setPreference(String key, int value) {
        SharedPreferences pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        Toolbar tb = (Toolbar) findViewById(R.id.listBar);
        setSupportActionBar(tb);
        titleList = new ArrayList();
        contentList = new ArrayList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titleList);
        listListView = (ListView) findViewById(R.id.listView1);
        listListView.setAdapter(adapter);

        listListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogView = (View) View.inflate(List.this, R.layout.view_data, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(List.this);
                dlg.setView(dialogView);
                dlg.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 데이터 수정
                    }
                });
                dlg.setNegativeButton("닫기", null);
                dlg.show();
            }
        });

        listListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(List.this);
                dlg.setMessage("삭제하시겠습니까?");
                dlg.setNegativeButton("아니오", null);
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        titleList.remove(position);
                        adapter.notifyDataSetChanged();
                        // 저장된 데이터 삭제해야 함.
                    }
                });
                dlg.show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater barInflater = getMenuInflater();
        barInflater.inflate(R.menu.appbar_action, menu);
        menu.getItem(2).setEnabled(false);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("검색할 내용을 입력하세요.");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String str_date = df.format(date);
                String str_year = new SimpleDateFormat("yyyy").format(date);
                String str_month = new SimpleDateFormat("MM").format(date);
                String str_day = new SimpleDateFormat("dd").format(date);

                dialogView = (View) View.inflate(List.this, R.layout.add_data, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(List.this);
                dlg.setView(dialogView);

                dataDate = (TextView) dialogView.findViewById(R.id.dataDate);
                dataDate.setText(str_date);

                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        dataDate.setText(year + "-" + (month+1) + "-" + day);
                    }
                };

                final DatePickerDialog dateDialog = new DatePickerDialog(this, listener, Integer.parseInt(str_year), Integer.parseInt(str_month) - 1, Integer.parseInt(str_day));

                dataDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateDialog.show();
                    }
                });

                dlg.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataContent = (EditText) dialogView.findViewById(R.id.dataContent);
                        dataTitle = (EditText) dialogView.findViewById(R.id.dataTitle);
                        titleList.add(dataTitle.getText().toString());
                        contentList.add(dataContent.getText().toString());
                        adapter.notifyDataSetChanged();
                        // 받아온 정보를 저장시키면 됨
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
                return true;
            case R.id.action_search:
                // 검색을 눌렀을때는 다른 메소드에서 처리
                return true;
            case R.id.action_list1:
                return true;
            case R.id.action_monthly1:
                Intent intentMonthly = new Intent(getApplicationContext(), Monthly.class);
                startActivity(intentMonthly);
                finish();
                return true;
            case R.id.action_weekly1:
                Intent intentWeekly = new Intent(getApplicationContext(), Weekly.class);
                startActivity(intentWeekly);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        setPreference(state, 0); // List에서 종료시 스테이트 값 0으로 반환
        super.onStop();
    }
}
