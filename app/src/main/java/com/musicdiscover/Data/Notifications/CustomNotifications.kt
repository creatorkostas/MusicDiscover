package com.musicdiscover.Data.Notifications

import android.R.attr.height
import android.R.attr.width
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.core.app.NotificationCompat
import com.musicdiscover.R
import java.io.FileNotFoundException
import kotlin.random.Random


data class ChannelInfo (
    val name_id: String,
    val channel_description: String,
    val importance: Int
)

class CustomNotifications(private val context: Context) {

    private val notificationManager =
        context.getSystemService(NotificationManager::class.java)

    fun createNotificationChannels(channels: List<ChannelInfo>){
        channels.forEach {
            createNotificationChannel(it.name_id,it.channel_description, it.importance)
        }
    }
    private fun createNotificationChannel(name_id: String, channel_description: String, importance: Int) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(name_id, name_id as CharSequence, importance).apply {
                description = channel_description
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun showBasicNotification(channel: String, title: String, content: String, importance: Int) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    fun showExpandableImageNotification(
        channel: String,
        title: String,
        content: String,
        @DrawableRes resId: Int,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            //.setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(context.bitmapFromResource(resId))
            )
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    fun showExpandableImageNotification(
        channel: String,
        title: String,
        content: String,
        imageUri: Uri?,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            //.setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(imageUri?.let { getBitmapFromUri(it) })
            )
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

//            rotateBitmap(bitmap, 90f)
            bitmap
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source,
            0,
            0,
            source.getWidth(),
            source.getHeight(),
            matrix,
            true
        )
    }

    fun showExpandableTextNotification(
        channel: String,
        title: String,
        content: String,
        expandedText: String,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            //.setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(expandedText)
            )
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    fun showActionNotification(
        channel: String,
        title: String,
        content: String,
        actionText: String,
        actionIntent: PendingIntent,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            //.setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .addAction(0, actionText, actionIntent)
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun Context.bitmapFromResource(@DrawableRes resId: Int) =
        BitmapFactory.decodeResource(resources, resId)
}