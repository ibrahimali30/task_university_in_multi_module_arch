package com.example.task.ui.contract


import com.example.base.UiEffect
import com.example.base.UiEvent
import com.example.base.UiState
import com.example.model.UniversityUiModel

/**
 * Contract of Main Screen
 */
class MainContract {

    sealed class Event : UiEvent {
        data class OnFetchUniversity(val city:String) : Event()
        data class OnUniversityItemClicked(val university : UniversityUiModel) : Event()
    }

    data class State(
        val universityState: UniversityState,
        val selectedUniversity: UniversityUiModel? = null
    ) : UiState

    sealed class UniversityState {
        object Empty : UniversityState()
        object Loading : UniversityState()
        data class Success(val universityList : List<UniversityUiModel>) : UniversityState()
    }

    sealed class Effect : UiEffect {

        data class ShowError(val message : String?) : Effect()

    }

}