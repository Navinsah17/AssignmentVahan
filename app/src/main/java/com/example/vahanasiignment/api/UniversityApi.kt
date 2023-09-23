package com.example.vahanasiignment.api

import com.example.vahanasiignment.models.University
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface UniversityApi {
    @GET("search")
    suspend fun getUniversity(): Response<List<University>>

}