package com.example.remote.mapper

import com.example.common.Mapper
import com.example.data.model.UniversityItemDTO
import com.example.remote.model.UniversityResponseNetwork
import com.example.remote.model.UniversityResponseNetworkItem
import javax.inject.Inject


class UniversityNetworkDataMapper @Inject constructor() :
    Mapper<List<UniversityResponseNetworkItem>, List<UniversityItemDTO>> {

    override fun from(i: List<UniversityResponseNetworkItem>?): List<UniversityItemDTO> {
        return i?.map {
            UniversityItemDTO(
                alpha_two_code = it.alpha_two_code,
                country = it.country,
                domains = it.domains,
                name = it.name,
                state_province = it.state_province,
                web_pages = it.web_pages,
            )
        } ?: listOf()
    }

    override fun to(o: List<UniversityItemDTO>?): UniversityResponseNetwork {
        return UniversityResponseNetwork()
    }
}