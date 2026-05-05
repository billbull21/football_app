package com.oemam.footballapp.core.data.api

import com.oemam.footballapp.core.data.model.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApi {
    @GET("search_all_teams.php")
    suspend fun getTeamsInLeague(
        @Query("l") leagueName: String
    ): TeamResponse
}
