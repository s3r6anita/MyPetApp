package com.example.mypet.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.mypet.R
import com.example.mypet.data.Pet
import com.example.mypet.nav.MAIN
import com.example.mypet.nav.Routes
import java.util.Date

@Composable
fun ListProfileScreen(navController: NavHostController, context: Context) {


    val pets = mutableListOf(
        Pet("Мурзик", "Кот", "Британская", "мужской", Date(), "черный", "123456789012345"),
//        Pet("Шарик", "Собака", "Дворняжка", "мужской", Date(), "рыжий", "234567890123456"),
//        Pet("Мася", "Кошка", "Персидская", "женский", Date(), "белый", "345678901234567"),
//        Pet("Мурзик", "Кот", "Британская", "мужской", Date(), "черный", "123456789012345"),
//        Pet("Шарик", "Собака", "Дворняжка", "мужской", Date(), "рыжий", "234567890123456"),
//        Pet("Мася", "Кошка", "Персидская", "женский", Date(), "белый", "345678901234567"),
//        Pet("Мурзик", "Кот", "Британская", "мужской", Date(), "черный", "123456789012345"),
//        Pet("Шарик", "Собака", "Дворняжка", "мужской", Date(), "рыжий", "234567890123456"),
//        Pet("Мася", "Кошка", "Персидская", "женский", Date(), "белый", "345678901234567")
    )

    val preferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    val value = preferences.getBoolean("key", true)

    val (rememberUserChoice, onStateChange) = remember { mutableStateOf(value) }

    preferences.edit {
        putBoolean("key", rememberUserChoice)
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .align(Alignment.Center),
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 25.dp, end = 25.dp)
                    .toggleable(
                        value = rememberUserChoice,
                        onValueChange = { onStateChange(!rememberUserChoice) },
                        role = Role.Checkbox
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = rememberUserChoice,
                    onCheckedChange = null
                )
                Text(
                    text = "Запоминать выбор питомца на время сессия приложения",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 16.dp)
//                        .weight(1f)
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(vertical = 10.dp, horizontal = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    pets.forEach { pet ->
                        PetItem(
                            pet = pet,
                            canExit = rememberUserChoice,
                            navController = navController
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate(Routes.CreateProfile.route) { launchSingleTop = true }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset((-20).dp, (-20).dp)
        ) {
            Icon(Icons.Rounded.Add, "Добавить животное")
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetItem(pet: Pet, canExit: Boolean, navController: NavHostController) {
    ListItem(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(Color.LightGray)
            .clickable {
                navController.navigate(MAIN) {
                    if (canExit) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
        headlineText = { Text(text = "${pet.nickname}") },
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.pets_icon),
                contentDescription = "Иконка питомца"
            )
        },
        overlineText = { Text("${pet.view} ") }
    )
}