package com.kazantsev.ui_common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.kazantsev.navigation.NavigateToFlow
import com.kazantsev.navigation.NavigationFlow
import com.kazantsev.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigateToFlow {
    private lateinit var navigator: Navigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val rootNavController = navHostFragment.navController
        navigator = Navigator(rootNavController)
    }

    override fun navigateToFlow(flow: NavigationFlow) = navigator.navigateToFlow(flow)

}