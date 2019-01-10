package com.frezrik.androidstudy.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.frezrik.androidstudy.R;
import com.frezrik.androidstudy.ui.view.custom.CombineView1;

/**
 * Created by zhouming on 2018/9/13.
 */

public class CombineViewFragment extends Fragment implements CombineView1.OnCheckListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combine, container, false);

        CombineView1 cv1 = view.findViewById(R.id.cv1);
        cv1.setText("This is a Test");
        cv1.setOnCheckListener(this);


        return view;
    }

    @Override
    public boolean onCheck() {
        SystemClock.sleep(5000);
        return false;
    }
}
