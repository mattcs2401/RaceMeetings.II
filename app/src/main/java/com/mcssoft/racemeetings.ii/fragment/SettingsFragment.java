package com.mcssoft.racemeetings.ii.fragment;

import android.preference.PreferenceFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcssoft.racemeetings.ii.R;
import com.mcssoft.racemeetings.ii.utility.Resources;

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_settings, container, false);
        addPreferencesFromResource(R.xml.preferences);
        sharedPreferences = getPreferenceManager().getSharedPreferences();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Listener">
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String bp = "";
    }
    //</editor-fold>

    private SharedPreferences sharedPreferences;
}