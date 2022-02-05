package com.kazantsev.navigation

sealed class NavigationFlow {

    class DetailFlow(val url: String) : NavigationFlow()
}