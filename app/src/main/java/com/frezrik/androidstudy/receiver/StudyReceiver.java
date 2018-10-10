package com.frezrik.androidstudy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StudyReceiver extends BroadcastReceiver {

    private final static String ACTION = "com.frezrik.studybroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "Receive broadcast:" + intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
        }
    }
}
