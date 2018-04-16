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
import android.widget.Button;

import com.frezrik.androidstudy.R;
import com.frezrik.androidstudy.ui.view.custom.CombineView;

public class MainActivity
        extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, CombineView.OnItemClickListener
{

    private DrawerLayout          mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView        mNavigationView;
    private Toolbar               mToolbar;
    private Button                btn;

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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        initToolBar(mToolbar, true, R.string.app_name);

        mToggle = new ActionBarDrawerToggle(this,
                                            mDrawer,
                                            mToolbar,
                                            R.string.navigation_drawer_open,
                                            R.string.navigation_drawer_close);

        CombineView cv_custom = (CombineView) findViewById(R.id.cv_custom);
        CombineView cv_json = (CombineView) findViewById(R.id.cv_json);
        cv_custom.setOnItemClickListener(this, "cv_custom");
        cv_json.setOnItemClickListener(this, "cv_json");
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
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            default:
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemClick(View v, int position) {
        Intent intent = null;
        switch ((String) v.getTag()) {
            case "cv_custom":
                intent = new Intent(this, CustomActivity.class);
                break;
            case "cv_json":
                intent = new Intent(this, JsonActivity.class);
                break;
            default:
                break;
        }

        if(intent != null) {
            startActivity(intent, v);
        }
    }
}
