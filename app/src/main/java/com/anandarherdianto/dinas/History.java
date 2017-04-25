package com.anandarherdianto.dinas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.anandarherdianto.dinas.helper.ListAdapterHistory;
import com.anandarherdianto.dinas.model.HistoryModel;
import com.anandarherdianto.dinas.model.LHistoryModel;
import com.anandarherdianto.dinas.util.DatabaseHandler;
import com.anandarherdianto.dinas.util.DetailHistoryActivity;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private List<LHistoryModel> history = new ArrayList<LHistoryModel>();
    private ListView listView;
    private ListAdapterHistory adapterHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listHistory);
        adapterHistory = new ListAdapterHistory(this, history);
        listView.setAdapter(adapterHistory);

        DatabaseHandler db = new DatabaseHandler(this);

        // Reading all history
        Log.d("Reading: ", "Reading all history..");
        List<HistoryModel> historyS = db.getAllHistory();
        for(HistoryModel hm : historyS) {


            LHistoryModel historyModels = new LHistoryModel();

            historyModels.setHistory_id(hm.getHistory_id());
            historyModels.setAvgLevel(hm.getAvg_level());
            historyModels.setNote(hm.getNote());
            historyModels.setDate(hm.getDate_check());

            history.add(historyModels);

            String log = "Id: "+hm.getHistory_id()+" ,Name: " + hm.getAvg_level() + " ,Phone: " + hm.getNote();
            // Writing Contacts to log
            Log.d("Name: ", log);

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String history_id = String.valueOf(history.get(position).getHistory_id());

                Intent i = new Intent(History.this, DetailHistoryActivity.class);
                i.putExtra("history_id", history_id);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
