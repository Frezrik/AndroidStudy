package com.ming.androidstudy.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ming.androidstudy.ui.activity.FrameworkActivity;

import java.util.LinkedList;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(initView(), container, false);
        setClick((ViewGroup) view);
        return view;
    }

    public abstract @LayoutRes int initView();

    private void setClick(ViewGroup viewGroup) {
        LinkedList<ViewGroup> linkedList = new LinkedList<>();
        linkedList.add(viewGroup);
        while (!linkedList.isEmpty()) {
            ViewGroup current = linkedList.removeFirst();
            for (int i = 0; i < current.getChildCount(); i++) {
                View view = current.getChildAt(i);
                if (view instanceof ViewGroup) {
                    linkedList.addLast((ViewGroup) view);
                } else {
                    if (view instanceof Button) {
                        view.setOnClickListener(this);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        click(view);
    }

    protected abstract void click(View view);

    public void log(String msg) {
        ((FrameworkActivity) getActivity()).log(msg);
    }

}
