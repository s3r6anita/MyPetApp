package com.example.mypet

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mypet.nav.BottomBarRoutes
import com.example.mypet.nav.SetupNavGraphs
import java.text.SimpleDateFormat

// Формат даты и времени в приложении
@SuppressLint("SimpleDateFormat")
val dateFormat = SimpleDateFormat("dd.MM.yyyy")
@SuppressLint("SimpleDateFormat")
val timeFormat = SimpleDateFormat("HH:mm")



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPetTopBar(
    text: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    actions: @Composable() (RowScope.() -> Unit),
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = text)
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                    )
                }
            }
        },
        actions =  actions,
        modifier = modifier
//        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    )
}

@Composable
fun MyPetBottomBar(
    navController: NavHostController,
    items: List<BottomBarRoutes>,
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route

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

                        popUpTo(BottomBarRoutes.ListProcedure.route) {
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

@Composable
fun MyPetApp(
    context: Context,
    navController: NavHostController = rememberNavController()
//    viewModel: OrderViewModel = viewModel()
) {
//    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = backStackEntry?.destination?.route

    SetupNavGraphs(
        navController = navController,
        context = context,
        modifier = Modifier
    )
}

