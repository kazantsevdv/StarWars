package com.kazantsev.navigation

sealed class NavigationFlow {
    object HomeFlow : NavigationFlow()
    class DetailFlow(val id: Int) : NavigationFlow()
}