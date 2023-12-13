package com.example.mypet.nav

import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.mypet.data.Pet

@Composable
fun SetupNavGraphs(navController: NavHostController, context: Context, pets: List<Pet>) {
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
        StartNavGraph(navController, context, pets)
        MainNavGraph(navController)
    }
}
