package com.example.task.ui.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.common.Mapper
import com.example.common.Resource
import com.example.domain.entity.UniversityEntity
import com.example.domain.usecase.GetUniversityUseCase
import kotlinx.coroutines.flow.onStart
import com.example.task.ui.contract.MainContract
import com.example.model.UniversityUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUniversityUseCase: GetUniversityUseCase,
    private val universityMapper : Mapper<UniversityEntity, UniversityUiModel>
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>(){

    var searchQuery = "United Arab Emirates"

//    private val _uiState : MutableStateFlow<MainContract.State> = MutableStateFlow(MainContract.State(
//        MainContract.UniversityState.Loading
//    ))
//    val uiState = _uiState.asStateFlow()

    /**
     * Fetch posts
     */
    fun fetchUniversity(city:String = searchQuery) {
        viewModelScope.launch {
                getUniversityUseCase.execute(city)
                    .onStart { emit(Resource.Loading) }
                    .collect {
                        when (it) {
                            is Resource.Loading -> {
                                _uiState.value = _uiState.value.copy(universityState = MainContract.UniversityState.Loading)
                            }
                            is Resource.Empty -> {
                                _uiState.value = _uiState.value.copy(universityState = MainContract.UniversityState.Empty)
                            }
                            is Resource.Success -> {
                                val universityList = universityMapper.fromList(it.data)
                                _uiState.value = _uiState.value.copy(
                                    universityState = MainContract.UniversityState.Success(
                                        universityList = universityList
                                    )
                                )
                            }
                            is Resource.Error -> {
                                _uiState.value = _uiState.value.copy(
                                        universityState = MainContract.UniversityState.Empty
                                    )
                            }
                        }
                    }
        }
    }

    /**
     * Set selected post item
     */
    private fun setSelectedPost(university : UniversityUiModel?) {
        // Set State
        _uiState.value = _uiState.value.copy(selectedUniversity = university)
    }

    fun onRefreshCallBackRequested() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(universityState = MainContract.UniversityState.Loading)
        delay(1000)
        fetchUniversity()
    }

    override fun createInitialState(): MainContract.State {
        return MainContract.State(
            MainContract.UniversityState.Loading
        )
    }

    override fun handleEvent(event: MainContract.Event) {

    }
}