package com.example.data

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.Resource
import com.example.data.mapper.UniversityDataDomainMapper
import com.example.data.model.UniversityItemDTO
import com.example.data.repository.LocalDataSource
import com.example.data.repository.RemoteDataSource
import com.example.data.repository.RepositoryImp
import com.google.common.truth.Truth
import com.example.data.utils.TestDataGenerator
import com.example.domain.entity.UniversityEntity
import com.example.domain.repository.Repository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class RepositoryImpTest {

    @MockK
    private lateinit var localDataSource: LocalDataSource
    @MockK
    private lateinit var remoteDataSource: RemoteDataSource

    private val universityMapper = UniversityDataDomainMapper()

    private lateinit var repository : Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RepositoryImp before every test
        repository = RepositoryImp(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            universityMapper = universityMapper
        )
    }

    @Test
    fun test_get_posts_remote_success() = runBlockingTest {

        val universityItem = TestDataGenerator.generateUniversity()
        val affectedIds = listOf(1L)

        // Given
        coEvery { remoteDataSource.getUniversity("United Arab Emirates") } returns universityItem
        coEvery { localDataSource.addItems(universityItem) } returns affectedIds
        coEvery { localDataSource.getItems() } returns universityItem

        // When & Assertions
        val flow = repository.getUniversity("United Arab Emirates")
        flow.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData.map { it.name }).isEqualTo(universityItem.map { universityMapper.from(it) }.map { it.name })
            expectComplete()
        }

        // Then
        coVerify { remoteDataSource.getUniversity("United Arab Emirates") }
        coVerify { localDataSource.addItems(universityItem) }
    }

    @Test
    fun test_get_posts_remote_fail_local_success() = runBlockingTest {

        val university = TestDataGenerator.generateUniversity()

        // Given
        coEvery { remoteDataSource.getUniversity("United Arab Emirates") } throws Exception()
        coEvery { localDataSource.getItems() } returns university

        // When && Assertions
        val flow = repository.getUniversity("United Arab Emirates")
        flow.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData).isEqualTo(university.map { universityMapper.from(it) })
            expectComplete()
        }

        // Then
        coVerify { remoteDataSource.getUniversity("United Arab Emirates") }
        coVerify { localDataSource.getItems() }

    }

    @Test
    fun test_get_posts_remote_fail_local_fail() = runBlockingTest {


        // Given
        coEvery { remoteDataSource.getUniversity("United Arab Emirates") } throws Exception()
        coEvery { localDataSource.getItems() } throws Exception()

        // When && Assertions
        val flow = repository.getUniversity("United Arab Emirates")
        flow.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { remoteDataSource.getUniversity("United Arab Emirates") }
        coVerify { localDataSource.getItems() }

    }

}