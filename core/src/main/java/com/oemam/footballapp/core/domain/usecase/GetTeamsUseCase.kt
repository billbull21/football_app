package com.oemam.footballapp.core.domain.usecase

import com.oemam.footballapp.core.domain.model.Team
import com.oemam.footballapp.core.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow

class GetTeamsUseCase(private val repository: TeamRepository) {
    operator fun invoke(): Flow<Result<List<Team>>> {
        return repository.getTeamsInLeague("English Premier League")
    }
}
