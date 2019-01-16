package com.frezrik.androidstudy.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by zhouming on 2018/3/22.
 */

public abstract class BaseActivity
        extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setToolbar(AppCompatActivity act) {
        ActionBar bar = act.getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            setTitle(getIntent().getStringExtra("title"));
        }
    }

    protected void startActivity(Intent intent, View v, String title, String position) {
        if (intent != null && intent.getComponent() != null && !intent.getComponent()
                .getClassName()
                .equals(MainActivity.class.getName()))
        {
            int[] pos = new int[2];
            v.getLocationOnScreen(pos);
            intent.setSourceBounds(new Rect(pos[0], pos[1], pos[0] + v.getWidth(), pos[1] + v.getHeight()));
            ActivityOptions opts = ActivityOptions.makeScaleUpAnimation(v,
                                                                        0,
                                                                        0,
                                                                        v.getMeasuredWidth(),
                                                                        v.getMeasuredHeight());
            intent.putExtra("title", title);
            intent.putExtra("position", position);
            startActivity(intent, opts.toBundle());
        }
    }

}
