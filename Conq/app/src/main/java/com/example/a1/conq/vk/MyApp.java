package com.example.a1.conq.vk;


import android.content.Intent;
import com.example.a1.conq.MainActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class MyApp extends  android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        VKSdk.initialize(this);

    }
    public class Application extends android.app.Application {
        VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
            @Override
            public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
                if (newToken == null) {
                    Intent intent = new Intent(MyApp.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                }
            }
        };
        @Override
        public void onCreate() {
            super.onCreate();
            vkAccessTokenTracker.startTracking();
            VKSdk.initialize(this);
        }
    }
}
