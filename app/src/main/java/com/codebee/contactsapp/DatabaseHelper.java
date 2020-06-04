package com.codebee.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Contacts.db";
    public static final String TABLE_NAME = "contacts_table";
    public static final int DATABASE_VERSION = 1;
    public static final String COL_1 = "Id";
    public static final String COL_2 = "Fname";
    public static final String COL_3 = "Lname";
    public static final String COL_4 = "Mobile";
    public static final String COL_5 = "Work";
    public static final String COL_6 = "Email";
    public static final String COL_7 = "Custom1";
    public static final String COL_8 = "Custom2";
    public static final String COL_9 = "Custom3";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_1 + " INTEGER PRIMARY KEY," +
                COL_2 + " TEXT," +
                COL_3 + " TEXT," +
                COL_4 + " INTEGER," +
                COL_5 + " INTEGER," +
                COL_6 + " TEXT," +
                COL_7 + " INTEGER," +
                COL_8 + " INTEGER," +
                COL_9 + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,contact.getFname());
        contentValues.put(COL_3,contact.getLName());
        contentValues.put(COL_4,contact.getMobile());
        contentValues.put(COL_5,contact.getWork());
        contentValues.put(COL_6,contact.getEmail());
        contentValues.put(COL_7,contact.getCustom1());
        contentValues.put(COL_8,contact.getCustom2());
        contentValues.put(COL_9,contact.getCustom3());
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}
