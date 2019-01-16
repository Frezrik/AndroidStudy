package com.frezrik.androidstudy.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.frezrik.androidstudy.R;

public class ComponentActivity
        extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_sv:
                start_service();
                break;
            case R.id.btn_exsv:
                start_explicit_service();
                break;
            case R.id.btn_bc:
                start_broadcast();
                break;
            case R.id.btn_ktx:
                start_ktx();
                break;
        }
    }

    private void start_service() {
        Intent intent = new Intent();
        intent.setAction("com.frezrik.studyService");
        startService(intent);
        stopService(intent);
    }


    private void start_explicit_service() {
        //方法一：设置包名
        Intent intent = new Intent();
        intent.setAction("com.frezrik.studyService");
        intent.setPackage(getPackageName());
        startService(intent);
        stopService(intent);

        //方法二：设置ComponentName
        /*Intent intent = new Intent();
        ComponentName cn = new ComponentName(getPackageName(), "com.frezrik.androidstudy.service.StudyService");
        intent.setComponent(cn);
        startService(intent);
        stopService(intent);*/
    }


    private void start_broadcast() {
        Intent intent = new Intent();
        intent.setAction("com.frezrik.studybroadcast");
        intent.putExtra("msg", "msg");
        intent.setData(Uri.fromParts("study", getPackageName(), null));
        sendBroadcast(intent);
    }

    private void start_ktx() {
    }
}
