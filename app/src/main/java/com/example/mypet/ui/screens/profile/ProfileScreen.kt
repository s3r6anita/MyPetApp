package com.example.mypet.ui.screens.profile

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.mypet.data.Pet
import com.example.mypet.data.pets
import com.example.mypet.dateFormat
import com.example.mypet.nav.Routes
import com.example.mypet.nav.START
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(InternalCoroutinesApi::class)
@Composable
fun ProfileScreen(navController: NavHostController, context: Context, profileId: String?, scope: CoroutineScope) {

    val id = profileId?.toInt() ?: 0
    val pet = pets[id]
    var openAlertDialog by remember { mutableStateOf(false) }

    if (openAlertDialog) {
        AlertDialog(
            title = {
                Text(text = "Удаление профиля")
            },
            text = {
                Text(text = "Вы уверены, что хотите удалить профиль?")
            },
            onDismissRequest = {
                openAlertDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                        navController.navigate(START) {
                            popUpTo(Routes.ListProfile.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        scope.launch {
                            delay(100)
                            removePet(id)
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
                text = stringResource(Routes.Profile.title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = {
                    // кнопка удалить
                    IconButton(onClick = {
                        openAlertDialog = true
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить"
                        )
                    }
                    // кнопка поделиться
                    IconButton(onClick = {
                        val message = formatPet(pet)
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            putExtra(Intent.EXTRA_TEXT, message)
                            type = "text/plain"
                        }
                        try{
                            context.startActivity(Intent.createChooser(intent, "Отправить сведения о питомце"))
                        } catch (e: Exception) {
                            Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_LONG).show()
                        }          }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Поделиться"
                        )
                    }
                    // кнопка выхода
                    IconButton(onClick = {
                        navController.navigate(START) {
                            popUpTo(Routes.ListProfile.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Выход"
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.UpdateProfile.route + "/" + profileId) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
            ) {
                Icon(Icons.Rounded.Edit, "Изменить профиль питомца")
            }
        },

        floatingActionButtonPosition = FabPosition.End

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Информация о питомце",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 20.dp),
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
            )
            TextComponent(
                header = "Кличка",
                value = pet.nickname
            )
            TextComponent(
                header = "Вид",
                value = pet.view
            )
            TextComponent(
                header = "Порода",
                value = pet.breed
            )
            TextComponent(
                header = "Пол",
                value = pet.paul
            )
            TextComponent(
                header = "Дата рождения",
                value = dateFormat.format(pet.birthday)
            )
            TextComponent(
                header = "Окрас",
                value = pet.coat
            )
            TextComponent(
                header = "Номер микрочипа",
                value = pet.microchipNumber
            )
        }
    }
}

@Composable
fun TextComponent(header: String, value: String) {
    Text(
        text = header,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(bottom = 4.dp),
//        fontSize = 17.sp,
//        fontWeight = FontWeight.SemiBold,
    )
    Text(
        text = value,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 12.dp)
//        fontSize = 20.sp,
    )
}


fun formatPet(pet: Pet): String {
    return buildString {
        append("Кличка: ${pet.nickname}\n")
        append("Вид: ${pet.view}\n")
        append("Порода: ${pet.breed}\n")
        append("Пол: ${pet.paul}\n")
        append("Дата рождения: ${dateFormat.format(pet.birthday)}\n")
        append("Окрас: ${pet.coat}\n")
        append("Номер микрочипа: ${pet.microchipNumber}\n")
    }
}


suspend fun removePet(id: Int) {
    withContext(Dispatchers.IO) {
        pets.removeAt(id)
    }
}