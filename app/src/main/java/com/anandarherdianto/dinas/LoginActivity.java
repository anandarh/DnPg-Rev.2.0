package com.anandarherdianto.dinas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anandarherdianto.dinas.util.AppConfig;
import com.anandarherdianto.dinas.util.CommunicationController;
import com.anandarherdianto.dinas.util.DatabaseHandler;
import com.anandarherdianto.dinas.util.SessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";

    private Button btnLogin;
    private EditText inputUsername;
    private EditText inputPassword;

    private ProgressDialog pDialog;

    private SessionManager session;

    private DatabaseHandler db;

    private HashMap<String, String> user;

    private DateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername   = (EditText) findViewById(R.id.userName);
        inputPassword   = (EditText) findViewById(R.id.password);
        btnLogin        = (Button) findViewById(R.id.btnLogin);

        // Progress dialog
        pDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new DatabaseHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Format Date
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            checkUpdate();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // login user
                checkLogin(username, password);

            }

        });

        TextView resPass = (TextView)findViewById(R.id.txtResetPass);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Courgette-Regular.ttf");
        resPass.setTypeface(myFont);

        resPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ResetPassActivity.class);
                startActivity(i);
            }
        });

    }

    private void checkLogin(final String username, final String password) {

        String tag_string_req = "req_login";

        if (!validate()) {

        } else {

            final String log = df.format(Calendar.getInstance().getTime());

            pDialog.setMessage("Logging in ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_LOGIN, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Login Response: " + response.toString());
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (!error) {
                            // User successfully logged in
                            // Create login session
                            session.setLogin(true);

                            // Store the user to database
                            JSONObject user = jObj.getJSONObject("user");
                            String userId = user.getString("user_id");
                            String username = user.getString("username");
                            String email = user.getString("email");
                            String name = user.getString("name");

                            // Inserting row in users table
                            db.addUser(userId, username, email, name, log);

                            // Launch main activity
                            Intent intent = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
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
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat menghubungi server!", Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("userpass", password);
                    params.put("log", log);

                    return params;
                }
            };

            // Adding request to request queue
            CommunicationController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }

    }

    private void checkUpdate(){
        // Fetching user details from database
        user = db.getUserDetails();
        final String userId = user.get("user_id");

        String tag_string_req = "req_update";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE_CHECK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        // Update the user to database
                        JSONObject user = jObj.getJSONObject("user");
                        String userId = user.getString("user_id");
                        String username = user.getString("username");
                        String email = user.getString("email");
                        String name = user.getString("name");

                        // Updating row in users table
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
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat menghubungi server!", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);

                return params;
            }
        };

        // Adding request to request queue
        CommunicationController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public boolean validate() {

        boolean valid = true;

        String user = inputUsername.getText().toString().trim();
        String pass = inputPassword.getText().toString().trim();

        if(user.isEmpty() && pass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Username dan Password harus diisi!", Toast.LENGTH_LONG).show();
            inputUsername.requestFocus();
            valid = false;
        }else {

            if (user.isEmpty()) {
                inputUsername.setError("Masukan username anda!");
                inputUsername.requestFocus();
                valid = false;
            } else if (!user.matches("[A-Za-z0-9_]+")) {
                inputUsername.setError("Username hanya boleh berupa huruf, angka dan _");
                inputUsername.requestFocus();
                valid = false;
            } else {
                inputUsername.setError(null);
            }

            if (pass.isEmpty() || pass.length() < 6) {
                inputPassword.setError("password harus 6 karakter atau lebih");
                inputPassword.requestFocus();
                valid = false;
            } else {
                inputPassword.setError(null);
            }

        }

        return valid;
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
