package com.kazantsev.detail_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazantsev.domain.entity.PersonInfo
import com.kazantsev.domain.use_case.ChangePersonFavoriteUseCase
import com.kazantsev.domain.use_case.GetPersonFavoriteUseCase
import com.kazantsev.domain.use_case.GetPersonInfoUseCase
import com.kazantsev.domain.use_case.GetPersonVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val changePersonFavoriteUseCase: ChangePersonFavoriteUseCase,
    private val getPersonInfoUseCase: GetPersonInfoUseCase,
    private val getPersonFavoriteUseCase: GetPersonFavoriteUseCase,
    private val getPersonVideoUseCase: GetPersonVideoUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val args = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val detailUrl = args.url

    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state.asStateFlow()

    private var _events = MutableSharedFlow<DetailsEvent>()
    val events: SharedFlow<DetailsEvent> get() = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                val personInfo = getPersonInfoUseCase(detailUrl)
                handleDetail(personInfo)
                reduceState { copy(isLoadingInfo = false) }
                val videoList = getPersonVideoUseCase(personInfo.films)
                reduceState { copy(isLoadingFilms = false, films = videoList) }
            } catch (e: Exception) {
                _events.emit(DetailsEvent.ShowErrorEvent(e.localizedMessage ?: ""))
                reduceState { copy(isLoadingInfo = false, isLoadingFilms = false) }
            }
        }
        viewModelScope.launch {
            getPersonFavoriteUseCase(detailUrl)
                .collect { reduceState { copy(favorite = it) } }
        }
    }

    fun favorite() {
        viewModelScope.launch {
            if (state.value.name.isNotEmpty())
                changePersonFavoriteUseCase(detailUrl, state.value.name)
        }
    }

    @Synchronized
    private fun reduceState(reducer: DetailsState.() -> DetailsState) {
        val value = _state.value
        _state.value = value.reducer()
    }

    private fun handleDetail(detail: PersonInfo) = reduceState {
        copy(
            isLoadingInfo = false,
            name = detail.name,
            birth_year = detail.birth_year,
            gender = detail.gender,
        )
    }
}