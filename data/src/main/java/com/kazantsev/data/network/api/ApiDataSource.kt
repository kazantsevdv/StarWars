package com.kazantsev.data.network.api

import com.kazantsev.data.network.entity.PeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiDataSource {
    @GET("people/")
    suspend fun searchPeople(
        @Query("search") search: String,
    ): Response<PeopleResponse>

    @GET
    suspend fun searchPeoplePage(@Url url: String): Response<PeopleResponse>
}