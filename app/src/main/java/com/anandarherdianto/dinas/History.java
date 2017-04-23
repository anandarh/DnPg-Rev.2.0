package com.anandarherdianto.dinas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.anandarherdianto.dinas.model.HistoryModel;
import com.anandarherdianto.dinas.util.DatabaseHandler;

import java.util.List;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addHistory(new HistoryModel(3,3,3,3,3,3,3,3,3,3,8,"3","270","kuy","2012-01-02"));
        db.addHistory(new HistoryModel(3,3,3,3,3,3,3,3,3,3,8,"3","270","kay","2015-01-02"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<HistoryModel> history = db.getAllHistory();

        for (HistoryModel cn : history) {
            String log = "Id: " + cn.getHistory_id() + " ,Note: " + cn.getNote() + " ,Date: " + cn.getDate_check();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }
}
