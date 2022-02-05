package com.kazantsev.data.repository

import android.accounts.NetworkErrorException
import androidx.paging.PagingSource
import com.kazantsev.data.database.AppDatabase
import com.kazantsev.data.database.model.FavoriteEntity
import com.kazantsev.data.network.api.ApiDataSource
import com.kazantsev.data.util.AppCoroutineDispatchers
import com.kazantsev.domain.entity.FavoriteItem
import com.kazantsev.domain.entity.FilmInfo
import com.kazantsev.domain.entity.PersonInfo
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun addPersonToFavorite(url: String, name: String) {
        withContext(dispatcher.io) {
            if (db.favoriteDao.getFavoriteByUrl(url) == null) {
                db.favoriteDao.insertFavorite(FavoriteEntity(Url = url, name = name))
            } else {
                db.favoriteDao.deleteByUrl(url)
            }
        }
    }

      override fun getPersonFavoriteByUrl(url: String): Flow<Boolean> {
        return db.favoriteDao.getFavoriteByUrlFlow(url).map { it != null }
    }

    override fun getFavorite(): Flow<List<FavoriteItem>> {
        return db.favoriteDao.getFavoriteFlow().map {
            it.map { favoriteEntity ->
                FavoriteItem(
                    name = favoriteEntity.name,
                    url = favoriteEntity.Url,
                    favorite = true,
                )
            }
        }
    }

    override suspend fun loadPerson(url: String): PersonInfo {
        try {
            val response = api.loadPerson(url)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { resp ->
                    return resp.toPersonInfo()
                }
                throw NetworkErrorException("No Data")
            } else {
                throw Exception(response.errorBody().toString())
            }
        } catch (e: Exception) {
            throw NetworkErrorException("Server error")
        }

    }

    override suspend fun loadFilm(url: String): FilmInfo {
        try {
            val response = api.loadFilm(url)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { resp ->
                    return resp.toFilmInfo()
                }
                throw NetworkErrorException("No Data")
            } else {
                throw Exception(response.errorBody().toString())
            }
        } catch (e: Exception) {
            throw NetworkErrorException("Server error")
        }
    }
}