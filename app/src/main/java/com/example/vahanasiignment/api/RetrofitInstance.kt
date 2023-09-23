package com.example.vahanasiignment.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create



object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("http://universities.hipolabs.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val universityApiService: UniversityApi by lazy {
        retrofit.create(UniversityApi::class.java)
    }
}
//    val apiInterface by lazy {
//        retrofit.create(UniversityApi::class.java)
//    }
