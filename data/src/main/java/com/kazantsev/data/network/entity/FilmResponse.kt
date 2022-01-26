package com.kazantsev.data.network.entity

import com.kazantsev.domain.entity.FilmInfo

data class FilmResponse(
    val title: String,
) {
    fun toFilmInfo() = FilmInfo(
        title = title,
    )
}
