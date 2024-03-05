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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.mypet.MyPetTopBar
import com.example.mypet.R
import com.example.mypet.data.Pet
import com.example.mypet.data.pets
import com.example.mypet.nav.BottomBarRoutes
import com.example.mypet.nav.Routes


@Composable
fun ListProfileScreen(navController: NavHostController, context: Context) {

    val preferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    val value = preferences.getBoolean("key", true)

    val (rememberUserChoice, onStateChange) = remember { mutableStateOf(value) }

    preferences.edit {
        putBoolean("key", rememberUserChoice)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(id = R.string.list_profile_title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Routes.BugReport.route) { launchSingleTop = true } }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = stringResource(R.string.delete_button)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.CreateProfile.route) { launchSingleTop = true }
                },
                modifier = Modifier
            ) {
                Icon(Icons.Rounded.Add, "Добавить животное")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    )
    { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
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
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        pets.forEachIndexed { index, pet ->
                            PetItem(
                                pet = pet,
                                index = index,
                                canExit = rememberUserChoice,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun PetItem(pet: Pet, index: Int, canExit: Boolean, navController: NavHostController) {
    ListItem(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(Color.Red)
            .clickable {
                navController.navigate(BottomBarRoutes.ListProcedure.route + "/$index") {
                    if (canExit) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
        headlineContent = { Text(text = pet.nickname) },
        leadingContent = {
            Icon(
                painter = painterResource(R.drawable.pets_icon),
                contentDescription = "Иконка питомца"
            )
        },
        overlineContent = { Text(pet.view) }
    )
}
