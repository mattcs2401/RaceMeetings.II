package com.mcssoft.racemeetings.ii.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.mcssoft.racemeetings.ii.R;
import com.mcssoft.racemeetings.ii.fragment.SettingsFragment;

public class SettingsActivity extends PreferenceActivity
            implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
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

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String bp = "";
//        if (key.equals(SOME_KEY)) {
//            // do something.
//        }
    }

    private SharedPreferences sharedPreferences;
}

