package com.anandarherdianto.dinas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anandarherdianto.dinas.R;
import com.anandarherdianto.dinas.model.HistoryModel;
import com.anandarherdianto.dinas.util.DatabaseHandler;

public class DetailHistoryActivity extends AppCompatActivity {

    private TextView level1, level2, level3, level4, level5,
                     level6, level7, level8, level9, level10,
                     target, avgLevel, nDosage, note;

    private String history_id;

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        level1 = (TextView) findViewById(R.id.lblLevelImg1);
        level2 = (TextView) findViewById(R.id.lblLevelImg2);
        level3 = (TextView) findViewById(R.id.lblLevelImg3);
        level4 = (TextView) findViewById(R.id.lblLevelImg4);
        level5 = (TextView) findViewById(R.id.lblLevelImg5);
        level6 = (TextView) findViewById(R.id.lblLevelImg6);
        level7 = (TextView) findViewById(R.id.lblLevelImg7);
        level8 = (TextView) findViewById(R.id.lblLevelImg8);
        level9 = (TextView) findViewById(R.id.lblLevelImg9);
        level10 = (TextView) findViewById(R.id.lblLevelImg10);
        target = (TextView) findViewById(R.id.lblTarget);
        avgLevel = (TextView) findViewById(R.id.lblLevelRata);
        nDosage = (TextView) findViewById(R.id.lblRekUrea);
        note = (TextView) findViewById(R.id.tNote);
        back = (Button) findViewById(R.id.btnHistoryBack);

        history_id = this.getIntent().getStringExtra("history_id");

        DatabaseHandler db = new DatabaseHandler(this);

        HistoryModel hm = db.getHistory(Integer.parseInt(history_id));

        level1.setText(String.valueOf(hm.getLevel1()));
        level2.setText(String.valueOf(hm.getLevel2()));
        level3.setText(String.valueOf(hm.getLevel3()));
        level4.setText(String.valueOf(hm.getLevel4()));
        level5.setText(String.valueOf(hm.getLevel5()));
        level6.setText(String.valueOf(hm.getLevel6()));
        level7.setText(String.valueOf(hm.getLevel7()));
        level8.setText(String.valueOf(hm.getLevel8()));
        level9.setText(String.valueOf(hm.getLevel9()));
        level10.setText(String.valueOf(hm.getLevel10()));
        target.setText(String.valueOf(hm.getTarget()));
        avgLevel.setText(hm.getAvg_level());
        nDosage.setText(hm.getN_dosage());
        note.setText(hm.getNote());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
