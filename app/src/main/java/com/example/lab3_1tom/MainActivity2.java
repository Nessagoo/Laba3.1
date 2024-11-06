package com.example.lab3_1tom;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    DBs dbs;
    SQLiteDatabase db;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dbs = new DBs(this);
        db = dbs.getReadableDatabase();
        listView = findViewById(R.id.listView);

        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        listView.setAdapter(adapter);
        loadRecords();
    }

    private void loadRecords() {
        Cursor cursor = db.rawQuery("SELECT * FROM classmates", null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex("surname"));
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range") String patronymic = cursor.getString(cursor.getColumnIndex("patronymic"));
                        @SuppressLint("Range") String addedTime = cursor.getString(cursor.getColumnIndex("added_time"));
                        String fio = surname + " " + name + " " + patronymic + " - " + addedTime;
                        studentList.add(fio);
                    } while (cursor.moveToNext());
                }
                adapter.notifyDataSetChanged();
            } finally {
                cursor.close();
            }
        }

        adapter.notifyDataSetChanged();
    }
}

