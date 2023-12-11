package com.example.mypet.nav

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
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

fun NavGraphBuilder.StartNavGraph(navController: NavHostController, context: Context) {
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
            CreateProfileScreen(navController)
        }
        composable(route = Routes.Profile.route) {
            ProfileScreen(navController)
        }
        composable(route = Routes.UpdateProfile.route) {
            UpdateProfileScreen(navController, context)
        }


        composable(route = Routes.CreateProcedure.route) {
            CreateProcedureScreen(navController)
        }
        composable(route = Routes.Procedure.route) {
            ProcedureScreen(navController)
        }
        composable(route = Routes.UpdateProcedure.route) {
            UpdateProcedureScreen(navController)
        }


        composable(route = Routes.CreateTherapy.route) {
            CreateTherapyScreen(navController)
        }
        composable(route = Routes.Therapy.route) {
            TherapyScreen(navController)
        }
        composable(route = Routes.UpdateTherapy.route) {
            UpdateTherapyScreen(navController)
        }
    }
}