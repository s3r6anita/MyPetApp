package com.example.mypet.nav

import android.content.Context
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
        modifier = modifier
    ) {
        StartNavGraph(navController, context)
        MainNavGraph(navController)
    }
}
