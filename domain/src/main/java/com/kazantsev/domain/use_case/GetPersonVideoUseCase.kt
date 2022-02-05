package com.kazantsev.domain.use_case

import com.kazantsev.domain.entity.FilmInfo
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetPersonVideoUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke(urls: List<String>): List<FilmInfo> {
        return coroutineScope {
            urls.map { url ->
                async {
                    try {
                        repository.loadFilm(url)
                    } catch (e: Exception) {
                        FilmInfo("----")
                    }
                }
            }.map { deferred -> deferred.await() }
        }

    }
}