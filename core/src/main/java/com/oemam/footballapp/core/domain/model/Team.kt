package com.oemam.footballapp.core.domain.model

data class Team(
    val id: String,
    val name: String,
    val shortName: String?,
    val badge: String?,
    val stadium: String?,
    val description: String?,
    val formedYear: String?,
    val website: String?,
    val isFavorite: Boolean = false
)
