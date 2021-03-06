package com.frezrik.androidstudy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.frezrik.androidstudy.R;
import com.frezrik.androidstudy.ui.view.custom.CombineView;

public class MainActivity
        extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, CombineView.OnItemClickListener {

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

        CombineView cv_custom = (CombineView) findViewById(R.id.cv_custom);
        CombineView cv_json = (CombineView) findViewById(R.id.cv_json);
        CombineView cv_im = (CombineView) findViewById(R.id.cv_im);
        cv_custom.setOnItemClickListener(this, "cv_custom");
        cv_json.setOnItemClickListener(this, "cv_json");
        cv_im.setOnItemClickListener(this, "cv_im");
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
            case R.id.nav_cryptography:
                ARouter.getInstance().build("/wheel/cryptography").navigation();
                break;
            case R.id.nav_ndk:
                intent = new Intent(this, NDKActivity.class);
                intent.putExtra("title", "NDK");
                break;
            case R.id.nav_component:
                intent = new Intent(this, ComponentActivity.class);
                intent.putExtra("title", "Component");
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


    @Override
    public void onItemClick(View v, String title, String position) {
        Intent intent = null;
        switch ((String) v.getTag()) {
            case "cv_custom":
                intent = new Intent(this, CustomActivity.class);
                break;
            case "cv_json":
                intent = new Intent(this, ParserActivity.class);
                break;
            case "cv_im":
                intent = new Intent(this, ImRecActivity.class);
                break;
            default:
                break;
        }

        if (intent != null) {
            startActivity(intent, v, title, position);
        }
    }
}
