package com.kazantsev.data.network.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PeopleResponse(
    val count: Int = 0,
    val next: String = "",
    val previous: String = "df",
    val results: List<PersonInfoResponse>,
)