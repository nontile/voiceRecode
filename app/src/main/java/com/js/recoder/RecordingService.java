package com.js.recoder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class RecordingService extends Service {

    private static final String LOG_TAG = "RecordingService";
    private DBHelper dbHelper;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }

    // 서비스에서 가장 먼저 호출됨(최초에 한번만)
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(LOG_TAG, "RecordingService onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "RecordingService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "RecordingService onDestroy");
    }
}
