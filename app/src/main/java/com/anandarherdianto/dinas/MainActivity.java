package com.anandarherdianto.dinas;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anandarherdianto.dinas.util.AppConfig;
import com.anandarherdianto.dinas.util.CommunicationController;
import com.anandarherdianto.dinas.util.DatabaseHandler;
import com.anandarherdianto.dinas.util.GPSTracker;
import com.anandarherdianto.dinas.util.SessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.anandarherdianto.dinas.util.AppConfig.APP_ID;
import static com.anandarherdianto.dinas.util.AppConfig.URL_API_WEATHER;
import static com.anandarherdianto.dinas.util.AppConfig.URL_PROFILE_IMAGE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RelativeLayout bg1, bg2, bg3, bg4;

    private int position;

    private double latitude, longitude;

    private TextView hLocation;

    private SessionManager session;

    private DatabaseHandler db;

    private GPSTracker gps;

    private Handler handler;

    private String loc;

    private ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View view = findViewById(R.id.root_view);

        position = 1;

        bg1 = (RelativeLayout) findViewById(R.id.bg1);
        bg2 = (RelativeLayout) findViewById(R.id.bg2);
        bg3 = (RelativeLayout) findViewById(R.id.bg3);
        bg4 = (RelativeLayout) findViewById(R.id.bg4);

        bg1.setVisibility(View.GONE);
        bg2.setVisibility(View.GONE);
        bg3.setVisibility(View.GONE);
        bg4.setVisibility(View.GONE);

        createFolder();

        // SqLite database handler
        db = new DatabaseHandler(getApplicationContext());

        // Create the Handler object
        handler = new Handler();

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from database
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        String userId = user.get("user_id");


        Snackbar.make(view, "Selamat Bekerja " + name, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        imgProfile = (ImageView) header.findViewById(R.id.imgProfile);
        TextView hName = (TextView) header.findViewById(R.id.hName);
        hLocation = (TextView) header.findViewById(R.id.hLocation);

        hName.setText(name);

        getGPS();

        getProfileImage(userId);

        // Call handler for change image
        handler.post(changeImage);

        // Call handler for update temperature
        handler.post(updateTemp);

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(changeImage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(changeImage);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dokumentasi) {
            Intent intent = new Intent(this, DocumentationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_analisis) {
            Intent intent = new Intent(this, NitrogenActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_riwayat) {
            Intent intent = new Intent(this, History.class);
            startActivity(intent);
        } else if (id == R.id.nav_akun) {
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_bantuan) {

        } else if (id == R.id.nav_logout) {
            logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Handle background image animation
    // The background image will alternate according to the time specified.
    private final Runnable changeImage = new Runnable() {

        @Override
        public void run() {

            TranslateAnimation ta = new TranslateAnimation(0.0f, 0.0f, 0.0f, 1500.0f);
            ta.setDuration(1000);
            ta.setFillAfter(false);

            if (position == 1) {
                bg4.startAnimation(ta);
                bg4.setVisibility(View.GONE);

                bg1.setVisibility(View.VISIBLE);
                bg1.setAlpha(0.0f);
                bg1.animate().alpha(1.0f).setDuration(1000);

                bg2.setVisibility(View.GONE);
                bg3.setVisibility(View.GONE);
                position = 2;
            } else if (position == 2) {
                bg1.startAnimation(ta);
                bg1.setVisibility(View.GONE);

                bg2.setVisibility(View.VISIBLE);
                bg2.setAlpha(0.0f);
                bg2.animate().alpha(1.0f).setDuration(1000);

                bg3.setVisibility(View.GONE);
                bg4.setVisibility(View.GONE);
                position = 3;
            } else if (position == 3) {
                bg2.startAnimation(ta);
                bg2.setVisibility(View.GONE);

                bg3.setVisibility(View.VISIBLE);
                bg3.setAlpha(0.0f);
                bg3.animate().alpha(1.0f).setDuration(1000);

                bg1.setVisibility(View.GONE);
                bg4.setVisibility(View.GONE);
                position = 4;
            } else {
                bg3.startAnimation(ta);
                bg3.setVisibility(View.GONE);

                bg4.setVisibility(View.VISIBLE);
                bg4.setAlpha(0.0f);
                bg4.animate().alpha(1.0f).setDuration(1000);

                bg1.setVisibility(View.GONE);
                bg2.setVisibility(View.GONE);
                position = 1;
            }

            handler.postDelayed(changeImage, 3000);
        }
    };


    // Logging out the user. Will set isLoggedIn flag to false in shared
    // preferences and clears the user data from user table in database
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUser();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Get Coordinate from GPS
    private void getGPS(){

            // call class object
            gps = new GPSTracker(MainActivity.this);

            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

            List<Address> addresses = null;

            // check if GPS enabled
            if (gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                Log.d("Coordinate", "Latitude = " + latitude + " Longitude = " + longitude);

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException ioException) {
                    // Catch network or other I/O problems.
                    //Toast.makeText(getApplicationContext(), "Error1", Toast.LENGTH_LONG).show();
                } catch (IllegalArgumentException illegalArgumentException) {
                    // Catch invalid latitude or longitude values.
                    //Toast.makeText(getApplicationContext(), "Error2", Toast.LENGTH_LONG).show();
                }

                if (addresses == null || addresses.size() == 0) {
                    hLocation.setText("");
                } else {
                    loc = addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
                    hLocation.setText(loc);
                }

            } else {
                hLocation.setText("");
            }

    }

    // Create folder for handle images
    private void createFolder() {
        String nfile = ".nomedia";
        String extr = Environment.getExternalStorageDirectory().toString();
        File mFolder = new File(extr + "/Dinas");
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }

        File folder1 = new File(mFolder + File.separator + "Documentation");
        File folder2 = new File(mFolder + File.separator + "Nitrogen");

        if (!folder1.exists()) {
            folder1.mkdir();
        }

        if (!folder2.exists()) {
            folder2.mkdir();
        }

        File nomedia = new File(folder2 + File.separator + nfile);
        try {
            nomedia.createNewFile();
        } catch (IOException e) {
            Log.e("Create", "Create file error : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Get temperature from OpenWeatherMap
    // Update every 5 minutes
    private final Runnable updateTemp = new Runnable() {
        @Override
        public void run() {

            String tag_string_req = "req_weather";

            String url = URL_API_WEATHER + "lat=" + latitude + "&lon=" + longitude + "&appid=" + APP_ID;

            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("Weather", "Weather Response: " + response.toString());

                    try {
                        JSONObject jObj = new JSONObject(response);

                        JSONObject weather = jObj.getJSONObject("main");
                        String temp = weather.getString("temp");

                        double celcius = Double.parseDouble(temp) - 273.15;

                        session.setTemp(String.valueOf(celcius));

                        Log.d("Temperature", "Temp = " + celcius);

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
            });

            // Adding request to request queue
            CommunicationController.getInstance().addToRequestQueue(strReq, tag_string_req);

            handler.postDelayed(updateTemp, 60 * 1000 * 10);
        }
    };

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
                    Log.d("PROFILE_IMAGE", "Server Response: " + urlProfileImage);

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

}
