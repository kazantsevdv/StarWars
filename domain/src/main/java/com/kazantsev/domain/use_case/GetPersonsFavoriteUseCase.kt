package com.kazantsev.domain.use_case

import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPersonsFavoriteUseCase @Inject constructor(
    private val repository: Repository,
) {
     operator fun invoke(): Flow<List<String>> {
       return repository.getPersonsFavorite()
    }
}