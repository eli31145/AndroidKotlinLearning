package com.example.exercise802_catagentforegroundservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RouteTrackingService: Service() {

    private lateinit var notificationBuilder:NotificationCompat.Builder
    //used to post work in the background
    private lateinit var serviceHandler: Handler

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        notificationBuilder = startForegroundService()
        val handlerThread = HandlerThread("Handler Thread Route Tracking").apply {
            start()
        }
        serviceHandler = Handler(handlerThread.looper)
    }


    //intent launched if the user taps on the notification
    private fun getPendingIntent() = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE)

    //channels used to group notifications and allow users to filter unwanted notifications
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channelId = "routeTracking"
        val channelName = "Route Tracking"
        //NotificationChannel instance
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //channel is created using NotificationService with data provided in NotificationChannel instance
        service.createNotificationChannel(channel)
        //channelId returned so it can be used to construct Notification
        return channelId
    }

    //constructs NotificationCompat.Builder
    private fun getNotificationBuilder(pendingIntent: PendingIntent, channelId: String) =
        NotificationCompat.Builder(this, channelId)
            .setContentTitle("Agent approaching destination")
            .setContentText("Agent dispatched")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setTicker("Agent dispatched, tracking movement")

    private fun startForegroundService(): NotificationCompat.Builder {
        val pendingIntent = getPendingIntent()
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        } else {
            ""
        }
        val notificationBuilder = getNotificationBuilder(pendingIntent, channelId)
        //start service as ForegroundService, providing with NotificationId and notification built using builder
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
        //used later to update notification
        return notificationBuilder
    }

    private fun trackToDestination(notificationBuilder: NotificationCompat.Builder){
        for (i in 10 downTo 0){
            Thread.sleep(1000)
            notificationBuilder.setContentText("$i seconds to destination")
            startForeground(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun notifyCompletion(agentId: String){
        //make sure that updates occur on main (UI) app thread by posting handler using main looper
        Handler(Looper.getMainLooper()).post {
            //notifying all observers that agentId has reached destination
            mutableTrackingCompletion.value = agentId
        }
    }

    //add functionality when notification service clicked to launch intent
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //super command internally calls onStart()
        val returnValue =  super.onStartCommand(intent, flags, startId)
        val agentId = intent?.getStringExtra(EXTRA_SECRET_CAT_AGENT_ID) ?: throw IllegalStateException("Agent Id must be provided")

        serviceHandler.post {
            trackToDestination(notificationBuilder)
            notifyCompletion(agentId)
            //stop foreground service and remove notification by passing 'true'
            stopForeground(true)
            stopSelf()
        }
        return returnValue
    }


    companion object {
        const val NOTIFICATION_ID = 0xCA7
        const val EXTRA_SECRET_CAT_AGENT_ID = "scaId"

        //hiding mutable LiveData instance behind immutable LiveData so consumers can only read
        //LiveData used to communicate with service
        private val mutableTrackingCompletion = MutableLiveData<String>()
        val trackingCompletion: LiveData<String> = mutableTrackingCompletion
    }
}