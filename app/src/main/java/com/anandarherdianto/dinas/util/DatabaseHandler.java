package com.anandarherdianto.dinas.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.anandarherdianto.dinas.model.HistoryModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ananda R. Herdianto on 23/04/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final String TAG = "Database";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DinasPanganDB";

    // Table name
    private static final String TABLE_USER = "tUser";
    private static final String TABLE_HISTORY = "tHistory";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE "+TABLE_USER+" ( id INTEGER PRIMARY KEY, " +
                "user_id TEXT NOT NULL, " +
                "username TEXT NOT NULL, " +
                "email TEXT, " +
                "name TEXT NOT NULL, log_time TEXT NOT NULL)";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_HISTORY_TABLE = "CREATE TABLE "+TABLE_HISTORY+" ( history_id INTEGER PRIMARY KEY, " +
                "level1 INTEGER NOT NULL, level2 INTEGER NOT NULL, level3 INTEGER NOT NULL, " +
                "level4 INTEGER NOT NULL, level5 INTEGER NOT NULL, level6 INTEGER NOT NULL, " +
                "level7 INTEGER NOT NULL, level8 INTEGER NOT NULL, level9 INTEGER NOT NULL, " +
                "level10 INTEGER NOT NULL, target INTEGER NOT NULL, avg_level text NOT NULL, " +
                "n_dosage text NOT NULL, note text, date_check text NOT NULL )";
        db.execSQL(CREATE_HISTORY_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + "," + TABLE_HISTORY);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */


    //User Table CRUD
    //Inserting a user
    public void addUser(String user_id, String username, String email, String name, String log){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", user_id);
        values.put("username", username);
        values.put("email", email);
        values.put("name", name);
        values.put("log_time", log);

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into database");
    }

    //Getting user data
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("user_id", cursor.getString(1));
            user.put("username", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("name", cursor.getString(4));
            user.put("log", cursor.getString(5));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from database: " + user.toString());

        return user;
    }

    //Deleting user data
    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from database");
    }

    //Update user dara
    public void updateUser(String username, String name, String email, String userId){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues data=new ContentValues();
        data.put("username", username);
        data.put("name", name);
        data.put("email", email);
        db.update(TABLE_USER, data, "user_id=" + userId, null);
    }


    //History Table CRUD
    //Inserting a history
    public void addHistory(HistoryModel history){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("level1", history.getLevel1());
        values.put("level2", history.getLevel2());
        values.put("level3", history.getLevel3());
        values.put("level4", history.getLevel4());
        values.put("level5", history.getLevel5());
        values.put("level6", history.getLevel6());
        values.put("level7", history.getLevel7());
        values.put("level8", history.getLevel8());
        values.put("level9", history.getLevel9());
        values.put("level10", history.getLevel10());
        values.put("target", history.getTarget());
        values.put("avg_level", history.getAvg_level());
        values.put("n_dosage", history.getN_dosage());
        values.put("note", history.getNote());
        values.put("date_check", history.getDate_check());

        // Inserting Row
        db.insert(TABLE_HISTORY, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New history inserted into database");
    }

    // Getting single history
    public HistoryModel getHistory(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HISTORY, new String[] { "history_id",
                        "level1", "level2", "level3", "level4", "level5",
                        "level6", "level7", "level8", "level9", "level10",
                        "target", "avg_level", "n_dosage", "note",
                        "date_check" }, "history_id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        HistoryModel history = new HistoryModel(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)),
                Integer.parseInt(cursor.getString(8)),
                Integer.parseInt(cursor.getString(9)),
                Integer.parseInt(cursor.getString(10)),
                Integer.parseInt(cursor.getString(11)),
                cursor.getString(12),
                cursor.getString(13),
                cursor.getString(14),
                cursor.getString(15)
                );

        cursor.close();
        db.close();

        return history;
    }

    //Getting all history
    public List<HistoryModel> getAllHistory() {
        List<HistoryModel> historyList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HistoryModel history = new HistoryModel();
                history.setHistory_id(Integer.parseInt(cursor.getString(0)));
                history.setLevel1(Integer.parseInt(cursor.getString(1)));
                history.setLevel2(Integer.parseInt(cursor.getString(2)));
                history.setLevel3(Integer.parseInt(cursor.getString(3)));
                history.setLevel4(Integer.parseInt(cursor.getString(4)));
                history.setLevel5(Integer.parseInt(cursor.getString(5)));
                history.setLevel6(Integer.parseInt(cursor.getString(6)));
                history.setLevel7(Integer.parseInt(cursor.getString(7)));
                history.setLevel8(Integer.parseInt(cursor.getString(8)));
                history.setLevel9(Integer.parseInt(cursor.getString(9)));
                history.setLevel10(Integer.parseInt(cursor.getString(10)));
                history.setTarget(Integer.parseInt(cursor.getString(11)));
                history.setAvg_level(cursor.getString(12));
                history.setN_dosage(cursor.getString(13));
                history.setNote(cursor.getString(14));
                history.setDate_check(cursor.getString(15));

                // Adding history to list
                historyList.add(history);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return history list
        return historyList;
    }

    // Deleting single history
    public void deleteHistory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, "history_id" + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }



}
