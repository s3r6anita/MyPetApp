package com.example.mypet.ui.screens.hygiene

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mypet.nav.Routes

@Composable
fun ProcedureScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Procedure",
                fontSize = 30.sp
            )
        }
        FloatingActionButton(
            onClick = {
                navController.navigate(Routes.UpdateProcedure.route) { launchSingleTop = true }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset((-20).dp, (-20).dp)
        ) {
            Icon(Icons.Rounded.Edit, "Update hygiene")
        }
    }
}

