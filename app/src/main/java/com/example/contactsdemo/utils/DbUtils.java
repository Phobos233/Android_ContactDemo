package com.example.contactsdemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author Phobos
 */
public class DbUtils extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    private static final String DATABASE_NAME="contacts.db";

    public DbUtils(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contacts(contactid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(20),tel VARCHAR(30))");
        db.execSQL("CREATE TABLE users (userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username VARCHAR(20),password VARCHAR(20))");
        db.execSQL("INSERT INTO contacts(contactid,name,tel) values(1,'example','12345678')");
        db.execSQL("INSERT INTO users (userid,username,password) values(1,'admin','123456')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
