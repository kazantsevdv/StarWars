package com.kazantsev.domain.use_case

import androidx.paging.PagingSource
import com.kazantsev.domain.entity.FilmInfo
import com.kazantsev.domain.entity.PersonInfo
import com.kazantsev.domain.repository.Repository
import javax.inject.Inject

class GetPersonVideoUseCase @Inject constructor(
    private val repository: Repository,
) {
     suspend operator fun invoke(url: String): FilmInfo {
        return repository.loadFilm(url)
    }
}