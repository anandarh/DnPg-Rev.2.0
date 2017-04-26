package com.anandarherdianto.dinas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private DateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername   = (EditText) findViewById(R.id.userName);
        inputPassword   = (EditText) findViewById(R.id.password);
        btnLogin        = (Button) findViewById(R.id.btnLogin);

        // Progress dialog
        pDialog = new ProgressDialog(this);
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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!username.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(username, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Username dan Password harus diisi!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

    }

    private void checkLogin(final String username, final String password) {

        String tag_string_req = "req_login";

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
                        String username = user.getString("username");
                        String finger_id = user.getString("finger_id");
                        String name = user.getString("name");

                        // Inserting row in users table
                        db.addUser(username, finger_id, name, log);

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
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Connection Error: " + ">>" + error.networkResponse.statusCode
                        + ">>" + Arrays.toString(error.networkResponse.data)
                        + ">>" + error.getCause()
                        + ">>" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error: " + ">>" + error.networkResponse.statusCode
                                + ">>" + Arrays.toString(error.networkResponse.data)
                                + ">>" + error.getCause()
                                + ">>" +error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){

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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
