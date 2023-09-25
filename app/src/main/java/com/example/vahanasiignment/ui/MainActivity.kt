package com.example.vahanasiignment.ui

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.vahanasiignment.refresher.DataRefreshService
import com.example.vahanasiignment.R
import com.example.vahanasiignment.databinding.ActivityMainBinding
import com.example.vahanasiignment.repository.UniversityRepository
import com.example.vahanasiignment.ui.fragments.UniversityViewModelFactoryProvider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: UniversityViewModel
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        onClickRequestPermission(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.title = ""
        setSupportActionBar(binding.toolbar)
        /*if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            // Request notification permission here
            showNotificationPermissionDialog()
        }*/


        val viewModelProviderFactory = UniversityViewModelFactoryProvider(application,universityRepository = UniversityRepository())
        viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        )[UniversityViewModel::class.java]
        //viewModel = UniversityViewModel.getInstance(UniversityRepository())


        val serviceIntent = Intent(this, DataRefreshService::class.java)
        startService(serviceIntent)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notification_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            /*R.id.notifaction_Off->{
                if(isMyServiceRunning()){
                    val serviceIntent = Intent(this, DataRefreshService::class.java)
                    stopService(serviceIntent)
                }
                return true
            }*/
            R.id.notifaction_On->{
                val serviceIntent = Intent(this, DataRefreshService::class.java)
                if (!isMyServiceRunning()){
                    startService(serviceIntent)

                }else{
                    stopService(serviceIntent)

                }
                return true

            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun isMyServiceRunning(): Boolean{

        val manager: ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE) as ActivityManager

        for (service : ActivityManager.RunningServiceInfo in
        manager.getRunningServices(Integer.MAX_VALUE)){

            if(DataRefreshService::class.java.name == service.service.className){
                return true
            }

        }
        return false

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("Permission: ", "Granted")
        } else {
            Log.d("Permission: ", "Denied")
        }
    }

    private fun onClickRequestPermission(view: View) {
        when {
            ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("Permission: ", "Granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                Snackbar.make(
                    view,
                    "Allow Notification",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("OK") {
                    requestPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }.setActionTextColor(resources.getColor(android.R.color.holo_red_light)).show()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
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