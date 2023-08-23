package dev.ehyeon.checkservicerunningwithbroadcastapplication;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ApplicationIntentActionEnum.SERVICE_IS_RUNNING.getAction())) {
                bindService(new Intent(MainActivity.this, TestService.class), serviceConnection, BIND_AUTO_CREATE);
            }
        }
    };

    private final MutableLiveData<TestService> testServiceMutableLiveData = new MutableLiveData<>();

    private final ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            testServiceMutableLiveData.setValue(((TestServiceBinder) service).getTestService());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            testServiceMutableLiveData.setValue(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        // INFO LocalBroadcastManager is deprecated
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(ApplicationIntentActionEnum.SERVICE_IS_RUNNING.getAction()));

        LocalBroadcastManager
                .getInstance(this)
                .sendBroadcast(new Intent(ApplicationIntentActionEnum.IS_SERVICE_RUNNING.getAction()));

        testServiceMutableLiveData.observe(this, testService -> textView.setText(testService != null ? "RUNNING" : "NOT RUNNING"));

        button.setOnClickListener(ignored -> {
            Intent testServiceIntent = new Intent(this, TestService.class);

            if (testServiceMutableLiveData.getValue() != null) {
                unbindService(serviceConnection);

                if (stopService(testServiceIntent)) {
                    testServiceMutableLiveData.setValue(null);
                }
            } else {
                if (startForegroundService(testServiceIntent) != null) {
                    bindService(testServiceIntent, serviceConnection, BIND_AUTO_CREATE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
