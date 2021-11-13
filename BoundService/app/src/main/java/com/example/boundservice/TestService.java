package com.example.boundservice;
import android.app.Service;
import android.content.*;
import android.os.*;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestService extends Service {
    public static final int COUNT_NULL = 1;
    public static final int COUNT_MINUS = 2;
    public static final int SET_COUNT = 0;
    public static final int GET_COUNT = 3;
   public static final int COUNT_PLUS = 4;
    public static final int MAX_COUNT = 5;
    private ScheduledExecutorService mScheduledExecutorService;
    int count = 0;

    IncomingHandler inHandler;

    Messenger messanger;
    Messenger toActivityMessenger;

    @Override
    public void onDestroy() {
        mScheduledExecutorService.shutdown();
        super.onDestroy();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("5", "Created");
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mScheduledExecutorService = Executors.newScheduledThreadPool(1);
        inHandler = new IncomingHandler(thread.getLooper());
        messanger = new Messenger(inHandler);
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(count>=95 && count<100){
                    Message outMsg = Message.obtain(inHandler, MAX_COUNT);
                    outMsg.replyTo = messanger;

                    try {
                        if( toActivityMessenger != null )
                            toActivityMessenger.send(outMsg);
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                if(count<100) count+=5;
                Message outMsg = Message.obtain(inHandler, GET_COUNT);
                Log.d("6", "count");
                outMsg.arg1 = count;
                outMsg.replyTo = messanger;

                try {
                    if( toActivityMessenger != null )
                        toActivityMessenger.send(outMsg);
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 1000, 200, TimeUnit.MILLISECONDS);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return messanger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    //обработчик сообщений активити
    private class IncomingHandler extends Handler {
        public IncomingHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg){
            //super.handleMessage(msg);

            toActivityMessenger = msg.replyTo;

            switch (msg.what) {
                case SET_COUNT:
                    count = msg.arg1;
                    Log.d(MainActivity.TAG, "(service)...set count");
                    break;
                case COUNT_NULL:
                    count=0;

                    Log.d(MainActivity.TAG, "(service)...count plus");
                    break;

                case COUNT_MINUS:
                    Log.d(MainActivity.TAG, "(service)...count plus");
                    count-=50;

                    break;
                case COUNT_PLUS:
                    if(count>=95 && count<100){
                        Message outMsg = Message.obtain(inHandler, MAX_COUNT);
                        outMsg.replyTo = messanger;

                        try {
                            if( toActivityMessenger != null )
                                toActivityMessenger.send(outMsg);
                        }
                        catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    if(count<100) count+=5;
                    break;
            }

            //отправляем значение счетчика в активити
            Message outMsg = Message.obtain(inHandler, GET_COUNT);
            outMsg.arg1 = count;
            outMsg.replyTo = messanger;

            try {
                if( toActivityMessenger != null )
                    toActivityMessenger.send(outMsg);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
