package com.kazantsev.data.network.entity

import com.kazantsev.domain.entity.PersonInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonInfoResponse(
    val name: String,
    val height: String,
    val mass: String,
    val birth_year: String,
    val gender: String,
    val url: String,
    val films:List<String>
) {
    fun toPersonInfo() = PersonInfo(
        name = name,
        height = height,
        mass = mass,
        birth_year = birth_year,
        gender = gender,
        url = url,
        films=films
    )
}
