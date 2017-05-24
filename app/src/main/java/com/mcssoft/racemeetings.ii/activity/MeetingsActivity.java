package com.mcssoft.racemeetings.ii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcssoft.racemeetings.ii.R;

public class MeetingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSecondActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeGreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        btnSecondActivity = (Button) findViewById(R.id.id_btn_secondActivity);
        btnSecondActivity.setOnClickListener(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);

        TextView textView = (TextView) findViewById(R.id.id_tv_toolbar);
        textView.setText("Main Activity");

        ImageView imageView = (ImageView) findViewById(R.id.id_iv_toolbar);
        imageView.setVisibility(ImageView.INVISIBLE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onClick(View v) {
        startActivity(SecondActivity.class);
    }

    private void startActivity(Class<?> activityClass) {
        Intent myIntent = new Intent(this, activityClass);
        startActivity(myIntent);
    }
}
