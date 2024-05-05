package com.example.local.source

import com.example.common.Mapper
import com.example.data.model.UniversityItemDTO
import com.example.data.repository.LocalDataSource
import com.example.local.database.UniversityDAO
import com.example.local.model.UniversityLocalModel
import javax.inject.Inject

/**
 * Implementation of [LocalDataSource] Source
 */
class LocalDataSourceImp @Inject constructor(
    private val universityDAO: UniversityDAO,
    private val universityMapper : Mapper<UniversityLocalModel, UniversityItemDTO>
) : LocalDataSource {

    override suspend fun addItems(weahterList: List<UniversityItemDTO>): List<Long> {
        val universityLocalList = universityMapper.toList(weahterList)
        return universityDAO.addUniversityItems(university = universityLocalList)
    }

    override suspend fun getItems(): List<UniversityItemDTO> {

        val universityLocalList = universityDAO.getUniversityItems()
        return universityMapper.fromList(universityLocalList)
    }

}

