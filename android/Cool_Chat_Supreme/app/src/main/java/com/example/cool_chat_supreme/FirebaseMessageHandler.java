package com.example.cool_chat_supreme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cool_chat_supreme.ViewModels.ChatViewModel;
import com.example.cool_chat_supreme.ViewModels.ChatViewModelManager;
import com.example.cool_chat_supreme.ViewModels.MessageViewModel;
import com.example.cool_chat_supreme.ViewModels.MessageViewModelManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class FirebaseMessageHandler extends FirebaseMessagingService {

    private final static String CHANNEL_ID = "1";
    private final static String CHANNEL_NAME = "messages";
    private final static String CHANNEL_DESC = "display push messages";


    private ActivityLifecycleCallbacks activityLifecycleCallbacks =
            new ActivityLifecycleCallbacks();

    public FirebaseMessageHandler() {
    }

    ChatViewModel chatViewModel;
    MessageViewModel messageViewModel;


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        SharedPreferences sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        // if the user is not logged in, no need to show ant notifications
        String token = "";
        token = sharedPreferences.getString("token", "");
        if (token.equals("")) {
            return;
        }
        // if the remote message has no data, show no notification
        if (remoteMessage.getData() == null) {
            return;
        }

        Map<String, String> data = remoteMessage.getData();
        String key1 = data.get("key1");
        if (key1.equals("updateChats")) {
            // get a ChatViewModel and with it update the chats
            chatViewModel = ChatViewModelManager.getChatViewModelWithToken();
            if (chatViewModel == null) {
                return;
            }
            chatViewModel.updateChats();

        }
        if (key1.equals("newMessages")) {

            String key2 = data.get("key2");
            // get a MessageViewModel and update if needed
            String[] parts = key2.split(",");
            int chatId = Integer.parseInt(parts[0]);
            String username = parts[1];
            String messageContent = parts[2];
            // if currently on this chat, update its messages
            messageViewModel = MessageViewModelManager.getMessageViewModelWithToken();
            if (messageViewModel != null && messageViewModel.getChat_id() == chatId) {
                messageViewModel.updateMessages();
            }

            Intent intent = new Intent(this, ChatContactsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            if (ActivityLifecycleManager.getInstance().activityCount >= 0) {
                return;
            }
            // create a notification in the phone
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(username)
                    .setContentText(messageContent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            int notificationId = 1;
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            notificationManager.notify(notificationId, builder.build());
        }
    }

    public void createNotificationChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            // create a notification channel
            manager.createNotificationChannel(channel);
        }
    }
}