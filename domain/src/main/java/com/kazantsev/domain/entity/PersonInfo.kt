package com.kazantsev.domain.entity


data class PersonInfo(
    val name: String,
    val height: String,
    val mass: String,
    val birth_year: String,
    val gender: String,
    val url: String,
    val films:List<String>
)
