package com.example.remote

import androidx.test.filters.SmallTest
import com.example.data.repository.RemoteDataSource
import com.example.remote.api.ApiService
import com.example.remote.mapper.UniversityNetworkDataMapper
import com.example.remote.source.RemoteDataSourceImp
import com.google.common.truth.Truth
import com.example.remote.utils.TestDataGenerator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class RemoteDataSourceImpTest {

    @MockK
    private lateinit var apiService : ApiService
    private val universityNetworkDataMapper = UniversityNetworkDataMapper()

    private lateinit var remoteDataSource : RemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RemoteDataSourceImp before every test
        remoteDataSource = RemoteDataSourceImp(
            apiService = apiService,
        )
    }

    @Test
    fun test_get_University_success() = runBlockingTest {

        val universityNetwork = TestDataGenerator.generateUniversity()

        // Given
        coEvery { apiService.getUniversity(any()) } returns universityNetwork

        // When
        val result = remoteDataSource.getUniversity("United Arab Emirates")

        // Then
        coVerify { apiService.getUniversity(any()) }

        // Assertion
        val expected = universityNetworkDataMapper.from(universityNetwork)
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_University_fail() = runBlockingTest {

        // Given
        coEvery { apiService.getUniversity(any()) } throws Exception()

        // When
        remoteDataSource.getUniversity("United Arab Emirates")

        // Then
        coVerify { apiService.getUniversity(any()) }

    }
}