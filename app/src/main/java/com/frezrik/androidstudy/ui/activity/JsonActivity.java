package com.frezrik.androidstudy.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.frezrik.androidstudy.R;

public class JsonActivity
        extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        initView();

    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
