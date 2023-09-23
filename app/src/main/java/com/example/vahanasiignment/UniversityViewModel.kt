package com.example.vahanasiignment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vahanasiignment.models.University
import com.example.vahanasiignment.repository.UniversityRepository
import com.example.vahanasiignment.utils.Resource
import kotlinx.coroutines.launch

class UniversityViewModel(val universityRepository: UniversityRepository):ViewModel() {
    var universityList: MutableLiveData<Resource<List<University>>> = MutableLiveData()

    init {
        getUniversity()
    }

    companion object {
        private var instance: UniversityViewModel? = null

        fun getInstance(universityRepository: UniversityRepository): UniversityViewModel {
            if (instance == null) {
                instance = UniversityViewModel(universityRepository)
            }
            return instance!!
        }
    }

    fun getUniversity() = viewModelScope.launch {
        universityList.postValue(Resource.Loading())
        val response = universityRepository.getUniversity()
        if (response.isSuccessful) {
            response.body()?.let { result ->
                universityList.postValue(Resource.Success(result))
                Log.d("TAG", result[0].name)
            }
        }

    }

}