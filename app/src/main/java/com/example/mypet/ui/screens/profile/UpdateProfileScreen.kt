package com.example.mypet.ui.screens.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.mypet.data.Pet
import com.example.mypet.nav.BottomBarRoutes
import com.example.mypet.nav.MAIN
import com.example.mypet.nav.Routes
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(navController: NavHostController, context: Context) {

    val pet = Pet("Шарик", "Собака", "Дворняжка", "мужской", Date(), "рыжий", "234567890123456")
    var mutablePet by remember { mutableStateOf(pet) }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(Routes.UpdateProfile.title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = {}
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = mutablePet.nickname,
                onValueChange = { mutablePet = mutablePet.copy(nickname = it) },
                label = { Text("Кличка") },
                trailingIcon = {
                    IconButton(onClick = { mutablePet = mutablePet.copy(nickname = "") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                }
            )
            OutlinedTextField(
                value = mutablePet.view,
                onValueChange = { mutablePet = mutablePet.copy(view = it) },
                label = { Text("Вид") },
                trailingIcon = {
                    IconButton(onClick = { mutablePet = mutablePet.copy(view = "") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                }
            )
            OutlinedTextField(
                value = mutablePet.breed,
                onValueChange = { mutablePet = mutablePet.copy(breed = it) },
                label = { Text("Порода") },
                trailingIcon = {
                    IconButton(onClick = { mutablePet = mutablePet.copy(breed = "") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                }
            )

            // Пол

            // Дата рождения

//            val snackState = remember { SnackbarHostState() }
//            val snackScope = rememberCoroutineScope()
//            SnackbarHost(hostState = snackState, Modifier)
//            val openDialog = remember { mutableStateOf(true) }
//
//            // TODO demo how to read the selected date from the state.
//            if (openDialog.value) {
//                val datePickerState = rememberDatePickerState()
//                val confirmEnabled = remember {
//                    derivedStateOf { datePickerState.selectedDateMillis != null }
//                }
//                DatePickerDialog(
//                    onDismissRequest = {
//                        // Dismiss the dialog when the user clicks outside the dialog or on the back
//                        // button. If you want to disable that functionality, simply use an empty
//                        // onDismissRequest.
//                        openDialog.value = false
//                    },
//                    confirmButton = {
//                        TextButton(
//                            onClick = {
//                                openDialog.value = false
//                                snackScope.launch {
//                                    snackState.showSnackbar(
//                                        "Selected date timestamp: ${datePickerState.selectedDateMillis}"
//                                    )
//                                }
//                            },
//                            enabled = confirmEnabled.value
//                        ) {
//                            Text("OK")
//                        }
//                    },
//                    dismissButton = {
//                        TextButton(
//                            onClick = {
//                                openDialog.value = false
//                            }
//                        ) {
//                            Text("Cancel")
//                        }
//                    }
//                ) {
//                    DatePicker(state = datePickerState)
//                }
//            }


            OutlinedTextField(
                value = mutablePet.coat,
                onValueChange = { mutablePet = mutablePet.copy(coat = it) },
                label = { Text("Окрас") },
                trailingIcon = {
                    IconButton(onClick = { mutablePet = mutablePet.copy(coat = "") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                }
            )
            OutlinedTextField(
                value = mutablePet.microchipNumber,
                onValueChange = { mutablePet = mutablePet.copy(microchipNumber = it) },
                label = { Text("Номер микрочипа") },
                trailingIcon = {
                    IconButton(onClick = {
                        mutablePet = mutablePet.copy(microchipNumber = "")
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                }
            )
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    Toast.makeText(
                        context,
                        "${mutablePet.nickname} ${mutablePet.view} ${mutablePet.breed}",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate(MAIN) {
                        popUpTo(BottomBarRoutes.ListProcedure.route) {
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
