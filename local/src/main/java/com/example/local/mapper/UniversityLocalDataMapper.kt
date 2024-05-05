package com.example.local.mapper

import com.example.common.Mapper
import com.example.data.model.UniversityItemDTO
import com.example.local.model.UniversityLocalModel
import javax.inject.Inject

class UniversityLocalDataMapper @Inject constructor() : Mapper<UniversityLocalModel, UniversityItemDTO> {

    override fun from(i: UniversityLocalModel?): UniversityItemDTO {
        return UniversityItemDTO(
            alpha_two_code = i?.alpha_two_code ?: "",
            country = i?.country?: "",
            domains = i?.domains?: listOf(),
            name = i?.name?: "",
            state_province = i?.state_province?: "",
            web_pages = i?.web_pages?: listOf(),
        )
    }

    override fun to(o: UniversityItemDTO?): UniversityLocalModel {
        return UniversityLocalModel(
            alpha_two_code = o?.alpha_two_code ?: "",
            country = o?.country?: "",
            domains = o?.domains?: listOf(),
            name = o?.name?: "",
            state_province = o?.state_province?: "",
            web_pages = o?.web_pages?: listOf(),
        )
    }
}