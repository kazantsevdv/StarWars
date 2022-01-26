package com.kazantsev.domain.use_case

import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPersonFavoriteUseCase @Inject constructor(
    private val repository: Repository,
) {
     operator fun invoke(url:String): Flow<Boolean> {
       return repository.getPersonFavoriteByUrl(url)
    }
}