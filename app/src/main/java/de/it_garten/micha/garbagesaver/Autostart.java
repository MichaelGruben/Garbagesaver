package de.it_garten.micha.garbagesaver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by micha on 22.07.16.
 */
public class Autostart extends BroadcastReceiver {
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
        String[] datesResidual = {
                "08.01.2016",
                "21.01.2016",
                "04.02.2016",
                "18.02.2016",
                "03.03.2016",
                "17.03.2016",
                "01.04.2016",
                "14.04.2016",
                "28.04.2016",
                "12.05.2016",
                "27.05.2016",
                "09.06.2016",
                "23.06.2016",
                "07.07.2016",
                "21.07.2016",
                "04.08.2016",
                "19.08.2016",
                "01.09.2016",
                "15.09.2016",
                "29.09.2016",
                "13.10.2016",
                "27.10.2016",
                "10.11.2016",
                "24.11.2016",
                "08.12.2016",
                "22.12.2016"
        };

        String[] datesOrganic = {
                "14.01.2016",
                "28.01.2016",
                "11.02.2016",
                "25.02.2016",
                "10.03.2016",
                "23.03.2016",
                "07.04.2016",
                "21.04.2016",
                "06.05.2016",
                "20.05.2016",
                "02.06.2016",
                "16.06.2016",
                "30.06.2016",
                "14.07.2016",
                "28.07.2016",
                "11.08.2016",
                "25.08.2016",
                "08.09.2016",
                "22.09.2016",
                "07.10.2016",
                "20.10.2016",
                "04.11.2016",
                "17.11.2016",
                "01.12.2016",
                "15.12.2016",
                "30.12.2016"
        };

        String[] datesPaper = {
                "21.01.2016",
                "16.02.2016",
                "15.03.2016",
                "12.04.2016",
                "10.05.2016",
                "07.06.2016",
                "05.07.2016",
                "02.08.2016",
                "30.08.2016",
                "27.09.2016",
                "25.10.2016",
                "22.11.2016",
                "20.12.2016"
        };

        String[] datesYellow = {
                "07.01.2016",
                "03.02.2016",
                "02.03.2016",
                "31.03.2016",
                "27.04.2016",
                "25.05.2016",
                "22.06.2016",
                "20.07.2016",
                "18.08.2016",
                "14.09.2016",
                "12.10.2016",
                "09.11.2016",
                "07.12.2016"
        };

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        long today=0;
        try {
            today = sdf.parse(sdf.format(new Date())).getTime();
        } catch (java.text.ParseException e) {
            today = 0;
        }

        String notificationText="";
        String infoTail="";
        for(int i=3;i>=0;i--){
            switch (i){
                case 3:infoTail="in 3 Tagen -- ";
                    break;
                case 2:infoTail="in 2 Tagen -- ";
                    break;
                case 1:infoTail="morgen -- ";
                    break;
                case 0:infoTail="heute -- ";
                    break;
            }
            for (String date: datesResidual) {
                Date datestring=new Date();
                try {
                    datestring = sdf.parse(date);
                } catch (java.text.ParseException e) {
                }

                if(today == datestring.getTime() - i * 1000 * 60 * 60 * 24) {
                    notificationText+= "Restmüll "+infoTail;
                    break;
                }
            }
            for (String date: datesOrganic) {
                Date datestring=new Date();
                try {
                    datestring = sdf.parse(date);
                } catch (java.text.ParseException e) {
                }
                if(today == datestring.getTime() - i * 1000 * 60 * 60 * 24) {
                    notificationText+= "Biomüll "+infoTail;
                    break;
                }
            }
            for (String date: datesPaper) {
                Date datestring=new Date();
                try {
                    datestring = sdf.parse(date);
                } catch (java.text.ParseException e) {
                }
                if(today == datestring.getTime() - i * 1000 * 60 * 60 * 24) {
                    notificationText+= "Papiermüll "+infoTail;
                    break;
                }
            }
            for (String date: datesYellow) {
                Date datestring=new Date();
                try {
                    datestring = sdf.parse(date);
                } catch (java.text.ParseException e) {
                }
                if(today == datestring.getTime() - i * 1000 * 60 * 60 * 24) {
                    notificationText+= "Gelber Sack "+infoTail;
                    break;
                }
            }
        }

        if(!notificationText.equals("")) {
            Intent resultIntent = new Intent(this.context, Show_Dates.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(Show_Dates.class);
            stackBuilder.addNextIntent(resultIntent);

            PendingIntent contentIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            //PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, Show_Dates.class), 0);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_garbage_notify)
                            .setContentTitle("Bald wird Müll abgeholt!")
                            .setContentText(notificationText);
            mBuilder.setContentIntent(contentIntent);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setAutoCancel(true);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }

    }
}
}
