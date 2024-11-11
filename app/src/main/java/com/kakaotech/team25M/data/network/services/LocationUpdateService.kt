package com.kakaotech.team25M.data.network.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.kakaotech.team25M.R
import com.kakaotech.team25M.ui.status.ReservationStatusActivity

class LocationUpdateService : Service() {
    private val locationUpdateLauncher: LocationUpdateLauncher by lazy {
        LocationUpdateLauncher(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())
        startLocationUpdate()

        Toast.makeText(this, "위치 정보를 공유합니다", Toast.LENGTH_SHORT).show()
        return START_STICKY
    }

    private fun startLocationUpdate(){
        locationUpdateLauncher.startLocationUpdate()
    }

    private fun removeLocationUpdate(){
        locationUpdateLauncher.stopLocationUpdate()
    }

    private fun createNotification(): Notification {
        createNotificationChannel()

        val intent = Intent(this, ReservationStatusActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("매니저 위치 공유 서비스")
            .setContentText("실시간 위치를 고객님과 공유하고 있습니다")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.marker)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        return notificationBuilder.build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        removeLocationUpdate()

        Toast.makeText(this, "위치 정보를 공유를 중단합니다", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "location_service_channel"
        private const val CHANNEL_NAME = "Location Service"
    }
}
