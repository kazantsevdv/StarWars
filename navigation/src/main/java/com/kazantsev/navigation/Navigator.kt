package com.kazantsev.navigation

import androidx.navigation.NavController

class Navigator(private val navController: NavController) : NavigateToFlow {
        override fun navigateToFlow(flow: NavigationFlow) = when (flow) {
        is NavigationFlow.DetailFlow -> navController.navigate(RootNavDirections.actionDetailFlow(
            flow.url))
    }
}