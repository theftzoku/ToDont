package rocks.poopjournal.todont

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val channel =
            NotificationChannel("Tasks", "tasks reminder", NotificationManager.IMPORTANCE_HIGH)
        val manager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        )
        manager!!.createNotificationChannel(channel)
        val task = intent.getStringExtra("task")
        // Create notification when the alarm is triggered
        val pendingIntent = Intent(context, MainActivity::class.java)
        pendingIntent.putExtra("openLog", true)
        val builder = NotificationCompat.Builder(context, "Tasks")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.todon_t_reminder))
            .setContentText(context.getString(R.string.mark_your_todon_t_task)+task)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    pendingIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            ) // Pass the pending intent here


        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(1, builder.build())
    }
}

