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
import com.example.vahanasiignment.refresher.DataRefreshService
import com.example.vahanasiignment.R
import com.example.vahanasiignment.repository.UniversityRepository

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: UniversityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)



        viewModel = UniversityViewModel.getInstance(UniversityRepository())


        val serviceIntent = Intent(this, DataRefreshService::class.java)
        startService(serviceIntent)




    }


}