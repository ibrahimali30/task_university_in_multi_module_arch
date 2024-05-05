package com.example.task.di

import com.example.common.Mapper
import com.example.data.mapper.UniversityDataDomainMapper
import com.example.data.model.UniversityItemDTO
import com.example.domain.entity.UniversityEntity
import com.example.task.mapper.UniversityDomainUiMapper
import com.example.model.UniversityUiModel
import com.example.local.model.UniversityLocalModel
import com.example.local.mapper.UniversityLocalDataMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that holds Mappers
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    //region Locale Mappers
    @Binds
    abstract fun bindsUniversityLocalDataMapper(mapper : UniversityLocalDataMapper) : Mapper<UniversityLocalModel, UniversityItemDTO>
    //endregion


    //region Data Mappers
    @Binds
    abstract fun bindsUniversityDataDomainMapper(mapper : UniversityDataDomainMapper) : Mapper<UniversityItemDTO, UniversityEntity>
    //endregion


    //region Presentation Mappers
    @Binds
    abstract fun bindsUniversityDomainUiMapper(mapper : UniversityDomainUiMapper) : Mapper<UniversityEntity, UniversityUiModel>
    //endregion


    //region Remote Mappers
//    @Binds
//    abstract fun bindsUniversityNetworkDataMapper(mapper: UniversityNetworkDataMapper): Mapper<UniversityResponseNetwork, List<UniversityItemDTO>>
    //endregion

}