package com.kazantsev.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kazantsev.data.network.api.ApiDataSource
import com.kazantsev.data.network.entity.PeopleResponse
import com.kazantsev.data.network.entity.PersonInfoResponse
import com.kazantsev.domain.entity.PersonInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class PersonPagingSource @AssistedInject constructor(
    private val api: ApiDataSource,
    @Assisted("name") private val name: String,
) : PagingSource<String, PersonInfo>() {


    override suspend fun load(params: LoadParams<String>): LoadResult<String, PersonInfo> {
        if (name.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        try {
            val response: Response<PeopleResponse>
            if (params.key == null) {
                response = api.searchPeople(name)
            } else {
                response = api.searchPeoplePage(params.key!!)
            }
            return if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()!!.results.map { it.toPersonInfo() },
                    prevKey = response.body()!!.previous,
                    nextKey = response.body()!!.next
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, PersonInfo>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }
    @AssistedFactory
    interface Factory {
        fun create(@Assisted("name") name: String): PersonPagingSource
    }
}