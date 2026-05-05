package com.oemam.footballapp.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey val id: String,
    val name: String,
    val shortName: String?,
    val badge: String?,
    val stadium: String?,
    val description: String?,
    val formedYear: String?,
    val website: String?,
    val isFavorite: Boolean
)
