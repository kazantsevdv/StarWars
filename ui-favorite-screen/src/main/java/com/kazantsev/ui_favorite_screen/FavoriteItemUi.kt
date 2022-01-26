package com.kazantsev.ui_favorite_screen

data class FavoriteItemUi(
    val name: String,
    val url: String,
    val favorite: Boolean = false,
)
