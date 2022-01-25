package com.kazantsev.home_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.kazantsev.domain.entity.PersonInfo
import com.kazantsev.domain.use_case.ChangePersonFavoriteUseCase
import com.kazantsev.domain.use_case.GetPersonsFavoriteUseCase
import com.kazantsev.domain.use_case.SearchPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TEXT_ENTERED_DEBOUNCE_MILLIS = 500L

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchPersonUseCase: SearchPersonUseCase,
    private val changePersonFavoriteUseCase: ChangePersonFavoriteUseCase,
    private val getPersonFavoriteUseCase: GetPersonsFavoriteUseCase,
) : ViewModel() {

    private var newPagingSource: PagingSource<*, *>? = null

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _dataList = MutableStateFlow<PagingData<PersonInfoUi>>(PagingData.empty())
    val dataList = _dataList.asStateFlow()


    val person: StateFlow<PagingData<PersonInfoUi>> = query
        .debounce(TEXT_ENTERED_DEBOUNCE_MILLIS)
        .map(::newPager)
        .flatMapLatest { pager ->
            pager.flow

        }
        .cachedIn(viewModelScope)
        .combine(getPersonFavoriteUseCase.invoke()) { personInfo, fav ->
            personInfo.map {
                PersonInfoUi(
                    name = it.name,
                    height = it.height,
                    mass = it.mass,
                    birth_year = it.birth_year,
                    gender = it.gender,
                    url = it.url,
                    favorite = fav.contains(it.url)
                )
            }

        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun onNewQuery(query: String) {
        _query.value = query
    }

    private fun newPager(name: String): Pager<String, PersonInfo> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            newPagingSource?.invalidate()
            searchPersonUseCase(name).also { newPagingSource = it }
        }
    }

    fun favorite(url: String) {
        viewModelScope.launch {
            changePersonFavoriteUseCase(url)
        }
    }
}