package com.oemam.footballapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oemam.footballapp.core.domain.model.Team
import com.oemam.footballapp.core.domain.usecase.GetTeamsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val getTeamsUseCase: GetTeamsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        fetchTeams()
    }

    fun fetchTeams() {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            getTeamsUseCase().collect { result ->
                result.onSuccess { teams ->
                    _uiState.value = MainUiState.Success(teams)
                }.onFailure { error ->
                    _uiState.value = MainUiState.Error(error.message ?: "Unknown Error")
                }
            }
        }
    }
}

sealed class MainUiState {
    object Loading : MainUiState()
    data class Success(val teams: List<Team>) : MainUiState()
    data class Error(val message: String) : MainUiState()
}
