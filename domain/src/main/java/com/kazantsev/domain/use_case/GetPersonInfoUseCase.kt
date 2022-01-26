package com.kazantsev.domain.use_case

import com.kazantsev.domain.entity.PersonInfo
import com.kazantsev.domain.repository.Repository
import javax.inject.Inject

class GetPersonInfoUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke(url: String): PersonInfo {
        return repository.loadPerson(url)
    }
}