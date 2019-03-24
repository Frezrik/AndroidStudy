package com.ming.androidstudy.ui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import com.ming.androidstudy.R;
import com.ming.androidstudy.ui.fragment.rpc.ServiceFragment;
import com.ming.androidstudy.ui.fragment.wm.WmFragment;

public class FrameworkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framework);

        setToolbar(this);

        init(getIntent().getStringExtra("select"));
    }

    private void init(String select) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (select) {
            case "AIDL":
                ft = ft.replace(R.id.container, new ServiceFragment());
                break;
            case "SampleWindow":
                ft = ft.replace(R.id.container, new WmFragment());
                break;
        }
        ft.commit();
    }
}
