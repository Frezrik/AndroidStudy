package com.ming.androidstudy.ui.fragment.rpc;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.*;
import android.view.View;
import com.ming.androidstudy.ui.fragment.BaseFragment;
import com.ming.api.fingerprint.FingerprintException;
import com.ming.api.fingerprint.FingerprintListener;
import com.ming.api.fingerprint.FingerprintManager;
import com.ming.api.fingerprint.FingerprintResult;
import com.ming.framework.R;
import com.orhanobut.logger.Logger;

public class IPCFragment extends BaseFragment {

    private static final String TAG = IPCFragment.class.getSimpleName();
    private Handler handler;

    @Override
    public int initView() {
        return R.layout.fragment_ipc;
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
            case R.id.btn_handler:
                start_handlerthread();
                break;
            case R.id.btn_send:
                send_msg();
                break;
            case R.id.btn_aidl:
                startAidl();
                break;
            case R.id.btn_messenger:
                startMessenger();
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

    private void startAidl() {
        FingerprintManager manager = null;
        try {
            manager = FingerprintManager.getInstance();
            manager.fingerOpen();
            manager.fingerStart(new FingerprintListener() {
                @Override
                public void onSuccess(FingerprintResult result) {
                    log(result.toString());
                }

                @Override
                public void onError(int errorCode) {

                }
            });

            manager.fingerStop();

            manager.fingerClose();
        } catch (FingerprintException e) {
            log(e.toString());
            if (manager != null) {
                try {
                    manager.fingerClose();
                } catch (FingerprintException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

    private void startMessenger() {
        // 1.bind
        Intent intent = new Intent();
        intent.setAction("com.ming.messengerService");
        intent.setPackage(getActivity().getPackageName());
        mContext.bindService(intent, mConn, Context.BIND_AUTO_CREATE);

    }
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //客户端的Messenger
            HandlerThread ht = new HandlerThread("MessengerClient");
            ht.start();
            Messenger clientMessenger = new Messenger(new Handler(ht.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    // 3. rec
                    Logger.d("客户端接收[%d]:%s", msg.what, msg.obj);
                    log("客户端接收:" + msg.obj);
                }
            });

            // 2.send
            //服务端的Messenger
            Messenger serverMessenger = new Messenger(iBinder);
            Message message = Message.obtain();
            message.what = 0x01;
            message.obj = "Hello messenger!";
            message.replyTo = clientMessenger;//注意指定回信人
            try {
                serverMessenger.send(message);
                Logger.d("客户端发送[%d]:%s", message.what, message.obj);
                log("客户端发送:" + message.obj);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    //在非主线程使用Handler如下，此时用HandlerThread更方便
    class LooperThread extends Thread {
        public Handler mHandler;

        public void run() {
            Looper.prepare();

            mHandler = new Handler() {
                public void handleMessage(Message msg) {

                }
            };

            Looper.loop();
        }
    }

    private void start_handlerthread() {
        HandlerThread ht = new HandlerThread("HandlerThreadDemo");
        ht.start();
        handler = new Handler(ht.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        log("rec:" + msg.obj);
                        break;
                }
            }
        };
    }

    private void send_msg() {
        if (handler != null) {
            handler.sendMessage(handler.obtainMessage(0, "主线程发送了消息。。。"));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.sendMessage(handler.obtainMessage(0, "子线程发送了消息。。。"));
                }
            }).start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unbind
        mContext.unbindService(mConn);
    }
}
