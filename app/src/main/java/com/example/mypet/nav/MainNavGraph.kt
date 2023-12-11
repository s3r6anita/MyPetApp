package com.example.mypet.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mypet.ui.screens.ListProcedureScreen
import com.example.mypet.ui.screens.ListTherapyScreen

fun NavGraphBuilder.MainNavGraph(navController: NavHostController) {
    navigation(
        route = MAIN,
        startDestination = BottomBarRoutes.ListProcedure.route
    ) {
        composable(route =  BottomBarRoutes.ListProcedure.route) {
            ListProcedureScreen(navController)
        }
        composable(route =  BottomBarRoutes.ListTherapy.route) {
            ListTherapyScreen(navController)
        }
    }
}