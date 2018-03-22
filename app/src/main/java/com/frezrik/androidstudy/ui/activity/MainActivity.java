package com.frezrik.androidstudy.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.frezrik.androidstudy.R;

import static com.frezrik.androidstudy.R.id.toolbar;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);

        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
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
}
