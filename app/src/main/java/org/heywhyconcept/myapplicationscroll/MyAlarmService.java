package org.heywhyconcept.myapplicationscroll;

/**
 * Created by Admin on 1/19/2016.
 */


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyAlarmService extends Service
{

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        List<String> name=new ArrayList<>();
        List<String> title=new ArrayList<>();
        List<String> date=new ArrayList<>();
        super.onStart(intent, startId);

        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(),MainActivity.class);



        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);


        Notification.Builder builder = new Notification.Builder(MyAlarmService.this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);


       DataBaseAccess databaseAccess = DataBaseAccess.getInstance(this);
        databaseAccess.open();
        name=databaseAccess.getQuotes();
        title=databaseAccess.getTitles();
        date=databaseAccess.getDate();
        DateFormat df = new SimpleDateFormat("MMM d");
        String now = df.format(new Date());


        int count=0;
        for (int i=0;i<=date.size();i++)
        {

            if (now.equals(date.get(i)) || date.get(i)==date.get(date.size()-1)) {

                break;
            }
            count++;
        }

        builder.setContentTitle(title.get(count));
        builder.setContentIntent(pendingNotificationIntent);
        Notification notification = builder.getNotification();
        mManager.notify(R.mipmap.ic_launcher, notification);


    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();

    }


}