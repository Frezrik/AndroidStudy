package com.frezrik.module_ndk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.frezrik.core.Core;
import com.frezrik.core.api.ConvertUtil;

public class NDKActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);

        byte[] bytes = Core.service(ConvertUtil.class).randomBytes(5);
        for (int i = 0; i < bytes.length; i++) {
            Log.d("frezrik", "" + bytes[i]);
        }
    }
}
