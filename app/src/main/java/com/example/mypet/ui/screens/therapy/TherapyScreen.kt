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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TherapyScreen(navController: NavHostController, profileId: String?, therapyId: String?, scope: CoroutineScope) {

    val therapy = pets[profileId?.toInt() ?: 0].therapies[therapyId?.toInt() ?: 0]
    var openAlertDialog by remember { mutableStateOf(false) }

    if (openAlertDialog) {
        AlertDialog(
            title = {
                Text(text = "Удаление терапии")
            },
            text = {
                Text(text = "Вы уверены, что хотите удалить терапию?")
            },
            onDismissRequest = {
                openAlertDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                        navController.navigateUp()
                        scope.launch {
                            delay(100)
                            removeTherapy(profileId, therapyId)
                        }
                    }
                ) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                    }
                ) {
                    Text("Отмена")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(Routes.Therapy.title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = {
                    IconButton(onClick = {
                        openAlertDialog = true
                    }
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
                    navController.navigate(Routes.UpdateTherapy.route +  "/" + profileId + "/" + therapyId) {
                        launchSingleTop = true
                    }
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
                text = therapy.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            // дата
            Text(
                text = dateFormat.format(therapy.date),
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

suspend fun removeTherapy(profileId: String?, therapyId: String?) {
    withContext(Dispatchers.IO) {
        pets[profileId?.toInt() ?: 0].therapies.removeAt(therapyId?.toInt() ?: 0)
    }
}
