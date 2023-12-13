package com.example.mypet.ui.screens.procedure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mypet.MyPetTopBar
import com.example.mypet.R
import com.example.mypet.data.pets
import com.example.mypet.dateFormat
import com.example.mypet.nav.Routes
import com.example.mypet.timeFormat
import java.util.Date

@Composable
fun ProcedureScreen(navController: NavHostController) {

    val procedure = pets[0].procedures[0]

//    val procedure = procedures[0]

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(Routes.Procedure.title),
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
                    navController.navigate(Routes.UpdateProcedure.route) { launchSingleTop = true }
                },
                modifier = Modifier
            ) {
                Icon(Icons.Rounded.Edit, "Редактировать процедуру")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            // название
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = procedure.type.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )
                if (procedure.isDone) {
                    Icon(Icons.Rounded.Done, contentDescription = "Процедура выполнена")
                }
                else {
                    if (procedure.dateDone < Date()){
                        Icon(Icons.Rounded.Close, contentDescription = "Процедура не выполнена")
                    }
                    else {
                        Icon(Icons.Rounded.Info, contentDescription = "Процедура будет выполнена")
                    }
                }
            }

            // дата и время выполенения
            Text(
                text = "${timeFormat.format(procedure.timeDone)}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${dateFormat.format(procedure.dateDone)}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Напоминание",
                style = MaterialTheme.typography.labelMedium,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )
            if (procedure.settings.isReminderEnabled) {
                Text(
                    text = "за ${procedure.settings.beforeReminderTime} минут до процедуры",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            else {
                Text(
                    text = "выключено" ,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            OutlinedTextField(
                value = procedure.notes,
                onValueChange = { procedure.notes },
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
