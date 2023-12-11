package com.example.mypet.nav

import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun SetupNavGraphs(navController: NavHostController, context: Context, modifier: Modifier) {
    NavHost(
        navController = navController,
        route = ROOT,
        startDestination = START,
        // Отключение анимаций перехода между экранами
        enterTransition = { EnterTransition.None },
//        exitTransition = { ExitTransition.None },
//        popEnterTransition = { EnterTransition.None },
//        popExitTransition = { ExitTransition.None },
    ) {
        StartNavGraph(navController, context)
        MainNavGraph(navController)
    }
}
