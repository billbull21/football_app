package com.oemam.footballapp.core.domain.repository

import com.oemam.footballapp.core.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getTeamsInLeague(league: String): Flow<Result<List<Team>>>
    fun getFavoriteTeams(): Flow<List<Team>>
    fun getTeamById(id: String): Flow<Team?>
    suspend fun setFavorite(team: Team, isFavorite: Boolean)
}
