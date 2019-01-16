package com.frezrik.androidstudy.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.View;
import com.frezrik.androidstudy.R;
import com.frezrik.androidstudy.ui.fragment.CombineViewFragment;

public class CustomActivity
        extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        setToolbar(this);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (getIntent().getStringExtra("position")) {
            case "LEFT":
                ft = ft.replace(R.id.cv_container, new CombineViewFragment());
                break;
            case "MID_UP":
                break;
            case "MID_DOWN":
                break;
            case "RIGHT_UP":
                break;
            case "RIGHT_DOWN":
                break;
            default:

                break;
        }

        ft.commit();
    }

}
