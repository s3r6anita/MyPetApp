package com.example.mypet.ui.screens.therapy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mypet.MyPetTopBar
import com.example.mypet.R
import com.example.mypet.data.pets
import com.example.mypet.dateFormat
import com.example.mypet.nav.Routes

@Composable
fun TherapyScreen(navController: NavHostController) {

    val therapy = pets[0].therapies[0]

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(Routes.Therapy.title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = {
                    IconButton(onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(R.string.delete_button)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.UpdateTherapy.route) { launchSingleTop = true }
                },
                modifier = Modifier
            ) {
                Icon(Icons.Rounded.Edit, "Изменить лечение")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            // тип
            Text(
                text = therapy.type.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp, bottom = 20.dp)
            )
            // название
            Text(
                text = "${therapy.name}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            // дата
            Text(
                text = "${dateFormat.format(therapy.date)}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            // заметки
            OutlinedTextField(
                value = therapy.notes,
                onValueChange = { },
                readOnly = true,
                label = { Text("Заметки") },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
        }
    }
}