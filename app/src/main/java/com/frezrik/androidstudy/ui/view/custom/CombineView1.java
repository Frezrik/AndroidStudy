package com.frezrik.androidstudy.ui.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.frezrik.androidstudy.R;

/**
 * Created by zhouming on 2018/4/16.
 */

public class CombineView1
        extends LinearLayout {

    private String mCv_text;
    private ImageView mIv_check;
    private ProgressBar mPb_check;
    private TextView mTv_check;

    public CombineView1(Context context) {
        this(context, null);
    }

    public CombineView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CombineView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initTypeArray(context, attrs);

        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context)
                .inflate(R.layout.view_combine1, this, true);

        LinearLayout container = findViewById(R.id.container);
        mTv_check = findViewById(R.id.tv_check);
        mIv_check = findViewById(R.id.iv_check);
        mPb_check = findViewById(R.id.pb_check);

        mTv_check.setText(mCv_text == null ? "" : mCv_text);
        //container.setOnClickListener(this);
    }

    private void initTypeArray(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CombineView1);
        mCv_text = typedArray.getString(R.styleable.CombineView1_cv_text);

        typedArray.recycle();
    }

    public void setOnCheckListener(@Nullable final OnCheckListener listener) {
        if (listener != null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    final boolean isPass = listener.onCheck();
                    post(new Runnable() {
                        @Override
                        public void run() {
                            mPb_check.setVisibility(View.INVISIBLE);
                            mIv_check.setVisibility(View.VISIBLE);
                            if (isPass) {
                                mIv_check.setImageResource(R.mipmap.ic_tick_green);
                            } else {
                                mIv_check.setImageResource(R.mipmap.ic_tick_red);
                            }
                        }
                    });
                }
            });

            thread.setName("CombineView1");
            thread.start();
        }
    }

    public void setText(String s) {
        mTv_check.setText(s);
    }

    public interface OnCheckListener {
        boolean onCheck();
    }
}
