package com.example.mypet.nav

import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.coroutines.CoroutineScope

@Composable
fun SetupNavGraphs(navController: NavHostController, context: Context, scope: CoroutineScope) {
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
        StartNavGraph(navController, context, scope)
        MainNavGraph(navController)
    }
}
