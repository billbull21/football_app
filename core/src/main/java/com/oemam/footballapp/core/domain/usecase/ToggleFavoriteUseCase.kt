package com.oemam.footballapp.core.domain.usecase

import com.oemam.footballapp.core.domain.model.Team
import com.oemam.footballapp.core.domain.repository.TeamRepository

class ToggleFavoriteUseCase(private val repository: TeamRepository) {
    suspend operator fun invoke(team: Team, isFavorite: Boolean) {
        repository.setFavorite(team, isFavorite)
    }
}
