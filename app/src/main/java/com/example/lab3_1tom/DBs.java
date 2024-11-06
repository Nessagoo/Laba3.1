package com.example.lab3_1tom;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBs extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 2;

    public DBs(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS classmates (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "surname TEXT," +
                "name TEXT," +
                "patronymic TEXT," +
                "added_time DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");");
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS classmates;");
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        for (int i = 1; i <= 5; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("surname", "Фамилия" + i);
            contentValues.put("name", "Имя" + i);
            contentValues.put("patronymic", "Отчество" + i);
            db.insert("classmates", null, contentValues);
        }
    }
}
