package dev.ehyeon.checkservicerunningwithbroadcastapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class TestApplication extends Application {

    private String channelId;

    @Override
    public void onCreate() {
        super.onCreate();

        channelId = "channelId";

        NotificationChannel serviceChannel = new NotificationChannel(
                channelId,
                "channelName",
                NotificationManager.IMPORTANCE_DEFAULT
        );

        getSystemService(NotificationManager.class).createNotificationChannel(serviceChannel);
    }

    public String getChannelId() {
        return channelId;
    }
}
