package com.karol.vimos.gym;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Ashwath on 24-11-2016.
 */


public class FireApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);

    }


}
