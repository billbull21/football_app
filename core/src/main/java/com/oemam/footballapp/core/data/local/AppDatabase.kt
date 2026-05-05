package com.oemam.footballapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oemam.footballapp.core.data.local.dao.TeamDao
import com.oemam.footballapp.core.data.local.entity.TeamEntity

@Database(entities = [TeamEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao
}
