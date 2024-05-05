package com.example.feature.utils

import com.example.domain.entity.UniversityEntity
import com.example.model.UniversityUiModel

class TestDataGenerator {

    companion object {
        fun generateUniversityItems() : List<UniversityEntity>{
            return listOf(
                UniversityEntity("ae", "london", listOf(), "name","04d", listOf())
            )
        }
    }

}