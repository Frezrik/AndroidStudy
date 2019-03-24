package com.ming.androidstudy.ui.fragment.wm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.ming.androidstudy.R;
import com.ming.androidstudy.ui.fragment.BaseFragment;
import com.ming.androidstudy.framework.wm.SampleWindow;

public class WmFragment extends BaseFragment {

    @Override
    public int initView() {
        return R.layout.fragment_wm;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startWM();
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

    @Override
    protected void click(View view) {
    }

}
