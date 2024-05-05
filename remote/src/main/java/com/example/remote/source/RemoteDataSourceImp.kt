package com.example.remote.source

import com.example.common.Mapper
import com.example.data.model.UniversityItemDTO
import com.example.data.repository.RemoteDataSource
import com.example.remote.api.ApiService
import com.example.remote.mapper.UniversityNetworkDataMapper
import com.example.remote.model.UniversityResponseNetwork
import javax.inject.Inject


class RemoteDataSourceImp @Inject constructor(
    private val apiService : ApiService,
    ) : RemoteDataSource {

    val universityMapper = UniversityNetworkDataMapper()


    override suspend fun getUniversity(city:String): List<UniversityItemDTO> {
        val networkData = apiService.getUniversity()
        return universityMapper.from(networkData)
    }
}
