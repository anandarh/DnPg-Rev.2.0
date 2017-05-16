package com.anandarherdianto.dinas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AddDocumentationActivity extends AppCompatActivity {

    EditText txtTitle, txtNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_documentation);

        ImageView back = (ImageView) findViewById(R.id.btnBack);
        ImageButton send = (ImageButton) findViewById(R.id.btnSend);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtNote = (EditText) findViewById(R.id.txtNote);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Okelah", Toast.LENGTH_LONG).show();
            }
        });



    }
}
