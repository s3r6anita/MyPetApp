package com.example.mypet.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mypet.data.Procedure
import com.example.mypet.data.ProcedureSettings
import com.example.mypet.data.ProcedureType
import com.example.mypet.dateFormat
import com.example.mypet.nav.Routes
import com.example.mypet.timeFormat
import java.util.Date

@Composable
fun ListProcedureScreen(navController: NavHostController) {

    val procedures = mutableListOf<Procedure>(
        Procedure(ProcedureType.HairCut, true, Date(), Date(), ProcedureSettings("20:00", "ежедневно", true)),
        Procedure(ProcedureType.NailTrim, true, Date(), Date(), ProcedureSettings("12:00", "еженедельно", true)),
        Procedure(ProcedureType.TeethCleaning, false, Date(), Date(), ProcedureSettings("08:00", "ежедневно", true)),
        Procedure(ProcedureType.PawTreatment, false, Date(), Date(), ProcedureSettings("10:00", "ежемесячно", true)),
        Procedure(ProcedureType.Another("Новый вид процедуры"), false, Date(), Date(), ProcedureSettings("12:00", "ежедневно", true))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            procedures.forEach { item ->
                ProcedureItem(
                    procedure = item,
                    navController = navController
                )
            }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate(Routes.CreateProcedure.route) { launchSingleTop = true }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset((-20).dp, (-20).dp)
        ) {
            Icon(Icons.Rounded.Add, "Добавить процедуру")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcedureItem(
    procedure: Procedure,
    navController: NavHostController
) {
    ListItem(
        headlineText = { Text(text = procedure.type.name) },
        supportingText = {
            Text(text = dateFormat.format(procedure.dateDone))
            Text(text = timeFormat.format(procedure.timeDone))
        },
        trailingContent = {
            if (procedure.isDone) {
                Icon(Icons.Rounded.Done, contentDescription = "Процедура выполнена")
            }
        },
        modifier = Modifier
            .clickable {
                navController.navigate(Routes.Procedure.route) {
                    launchSingleTop = true
                }
            }
    )
}
