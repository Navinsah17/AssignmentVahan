package com.example.vahanasiignment.ui.fragments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vahanasiignment.repository.UniversityRepository
import com.example.vahanasiignment.ui.UniversityViewModel

class UniversityViewModelFactoryProvider(
    val app: Application,
    val universityRepository: UniversityRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UniversityViewModel(app,universityRepository) as T
    }
}
