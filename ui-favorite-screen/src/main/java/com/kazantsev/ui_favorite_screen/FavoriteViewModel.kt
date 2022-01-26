package com.kazantsev.ui_favorite_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazantsev.domain.use_case.ChangePersonFavoriteUseCase
import com.kazantsev.domain.use_case.GetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val changePersonFavoriteUseCase: ChangePersonFavoriteUseCase,
) : ViewModel() {

    val dataList = getFavoriteUseCase.invoke().map {
        it.map { favoriteItem ->
            FavoriteItemUi(
                name = favoriteItem.name,
                url = favoriteItem.url,
                favorite = favoriteItem.favorite
            )
        }
    }

    fun favorite(url: String, name: String) {
        viewModelScope.launch {
            changePersonFavoriteUseCase(url, name)
        }
    }
}