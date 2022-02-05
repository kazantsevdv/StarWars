package com.kazantsev.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.kazantsev.domain.entity.PersonInfo
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPersonUseCase @Inject constructor(

    private val repository: Repository,
) {
    private var newPagingSource: PagingSource<*, *>? = null
     operator fun invoke(name: String): Flow<PagingData<PersonInfo>> {
         return  Pager(PagingConfig(5, enablePlaceholders = false)) {
             newPagingSource?.invalidate()
             repository.searchPerson(name).also { newPagingSource = it }
         }.flow
    }
}