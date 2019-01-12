package com.africa3d.keeprawteach.quotes.Messaging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class Message extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        String refresh= FirebaseInstanceId.getInstance().getToken();

        Log.d("TAG","Refreshed token");

        sendRegistrationToServer(refresh);

        super.onTokenRefresh();
    }

    private void sendRegistrationToServer(String refresh) {
    }
}
