package com.mcssoft.racemeetings.ii.activity;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mcssoft.racemeetings.ii.R;
import com.mcssoft.racemeetings.ii.database.DatabaseOperations;
import com.mcssoft.racemeetings.ii.database.SchemaConstants;
import com.mcssoft.racemeetings.ii.dialog.DeleteDialog;
import com.mcssoft.racemeetings.ii.dialog.NetworkDialog;
import com.mcssoft.racemeetings.ii.fragment.DateSelectFragment;
import com.mcssoft.racemeetings.ii.fragment.MeetingsFragment;
import com.mcssoft.racemeetings.ii.network.DownloadRequestQueue;
import com.mcssoft.racemeetings.ii.network.NetworkReceiver;
import com.mcssoft.racemeetings.ii.utility.DateTime;
import com.mcssoft.racemeetings.ii.utility.Preferences;
import com.mcssoft.racemeetings.ii.utility.Resources;


public class MeetingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        initialiseBaseUI();
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Navigation">
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overflow_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_preferences:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.id_menu_delete:
                showDeleteDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.id_nav_menu_show_all) {
            Bundle bundle = new Bundle();
            bundle.putString("meetings_show_all_key", null);
            loadMeetingsFragment(bundle);
        }
        else if (id == R.id.id_nav_menu_races_today) {
            // Get today's date and set bundle args.
            DateTime dt = new DateTime();
            String today = dt.getCurrentDateYearFirst();
            Bundle bundle = new Bundle();
            bundle.putString("meetings_show_day_key", today);

            // Check if Meetings already exist for today.
            DatabaseOperations dbOper = new DatabaseOperations(this);
            if(dbOper.checkMeetingDate(today)) {
                loadMeetingsFragment(bundle);
            } else {
                // download today's Meetings.
                this.bundle = bundle;
                getMeetingsOnDay(today.split("-"));
            }

        } else if (id == R.id.id_nav_menu_races_select) {
            DialogFragment dateSelectFragment = new DateSelectFragment();
            dateSelectFragment.show(getFragmentManager(),
                    Resources.getInstance().getString(R.string.date_select_fragment_tag));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    private void loadMeetingsFragment(@Nullable Bundle args) {
        String fragment_tag = Resources.getInstance().getString(R.string.meetings_fragment_tag);
        MeetingsFragment meetingsFragment = new MeetingsFragment();
        if(args != null) {
            meetingsFragment.setArguments(args);
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.id_container, meetingsFragment, fragment_tag)
                .addToBackStack(fragment_tag)
                .commit();
    }

    private void getMeetingsOnDay(@Nullable String[] date) {
//        String uri = null;
//        Url url = new Url();
//        if(date == null) {
//            uri = url.createRaceDayUrl(null);
//        } else {
//            uri = url.createRaceDayUrl(date);
//        }
//        doProgressDialog(true);
//        DownloadRequest dlReq = new DownloadRequest(Request.Method.GET, uri, this, this, this, SchemaConstants.MEETINGS_TABLE);
//        DownloadRequestQueue.getInstance().addToRequestQueue(dlReq);
    }

    private void initialiseBaseUI() {
        initialiseToolbar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.id_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initialiseToolbar() {
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // set title.
        TextView textView = (TextView) findViewById(R.id.id_tv_toolbar);
        textView.setText("Racemeetings II");
    }

    private void showDeleteDialog() {
        DeleteDialog deleteDialog = new DeleteDialog();
        deleteDialog.setShowsDialog(true);
        deleteDialog.show(getSupportFragmentManager(),
                Resources.getInstance().getString(R.string.delete_dialog_fragment_tag));
    }

    private void showNetworkDialog() {
        NetworkDialog nd = new NetworkDialog();
        nd.setShowsDialog(true);
        Bundle bundle = new Bundle();
        bundle.putString(Resources.getInstance().getString(R.string.network_dialog_text),
                Resources.getInstance().getString(R.string.network_connection_error));
        nd.setArguments(bundle);
        nd.show(getSupportFragmentManager(), null);
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);
    }

    private void unRegisterReceiver() {
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }

    private Bundle setEmptyView() {
        Bundle args = new Bundle();
        args.putString("meetings_show_empty_key", null);
        return args;
    }

    private void doProgressDialog(boolean doProgress) {
        if(doProgress) {
            progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Getting Meetings information.");
            progressDialog.show();
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

    private void initialise() {
        registerReceiver();            // register network broadcast receiver.
        DownloadRequestQueue.getInstance(this);
        Preferences.getInstance(this); // setup preferenxes access.
        Resources.getInstance(this);   // setup resources access.
        dbOper = new DatabaseOperations(this);
    }

    private void finalise() {

        // Check cache preference.
        if(!Preferences.getInstance().getSaveMeetings()) {
            dbOper.deleteAllFromTable(SchemaConstants.RUNNERS_TABLE);
            dbOper.deleteAllFromTable(SchemaConstants.RACES_TABLE);
            dbOper.deleteAllFromTable(SchemaConstants.MEETINGS_TABLE);
        }

        // De-register the network state broadcast receiver.
        unRegisterReceiver();

        // Close off static references.
        Preferences.getInstance().destroy();
        Resources.getInstance().destroy();
        DownloadRequestQueue.getInstance().destroy();

        // Basically just ensure database is closed.
        if(dbOper.getDbHelper() != null) {
            dbOper.getDbHelper().onDestroy();
        }
        dbOper = null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private Bundle bundle;                  // contains meeting action key, used by fragment.
    private Toolbar toolbar;                // to get access to the toolbar.
    private boolean meetingsExist;          // flag meetings exist in database.
    private NetworkReceiver receiver;       // for network availability check.
    private DatabaseOperations dbOper;      // database related methods.
    private ProgressDialog progressDialog;  // used by Volley download to show something happening.
    //</editor-fold>

}
