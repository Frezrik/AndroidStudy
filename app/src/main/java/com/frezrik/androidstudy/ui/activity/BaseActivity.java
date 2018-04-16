package com.frezrik.androidstudy.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by zhouming on 2018/3/22.
 */

public abstract class BaseActivity
        extends AppCompatActivity
{
    private final AppPreferences appPreferences = new AppPreferences(this);

    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
        toolbar.setTitle(getString(resTitle));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    public AppPreferences getPreferences() {
        return appPreferences;
    }

    public void startActivity(Intent intent, View v) {
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
            startActivity(intent, opts.toBundle());
        }
    }

}
