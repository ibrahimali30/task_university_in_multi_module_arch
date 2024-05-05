package com.example.data.repository

import com.example.data.model.UniversityItemDTO

/**
 * Methods of Remote Data Source
 */
interface RemoteDataSource {

    suspend fun getUniversity(city:String) : List<UniversityItemDTO>

}