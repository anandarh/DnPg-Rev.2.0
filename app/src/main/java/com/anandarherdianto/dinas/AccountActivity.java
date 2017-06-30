package com.anandarherdianto.dinas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anandarherdianto.dinas.util.DatabaseHandler;
import com.anandarherdianto.dinas.util.FButton;

import java.util.HashMap;

public class AccountActivity extends AppCompatActivity {

    private DatabaseHandler db;

    private EditText txtUsername,txtOldPass, txtNewPass, txtConfirmPass;

    private FButton btnSave;

    private String username;

    private Boolean passChange, uNameChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SqLite database handler
        db = new DatabaseHandler(getApplicationContext());

        // Fetching user details from database
        HashMap<String, String> user = db.getUserDetails();
        username = user.get("username");

        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtOldPass = (EditText)findViewById(R.id.txtOldPassword);
        txtNewPass = (EditText)findViewById(R.id.txtNewPassword);
        txtConfirmPass = (EditText)findViewById(R.id.txtCNewPassword);
        btnSave = (FButton)findViewById(R.id.btnSave);

        txtUsername.setText(username);

        passChange = false;
        uNameChange = false;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateAccount(){

        String uName = txtUsername.getText().toString().trim();
        String oldPass = txtOldPass.getText().toString().trim();
        String newPass = txtNewPass.getText().toString().trim();
        String confirmPass = txtConfirmPass.getText().toString().trim();

        if(!newPass.isEmpty()) {
            if(!confirmPass.isEmpty()) {
                if (newPass.equals(confirmPass)) {
                    if(oldPass.isEmpty()){
                        Toast.makeText(this, "Password Lama harus diisi!", Toast.LENGTH_LONG).show();
                        txtOldPass.requestFocus();
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(txtOldPass, InputMethodManager.SHOW_IMPLICIT);

                        passChange = false;
                    }else {
                        Log.d("ACCOUNT_TAG", "Password has been changed.");
                        passChange = true;
                    }
                }else {
                    Toast.makeText(this, "Password Baru dengan Konfirmasi Password tidak sesuai!", Toast.LENGTH_LONG).show();
                    txtConfirmPass.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtConfirmPass, InputMethodManager.SHOW_IMPLICIT);

                    passChange = false;
                }
            }else {
                Toast.makeText(this, "Konfirmasi Password harus diisi!", Toast.LENGTH_LONG).show();
                txtConfirmPass.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(txtConfirmPass, InputMethodManager.SHOW_IMPLICIT);

                passChange = false;
            }
        }else{
            passChange = false;
        }

        if(!uName.equals(username)){
            if(!uName.isEmpty()){
                if(oldPass.isEmpty()){
                    Toast.makeText(this, "Password Lama harus diisi!", Toast.LENGTH_LONG).show();
                    txtOldPass.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtOldPass, InputMethodManager.SHOW_IMPLICIT);

                    uNameChange = false;
                }else {
                    Log.d("ACCOUNT_TAG", "Username has been changed.");
                    uNameChange = true;
                }
            }else {
                txtUsername.setText(username);
                uNameChange = false;
            }
        }else {
            uNameChange = false;
        }

        if(uNameChange.equals(true) && passChange.equals(true)){
            Toast.makeText(this, "Username dan Password berhasil diubah", Toast.LENGTH_LONG).show();
        }else if(uNameChange.equals(true)){
            Toast.makeText(this, "Username berhasil diubah", Toast.LENGTH_LONG).show();
        }else if(passChange.equals(true)){
            Toast.makeText(this, "Password berhasil diubah", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Tidak ada  yang diubah", Toast.LENGTH_LONG).show();
        }
    }
}
