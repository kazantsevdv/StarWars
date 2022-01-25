package com.kazantsev.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kazantsev.data.database.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("DELETE  FROM favorite")
    suspend fun deleteByUrl()

    @Query("DELETE  FROM favorite WHERE url = :url")
    suspend fun deleteByUrl(url: String)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(item: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun getAllFavorite(): Flow<List<FavoriteEntity>?>

    @Query("SELECT * FROM favorite")
    fun getFavoriteByUrlFlow(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE url = :url")
    suspend fun getFavoriteByUrl(url: String): FavoriteEntity?
}