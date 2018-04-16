package com.frezrik.androidstudy.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.frezrik.androidstudy.R;
import com.frezrik.androidstudy.ui.view.svg.AnimatedSvgView;
import com.frezrik.androidstudy.utils.AnimUtil;
import com.frezrik.androidstudy.utils.AppUtil;
import com.frezrik.androidstudy.utils.SPHelper;

public class SplashActivity
        extends AppCompatActivity
{


    private AnimatedSvgView mSvgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!(boolean) SPHelper.getInstance(this).get(getString(R.string.IS_FIRST_START), true)) {
            gotoMain();
        }

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        initView();
        initListener();
    }

    private void initView() {
        mSvgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        final TextView tvTitle = (TextView) findViewById(R.id.tv_title);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSvgView.start();

                AnimUtil.animAlphaScaleShowView(tvTitle, 3000);
            }
        }, 500);
    }

    private void initListener() {
        mSvgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {
                if(state == AnimatedSvgView.STATE_FINISHED) {
                    SPHelper.getInstance(getApplicationContext()).put(getString(R.string.IS_FIRST_START), false);
                    gotoMain();
                }
            }
        });
    }

    private void gotoMain() {
        AppUtil.gotoActivity(getApplicationContext(), MainActivity.class);
        finish();
    }
}
