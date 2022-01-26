package com.kazantsev.domain.entity

data class FavoriteItem(
    val name: String,
    val url: String,
    val favorite: Boolean = false,
)
