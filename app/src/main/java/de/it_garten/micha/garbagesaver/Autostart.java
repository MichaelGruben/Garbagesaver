package de.it_garten.micha.garbagesaver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by micha on 22.07.16.
 * Task to do on gadged startup
 */
public class Autostart extends BroadcastReceiver {

    /**
     * What to do gadged startup when a Broadcast is send
     * @param context
     * @param arg1
     */
    @Override
    public void onReceive(Context context, Intent arg1) {
        TimerTask showNotification = new ShowNotification(context);
        showNotification.run();

        Timer timer = new Timer();
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR, 0);
        date.set(Calendar.MINUTE, 30);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        // Schedule to run every Sunday in midnight
        timer.schedule(
                showNotification,
                date.getTime(),
                1000 * 60 * 60 * 24 * 7
        );
    }

    private class ShowNotification extends TimerTask {
        Context context;
        public ShowNotification (Context context){
            this.context=context;
        }

        @Override
        public void run(){
            //Set Notification
            Garbage_Dates.notifyGarbageDate(context);
    }
}
}
