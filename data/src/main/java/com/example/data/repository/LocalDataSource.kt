package com.example.data.repository

import com.example.data.model.UniversityItemDTO


/**
 * Methods of Local Data Source
 */
interface LocalDataSource {

    suspend fun addItems(universitys: List<UniversityItemDTO>) : List<Long>

    suspend fun getItems(): List<UniversityItemDTO>

}