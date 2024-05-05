package com.example.feature



import com.example.common.Resource
import com.example.domain.usecase.GetUniversityUseCase
import com.google.common.truth.Truth
import com.example.feature.utils.MainCoroutineRule
import com.example.feature.utils.TestDataGenerator


import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.SmallTest
import app.cash.turbine.test
//import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class MainViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var getUniversityUseCase: GetUniversityUseCase

    private val universityMapper = com.example.task.mapper.UniversityDomainUiMapper()

    private lateinit var mainViewModel: com.example.task.ui.vm.MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create MainViewModel before every test
        mainViewModel = com.example.task.ui.vm.MainViewModel(
            savedStateHandle = savedStateHandle,
            getUniversityUseCase = getUniversityUseCase,
            universityMapper = universityMapper
        )
    }

    @Test
    fun test_fetch_universityItems_success() = runTest {

        val universityItems = TestDataGenerator.generateUniversityItems()
        val universityFlow = flowOf(Resource.Success(universityItems))

        // Given
        coEvery { getUniversityUseCase.execute(any()) } returns universityFlow

        // When && Assertions
        mainViewModel.uiState.test {
            mainViewModel.fetchUniversity("United Arab Emirates")
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectMostRecentItem()).isEqualTo(
                com.example.task.ui.contract.MainContract.State(
                    universityState = com.example.task.ui.contract.MainContract.UniversityState.Empty,
                    selectedUniversity = null
                )
            )
            // Expect Resource.Loading
            Truth.assertThat(expectMostRecentItem()).isEqualTo(
                com.example.task.ui.contract.MainContract.State(
                    universityState = com.example.task.ui.contract.MainContract.UniversityState.Loading,
                    selectedUniversity = null
                )
            )
            // Expect Resource.Success
            val expected = expectMostRecentItem()
            val expectedData = (expected.universityState as com.example.task.ui.contract.MainContract.UniversityState.Success).universityList
            Truth.assertThat(expected).isEqualTo(
                com.example.task.ui.contract.MainContract.State(
                    universityState = com.example.task.ui.contract.MainContract.UniversityState.Success(universityItems.map { universityMapper.from(it) }),
                    selectedUniversity = null
                )
            )
            Truth.assertThat(expectedData).containsExactlyElementsIn(universityItems.map { universityMapper.from(it) })


            //Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify { getUniversityUseCase.execute(any()) }
    }

    @Test
    fun test_fetch_university_fail() = runBlockingTest {

        val universityErrorFlow = flowOf(Resource.Error(Exception("error string")))

        // Given
        coEvery { getUniversityUseCase.execute(any()) } returns universityErrorFlow

        // When && Assertions (UiState)
        mainViewModel.uiState.test {
            // Call method inside of this scope
            mainViewModel.setEvent(com.example.task.ui.contract.MainContract.Event.OnFetchUniversity("United Arab Emirates"))
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectMostRecentItem()).isEqualTo(
                com.example.task.ui.contract.MainContract.State(
                    universityState = com.example.task.ui.contract.MainContract.UniversityState.Empty,
                    selectedUniversity = null
                )
            )
            // Expect Resource.Loading
            Truth.assertThat(expectMostRecentItem()).isEqualTo(
                com.example.task.ui.contract.MainContract.State(
                    universityState = com.example.task.ui.contract.MainContract.UniversityState.Loading,
                    selectedUniversity = null
                )
            )
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // When && Assertions (UiEffect)
        mainViewModel.effect.test {
            // Expect ShowError Effect
            val expected = expectMostRecentItem()
            val expectedData = (expected as com.example.task.ui.contract.MainContract.Effect.ShowError).message
            Truth.assertThat(expected).isEqualTo(
                com.example.task.ui.contract.MainContract.Effect.ShowError("error string")
            )
            Truth.assertThat(expectedData).isEqualTo("error string")
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify { getUniversityUseCase.execute(any()) }
    }


    @Test
    fun test_select_university_item() = runBlockingTest {

        val university = TestDataGenerator.generateUniversityItems()

        // Given (no-op)

        // When && Assertions
        mainViewModel.uiState.test {
            // Call method inside of this scope
            // For more info, see https://github.com/cashapp/turbine/issues/19
            mainViewModel.setEvent(com.example.task.ui.contract.MainContract.Event.OnUniversityItemClicked(university = universityMapper.from(university.first())))
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectMostRecentItem()).isEqualTo(
                com.example.task.ui.contract.MainContract.State(
                    universityState = com.example.task.ui.contract.MainContract.UniversityState.Empty,
                    selectedUniversity = null
                )
            )
            // Expect Resource.Success
            val expected = expectMostRecentItem()
            val expectedData = expected.selectedUniversity
            Truth.assertThat(expected).isEqualTo(
                com.example.task.ui.contract.MainContract.State(
                    universityState = com.example.task.ui.contract.MainContract.UniversityState.Empty,
                    selectedUniversity = universityMapper.from(university.first())
                )
            )
            Truth.assertThat(expectedData).isEqualTo(universityMapper.from(university.first()))
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then (no-op)
    }
}