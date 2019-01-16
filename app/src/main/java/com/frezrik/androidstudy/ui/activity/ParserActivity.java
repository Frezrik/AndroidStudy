package com.frezrik.androidstudy.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.frezrik.androidstudy.R;

public class ParserActivity
        extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parser);

        initView();

    }

    private void initView() {
        setToolbar(this);
    }

    //https://developers.google.cn/protocol-buffers/docs/proto3

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
