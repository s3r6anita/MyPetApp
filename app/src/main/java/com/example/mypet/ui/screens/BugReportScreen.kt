package com.example.mypet.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.mypet.nav.Routes

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "IntentReset",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@Composable
fun BugReportScreen(navController: NavHostController, context: Context) {

    var mutableSubject by remember { mutableStateOf("") }
    var mutableMessage by remember { mutableStateOf("") }
    var openAlertDialog by remember { mutableStateOf(false) }

    val email = "supp0rt_app@mail.ru"

    when {
        openAlertDialog -> {
            CustomAlertDialog(
                onDismissRequest = { openAlertDialog = false },
                onConfirmation = {
                    openAlertDialog = false

                    navController.navigate(Routes.ListProfile.route) {
                        popUpTo(Routes.ListProfile.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }

                    // логика создания наполнения письма и его отправки

                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        type = "text/plain"
                        data = Uri.parse("mailto:$email?subject=$mutableSubject&body=$mutableMessage")
                    }

                    try{
                        context.startActivity(Intent.createChooser(intent, "Send Email"))
                    } catch (e: Exception) {
                        Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_SHORT).show()
                    }
                },
                dialogTitle = "Переход в почтовое приложение",
                dialogText = "Для отправки сообщения об ошибке будет использован Ваш почтовый аккаунт. Желаете продолжить?"
            )
        }
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(Routes.BugReport.title),
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // заголовок
            OutlinedTextField(
                value = mutableSubject,
                maxLines = 1,
                onValueChange = { mutableSubject = it },
                label = { Text("Ошибка") },
                modifier = Modifier
                    .fillMaxWidth()
            )


            // сообщение
            OutlinedTextField(
                value = mutableMessage,
                readOnly = true,
                singleLine = false,
                maxLines = 100,
                onValueChange = { mutableMessage = it },
                label = { Text("Подробное описание ошибки") },
                placeholder = { Text(text = "Когда, Что и Где сломалось") },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .height(400.dp)
            )
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    openAlertDialog = true
                }
            ) {
                Text(text = "Отправить отчет об ошибке")
            }
        }
    }
}


@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("ОК")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Отмена")
            }
        }
    )
}