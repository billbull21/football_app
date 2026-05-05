package com.oemam.footballapp.core.data.repository

import com.oemam.footballapp.core.data.api.FootballApi
import com.oemam.footballapp.core.data.local.dao.TeamDao
import com.oemam.footballapp.core.data.mapper.toDomain
import com.oemam.footballapp.core.data.mapper.toEntity
import com.oemam.footballapp.core.domain.model.Team
import com.oemam.footballapp.core.domain.repository.TeamRepository
import kotlinx.coroutines.flow.*

class TeamRepositoryImpl(
    private val api: FootballApi,
    private val teamDao: TeamDao
) : TeamRepository {

    override fun getTeamsInLeague(league: String): Flow<Result<List<Team>>> = flow {
        try {
            val response = api.getTeamsInLeague(league)
            val teams = response.teams?.map { it.toDomain() } ?: emptyList()
            emit(Result.success(teams))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getFavoriteTeams(): Flow<List<Team>> {
        return teamDao.getAllFavoriteTeams().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTeamById(id: String): Flow<Team?> {
        return teamDao.getTeamById(id).map { it?.toDomain() }
    }

    override suspend fun setFavorite(team: Team, isFavorite: Boolean) {
        if (isFavorite) {
            teamDao.insertTeam(team.copy(isFavorite = true).toEntity())
        } else {
            teamDao.deleteTeamById(team.id)
        }
    }
}
