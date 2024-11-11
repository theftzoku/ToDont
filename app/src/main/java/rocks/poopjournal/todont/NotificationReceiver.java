package rocks.poopjournal.todont;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationChannel channel = new NotificationChannel("Tasks", "tasks reminder", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = getSystemService(context,NotificationManager.class);
        manager.createNotificationChannel(channel);
        String task=intent.getStringExtra("task");
        // Create notification when the alarm is triggered
        Intent pendingIntent=new Intent(context,MainActivity.class);
        pendingIntent.putExtra("openLog",true);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Tasks")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ToDon't Reminder")
                .setContentText("Mark your ToDon't task "+task)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(context, 0, pendingIntent, PendingIntent.FLAG_IMMUTABLE)); // Pass the pending intent here


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}

