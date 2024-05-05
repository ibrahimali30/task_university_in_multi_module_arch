package com.example.local.utils

import com.example.data.model.UniversityItemDTO
import com.example.local.model.UniversityLocalModel

/**
 * Dummy data generator for tests
 */
class TestDataGenerator {

    companion object {
        fun generateUniversityItems() : List<UniversityLocalModel> {
            val item1 = UniversityLocalModel("ae", "london", listOf(), "name 1","04d", listOf())
            val item2 = UniversityLocalModel("ae", "london", listOf(), "name 1","04d", listOf())
            val item3 = UniversityLocalModel("ae", "london", listOf(), "name 1","04d", listOf())
            return listOf(item1, item2, item3)
        }

        fun generateUniversityItem() : UniversityLocalModel {
            return UniversityLocalModel("ae", "london", listOf(), "name 1","04d", listOf())
        }
    }

}