package com.example.mypet.ui.screens.hygiene

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mypet.nav.BottomBarRoutes

@Composable
fun CreateProcedureScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create Procedure",
            fontSize = 30.sp
        )
        Button(
            modifier = Modifier.padding(10.dp),
            onClick = {
                navController.navigate(BottomBarRoutes.ListHygiene.route) {
                    popUpTo(BottomBarRoutes.ListHygiene.route) {
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