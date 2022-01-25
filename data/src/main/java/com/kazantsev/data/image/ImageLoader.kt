package com.kazantsev.data.image

interface ImageLoader<T> {
    fun loadInto(url: String, container: T)
}