package com.mcssoft.racemeetings.ii.activity;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcssoft.racemeetings.ii.activity.MeetingsActivity;
import com.mcssoft.racemeetings.ii.R;
import com.mcssoft.racemeetings.ii.fragment.SecondFragment;

public class SecondActivity extends AppCompatActivity
    implements ImageView.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initToolbar();
        loadFragment();
    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(this, "Back navigation pressed", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MeetingsActivity.class));
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);

        TextView textView = (TextView) findViewById(R.id.id_tv_toolbar);
        textView.setText("Second Activity");

        ImageView imageView = (ImageView) findViewById(R.id.id_iv_toolbar);
        imageView.setOnClickListener(this);
//        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void loadFragment() {
        SecondFragment p3f = SecondFragment.createInstance(20);
        getFragmentManager().beginTransaction()
                .replace(R.id.id_frameLayout, p3f, "P3F")
                .addToBackStack("P3F")
                .commit();
    }
}
