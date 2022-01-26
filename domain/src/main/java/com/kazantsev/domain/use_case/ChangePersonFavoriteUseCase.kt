package com.kazantsev.domain.use_case

import com.kazantsev.domain.repository.Repository
import javax.inject.Inject

class ChangePersonFavoriteUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke(url: String,name:String) {
        repository.addPersonToFavorite(url,name)
    }
}