package com.example.vahanasiignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.vahanasiignment.repository.UniversityRepository

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: UniversityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val viewModelProviderFactory = UniversityViewModelFactoryProvider(universityRepository = UniversityRepository())
//        viewModel = ViewModelProvider(
//            this,
//            viewModelProviderFactory
//        )[UniversityViewModel::class.java]
        viewModel = UniversityViewModel.getInstance(UniversityRepository())


        val serviceIntent = Intent(this, DataRefreshService::class.java)
        startService(serviceIntent)



    }

}