package com.frezrik.androidstudy.ui.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frezrik.androidstudy.R;

/**
 * Created by zhouming on 2018/4/16.
 */

public class CombineView
        extends LinearLayout
        implements View.OnClickListener
{

    private int    mColor;
    private int    mImage;
    private String mTitle;
    private String mLeft;
    private String mMid_up;
    private String mMid_down;
    private String mRight_up;
    private String mRight_down;

    private OnItemClickListener mOnItemClickListener;
    private String              mTag;

    public CombineView(Context context) {
        this(context, null);
    }

    public CombineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CombineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initTypeArray(context, attrs);

        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context)
                .inflate(R.layout.view_combine, this, true);

        LinearLayout ll_left = findViewById(R.id.ll_left);
        LinearLayout ll_mid_up = findViewById(R.id.ll_mid_up);
        LinearLayout ll_mid_down = findViewById(R.id.ll_mid_down);
        LinearLayout ll_right_up = findViewById(R.id.ll_right_up);
        LinearLayout ll_right_down = findViewById(R.id.ll_right_down);
        ImageView iv_left = findViewById(R.id.iv_left);
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_left = findViewById(R.id.tv_left);
        TextView tv_mid_up = findViewById(R.id.tv_mid_up);
        TextView tv_mid_down = findViewById(R.id.tv_mid_down);
        TextView tv_right_up = findViewById(R.id.tv_right_up);
        TextView tv_right_down = findViewById(R.id.tv_right_down);

        ll_left.setBackground(getDrawable(mColor));
        ll_mid_up.setBackground(getDrawable(mColor));
        ll_mid_down.setBackground(getDrawable(mColor));
        ll_right_up.setBackground(getDrawable(mColor));
        ll_right_down.setBackground(getDrawable(mColor));
        iv_left.setImageResource(mImage);
        tv_title.setTextColor(mColor);
        tv_title.setText(TextUtils.isEmpty(mTitle) ? "" : mTitle);
        tv_left.setText(TextUtils.isEmpty(mLeft) ? "" : mLeft);
        tv_mid_up.setText(TextUtils.isEmpty(mMid_up) ? "" : mMid_up);
        tv_mid_down.setText(TextUtils.isEmpty(mMid_down) ? "" : mMid_down);
        tv_right_up.setText(TextUtils.isEmpty(mRight_up) ? "" : mRight_up);
        tv_right_down.setText(TextUtils.isEmpty(mRight_down) ? "" : mRight_down);

        ll_left.setOnClickListener(this);
        ll_mid_up.setOnClickListener(this);
        ll_mid_down.setOnClickListener(this);
        ll_right_up.setOnClickListener(this);
        ll_right_down.setOnClickListener(this);
    }

    private void initTypeArray(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CombineView);
        mColor = typedArray.getColor(R.styleable.CombineView_cv_color, Color.BLACK);
        mImage = typedArray.getResourceId(R.styleable.CombineView_cv_image, R.mipmap.ic_launcher);
        mTitle = typedArray.getString(R.styleable.CombineView_cv_text_title);
        mLeft = typedArray.getString(R.styleable.CombineView_cv_text_left);
        mMid_up = typedArray.getString(R.styleable.CombineView_cv_text_mid_up);
        mMid_down = typedArray.getString(R.styleable.CombineView_cv_text_mid_down);
        mRight_up = typedArray.getString(R.styleable.CombineView_cv_text_right_up);
        mRight_down = typedArray.getString(R.styleable.CombineView_cv_text_right_down);
        typedArray.recycle();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            v.setTag(mTag);
            switch (v.getId()) {
                case R.id.ll_left:
                    mOnItemClickListener.onItemClick(v, 0);
                    break;
                case R.id.ll_mid_up:
                    mOnItemClickListener.onItemClick(v, 1);
                    break;
                case R.id.ll_mid_down:
                    mOnItemClickListener.onItemClick(v, 2);
                    break;
                case R.id.ll_right_up:
                    mOnItemClickListener.onItemClick(v, 3);
                    break;
                case R.id.ll_right_down:
                    mOnItemClickListener.onItemClick(v, 4);
                    break;
            }
        }
    }

    private Drawable getDrawable(int color) {
        StateListDrawable drawable = new StateListDrawable();
        Drawable selected = new ColorDrawable(color & 0xddffffff);
        Drawable unSelected = new ColorDrawable(color);
        drawable.addState(new int[]{android.R.attr.state_pressed}, selected);
        drawable.addState(new int[]{}, unSelected);

        return drawable;
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener, String tag) {
        mOnItemClickListener = listener;
        mTag = tag;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
