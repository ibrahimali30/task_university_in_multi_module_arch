package com.example.remote.utils

import com.example.remote.model.*

/**
 * Dummy data generator for tests
 */
class TestDataGenerator {

    companion object {
        fun generateUniversity(): UniversityResponseNetwork {
            return UniversityResponseNetwork().apply {
                addAll(
                    listOf(
                        UniversityResponseNetworkItem("ae", "london", listOf(), "name 1","04d", listOf()),
                        UniversityResponseNetworkItem("ae", "london", listOf(), "name 1","04d", listOf()),
                        UniversityResponseNetworkItem("ae", "london", listOf(), "name 1","04d", listOf()),
                        )
                )
            }
}
}

}