package com.example.remote.api


import com.example.remote.BuildConfig
import com.example.remote.model.UniversityResponseNetwork
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {


    @GET("search")
    suspend fun getUniversity(
        @Query("country") city : String = "United Arab Emirates",
    ) : UniversityResponseNetwork

}