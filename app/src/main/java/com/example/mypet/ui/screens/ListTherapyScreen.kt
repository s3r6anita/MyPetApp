package com.example.mypet.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mypet.MyPetBottomBar
import com.example.mypet.MyPetTopBar
import com.example.mypet.R
import com.example.mypet.data.Therapy
import com.example.mypet.data.pets
import com.example.mypet.dateFormat
import com.example.mypet.nav.BottomBarRoutes
import com.example.mypet.nav.Routes

@Composable
fun ListTherapyScreen(navController: NavHostController) {

    val pet = pets[0]

    val therapies = pet.therapies

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = "${pet.nickname}",
                canNavigateBack = false,
                navigateUp = { navController.navigateUp() },
                actions = {
                    Icon(
                        painter = painterResource(R.drawable.pets_icon),
                        contentDescription = "Ваш питомец",
                        modifier = Modifier
                            .clickable { navController.navigate(Routes.Profile.route) }
                            .padding(10.dp, 0.dp)
                    )
                }
            )
        },
        bottomBar = {
            MyPetBottomBar(
                navController = navController,
                listOf(
                    BottomBarRoutes.ListProcedure,
                    BottomBarRoutes.ListTherapy
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.CreateTherapy.route) { launchSingleTop = true }
                },
                modifier = Modifier
            ) {
                Icon(Icons.Rounded.Add, "Добавить процедуру")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            therapies.forEach { item ->
                TherapyItem(
                    therapy = item,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun TherapyItem(
    therapy: Therapy,
    navController: NavHostController
) {
    ListItem(
        headlineContent = { Text(text = therapy.type.name) },
        supportingContent = {
            Text(text = dateFormat.format(therapy.date))
        },
        trailingContent = { },
        modifier = Modifier
            .clickable {
                navController.navigate(Routes.Therapy.route) {
                    launchSingleTop = true
                }
            }
    )
}
