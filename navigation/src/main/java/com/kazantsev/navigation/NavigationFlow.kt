package com.kazantsev.navigation

sealed class NavigationFlow {
    object HomeFlow : NavigationFlow()
    object FavoriteFlow : NavigationFlow()
    class DetailFlow(val url: String) : NavigationFlow()
}