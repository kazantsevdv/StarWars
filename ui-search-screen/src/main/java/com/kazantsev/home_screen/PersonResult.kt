package com.kazantsev.home_screen

import androidx.paging.PagingData
import com.kazantsev.domain.entity.PersonInfo


sealed class PersonResult {
    object Loading : PersonResult()
    object EmptyResult : PersonResult()
    object EmptyQuery : PersonResult()
    data class SuccessResult(val result: PagingData<PersonInfo>) : PersonResult()
    data class ErrorResult(val e: Throwable) : PersonResult()
}
