package com.example.tallesrodrigues.medicament11;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by TallesRodrigues on 8/23/2016.
 */
public class NotificationReceiver extends Activity {

    public static final String NOTIFICATION_ID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //manager.cancel(getIntent().getIntExtra(NOTIFICATION_ID, -1));
        manager.cancelAll();
        finish(); // since finish() is called in onCreate(), onDestroy() will be called immediately

    }

    public static PendingIntent getDismissIntent(int notificationId, Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(NOTIFICATION_ID, notificationId);
        PendingIntent dismissIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return dismissIntent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
