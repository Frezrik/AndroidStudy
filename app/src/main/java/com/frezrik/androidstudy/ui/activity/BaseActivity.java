package com.frezrik.androidstudy.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.frezrik.androidstudy.utils.SPHelper;

/**
 * Created by zhouming on 2018/3/22.
 */

public abstract class BaseActivity
        extends AppCompatActivity
{
    protected String mPosition;
    protected SPHelper mSPHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getIntent().getStringExtra("title"));
        mPosition = getIntent().getStringExtra("position");
        mSPHelper = SPHelper.getInstance(getApplicationContext());
    }

    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
        toolbar.setTitle(getString(resTitle));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    public void startActivity(Intent intent, View v, String title, String position) {
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
