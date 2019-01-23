package com.frezrik.androidstudy.ui.activity;

import android.os.Bundle;
import android.view.View;
import com.frezrik.androidstudy.R;
import com.frezrik.androidstudy.framework.SampleWindow;

public class FrameworkActivity  extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framework);

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_wm:
                startWM();
                break;
            default:

                break;
        }
    }

    private void startWM() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new SampleWindow().Run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
