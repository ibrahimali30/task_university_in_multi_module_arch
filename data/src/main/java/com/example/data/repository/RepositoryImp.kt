package com.example.data.repository

import com.example.common.Mapper
import com.example.common.Resource
import com.example.data.model.UniversityItemDTO
import com.example.domain.entity.UniversityEntity
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation class of [Repository]
 */
class RepositoryImp @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val universityMapper : Mapper<UniversityItemDTO, UniversityEntity>,
) : Repository {


    override suspend fun getUniversity(city:String): Flow<Resource<List<UniversityEntity>>> {
        return flow {
            try {
                // Get data from RemoteDataSource
                val data = remoteDataSource.getUniversity(city)
                // Save to local or update if exist
                localDataSource.addItems(data)

                // Emit data

                emit(Resource.Success(data.map { universityMapper.from(it) }))
            } catch (ex : Exception) {
                ex.printStackTrace()
                // If remote request fails
                try {
                    // Get data from LocalDataSource
                    val local = localDataSource.getItems()
                    if (local.isNotEmpty()){
                        // Emit data
                        emit(Resource.Success(local.map { universityMapper.from(it) }))
                    }else{
                        emit(Resource.Error(Exception("Empty List")))
                    }
                } catch (ex1 : Exception) {
                    // Emit error
                    emit(Resource.Error(ex1))
                }
            }
        }
    }
}