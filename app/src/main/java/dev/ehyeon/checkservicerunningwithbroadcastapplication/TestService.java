package dev.ehyeon.checkservicerunningwithbroadcastapplication;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TestService extends Service {

    private static final String TAG = "TAG";

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ApplicationIntentActionEnum.IS_SERVICE_RUNNING.getAction())) {
                Intent responseIntent = new Intent(ApplicationIntentActionEnum.SERVICE_IS_RUNNING.getAction());
                LocalBroadcastManager.getInstance(TestService.this).sendBroadcast(responseIntent);
            }
        }
    };

    private Notification notification;

    private IBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "Test Service : onCreate");

        notification =
                new Notification.Builder(this, ((TestApplication) getApplication()).getChannelId())
                        .setContentTitle("Test Service")
                        .setContentText("Test Service Running")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .build();

        binder = new TestServiceBinder(this);

        // INFO LocalBroadcastManager is deprecated
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(ApplicationIntentActionEnum.IS_SERVICE_RUNNING.getAction()));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Test Service : onStartCommand");

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Test Service : onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "Test Service : onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "Test Service : onDestroy");

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
