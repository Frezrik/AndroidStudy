package com.ming.androidstudy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ming.androidstudy.R;

public class MainActivity
        extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private long lastTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

    }

    private void initView() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToggle = new ActionBarDrawerToggle(this,
                mDrawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);


    }

    private void initListener() {
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - lastTime < 2000) {
                super.onBackPressed();
            } else {
                lastTime = System.currentTimeMillis();
                Toast.makeText(this, getString(R.string.toast_exit_app), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_ndk:
                intent = new Intent(this, NDKActivity.class);
                intent.putExtra("title", "NDK");
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            default:
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        if (intent != null) {
            startActivity(intent);
        }
        return true;
    }

    public void click(View v) {
        String title = "AndroidStudy";
        switch (v.getId()) {
            case R.id.btn_aidl:
                title = "RPC";
                break;
            case R.id.btn_sample:
                title = "WindowManager";
                break;
        }
        startActivity((Button) v, title);
    }
}
