package com.example.vahanasiignment.repository

import com.example.vahanasiignment.api.RetrofitInstance

class UniversityRepository {

    suspend fun getUniversity()=RetrofitInstance.universityApiService.getUniversity()
}