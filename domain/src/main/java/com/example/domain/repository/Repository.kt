package com.example.domain.repository

import com.example.common.Resource
import com.example.domain.entity.UniversityEntity
import kotlinx.coroutines.flow.Flow

/**
 * Methods of Repository
 */
interface Repository {

    suspend fun getUniversity(city:String) : Flow<Resource<List<UniversityEntity>>>

}