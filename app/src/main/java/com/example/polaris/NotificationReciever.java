package com.example.polaris;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.polaris.ApplicationClass.ACTION_NEXT;
import static com.example.polaris.ApplicationClass.ACTION_PLAY;
import static com.example.polaris.ApplicationClass.ACTION_PREVIOUS;

public class NotificationReciever extends BroadcastReceiver {

    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";
    public static final String ACTION_PREVIOUS = "actionprevious";
    public static final String ACTION_DELETE = "actiondelete";

    @Override
    public void onReceive(Context context, Intent intent) {
        String actionName = intent.getAction();
        Intent serviceIntent = new Intent(context, MusicService.class);
        if (actionName != null) {
            switch (actionName) {
                case ACTION_PLAY:
                    serviceIntent.putExtra("ActionName", "playPause");
                    context.startService(serviceIntent);
                    break;
                case ACTION_NEXT:
                    serviceIntent.putExtra("ActionName", "next");
                    context.startService(serviceIntent);
                    break;
                case ACTION_PREVIOUS:
                    serviceIntent.putExtra("ActionName", "previous");
                    context.startService(serviceIntent);
                    break;
                case ACTION_DELETE:
                    serviceIntent.putExtra("ActionName", "delete");
                    context.startService(serviceIntent);
                    break;
            }
        }
    }
}
