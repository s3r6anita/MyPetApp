package com.example.mypet.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mypet.MyPetTopBar
import com.example.mypet.data.Pet
import com.example.mypet.dateFormat
import com.example.mypet.nav.Routes
import com.example.mypet.nav.START
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {

    val pet = Pet("Шарик", "Собака", "Дворняжка", "мужской", Date(), "рыжий", "234567890123456")

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(Routes.Profile.title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = {
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
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Выход"
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.UpdateProfile.route) {
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
