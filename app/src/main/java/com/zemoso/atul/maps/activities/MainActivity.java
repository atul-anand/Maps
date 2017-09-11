package com.zemoso.atul.maps.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zemoso.atul.maps.R;
import com.zemoso.atul.maps.fragments.FlightPlan;
import com.zemoso.atul.maps.fragments.MyProfile;
import com.zemoso.atul.maps.fragments.Notifications;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SharedPreferences.Editor mEditor;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(view);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
        mNavigationView.bringToFront();
        mDrawerLayout.requestLayout();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragments,
                FlightPlan.newInstance()).addToBackStack("flightPlan").commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (onNavigationItemSelected(item))
            return true;
        switch (item.getItemId()) {
            case R.id.main_logout:
                mEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                mEditor.putString("access_token", "");
                mEditor.putString("refresh_token", "");
                mEditor.apply();
                logout();
                return true;
            default:
                return true;
        }
    }

    private void logout() {
        RealmConfiguration realmConfiguration = Realm.getDefaultConfiguration();
        Realm.deleteRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);
        finish();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "onPrepareOptionsMenu");
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mNavigationView);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
//        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragments,
                        MyProfile.newInstance()).addToBackStack("myProfile").commit();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.nav_profile)
                        , Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_notifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragments,
                        Notifications.newInstance()).addToBackStack("notifications").commit();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.nav_notifications)
                        , Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_live_view:
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.nav_live_view)
                        , Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_flight_plan:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragments,
                        FlightPlan.newInstance()).addToBackStack("flightPlan").commit();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.nav_flight_plan)
                        , Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_flight_review:
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.nav_flight_review)
                        , Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_equipment:
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.nav_equipment)
                        , Toast.LENGTH_SHORT).show();
                break;
            default:
                return false;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
//        else
//            super.onBackPressed();
    }
}
