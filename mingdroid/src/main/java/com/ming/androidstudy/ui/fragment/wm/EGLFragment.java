package com.ming.androidstudy.ui.fragment.wm;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.ming.androidstudy.framework.wm.EGLRenderer;
import com.ming.androidstudy.framework.wm.GLTriangle;
import com.ming.androidstudy.ui.fragment.BaseFragment;
import com.ming.framework.R;
import com.orhanobut.logger.Logger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class EGLFragment extends BaseFragment implements SurfaceHolder.Callback {

    private boolean rendererSet;
    private EGLRenderer mEGLRenderer;

    @Override
    public int initView() {
        return R.layout.fragment_egl;
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
            mEGLRenderer = new EGLRenderer();
            mEGLRenderer.start();
            rendererSet = true;

            SurfaceView sv = mLayout.findViewById(R.id.sv);
            sv.getHolder().addCallback(this);
        }
    }


    @Override
    public void onDestroyView() {
        if (rendererSet) {
            mEGLRenderer.release();
            mEGLRenderer = null;
        }
        super.onDestroyView();
    }



    //--------------------------------SurfaceHolder Callback--------------------------------//
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mEGLRenderer.draw(surfaceHolder.getSurface());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
    //--------------------------------EGL--------------------------------//

}
