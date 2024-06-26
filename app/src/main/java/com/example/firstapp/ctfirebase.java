package com.example.firstapp;

import android.app.Service;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.text.BidiFormatter;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.pushnotification.NotificationInfo;
import com.clevertap.android.sdk.pushnotification.fcm.CTFcmMessageHandler;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class ctfirebase extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        try {
            if (message.getData().size() > 0) {
                Bundle extras = new Bundle();
                for (Map.Entry<String, String> entry : message.getData().entrySet()) {
                    extras.putString(entry.getKey(), entry.getValue());
                }

                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);
                Thread.sleep(5000);

                if (info.fromCleverTap || extras.containsKey("pt_id")) {
                    new CTFcmMessageHandler().createNotification(getApplicationContext(), message);
                } else {
                    // not from CleverTap handle yourself or pass to another provider
                    Log.d("clevertap", "not from cleverTap");
                }
            }
        } catch (Throwable t) {
            Log.d("MYFCMLIST", "Error parsing FCM message", t);
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

    }
}
