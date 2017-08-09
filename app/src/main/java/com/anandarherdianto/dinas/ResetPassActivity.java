package com.anandarherdianto.dinas;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anandarherdianto.dinas.helper.MyStatusDialogBox;
import com.anandarherdianto.dinas.util.AppConfig;
import com.anandarherdianto.dinas.util.CommunicationController;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassActivity extends AppCompatActivity implements MyStatusDialogBox.MyInterface{

    private EditText email;

    private MyStatusDialogBox myStatusDialogBox;

    private static final String TAG = "ResetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        TextView lblReset = (TextView) findViewById(R.id.lblReset);
        TextView txtInfo = (TextView) findViewById(R.id.txtInfoReset);
        email = (EditText) findViewById(R.id.inputReset);
        Button btnReset = (Button) findViewById(R.id.btnReset);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Niconne-Regular.ttf");
        lblReset.setTypeface(myFont);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myStatusDialogBox = new MyStatusDialogBox();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uEmail = email.getText().toString().trim();
                reset(uEmail);
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

    public void reset(final String uEmail) {
        Log.d(TAG, "Reset Password");
        String tag_string_req = "req_reset";

        if (!validate()) {

        } else {


            final ProgressDialog progressDialog = new ProgressDialog(ResetPassActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Mohon Tunggu...");
            progressDialog.show();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_RESET, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Reset Response: " + response);
                    progressDialog.dismiss();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (!error) {

                            String responseMsg = jObj.getString("message");
                            myStatusDialogBox.setMessage(responseMsg);
                            myStatusDialogBox.setType("success");
                            myStatusDialogBox.show(getSupportFragmentManager(),"ResetSuccess");

                        } else {
                            // Error in reset password. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            myStatusDialogBox.setMessage(errorMsg);
                            myStatusDialogBox.setType("error");
                            myStatusDialogBox.show(getSupportFragmentManager(),"ResetFailed");
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
                    progressDialog.dismiss();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();
                    params.put("email", uEmail);

                    return params;
                }
            };

            // Adding request to request queue
            CommunicationController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }
    }

    public boolean validate() {

        boolean valid = true;

        String uEmail = email.getText().toString().trim();

        if(uEmail.isEmpty()){
            email.setError("Masukkan alamat email anda!");
            valid = false;
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()) {
            email.setError("Alamat email tidak valid");
            valid = false;
        } else {
            email.setError(null);
        }

        return valid;
    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onError() {
        email.setText(null);
        email.requestFocus();
    }
}
