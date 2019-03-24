package com.ming.androidstudy.ui.fragment.rpc;


import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.ming.androidstudy.R;
import com.ming.androidstudy.ui.fragment.BaseFragment;

public class ServiceFragment extends BaseFragment {

    @Override
    public int initView() {
        return R.layout.fragment_service;
    }

    @Override
    protected void click(View view) {
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
        }
    }

    private void start_service() {
        Intent intent = new Intent();
        intent.setAction("com.ming.studyService");
        getActivity().startService(intent);
        getActivity().stopService(intent);
    }


    private void start_explicit_service() {
        //方法一：设置包名
        Intent intent = new Intent();
        intent.setAction("com.ming.studyService");
        intent.setPackage(getActivity().getPackageName());
        getActivity().startService(intent);
        getActivity().stopService(intent);

        //方法二：设置ComponentName
        /*Intent intent = new Intent();
        ComponentName cn = new ComponentName(getPackageName(), "com.ming.androidstudy.service.StudyService");
        intent.setComponent(cn);
        startService(intent);
        stopService(intent);*/
    }


    private void start_broadcast() {
        Intent intent = new Intent();
        intent.setAction("com.ming.studybroadcast");
        intent.putExtra("msg", "msg");
        intent.setData(Uri.fromParts("study", getActivity().getPackageName(), null));
        getActivity().sendBroadcast(intent);
    }

}
