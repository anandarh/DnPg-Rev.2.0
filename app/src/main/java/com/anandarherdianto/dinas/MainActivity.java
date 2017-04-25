package com.anandarherdianto.dinas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RelativeLayout bg1, bg2, bg3, bg4;
    private int position;

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

        Snackbar.make(view, "Selamat Bekerja", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new bgSlide(), 3000, 5000);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            // Handle the camera action
        } else if (id == R.id.nav_analisis) {
            Intent intent = new Intent(this, NitrogenActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_riwayat) {
            Intent intent = new Intent(this, History.class);
            startActivity(intent);
        } else if (id == R.id.nav_akun) {

        } else if (id == R.id.nav_bantuan) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class bgSlide extends TimerTask{

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    TranslateAnimation ta = new TranslateAnimation(0.0f, 0.0f, 0.0f, 1500.0f);
                    ta.setDuration(1000);
                    ta.setFillAfter(false);

                    if(position == 1){
                        bg4.startAnimation(ta);
                        bg4.setVisibility(View.GONE);

                        bg1.setVisibility(View.VISIBLE);
                        bg1.setAlpha(0.0f);
                        bg1.animate().alpha(1.0f).setDuration(1000);

                        bg2.setVisibility(View.GONE);
                        bg3.setVisibility(View.GONE);
                        position = 2;
                    }else  if(position == 2){
                        bg1.startAnimation(ta);
                        bg1.setVisibility(View.GONE);

                        bg2.setVisibility(View.VISIBLE);
                        bg2.setAlpha(0.0f);
                        bg2.animate().alpha(1.0f).setDuration(1000);

                        bg3.setVisibility(View.GONE);
                        bg4.setVisibility(View.GONE);
                        position = 3;
                    }else if(position == 3){
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

                }
            });

        }
    }
}
