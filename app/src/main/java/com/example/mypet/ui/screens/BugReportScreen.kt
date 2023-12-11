package com.example.mypet.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mypet.nav.Routes

@Composable
fun BugReportScreen(navController: NavHostController, context: Context) {

    var openAlertDialog by remember { mutableStateOf(false) }
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
                    val email = "supp0rt_app@mail.ru"
                    val subject = "Ошибка в работе приложения"
                    val message = "Оно не работает!!!"

                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        type = "text/plain"
                        data = Uri.parse("mailto:$email?subject=$subject&body=$message")
                    }

                    try{
                        context.startActivity(Intent.createChooser(intent, "Send Email"))
                    } catch (e: Exception) {
                        Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_SHORT).show()
                    }
                },
                dialogTitle = "Переход на почту",
                dialogText = "Для отправки сообщения об ошибке будет использован Ваш почтовый аккаунт. Желаете продолжить?"
            )
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bug Report",
            fontSize = 30.sp
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
                Text("Подтвердить")
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