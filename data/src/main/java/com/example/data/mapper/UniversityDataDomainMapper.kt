package com.example.data.mapper

import com.example.common.Mapper
import com.example.data.model.UniversityItemDTO
import com.example.domain.entity.UniversityEntity
import javax.inject.Inject


class UniversityDataDomainMapper @Inject constructor() :
    Mapper<UniversityItemDTO, UniversityEntity> {

    override fun from(i: UniversityItemDTO?): UniversityEntity {
        return UniversityEntity(
            alpha_two_code = i?.alpha_two_code ?: "",
            country = i?.country?: "",
            domains = i?.domains?: listOf(),
            name = i?.name?: "",
            state_province = i?.state_province?: "",
            web_pages = i?.web_pages?: listOf(),
        )
    }


    override fun to(o: UniversityEntity?): UniversityItemDTO {
        return UniversityItemDTO(
            alpha_two_code = o?.alpha_two_code ?: "",
            country = o?.country?: "",
            domains = o?.domains?: listOf(),
            name = o?.name?: "",
            state_province = o?.state_province?: "",
            web_pages = o?.web_pages?: listOf(),
        )
    }
}