package com.oemam.footballapp.favorites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oemam.footballapp.core.domain.model.Team
import com.oemam.footballapp.core.domain.usecase.GetFavoriteTeamsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(
    private val getFavoriteTeamsUseCase: GetFavoriteTeamsUseCase
) : ViewModel() {

    val favoriteTeams: StateFlow<List<Team>> = getFavoriteTeamsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}