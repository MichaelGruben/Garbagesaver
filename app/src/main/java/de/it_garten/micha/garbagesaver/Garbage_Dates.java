package de.it_garten.micha.garbagesaver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by micha on 21.10.16.
 */
public class Garbage_Dates {

    public static String[] residualDates={
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

    public static String[] organicDates = {
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

    public static String[] paperDates = {
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

    public static String[] yellowDates = {
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

    /**
     * @param context
     * @return
     */
    public static void notifyGarbageDate(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        long today = 0;
        try {
            today = sdf.parse(sdf.format(new Date())).getTime();
        } catch (java.text.ParseException e) {
            today = 0;
        }

        String notificationText = "";
        String infoTail = "";
        for (int i = 3; i >= 0; i--) {
            switch (i) {
                case 3:
                    infoTail = "in 3 Tagen -- ";
                    break;
                case 2:
                    infoTail = "in 2 Tagen -- ";
                    break;
                case 1:
                    infoTail = "morgen -- ";
                    break;
                case 0:
                    infoTail = "heute -- ";
                    break;
            }
            for (String date : Garbage_Dates.residualDates) {
                try {
                    if (today == sdf.parse(date).getTime() - i * 1000 * 60 * 60 * 24) {
                        notificationText += "Restmüll " + infoTail;
                        break;
                    }
                } catch (java.text.ParseException e) {
                    today = 0;
                }
            }
            for (String date : Garbage_Dates.organicDates) {
                try {
                    if (today == sdf.parse(date).getTime() - i * 1000 * 60 * 60 * 24) {
                        notificationText += "Biomüll " + infoTail;
                        break;
                    }
                } catch (java.text.ParseException e) {
                    today = 0;
                }
            }
            for (String date : Garbage_Dates.paperDates) {
                try {
                    if (today == sdf.parse(date).getTime() - i * 1000 * 60 * 60 * 24) {
                        notificationText += "Papiermüll " + infoTail;
                        break;
                    }
                } catch (java.text.ParseException e) {
                    today = 0;
                }
            }
            for (String date : Garbage_Dates.yellowDates) {
                try {
                    if (today == sdf.parse(date).getTime() - i * 1000 * 60 * 60 * 24) {
                        notificationText += "Gelber Sack " + infoTail;
                        break;
                    }
                } catch (java.text.ParseException e) {
                    today = 0;
                }
            }
        }


        if (notificationText != "") {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_icon_garbage)
                            .setContentTitle("Bald wird Müll abgeholt!")
                            .setContentText(notificationText);
            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(context, Show_Dates.class);
            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(Show_Dates.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            //mBuilder.setAutoCancel(true);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            mNotificationManager.notify(0, mBuilder.build());
        }
    }
}
