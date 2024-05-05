package com.example.domain

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.Resource
import com.example.domain.repository.Repository
import com.example.domain.usecase.GetUniversityUseCase
import com.google.common.truth.Truth
import com.example.domain.utils.MainCoroutineRule
import com.example.domain.utils.TestDataGenerator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.never
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class GetUniversityUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: Repository

    private lateinit var getUniversityUseCase: GetUniversityUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        getUniversityUseCase = GetUniversityUseCase(
            repository = repository,
            dispatcher = mainCoroutineRule.dispatcher
        )
    }

    @Test
    fun test_get_university_success() = runTest {

        val universityItem = TestDataGenerator.generateUniversityItems()
        val universityFlow = flowOf(Resource.Success(universityItem))

        // Given
        coEvery { repository.getUniversity("United Arab Emirates") } returns universityFlow

        // When & Assertions
        val result = getUniversityUseCase.execute("United Arab Emirates")
        result.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData).isEqualTo(universityItem)
            expectComplete()
        }

        // Then
        coVerify { repository.getUniversity("United Arab Emirates") }

    }




    @Test
    fun test_get_university_fail() = runTest {

        val errorFlow = flowOf(Resource.Error(Exception()))

        // Given
        coEvery { repository.getUniversity("United Arab Emirates") } returns errorFlow

        // When & Assertions
        val result = getUniversityUseCase.execute("United Arab Emirates")
        result.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { repository.getUniversity("United Arab Emirates") }

    }



    @Test
    fun test_get_university_fail_pass_paameter_with_null() = runBlockingTest {

        val errorFlow = flowOf(Resource.Error(Exception()))

        // When & Assertions
        val result = getUniversityUseCase.execute(null)
        result.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then

//        coVerify { repository.getUniversity() }

    }
}