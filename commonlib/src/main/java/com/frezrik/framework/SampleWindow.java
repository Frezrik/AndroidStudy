package com.frezrik.framework;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.IDisplayManager;
import android.os.*;
import android.view.Choreographer;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.IWindow;
import android.view.IWindowManager;
import android.view.IWindowSession;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.WindowManagerGlobal;

public class SampleWindow {
    public static void main(String[] args) {
        try {
            new SampleWindow().Run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // IWindosSession是客户端向WMS请求窗口操作的中间代理，并且是进程唯一的
    IWindowSession mSession = null;
    // InputChannel是窗口接收用户输入事件的管道。
    InputChannel mInputChannel = new InputChannel();

    // 下面的Rect保存了窗口的布局结果。其中mFrame表示了窗口在屏幕上的位置和尺寸
    Rect mFrame = new Rect();
    Rect mOverscanInsets = new Rect();
    Rect mContentInsets = new Rect();
    Rect mVisibleInsets = new Rect();
    Rect mStableInsets = new Rect();

    Configuration mConfig = new Configuration();
    // 窗口的Surface，在此Surface上进行的绘制都将在此窗口上显示出来
    Surface mSurface = new Surface();
    Paint mPaint = new Paint();
    // 添加窗口所需的令牌
    IBinder mToken = new Binder();
    // 一个窗口对象，将其添加到WMS中，并在其上进行绘制
    MyWindow mWindow = new MyWindow();

    // 定义窗口的布局属性，包括位置、尺寸以及窗口类型等
    LayoutParams mLp = new LayoutParams();

    Choreographer mChoreographer = null;

    // InputHandler用于从InputChannel接收按键事件并做出响应
    InputHandler mInputHandler = null;

    boolean mContinueAnime = true;

    class InputHandler extends InputEventReceiver {
        Looper mLooper = null;

        public InputHandler(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
            mLooper = looper;
        }

        @Override
        public void onInputEvent(InputEvent event) {
            if (event instanceof MotionEvent) {
                MotionEvent me = (MotionEvent) event;
                if (me.getAction() == MotionEvent.ACTION_UP) {
                    // 退出程序
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        mLooper.quitSafely();
                    }
                }
            }
            super.onInputEvent(event);
        }
    }

    public void Run() throws Exception {
        Looper.prepare();
        // 获取wms服务
        IWindowManager wms = IWindowManager.Stub
                .asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));

        // 通过WindowManagerGlobal获取进程唯一的IWindowSession实例。它将用于向WMS发送请求
        mSession = WindowManagerGlobal.getWindowSession();

        // 获取屏幕分辨率
        IDisplayManager dm = IDisplayManager.Stub
                .asInterface(ServiceManager.getService(Context.DISPLAY_SERVICE));
        DisplayInfo di = dm.getDisplayInfo(Display.DEFAULT_DISPLAY);
        Point scrnSize = new Point(di.appWidth, di.appHeight);

        initLayoutParams(scrnSize);

        installWindow(wms);

        // 初始化Choreographer的实例，此实例为线程唯一
        mChoreographer = Choreographer.getInstance();

        scheduleNextFrame();

        Looper.loop();

        mContinueAnime = false;

        uninstallWindow(wms);
    }

    private void initLayoutParams(Point scrnSize) {
        mLp.type = LayoutParams.TYPE_SYSTEM_ALERT;
        mLp.setTitle("SampleWindow");
        mLp.gravity = Gravity.LEFT | Gravity.TOP;
        mLp.x = scrnSize.x / 4;
        mLp.y = scrnSize.y / 4;
        mLp.width = scrnSize.x / 2;
        mLp.height = scrnSize.y / 2;

        mLp.flags = mLp.flags | LayoutParams.FLAG_NOT_TOUCH_MODAL;
    }

    private void installWindow(IWindowManager wms) throws Exception {
        // 向wms声明一个Token，任何一个Window都需要隶属于一个特定类型的Token
        wms.addWindowToken(mToken, LayoutParams.TYPE_TOAST);
        // 设置窗口所属的Token
        mLp.token = mToken;
        // 通过IWindowSession将窗口添加到wms，注意此时仅是添加，目前仍没有有效的Surface。不过，经过这个调用后，mInputChannel已经可以用来接收输入事件了
        mSession.add(mWindow, 0, mLp, View.VISIBLE, mContentInsets, mStableInsets, mInputChannel);
        // 通过IWindowSession要求WMS对本窗口进行重新布局，经过这个操作后，WMS将会为窗口创建一块用于绘制的Surface并保存在参数mSurface中。
        mSession.relayout(mWindow, 0, mLp, mLp.width, mLp.height, View.VISIBLE, 0, mFrame,
                mOverscanInsets, mContentInsets, mVisibleInsets, mStableInsets, mConfig, mSurface);

        if (!mSurface.isValid()) {
            throw new RuntimeException("Failed creating Suface.");
        }

        // 基于WMS返回的InputChannel创建一个Handler，用于监听输入事件
        mInputHandler = new InputHandler(mInputChannel, Looper.myLooper());
    }

    private void uninstallWindow(IWindowManager wms) throws Exception {
        mSession.remove(mWindow);
        wms.removeWindowToken(mToken);
    }

    private void scheduleNextFrame() {
        mChoreographer.postCallback(Choreographer.CALLBACK_ANIMATION, mFrameRender, null);

    }

    public Runnable mFrameRender = new Runnable() {

        @Override
        public void run() {
            long time = mChoreographer.getFrameTime() % 1000;

            try {
                if (mSurface.isValid()) {
                    Canvas canvas = mSurface.lockCanvas(null);
                    canvas.drawColor(Color.RED);
                    canvas.drawRect(2 * mLp.width * time / 1000 - mLp.width, 0,
                            2 * mLp.width * time / 1000, mLp.height, mPaint);
                    mSurface.unlockCanvasAndPost(canvas);
                    mSession.finishDrawing(mWindow);
                }

                if(mContinueAnime) {
                    scheduleNextFrame();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    class MyWindow extends IWindow.Stub {

        @Override
        public void executeCommand(String s, String s1, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {

        }

        @Override
        public void resized(Rect rect, Rect rect1, Rect rect2, Rect rect3, Rect rect4, boolean b, Configuration configuration) throws RemoteException {

        }

        @Override
        public void moved(int i, int i1) throws RemoteException {

        }

        @Override
        public void dispatchAppVisibility(boolean b) throws RemoteException {

        }

        @Override
        public void dispatchGetNewSurface() throws RemoteException {

        }

        @Override
        public void windowFocusChanged(boolean b, boolean b1) throws RemoteException {

        }

        @Override
        public void closeSystemDialogs(String s) throws RemoteException {

        }

        @Override
        public void dispatchWallpaperOffsets(float v, float v1, float v2, float v3, boolean b) throws RemoteException {

        }

        @Override
        public void dispatchWallpaperCommand(String s, int i, int i1, int i2, Bundle bundle, boolean b) throws RemoteException {

        }

        @Override
        public void dispatchDragEvent(DragEvent dragEvent) throws RemoteException {

        }

        @Override
        public void dispatchSystemUiVisibilityChanged(int i, int i1, int i2, int i3) throws RemoteException {

        }

        @Override
        public void doneAnimating() throws RemoteException {

        }

        @Override
        public void dispatchWindowShown() throws RemoteException {

        }
    }
}

