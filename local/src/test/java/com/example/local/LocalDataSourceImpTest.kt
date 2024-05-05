package com.example.local

import androidx.test.filters.SmallTest
import com.example.data.repository.LocalDataSource
import com.example.local.database.UniversityDAO
import com.example.local.mapper.UniversityLocalDataMapper
import com.example.local.source.LocalDataSourceImp
import com.example.local.utils.TestData
import com.google.common.truth.Truth
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
class LocalDataSourceImpTest {

    @MockK
    private lateinit var universityDAO: UniversityDAO

    private val universityLocalDataMapper = UniversityLocalDataMapper()
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create LocalDataSourceImp before every test
        localDataSource = LocalDataSourceImp(
            universityDAO = universityDAO,
            universityMapper = universityLocalDataMapper
        )
    }

    @Test
    fun test_add_university_items_success() = runBlockingTest {

        val universityItems =
            universityLocalDataMapper.fromList(list = TestData.generateUniversityItems())
        val expected = MutableList(universityItems.size) { index -> index.toLong() }

        // Given
        coEvery { universityDAO.addUniversityItems(any()) } returns expected

        // When
        val returned = localDataSource.addItems(universityItems)

        // Then
        coVerify { universityDAO.addUniversityItems(any()) }

        // Assertion
        Truth.assertThat(returned).hasSize(expected.size)
    }

    @Test(expected = Exception::class)
    fun test_add_university_items_fail() = runBlockingTest {

        val universityItems =
            universityLocalDataMapper.fromList(list = TestData.generateUniversityItems())

        // Given
        coEvery { universityDAO.addUniversityItems(any()) } throws Exception()

        // When
        localDataSource.addItems(universityItems)

        // Then
        coVerify { universityDAO.addUniversityItems(any()) }

    }

    @Test
    fun test_get_university_items_success() = runBlockingTest {

        val universityItems = TestData.generateUniversityItems()
        val expected = universityLocalDataMapper.fromList(list = universityItems)

        // Given
        coEvery { universityDAO.getUniversityItems() } returns universityItems

        // When
        val returned = localDataSource.getItems()

        // Then
        coVerify { universityDAO.getUniversityItems() }

        // Assertion
        Truth.assertThat(returned).containsExactlyElementsIn(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_university_items_fail() = runBlockingTest {

        // Given
        coEvery { universityDAO.getUniversityItems() } throws Exception()

        // When
        localDataSource.getItems()

        // Then
        coVerify { universityDAO.getUniversityItems() }

    }

}