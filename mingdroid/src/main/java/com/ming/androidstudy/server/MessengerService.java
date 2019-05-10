package com.ming.androidstudy.server;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.support.annotation.Nullable;
import com.orhanobut.logger.Logger;

/**
 * Messenger的服务端
 * 返回一个Messenger.getBinder()即可
 */
public class MessengerService extends Service {

    private Messenger mMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread ht = new HandlerThread(getClass().getSimpleName());
        ht.start();

        mMessenger = new Messenger(new Handler(ht.getLooper()) {
            @Override
            public void handleMessage(Message msg) {

                Logger.d("服务端接收到[%d]:%s", msg.what, msg.obj);

                //客户端回发
                Message message = Message.obtain();
                message.what = 0x02;
                message.obj = "echo " + msg.obj;
                try {
                    Logger.d("服务端echo[%d]:%s", message.what, message.obj);
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                super.handleMessage(msg);
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
