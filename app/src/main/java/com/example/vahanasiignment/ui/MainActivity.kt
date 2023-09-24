package com.example.vahanasiignment.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.vahanasiignment.refresher.DataRefreshService
import com.example.vahanasiignment.R
import com.example.vahanasiignment.repository.UniversityRepository
import com.example.vahanasiignment.ui.fragments.UniversityViewModelFactoryProvider

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: UniversityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            // Request notification permission here
            showNotificationPermissionDialog()
        }


        val viewModelProviderFactory = UniversityViewModelFactoryProvider(application,universityRepository = UniversityRepository())
        viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        )[UniversityViewModel::class.java]
        //viewModel = UniversityViewModel.getInstance(UniversityRepository())


        val serviceIntent = Intent(this, DataRefreshService::class.java)
        startService(serviceIntent)




    }

    private fun showNotificationPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Notification Permission Required")
            .setMessage("This app requires notification permission to function properly. Please enable notifications in app settings.")
            .setPositiveButton("Open App Settings") { _, _ ->
                // Open app settings so the user can enable notifications
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // Handle the case where the user cancels the dialog
                dialog.dismiss()
                // You can choose to take specific actions here
            }
            .setCancelable(false)
            .show()
    }

}