package com.mcssoft.racemeetings.ii.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcssoft.racemeetings.ii.R;
import com.mcssoft.racemeetings.ii.fragment.SettingsFragment;

import static android.R.id.list;
import static com.mcssoft.racemeetings.ii.R.layout.toolbar;

public class SettingsActivity extends AppCompatActivity
            implements SharedPreferences.OnSharedPreferenceChangeListener,
                       ImageView.OnClickListener {

    @Override
    protected void onCreate(Bundle savedState) {
        setTheme(R.style.AppThemeBlue);
        super.onCreate(savedState);
        setContentView(R.layout.activity_settings);
        initialiseToolbar();
        getFragmentManager().beginTransaction()
                .replace(R.id.id_container, new SettingsFragment()).commit();
    }

     @Override
     protected void onResume() {
         super.onResume();

         sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
         sharedPreferences.registerOnSharedPreferenceChangeListener(this);
     }

     @Override
     protected void onPause() {
         super.onPause();
         sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
     }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, MeetingsActivity.class));
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String bp = "";
//        if (key.equals(SOME_KEY)) {
//            // do something.
//        }
    }

    private void initialiseToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_tb_settings);
        TextView textView = (TextView) toolbar.findViewById(R.id.id_tv_toolbar);
        textView.setText("Preferences");
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.id_iv_toolbar);
        imageView.setImageResource(R.drawable.ic_arrow_back);
        imageView.setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    Toolbar toolbar;
    private SharedPreferences sharedPreferences;
}

