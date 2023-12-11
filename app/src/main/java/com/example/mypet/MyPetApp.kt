package com.example.mypet

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mypet.nav.BottomBarRoutes
import com.example.mypet.nav.Routes
import com.example.mypet.nav.SetupNavGraphs
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
val dateFormat = SimpleDateFormat("dd.MM.yyyy")
@SuppressLint("SimpleDateFormat")
val timeFormat = SimpleDateFormat("HH:mm")



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPetTopBar(
    navController: NavHostController,
    currentScreen: String?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                when (currentScreen) {
                    Routes.ListProfile.route -> stringResource(Routes.ListProfile.title)
                    Routes.BugReport.route -> stringResource(Routes.BugReport.title)
                    Routes.CreateProfile.route -> stringResource(Routes.CreateProfile.title)
                    Routes.Profile.route -> stringResource(Routes.Profile.title)
                    Routes.UpdateProfile.route -> stringResource(Routes.UpdateProfile.title)

                    Routes.UpdateProcedure.route -> stringResource(Routes.UpdateProcedure.title)
                    Routes.Procedure.route -> stringResource(Routes.Procedure.title)
                    Routes.CreateProcedure.route -> stringResource(Routes.CreateProcedure.title)

                    Routes.UpdateTherapy.route -> stringResource(Routes.UpdateTherapy.title)
                    Routes.Therapy.route -> stringResource(Routes.Therapy.title)
                    Routes.CreateTherapy.route -> stringResource(Routes.CreateTherapy.title)

                    else -> {
                        stringResource(R.string.app_name)
                    } // нужно вставить кличку животного
                }.toString(),
            )
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        navigationIcon = {
            if (canNavigateBack && currentScreen != BottomBarRoutes.ListHygiene.route && currentScreen != BottomBarRoutes.ListMedicine.route) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                    )
                }
            }
        },
        actions = {
            if (currentScreen == Routes.Procedure.route) {
                IconButton( onClick = {  }
                ) {
                    Icon( imageVector = Icons.Filled.Delete, contentDescription = stringResource(R.string.delete_button) )
                }
            }
            if (currentScreen == BottomBarRoutes.ListHygiene.route || currentScreen == BottomBarRoutes.ListMedicine.route) {
                Icon(
                    painter = painterResource(R.drawable.pets_icon),
                    contentDescription = "Ваш питомец",
                    modifier = Modifier
                        .clickable { navController.navigate(Routes.Profile.route) }
                        .padding(10.dp, 0.dp)
                        .size(30.dp)
                )
            }
            if (currentScreen == Routes.Procedure.route) {
                IconButton( onClick = {  }
                ) {
                    Icon( imageVector = Icons.Filled.Delete, contentDescription = stringResource(R.string.delete_button) )
                }
            }
            if (currentScreen == Routes.Therapy.route) {
                IconButton( onClick = {  }
                ) {
                    Icon( imageVector = Icons.Filled.Delete, contentDescription = stringResource(R.string.delete_button) )
                }
            }
            if (currentScreen == Routes.ListProfile.route) {
                IconButton( onClick = {
                    navController.navigate(Routes.BugReport.route) { launchSingleTop = true }
                }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = stringResource(R.string.delete_button)
                    )
                }
            }
        },
        modifier = modifier
//        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    )
}

@Composable
fun MyPetBottomBar(
    navController: NavHostController,
    currentScreen: String?,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomBarRoutes.ListHygiene,
        BottomBarRoutes.ListMedicine
    )
    NavigationBar(
        modifier = modifier
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.title)
                    )
                },
                label = { Text(text = stringResource(item.title)) },
                selected = currentScreen == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(BottomBarRoutes.ListHygiene.route) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPetApp(
    context: Context,
    navController: NavHostController = rememberNavController(),
//    viewModel: OrderViewModel = viewModel()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            MyPetTopBar(
                navController = navController,
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            if (currentScreen == BottomBarRoutes.ListHygiene.route || currentScreen == BottomBarRoutes.ListMedicine.route) {
                MyPetBottomBar(
                    navController = navController,
                    currentScreen = currentScreen
                )
            }
        }
    ) { innerPadding ->
        SetupNavGraphs(
            navController = navController,
            context = context,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

