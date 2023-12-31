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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mypet.MyPetTopBar
import com.example.mypet.R
import com.example.mypet.data.pets
import com.example.mypet.dateFormat
import com.example.mypet.nav.Routes
import com.example.mypet.timeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

@Composable
fun ProcedureScreen(navController: NavHostController, profileId: String?, procedureId: String?, scope: CoroutineScope) {

    val procedure = pets[profileId?.toInt() ?: 0].procedures[procedureId?.toInt() ?: 0]
    var openAlertDialog by remember { mutableStateOf(false) }

    if (openAlertDialog) {
        AlertDialog(
            title = {
                Text(text = "Удаление процедуры")
            },
            text = {
                Text(text = "Вы уверены, что хотите удалить процедуру?")
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
                            removeRrocedure(profileId, procedureId)
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
                text = stringResource(Routes.Procedure.title),
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
                    navController.navigate(Routes.UpdateProcedure.route +  "/" + profileId + "/" + procedureId) {
                        launchSingleTop = true
                    }
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
                text = timeFormat.format(procedure.timeDone),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = dateFormat.format(procedure.dateDone),
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

suspend fun removeRrocedure(profileId: String?, procedureId: String?) {
    withContext(Dispatchers.IO) {
        pets[profileId?.toInt() ?: 0].procedures.removeAt(procedureId?.toInt() ?: 0)
    }
}
