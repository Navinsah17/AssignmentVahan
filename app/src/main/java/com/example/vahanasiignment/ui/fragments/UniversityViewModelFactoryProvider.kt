package com.example.vahanasiignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vahanasiignment.repository.UniversityRepository

class UniversityViewModelFactoryProvider(
    val universityRepository: UniversityRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UniversityViewModel(universityRepository) as T
    }
}
