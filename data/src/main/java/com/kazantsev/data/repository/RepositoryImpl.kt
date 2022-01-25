package com.kazantsev.data.repository

import androidx.paging.PagingSource
import com.kazantsev.data.database.AppDatabase
import com.kazantsev.data.database.model.FavoriteEntity
import com.kazantsev.data.network.api.ApiDataSource
import com.kazantsev.data.util.AppCoroutineDispatchers
import com.kazantsev.domain.entity.PersonInfo
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiDataSource,
    private val personPagingSource: PersonPagingSource.Factory,
    private val db: AppDatabase,
    private val dispatcher: AppCoroutineDispatchers,
) : Repository {

    override fun searchPerson(search: String): PagingSource<String, PersonInfo> {
        return personPagingSource.create(search)
    }

    override suspend fun addPersonToFavorite(url: String) {
        withContext(dispatcher.io) {
            if (db.favoriteDao.getFavoriteByUrl(url) == null) {
                db.favoriteDao.insertFavorite(FavoriteEntity(Url = url))
            } else {
                db.favoriteDao.deleteByUrl(url)
            }
        }
    }

    override fun getPersonsFavoriteUseCase(): Flow<List<String>> {
        return db.favoriteDao.getFavoriteByUrlFlow().map { it.map { it.Url } }
    }
}