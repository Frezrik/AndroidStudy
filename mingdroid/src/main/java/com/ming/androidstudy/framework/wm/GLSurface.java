package com.ming.androidstudy.framework.wm;

import android.opengl.EGL14;
import android.opengl.EGLSurface;
import android.view.Surface;

public class GLSurface {
    // 在屏幕上的一块显示区的封装，渲染后即显示在界面上
    public static final int TYPE_WINDOW_SURFACE  = 0;
    // 在显存中开辟一个空间，将渲染后的数据(帧)存放在这里
    public static final int TYPE_PBUFFER_SURFACE = 1;
    // 以位图的形式存放在内存中，据说各平台的支持不是很好
    public static final int TYPE_PIXMAP_SURFACE  = 2;

    protected final int type;
    protected Object surface; // 显示控件(支持SurfaceView、SurfaceHolder、Surface和SurfaceTexture)
    protected EGLSurface eglSurface = EGL14.EGL_NO_SURFACE;
    protected Viewport viewport = new Viewport();

    public GLSurface(int width, int height) {
        setViewport(0, 0, width, height);
        surface = null;
        type = TYPE_PBUFFER_SURFACE;
    }

    public GLSurface(Surface surface, int width, int height) {
        this(surface,0,0,width,height);
    }

    public GLSurface(Surface surface, int x, int y, int width, int height) {
        setViewport(x, y, width, height);
        this.surface = surface;
        type = TYPE_WINDOW_SURFACE;
    }

    public void setViewport(int x, int y, int width, int height){
        viewport.x = x;
        viewport.y = y;
        viewport.width = width;
        viewport.height = height;
    }

    public void setViewport(Viewport viewport){
        this.viewport = viewport;
    }

    public Viewport getViewport(){
        return viewport;
    }

    public static class Viewport{
        public int x;
        public int y;
        public int width;
        public int height;
    }
}
