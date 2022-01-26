package com.kazantsev.detail_screen

sealed class DetailsEvent {
    data class ShowErrorEvent(val errorMessage: String) : DetailsEvent()
}