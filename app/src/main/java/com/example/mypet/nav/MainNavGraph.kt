package com.example.mypet.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.mypet.ui.screens.ListProcedureScreen
import com.example.mypet.ui.screens.ListTherapyScreen

fun NavGraphBuilder.MainNavGraph(navController: NavHostController) {
    navigation(
        route = MAIN,
        startDestination = BottomBarRoutes.ListProcedure.route + "/{profileId}"
    ) {
        composable(route =  BottomBarRoutes.ListProcedure.route + "/{profileId}",
            arguments = listOf(navArgument(name = "profileId") {
                type = NavType.StringType
                defaultValue = "0"
                nullable = true
            })
        ) { backStackEntry ->
            ListProcedureScreen(navController, backStackEntry.arguments?.getString("profileId"))
        }
        composable(route =  BottomBarRoutes.ListTherapy.route+ "/{profileId}",
            arguments = listOf(navArgument(name = "profileId") {
                type = NavType.StringType
                defaultValue = "0"
                nullable = true
            })
        ) { backStackEntry ->
            ListTherapyScreen(navController, backStackEntry.arguments?.getString("profileId"))
        }
    }
}