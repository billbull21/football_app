package com.oemam.footballapp.core.data.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teams") val teams: List<TeamDto>?
)

data class TeamDto(
    @SerializedName("idTeam") val idTeam: String?,
    @SerializedName("strTeam") val strTeam: String?,
    @SerializedName("strTeamShort") val strTeamShort: String?,
    @SerializedName("strBadge") val strBadge: String?,
    @SerializedName("strStadium") val strStadium: String?,
    @SerializedName("strDescriptionEN") val strDescriptionEN: String?,
    @SerializedName("intFormedYear") val intFormedYear: String?,
    @SerializedName("strWebsite") val strWebsite: String?
)
