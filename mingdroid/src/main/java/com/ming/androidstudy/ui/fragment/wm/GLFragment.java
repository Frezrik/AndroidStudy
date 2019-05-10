package com.ming.androidstudy.ui.fragment.wm;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import com.ming.androidstudy.framework.wm.GLTriangle;
import com.ming.androidstudy.ui.fragment.BaseFragment;
import com.ming.framework.R;
import com.orhanobut.logger.Logger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLFragment extends BaseFragment implements GLSurfaceView.Renderer {

    private boolean rendererSet;
    private GLSurfaceView mGlSurfaceView;
    private GLTriangle mTriangle;

    @Override
    public int initView() {
        return R.layout.fragment_gl;
    }

    @Override
    protected void click(View view) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        String ver = "UNKNOWN";
        switch (info.reqGlEsVersion) {//本质是读取属性ro.opengles.version
            case 0x10000:
                ver = "1.0";
                break;
            case 0x20000:
                ver = "2.0";
                break;
            case 0x30000:
                ver = "3.0";
                break;
        }
        log("OpenGL ES " + ver);

        if (info.reqGlEsVersion >= 0x20000) {
            mGlSurfaceView = new GLSurfaceView(mContext);
            mGlSurfaceView.setEGLContextClientVersion(2);
            mGlSurfaceView.setRenderer(this);
            rendererSet = true;

            ConstraintLayout container = mLayout.findViewById(R.id.container);
            container.addView(mGlSurfaceView);
        }
    }

    @Override
    public void onResume() {
        Logger.d("setRenderer onResume");
        super.onResume();
        if (rendererSet) {
            mGlSurfaceView.onResume();
        }
    }

    @Override
    public void onPause() {
        Logger.d("setRenderer onPause");
        super.onPause();
        if (rendererSet) {
            mGlSurfaceView.onPause();
        }
    }

    //--------------------------------GLSurfaceView setRenderer--------------------------------//

    /**
     * 当GLSurfaceView中的Surface被创建的时候回调此方法，一般在这里做一些初始化
     * @param gl10 1.0版本的OpenGL对象，这里用于兼容老版本，用处不大
     * @param eglConfig egl的配置信息
     */
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Logger.d("onSurfaceCreated");

        mTriangle = new GLTriangle();

        // 设置clear color颜色RGBA(这里仅仅是设置清屏时GLES20.glClear()用的颜色值而不是执行清屏)
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    /**
     * 当GLSurfaceView中的Surface被改变的时候回调此方法(一般是大小变化)
     * @param gl10 1.0版本的OpenGL对象
     * @param width Surface的宽度
     * @param height Surface的高度
     */
    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        Logger.d("onSurfaceChanged");
        GLES20.glViewport(0, 0, width, height);
    }

    /**
     * 当surface需要绘制的时候回调此方法
     * GLSurfaceView.RENDERMODE_CONTINUOUSLY : 固定一秒回调60次(60fps)
     * GLSurfaceView.RENDERMODE_WHEN_DIRTY   : 当调用GLSurfaceView.requestRender()之后回调一次
     * @param gl10 1.0版本的OpenGL对象
     */
    @Override
    public void onDrawFrame(GL10 gl10) {
        //Logger.d("onDrawFrame");
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw();
    }
    //--------------------------------GLSurfaceView setRenderer--------------------------------//

}
