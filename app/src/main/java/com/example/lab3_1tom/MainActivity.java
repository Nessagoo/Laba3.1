package com.example.lab3_1tom;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBs dbs;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbs = new DBs(this);
        db = dbs.getWritableDatabase();

        Button btnShowRecords = findViewById(R.id.button2);
        Button btnAddRecord = findViewById(R.id.button);
        Button btnUpdateLastRecord = findViewById(R.id.button3);

        btnShowRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });

        btnAddRecord.setOnClickListener(v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put("surname", "Новая Фамилия");
            contentValues.put("name", "Новое Имя");
            contentValues.put("patronymic", "Новое Отчество");
            long newRowId = db.insert("classmates", null, contentValues);
            if (newRowId != -1) {
                Toast.makeText(MainActivity.this, "Студент добавлен", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Ошибка добавления студента", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdateLastRecord.setOnClickListener(v -> {
            if (updateLastRecord()) {
                Toast.makeText(MainActivity.this, "Студент обновлен", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Ошибка обновления студента", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean updateLastRecord() {
        Cursor cursor = db.rawQuery("SELECT * FROM classmates ORDER BY ID DESC LIMIT 1", null);
        boolean updated = false;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("surname", "Иванов");
                    contentValues.put("name", "Иван");
                    contentValues.put("patronymic", "Иванович");
                    int rowsAffected = db.update("classmates", contentValues, "ID = ?", new String[]{String.valueOf(id)});
                    updated = rowsAffected > 0;
                }
            } finally {
                cursor.close();
            }
        }
        return updated;
    }

}
