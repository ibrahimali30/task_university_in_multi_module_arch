package com.example.data.utils

import com.example.data.model.UniversityItemDTO
import com.example.domain.entity.UniversityEntity


class TestDataGenerator {

    companion object {
        fun generateUniversity() : List<UniversityItemDTO> {
            return listOf(
                UniversityItemDTO("ae", "london", listOf(), "name","04d", listOf())

            )
        }




        fun generateUniversityItems() : List<UniversityItemDTO> {
            return listOf(
//                UniversityItemDTO(1, "United Arab Emirates", "desc",299.57,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691),
//                UniversityItemDTO(1, "United Arab Emirates", "desc",299.57,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691),
//                UniversityItemDTO(1, "United Arab Emirates", "desc",299.57,"04d",12.0,9.0,12.5,1021,52,0.0,1640148442,1640148442,1640183691)
                UniversityItemDTO("ae", "london", listOf(), "name 1","04d", listOf()),
                UniversityItemDTO("ae", "london", listOf(), "name 2","04d", listOf()),
                UniversityItemDTO("ae", "london", listOf(), "name 3","04d", listOf()),
            )
        }

    }

}