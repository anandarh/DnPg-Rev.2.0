package com.anandarherdianto.dinas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Calendar;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anandarherdianto.dinas.util.CompressImage;
import com.anandarherdianto.dinas.util.SessionManager;
import com.anandarherdianto.dinas.util.CommunicationController;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static com.anandarherdianto.dinas.util.AppConfig.URL_UPLOAD_DOCUMENTATION;

public class AddDocumentationActivity extends AppCompatActivity {

    private String filePath, fileName, encoded_string, title,
            desc, lat, lon, location, village, temp, userId;
    private ImageView imgPreview;
    private ProgressDialog pDialog;
    private Bitmap imgSrc;
    private  EditText txtTitle, txtNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_documentation);

        ImageView back = (ImageView) findViewById(R.id.btnBack);
        ImageButton send = (ImageButton) findViewById(R.id.btnSend);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtNote = (EditText) findViewById(R.id.txtNote);
        TextView txtTemp = (TextView) findViewById(R.id.txtTemp);
        TextView txtLat = (TextView) findViewById(R.id.txtLat);
        TextView txtLon = (TextView) findViewById(R.id.txtLong);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);

        // Receiving the data from documentation activity
        Intent i = getIntent();

        // Session manager
        SessionManager session = new SessionManager(getApplicationContext());

        // image path that is captured in documentation activity
        filePath = i.getStringExtra("filePath");
        fileName = i.getStringExtra("fileName");
        lat = i.getStringExtra("latitude");
        lon = i.getStringExtra("longitude");
        location = i.getStringExtra("location");
        village = i.getStringExtra("village");
        userId = i.getStringExtra("user_id");

        temp = session.getTemp().substring(0, 2);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        if (filePath != null) {
            // Displaying the image on the screen
            previewMedia();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
            finish();
        }

        txtTemp.setText("Suhu : "+ temp +" \u2103");
        txtLat.setText("Lat : "+ lat.substring(0, 9));
        txtLon.setText("Long : "+ lon.substring(0, 10));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = txtTitle.getText().toString().trim();
                desc = txtNote.getText().toString().trim();

                // Check for empty data in the form
                if(title.isEmpty()){
                    // Prompt user to enter title
                    Toast.makeText(getApplicationContext(),
                            "Masukan judul terlebih dahulu!", Toast.LENGTH_LONG)
                            .show();
                }else if(desc.isEmpty()){
                    // Prompt user to enter title
                    Toast.makeText(getApplicationContext(),
                            "Masukan keterangan terlebih dahulu!", Toast.LENGTH_LONG)
                            .show();
                }else {

                    new EncodeImageFromGallery(imgSrc).execute();
                }

            }
        });
    }

    private void previewMedia(){

        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this,
                new String[] { filePath }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

        imgSrc = BitmapFactory.decodeFile(new CompressImage().compressImage(this, filePath, fileName, false));

        imgPreview.setImageBitmap(imgSrc);

    }

    private class EncodeImageFromGallery extends AsyncTask<Void, Void, Void> {
        Bitmap image;

        public EncodeImageFromGallery(Bitmap image){
            this.image = image;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            encoded_string = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            makeRequest();
        }
    }

    private void makeRequest() {

        // Tag used to cancel the request
        String tag_string_req = "req_upload";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        final String mdate = df.format(Calendar.getInstance().getTime());

        pDialog.setMessage("Uploading...");
        showDialog();

        StringRequest request = new StringRequest(Request.Method.POST, URL_UPLOAD_DOCUMENTATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response from server : ", response);
                        hideDialog();
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            // Check for error node in json
                            if (!error) {
                                // successfully upload
                                Toast.makeText(getApplicationContext(),
                                        "Upload Success!", Toast.LENGTH_LONG).show();
                                //session.setStatus("uploaded");
                                finish();

                            } else {
                                // Error on upload. Get the error message
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
                Toast.makeText(getApplicationContext(), "Foto gagal diupload!", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();

                map.put("encoded_string",encoded_string);
                map.put("title",title);
                map.put("description",desc);
                map.put("latitude",lat);
                map.put("longitude",lon);
                map.put("location",location);
                map.put("village",village);
                map.put("temp",temp);
                map.put("post_date",mdate);
                map.put("user_id",userId);

                return map;
            }
        };

        // Adding request to request queue
        // Max loading for upload 15 sec
        request.setRetryPolicy(new DefaultRetryPolicy(
                15*1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        CommunicationController.getInstance().addToRequestQueue(request, tag_string_req);

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
