package ro.scoala.mytodolist.presentation.notification;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;

public class TodoListNotificationFactory {
    public static Notification createNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, TodoListNotificationChannelFactory.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);

        return builder.build();
    }

}
