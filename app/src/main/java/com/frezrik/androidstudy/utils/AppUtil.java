package com.frezrik.androidstudy.utils;

import android.content.Context;
import android.content.Intent;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AppUtil {

    public static void gotoActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



}
