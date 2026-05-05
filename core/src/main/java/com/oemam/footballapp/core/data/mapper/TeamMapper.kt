package com.oemam.footballapp.core.data.mapper

import com.oemam.footballapp.core.data.local.entity.TeamEntity
import com.oemam.footballapp.core.data.model.TeamDto
import com.oemam.footballapp.core.domain.model.Team

fun TeamDto.toDomain(): Team {
    return Team(
        id = idTeam ?: "",
        name = strTeam ?: "",
        shortName = strTeamShort,
        badge = strBadge,
        stadium = strStadium,
        description = strDescriptionEN,
        formedYear = intFormedYear,
        website = strWebsite
    )
}

fun TeamEntity.toDomain(): Team {
    return Team(
        id = id,
        name = name,
        shortName = shortName,
        badge = badge,
        stadium = stadium,
        description = description,
        formedYear = formedYear,
        website = website,
        isFavorite = isFavorite
    )
}

fun Team.toEntity(): TeamEntity {
    return TeamEntity(
        id = id,
        name = name,
        shortName = shortName,
        badge = badge,
        stadium = stadium,
        description = description,
        formedYear = formedYear,
        website = website,
        isFavorite = isFavorite
    )
}
