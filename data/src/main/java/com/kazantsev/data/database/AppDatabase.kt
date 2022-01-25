package com.kazantsev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kazantsev.data.database.model.FavoriteEntity

@Database(
    entities = [
        FavoriteEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
}