package com.kazantsev.domain.repository

import androidx.paging.PagingSource
import com.kazantsev.domain.entity.FavoriteItem
import com.kazantsev.domain.entity.FilmInfo
import com.kazantsev.domain.entity.PersonInfo
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun searchPerson(search: String): PagingSource<String, PersonInfo>
    suspend fun addPersonToFavorite(url: String, name: String)
    fun getFavorite(): Flow<List<FavoriteItem>>
    fun getPersonFavoriteByUrl(url: String): Flow<Boolean>
    suspend fun loadPerson(url: String): PersonInfo
    suspend fun loadFilm(url: String): FilmInfo
}