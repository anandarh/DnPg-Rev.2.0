package com.anandarherdianto.dinas;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anandarherdianto.dinas.util.AppConfig;
import com.anandarherdianto.dinas.util.CommunicationController;
import com.anandarherdianto.dinas.util.DatabaseHandler;
import com.anandarherdianto.dinas.util.FButton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.anandarherdianto.dinas.util.AppConfig.URL_PROFILE_IMAGE;

public class AccountActivity extends AppCompatActivity {

    private DatabaseHandler db;

    private HashMap<String, String> user;

    private EditText txtUsername, txtEmail, txtOldPass, txtNewPass, txtConfirmPass;

    private TextView txtName;

    private FButton btnSave;

    private String username, userId, names, email;

    private Boolean passChange, uNameChange, nameChange, emailChange;

    private ProgressDialog pDialog;

    private CircleImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SqLite database handler
        db = new DatabaseHandler(getApplicationContext());

        // Fetching user details from database
        user = db.getUserDetails();
        username = user.get("username");
        userId = user.get("user_id");
        names = user.get("name");
        email = user.get("email");

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        txtName = (TextView)findViewById(R.id.txtName);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtOldPass = (EditText)findViewById(R.id.txtOldPassword);
        txtNewPass = (EditText)findViewById(R.id.txtNewPassword);
        txtConfirmPass = (EditText)findViewById(R.id.txtCNewPassword);
        btnSave = (FButton)findViewById(R.id.btnSave);
        imgProfile = (CircleImageView)findViewById(R.id.imgProfile);

        txtUsername.setText(username);
        txtName.setText(names);
        txtEmail.setText(email);

        passChange = false;
        uNameChange = false;
        nameChange = false;
        emailChange = false;

        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AccountActivity.this);
                alert.setTitle("Ubah Nama");
                alert.setMessage("\nMasukan nama anda : ");

                final EditText input = new EditText(AccountActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                input.setSingleLine(true);
                input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(25)});
                input.setVerticalScrollBarEnabled(true);
                input.setMovementMethod(ScrollingMovementMethod.getInstance());
                input.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                input.setText(txtName.getText());

                alert.setView(input);
                alert.setIcon(R.drawable.ic_edit_black_24dp);

                alert.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String str = input.getEditableText().toString();

                        if(!str.trim().isEmpty()) {
                            txtName.setText(str);
                        }else{
                            dialog.cancel();
                        }

                    }
                });
                alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });

        getProfileImage(userId);

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

        String name = txtName.getText().toString().trim();
        String uName = txtUsername.getText().toString().trim();
        String uEmail = txtEmail.getText().toString().trim();
        String oldPass = txtOldPass.getText().toString().trim();
        String newPass = txtNewPass.getText().toString().trim();
        String confirmPass = txtConfirmPass.getText().toString().trim();

        if(!newPass.isEmpty()) {
            if(newPass.length() < 6){
                txtNewPass.setError("password harus 6 karakter atau lebih");
                txtNewPass.requestFocus();

                passChange = false;
            }else {
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
            }

        }else{
            passChange = false;
        }

        if(!uName.equals(username)){
            if(!uName.isEmpty()){
                if(uName.matches("[A-Za-z0-9_]+")){
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
                    txtUsername.setError("Username hanya boleh berupa huruf, angka dan _");
                    txtUsername.requestFocus();

                    uNameChange = false;
                }

            }else {
                txtUsername.setText(username);
                uNameChange = false;
            }
        }else {
            uNameChange = false;
        }

        if(!name.equals(names)){
            if(!name.isEmpty()){
                if(oldPass.isEmpty()){
                    Toast.makeText(this, "Password Lama harus diisi!", Toast.LENGTH_LONG).show();
                    txtOldPass.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtOldPass, InputMethodManager.SHOW_IMPLICIT);

                    nameChange = false;
                }else {
                    Log.d("ACCOUNT_TAG", "Name has been changed. "+names);
                    nameChange = true;
                }
            }else {
                nameChange = false;
            }
        }else {
            nameChange = false;
        }

        if(!uEmail.equals(email)){
            if(!uEmail.isEmpty()){
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()){
                    txtEmail.setError("Alamat email tidak valid");
                    txtEmail.requestFocus();

                    emailChange = false;
                }else {
                    if(oldPass.isEmpty()){
                        Toast.makeText(this, "Password Lama harus diisi!", Toast.LENGTH_LONG).show();
                        txtOldPass.requestFocus();
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(txtOldPass, InputMethodManager.SHOW_IMPLICIT);

                        emailChange = false;
                    }else {
                        Log.d("ACCOUNT_TAG", "Email has been changed. ");
                        emailChange = true;
                    }
                }

            }else {
                txtEmail.setText(email);
                emailChange = false;
            }
        }else {
            emailChange = false;
        }

        if((uNameChange.equals(true) || nameChange.equals(true) || emailChange.equals(true)) && passChange.equals(false)){
           update2(uName, oldPass, name, uEmail);
        }else if(passChange.equals(true)){
            updateAll(uName, oldPass, name, uEmail, newPass);
        }else {
            //Toast.makeText(this, "Tidak ada  yang diubah", Toast.LENGTH_LONG).show();
        }
    }

    private void updateAll(final String username, final String oldPassword, final String name, final String email, final String newPassword) {

        String tag_string_req = "req_updateAccount";

        pDialog.setMessage("Loading...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE_ALL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ACCOUNT_TAG", "Update Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        Log.d("ACCOUNT_TAG", "Update success.");
                        Toast.makeText(getApplicationContext(),
                                "Perubahan akun berhasil.", Toast.LENGTH_LONG).show();

                        db.updateUser(username, name, email, userId);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("Error in JSON:", e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat menghubungi server!", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("userpass", oldPassword);
                params.put("newpass", newPassword);
                params.put("name", name);
                params.put("name", email);
                params.put("user_id", userId);

                return params;
            }
        };

        // Adding request to request queue
        CommunicationController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void update2(final String username, final String oldPassword, final String name, final String email) {

        String tag_string_req = "req_updateAccount";

        pDialog.setMessage("Loading...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE_ALL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("ACCOUNT_TAG", "Update Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        Log.d("ACCOUNT_TAG", "Update success.");
                        Toast.makeText(getApplicationContext(),
                                "Perubahan akun berhasil.", Toast.LENGTH_LONG).show();

                        db.updateUser(username, name, email, userId);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("Error in JSON:", e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat menghubungi server!", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("userpass", oldPassword);
                params.put("name", name);
                params.put("email", email);
                params.put("user_id", userId);

                return params;
            }
        };

        // Adding request to request queue
        CommunicationController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void getProfileImage(final String userId){
        String tag_string_req = "req_profile";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_PROFILE_IMAGE+"/"+userId, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("PROFILE_IMAGE", "Server Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);

                    String urlProfileImage = jObj.getString("profile_image");

                    Glide.with(getApplicationContext()).load(urlProfileImage).into(imgProfile);

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e("Error in JSON:", e.getMessage());
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat menghubungi server!", Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        CommunicationController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
