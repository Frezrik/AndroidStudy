package com.ming.androidstudy.ui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.widget.TextView;
import com.ming.androidstudy.ui.fragment.rpc.IPCFragment;
import com.ming.androidstudy.ui.fragment.wm.WmFragment;
import com.ming.framework.R;

public class FrameworkActivity extends BaseActivity {

    private final static int SHOW_TEXT = 0;
    private TextView mLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framework);

        setToolbar(this);

        mLog = findViewById(R.id.log);
        mLog.setMovementMethod(ScrollingMovementMethod.getInstance());
        mLog.setGravity(Gravity.BOTTOM);

        init(getIntent().getStringExtra("select"));
    }

    private void init(String select) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (select) {
            case "IPC":
                ft = ft.replace(R.id.container, new IPCFragment());
                break;
            case "SampleWindow":
                ft = ft.replace(R.id.container, new WmFragment());
                break;
        }
        ft.commit();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TEXT:
                    mLog.append((String) msg.obj + "\n");

                    break;
            }
            
        }
    };

    public void log(String msg) {
        mHandler.sendMessage(mHandler.obtainMessage(SHOW_TEXT, msg));
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
