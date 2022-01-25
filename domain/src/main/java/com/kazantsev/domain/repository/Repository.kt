package com.kazantsev.domain.repository

import androidx.paging.PagingSource
import com.kazantsev.domain.entity.PersonInfo
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun searchPerson(search: String): PagingSource<String, PersonInfo>
    suspend fun addPersonToFavorite(url: String)
    fun getPersonsFavoriteUseCase():Flow<List<String>>
}