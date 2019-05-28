package com.example.mpp;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        Toolbar tb = (Toolbar) findViewById(R.id.listBar);
        setSupportActionBar(tb);
        titleList = new ArrayList();
        contentList = new ArrayList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titleList);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                String str_date = df.format(new Date());

                dialogView = (View) View.inflate(List.this, R.layout.add_data, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(List.this);
                dlg.setView(dialogView);

                dataDate = (TextView) dialogView.findViewById(R.id.dataDate);
                dataDate.setText(str_date);

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
            case R.id.action_list:
                return true;
            case R.id.action_monthly:
                Intent intentMonthly = new Intent(getApplicationContext(), Monthly.class);
                startActivity(intentMonthly);
                finish();
                return true;
            case R.id.action_weekly:
                Intent intentWeekly = new Intent(getApplicationContext(), Weekly.class);
                startActivity(intentWeekly);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
