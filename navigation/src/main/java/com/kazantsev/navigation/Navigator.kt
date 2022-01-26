package com.kazantsev.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController
    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.HomeFlow -> navController.navigate(MainNavGraphDirections.actionSearchFlow())
        is NavigationFlow.DetailFlow -> navController.navigate(MainNavGraphDirections.actionDetailFlow(navigationFlow.url))
        NavigationFlow.FavoriteFlow->navController.navigate(MainNavGraphDirections.actionFavoriteFlow())
    }
}