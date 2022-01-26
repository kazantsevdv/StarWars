package com.kazantsev.detail_screen

import com.kazantsev.domain.entity.FilmInfo

data class DetailsState(
    val isLoadingInfo: Boolean = true,
    val isLoadingFilms: Boolean = true,
    val name: String="",
    val birth_year: String="",
    val gender: String="",
    val favorite: Boolean = false,
    val films: List<FilmInfo> = emptyList(),
)