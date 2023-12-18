package com.example.mypet.nav

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.mypet.ui.screens.BugReportScreen
import com.example.mypet.ui.screens.ListProfileScreen
import com.example.mypet.ui.screens.procedure.CreateProcedureScreen
import com.example.mypet.ui.screens.procedure.ProcedureScreen
import com.example.mypet.ui.screens.procedure.UpdateProcedureScreen
import com.example.mypet.ui.screens.profile.CreateProfileScreen
import com.example.mypet.ui.screens.profile.ProfileScreen
import com.example.mypet.ui.screens.profile.UpdateProfileScreen
import com.example.mypet.ui.screens.therapy.CreateTherapyScreen
import com.example.mypet.ui.screens.therapy.TherapyScreen
import com.example.mypet.ui.screens.therapy.UpdateTherapyScreen
import kotlinx.coroutines.CoroutineScope

fun NavGraphBuilder.StartNavGraph(navController: NavHostController, context: Context, scope: CoroutineScope) {
    navigation(
        route = START,
        startDestination = Routes.ListProfile.route
    ) {
        composable(route = Routes.ListProfile.route) {
            ListProfileScreen(navController, context)
        }
        composable(route = Routes.BugReport.route) {
            BugReportScreen(navController, context)
        }

        composable(route = Routes.CreateProfile.route) {
            CreateProfileScreen(navController, context)
        }
        composable(
            route = Routes.Profile.route + "/{profileId}",
            arguments = listOf(navArgument(name = "profileId") {
                type = NavType.StringType
                defaultValue = "0"
                nullable = true
            })
        ) { backStackEntry ->
            ProfileScreen(navController, context, backStackEntry.arguments?.getString("profileId"), scope)
        }
        composable(route = Routes.UpdateProfile.route + "/{profileId}",
            arguments = listOf(navArgument(name = "profileId") {
                type = NavType.StringType
                defaultValue = "0"
                nullable = true
            })
        ) { backStackEntry ->
            UpdateProfileScreen(navController, context, backStackEntry.arguments?.getString("profileId"))
        }


        composable(route = Routes.CreateProcedure.route + "/{profileId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )
        ) { backStackEntry ->
            CreateProcedureScreen(navController, context, backStackEntry.arguments?.getString("profileId"))
        }
        composable(route = Routes.Procedure.route + "/{profileId}" + "/{procedureId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument(name = "procedureId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )
        ) { backStackEntry ->
            ProcedureScreen(navController, backStackEntry.arguments?.getString("profileId"), backStackEntry.arguments?.getString("procedureId"), scope)
        }
        composable(route = Routes.UpdateProcedure.route + "/{profileId}" + "/{procedureId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument(name = "procedureId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )
        ) { backStackEntry ->
            UpdateProcedureScreen(navController, context, backStackEntry.arguments?.getString("profileId"), backStackEntry.arguments?.getString("procedureId"))
        }


        composable(route = Routes.CreateTherapy.route + "/{profileId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )
        ){ backStackEntry ->
            CreateTherapyScreen(navController, context, backStackEntry.arguments?.getString("profileId"))
        }
        composable(route = Routes.Therapy.route + "/{profileId}" + "/{therapyId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument(name = "therapyId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )
        ) { backStackEntry ->
            TherapyScreen(navController, backStackEntry.arguments?.getString("profileId"), backStackEntry.arguments?.getString("therapyId"), scope)
        }
        composable(route = Routes.UpdateTherapy.route + "/{profileId}" + "/{therapyId}",
            arguments = listOf(
                navArgument(name = "profileId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument(name = "therapyId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )
        ) { backStackEntry ->
            UpdateTherapyScreen(navController, context, backStackEntry.arguments?.getString("profileId"), backStackEntry.arguments?.getString("therapyId"))
        }
    }
}