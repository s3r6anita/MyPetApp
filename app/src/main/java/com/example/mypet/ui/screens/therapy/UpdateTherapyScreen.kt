package com.example.mypet.ui.screens.therapy

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mypet.MyPetTopBar
import com.example.mypet.data.TherapyType
import com.example.mypet.data.pets
import com.example.mypet.dateFormat
import com.example.mypet.nav.Routes
import com.example.mypet.ui.screens.profile.CORRECT_DATE_DIGIT_NUMBER
import com.example.mypet.validateDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTherapyScreen(navController: NavHostController, context: Context, profileId: String?, therapyId: String?) {

    val therapy = pets[profileId?.toInt() ?: 0].therapies[therapyId?.toInt() ?: 0]

    var mutableTherapy by remember { mutableStateOf(therapy) }

    Scaffold (
        topBar = {
            MyPetTopBar(
                text = stringResource(Routes.UpdateTherapy.title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = { }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val modifier = Modifier
                .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()

            // Тип терапии - выпадающее меню с выбором
            val typeOptions = listOf("Болезнь", "Вакцинация", "Анализ", "Посещение врача", "Пользовательская")
            var typeExpanded by remember { mutableStateOf(false) }
            var selectedType by remember { mutableStateOf(mutableTherapy.type.name) }

            ExposedDropdownMenuBox(
                expanded = typeExpanded,
                onExpandedChange = {
                    typeExpanded = it
                },
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .padding(top = 10.dp),
                    readOnly = true,
                    value = selectedType,
                    onValueChange = { },
                    label = { Text("Тип терапии") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = typeExpanded
                        )
                    }
                )
                ExposedDropdownMenu(
                    expanded = typeExpanded,
                    onDismissRequest = {
                        typeExpanded = false
                    }
                ) {
                    typeOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedType = selectionOption
                                typeExpanded = false
                            }
                        )
                    }
                }
            }

            // название
            OutlinedTextField(
                value = mutableTherapy.name,
                onValueChange = { mutableTherapy = mutableTherapy.copy(name = it) },
                label = { Text("Название") },
                trailingIcon = {
                    IconButton(onClick = { mutableTherapy = mutableTherapy.copy(name = "") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                },
                modifier = modifier
            )

            // дата
            var openDateDialog by remember { mutableStateOf(false) }
            var dateString by remember { mutableStateOf(dateFormat.format(mutableTherapy.date)) }

            OutlinedTextField(
                value = dateString,
                onValueChange = {
                    if (it.length <= CORRECT_DATE_DIGIT_NUMBER) dateString = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Дата") },
                supportingText = { Text(text = "Формат даты: ДД.ММ.ГГГГ") },
                trailingIcon = {
                    IconButton(onClick = { openDateDialog = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Открыть календарь")
                    }
                },
                modifier = modifier
            )
            if (openDateDialog) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = {
                        openDateDialog = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDateDialog = false
                                dateString = dateFormat.format(datePickerState.selectedDateMillis ?: 0)
                            },
                        ) {
                            Text("ОК")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { openDateDialog = false }
                        ) {
                            Text("Отмена")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            // заметки
            OutlinedTextField(
                value = mutableTherapy.notes,
                onValueChange = { mutableTherapy = mutableTherapy.copy(notes = it) },
                label = { Text("Заметки") },
                trailingIcon = {
                    IconButton(onClick = { mutableTherapy = mutableTherapy.copy(notes = "") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                },
                modifier = modifier
                    .height(120.dp)
            )
            // сохранение
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    try {
                        // Проверка на формат даты и на "дату из будущего"
                        validateDate(dateString)
                        if (mutableTherapy.name == "") {
                            throw IllegalArgumentException("Некорректное название")
                        }
                        mutableTherapy = mutableTherapy.copy(
                            type = when (selectedType) {
                                "Болезнь" -> TherapyType.Illness
                                "Вакцинация" -> TherapyType.Vaccination
                                "Анализ" -> TherapyType.Analysis
                                "Посещение врача" -> TherapyType.VisitDoctor
                                "Пользовательская" -> TherapyType.User(mutableTherapy.name)
                                else -> throw IllegalArgumentException("Некорректное название")
                            },
                            // name уже изменено
                            date = dateFormat.parse(dateString)
                            // notes уже изменено
                        )

                        // добавление в список терапий

//       не нужно потому что уже в mutableTherapy лежит ссылка
                        therapy.let {
                            it.type = mutableTherapy.type
                            it.name = mutableTherapy.name
                            it.notes = mutableTherapy.notes
                            it.date = mutableTherapy.date
                        }

                        Toast.makeText(
                            context,
                            "Терапия успешно добавлено",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.navigate(Routes.Therapy.route + "/" + profileId + "/" + therapyId) {
                            popUpTo(Routes.Therapy.route + "/" + profileId + "/" + therapyId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    } catch (e: IllegalArgumentException) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}