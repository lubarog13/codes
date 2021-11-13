package com.example.boundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TestService";
    TestServiceConnection testServConn;
    SeekBar seekBar;

    final Messenger messenger = new Messenger(new IncomingHandler());
    Messenger toServiceMessenger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= findViewById(R.id.button);
        seekBar = findViewById(R.id.progressBar);
        seekBar.setProgress(0);

        Log.d("1", "Hello");
        bindService(new Intent(this, TestService.class), (testServConn = new TestServiceConnection()), Context.BIND_AUTO_CREATE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = seekBar.getProgress();
                if(val>50) {
                    Message msg = Message.obtain(null, TestService.COUNT_MINUS);
                    msg.replyTo = messenger;
                    msg.arg1 = 2;
                    try {
                        toServiceMessenger.send(msg);
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Message msg = Message.obtain(null, TestService.COUNT_NULL);
                    msg.replyTo = messenger;
                    msg.arg1 = 1;
                    try {
                        toServiceMessenger.send(msg);
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("3", "Started");
        //Intent intent = new Intent(this, TestService.class);

    }


    @Override
    protected void onDestroy() {
        unbindService(testServConn);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case TestService.GET_COUNT:
                    seekBar.setProgress( msg.arg1);
                    break;
                case TestService.MAX_COUNT:
                    Toast.makeText(MainActivity.this, "Прогресс достиг 100!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class TestServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("2", "Connected");
            toServiceMessenger = new Messenger(service);
            Message msg = Message.obtain(null, TestService.SET_COUNT);
            msg.replyTo = messenger;
            msg.arg1 = 0; //наш счетчик
            try {
                toServiceMessenger.send(msg);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {	}
    }
}