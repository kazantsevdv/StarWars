package com.kazantsev.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kazantsev.domain.use_case.ChangePersonFavoriteUseCase
import com.kazantsev.domain.use_case.GetFavoriteUseCase
import com.kazantsev.domain.use_case.SearchPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TEXT_ENTERED_DEBOUNCE_MILLIS = 500L

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPersonUseCase: SearchPersonUseCase,
    private val changePersonFavoriteUseCase: ChangePersonFavoriteUseCase,
    private val getPersonFavoriteUseCase: GetFavoriteUseCase,
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val person: StateFlow<PagingData<PersonInfoUi>> = query
        .debounce(TEXT_ENTERED_DEBOUNCE_MILLIS)
        .flatMapLatest { searchPersonUseCase(it) }
        .cachedIn(viewModelScope)
        .combine(getPersonFavoriteUseCase()) { personInfo, fav ->
            personInfo.map {
                PersonInfoUi(
                    name = it.name,
                    height = it.height,
                    mass = it.mass,
                    birth_year = it.birth_year,
                    gender = it.gender,
                    url = it.url,
                    favorite = fav.firstOrNull { favoriteItem -> favoriteItem.url == it.url } != null
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun onNewQuery(query: String) {
        _query.value = query
    }

    fun favorite(url: String, name: String) {
        viewModelScope.launch {
            changePersonFavoriteUseCase(url, name)
        }
    }
}