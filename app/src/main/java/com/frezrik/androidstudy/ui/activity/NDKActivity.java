package com.frezrik.androidstudy.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.frezrik.androidstudy.R;
import com.frezrik.jniapi.JMethod;
import com.frezrik.jniapi.JPersistent;
import com.frezrik.jniapi.NativeLib;

public class NDKActivity
        extends BaseActivity
{

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);

        initView();

        initData();
    }

    private void initView() {
        setTitle(R.string.ndk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTv = (TextView) findViewById(R.id.tv);
    }

    private void initData() {
        NativeLib nl = new NativeLib();
        //mTv.setText(nl.test() + "\n");
        mTv.setText(nl.NewStringUTF() + "\n");
        mTv.append(nl.native_init() + "\n");
        mTv.append(nl.dynamicString() + "\n");

        JMethod jm = new JMethod();
        mTv.append(jm.ccalljava() + "\n");

        JPersistent jp = new JPersistent();
        jp.init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    static {
        System.loadLibrary("jniapi");
    }
}
