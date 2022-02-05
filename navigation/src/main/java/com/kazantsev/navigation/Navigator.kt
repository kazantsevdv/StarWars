package com.kazantsev.navigation

import androidx.navigation.NavController

class Navigator(private val navController: NavController) : NavigateToFlow {
        override fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        is NavigationFlow.DetailFlow -> navController.navigate(RootNavDirections.actionDetailFlow(
            navigationFlow.url))
    }
}