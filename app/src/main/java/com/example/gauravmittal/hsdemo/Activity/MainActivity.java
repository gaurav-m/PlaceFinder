package com.example.gauravmittal.hsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Layout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gauravmittal.hsdemo.R;
import com.example.gauravmittal.hsdemo.location.DeviceLocation;
import com.example.gauravmittal.hsdemo.map.MapScreenFragment;
import com.example.gauravmittal.hsdemo.places.PlaceType;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MapScreenFragment mapScreenFragment;
    View actionPalette;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(fabClickListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addMapScreenFragment();
        setupActionPalette();
    }

    private void addMapScreenFragment() {
        mapScreenFragment = MapScreenFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, mapScreenFragment,
                        MapScreenFragment.TAG).commitAllowingStateLoss();
    }

    public void setupActionPalette()
    {
        actionPalette = findViewById(R.id.action);

        EditText radius = (EditText) actionPalette.findViewById(R.id.txt_radius);
        radius.setSelection(1);
        actionPalette.findViewById(R.id.btn_food).setOnClickListener(categoryButtonListener);
        actionPalette.findViewById(R.id.btn_school).setOnClickListener(categoryButtonListener);
        actionPalette.findViewById(R.id.btn_spa).setOnClickListener(categoryButtonListener);
        actionPalette.findViewById(R.id.btn_gym).setOnClickListener(categoryButtonListener);
        actionPalette.findViewById(R.id.btn_hospital).setOnClickListener(categoryButtonListener);
        actionPalette.findViewById(R.id.btn_restaurant).setOnClickListener(categoryButtonListener);
        actionPalette.setVisibility(View.INVISIBLE);
    }

    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            toggleActionPalette();
        }
    };

    private void toggleActionPalette()
    {
        if (actionPalette == null)
        {
            setupActionPalette();
        }

        if (actionPalette.getVisibility() == View.VISIBLE)
        {
            Animation slideDown = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.slide_down);
            slideDown.setDuration(400);
            slideDown.setFillBefore(true);
            slideDown.setFillEnabled(true);
            slideDown.setFillAfter(true);
            actionPalette.setVisibility(View.INVISIBLE);
            actionPalette.setAnimation(slideDown);
            slideDown.start();

        }
        else
        {
            Animation slideUp = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.slide_up);
            slideUp.setDuration(400);
            slideUp.setFillAfter(true);
            slideUp.setFillEnabled(true);
            slideUp.setFillBefore(true);
            actionPalette.setVisibility(View.VISIBLE);
            actionPalette.setAnimation(slideUp);
            slideUp.start();
        }
    }

    View.OnClickListener categoryButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int radiusInMeter = Integer.parseInt(((TextView) findViewById(R.id.txt_radius)).getText().toString()) * 1000;
            if (radiusInMeter <= 0)
            {
                Toast.makeText(MainActivity.this, "Enter positive radius to continue", Toast.LENGTH_SHORT).show();
                return;
            }

            PlaceType pt = null;
            switch(view.getId())
            {
                case R.id.btn_food:
                    pt = PlaceType.FOOD;
                    break;
                case R.id.btn_gym:
                    pt = PlaceType.GYM;
                    break;
                case R.id.btn_spa:
                    pt = PlaceType.SPA;
                    break;
                case R.id.btn_hospital:
                    pt = PlaceType.HOSPITAL;
                    break;
                case R.id.btn_school:
                    pt = PlaceType.SCHOOL;
                    break;
                case R.id.btn_restaurant:
                    pt = PlaceType.RESTAURANT;
                    break;

            }

            DeviceLocation location = mapScreenFragment.getSelectedLocation();

            Intent i = new Intent(MainActivity.this, PlacesListActivity.class);
            i.putExtra("location",location);
            i.putExtra("type",pt.name());
            i.putExtra("radius",radiusInMeter);
            MainActivity.this.startActivity(i);

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bookmarks)
        {
            Intent i = new Intent(MainActivity.this, PlacesListActivity.class);
            i.putExtra("bookmarks",true);
            MainActivity.this.startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
