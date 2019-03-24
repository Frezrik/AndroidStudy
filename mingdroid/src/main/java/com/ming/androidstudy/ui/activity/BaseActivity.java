package com.ming.androidstudy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;

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

    protected void startActivity(Button v, String title) {
        Intent intent = new Intent(this, FrameworkActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("select", v.getText());
        startActivity(intent);
    }

}
