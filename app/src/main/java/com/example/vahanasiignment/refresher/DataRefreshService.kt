package com.example.vahanasiignment.refresher

import android.app.*
import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.*
import com.example.vahanasiignment.R
import com.example.vahanasiignment.repository.UniversityRepository
import com.example.vahanasiignment.ui.UniversityViewModel
import com.google.android.material.snackbar.Snackbar

class DataRefreshService : LifecycleService() {



    private val channelId = "api_refresh_channel"
    private val notificationId = 101
    private val refreshInterval = 10000L // 10 seconds
    private val handler = Handler()
    private lateinit var universityRepository: UniversityRepository
    private lateinit var viewModel: UniversityViewModel
    private var isAppInForeground = false

    override fun onCreate() {
        super.onCreate()
        universityRepository = UniversityRepository() // Initialize your repository here.

        ProcessLifecycleOwner.get().lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onBackground() {
                isAppInForeground = false
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onForeground() {
                isAppInForeground = true
            }
        })


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "API Refresh Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        viewModel = UniversityViewModel(application,universityRepository)
        //viewModel = UniversityViewModel.getInstance(universityRepository)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("API Refresh Service")
            .setContentText("Refreshing API every 10 seconds")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Change this to your app's icon
            .build()


        startForeground(notificationId, notification)

        startRefreshTimer()
    }





    private var refreshHandler = Handler()
    private val refreshRunnable = object : Runnable {
        override fun run() {
            Log.d("APIRefresh", "Starting API refresh...")
            //Toast.makeText(this@DataRefreshService,"Updated!!", Toast.LENGTH_SHORT).show()
            if (isAppInForeground) {
                // Show toast only when the app is in the foreground
                Toast.makeText(this@DataRefreshService, "Updated!!", Toast.LENGTH_SHORT).show()
            }
            viewModel.getUniversity()
            Log.d("APIRefresh", "API refresh completed.")

            // Schedule the next refresh
            refreshHandler.postDelayed(this, refreshInterval)
        }
    }

    private fun startService() {
        // Implement the logic to start your service here

        // Create a notification channel (for Android Oreo and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "API Refresh Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Build and show the notification
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("API Refresh Service")
            .setContentText("Refreshing API every 10 seconds")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Change this to your app's icon
            .build()

        startForeground(notificationId, notification)

        // Initialize and start the refresh timer
        refreshHandler = Handler()
        refreshHandler.postDelayed(refreshRunnable, refreshInterval)
    }
// ...

    private fun startRefreshTimer() {
        refreshHandler.postDelayed(refreshRunnable, refreshInterval)
    }

    private fun stopRefreshTimer() {
        refreshHandler.removeCallbacks(refreshRunnable)
    }



    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(refreshRunnable)
    }

}