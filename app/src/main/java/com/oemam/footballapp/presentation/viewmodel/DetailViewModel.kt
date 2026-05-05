package com.oemam.footballapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oemam.footballapp.core.domain.model.Team
import com.oemam.footballapp.core.domain.usecase.ToggleFavoriteUseCase
import com.oemam.footballapp.core.domain.repository.TeamRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: TeamRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun checkFavoriteStatus(teamId: String) {
        viewModelScope.launch {
            repository.getTeamById(teamId).collect { team ->
                _isFavorite.value = team?.isFavorite ?: false
            }
        }
    }

    fun toggleFavorite(team: Team) {
        viewModelScope.launch {
            val currentStatus = _isFavorite.value
            val newStatus = !currentStatus
            toggleFavoriteUseCase(team, newStatus)
            // The flow from repository will update _isFavorite if we collect it, 
            // but for simplicity we can manually update or just rely on the collect in checkFavoriteStatus.
        }
    }
}
