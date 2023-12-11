package com.example.mypet.ui.screens.therapy
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mypet.MyPetTopBar
import com.example.mypet.nav.BottomBarRoutes
import com.example.mypet.nav.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTherapyScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(Routes.CreateTherapy.title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = {}
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create Medicine",
                fontSize = 30.sp
            )
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    navController.navigate(BottomBarRoutes.ListTherapy.route) {
                        popUpTo(BottomBarRoutes.ListTherapy.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}