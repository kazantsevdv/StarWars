package com.kazantsev.home_screen


data class PersonInfoUi(
    val name: String,
    val height: String,
    val mass: String,
    val birth_year: String,
    val gender: String,
    val url: String,
    val favorite:Boolean=false,
)
