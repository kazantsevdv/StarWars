package com.kazantsev.domain.use_case

import androidx.paging.PagingSource
import com.kazantsev.domain.entity.PersonInfo
import com.kazantsev.domain.repository.Repository
import javax.inject.Inject

class SearchPersonUseCase @Inject constructor(
    private val repository: Repository,
) {
     operator fun invoke(name: String): PagingSource<String, PersonInfo> {
        return repository.searchPerson(name)
    }
}