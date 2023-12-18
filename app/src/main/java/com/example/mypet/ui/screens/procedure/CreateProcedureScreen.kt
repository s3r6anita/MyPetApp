package com.example.mypet.ui.screens.procedure

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mypet.MyPetTopBar
import com.example.mypet.R
import com.example.mypet.data.Procedure
import com.example.mypet.data.ProcedureSettings
import com.example.mypet.data.ProcedureType
import com.example.mypet.data.pets
import com.example.mypet.dateFormat
import com.example.mypet.nav.BottomBarRoutes
import com.example.mypet.nav.Routes
import com.example.mypet.timeFormat
import com.example.mypet.ui.screens.profile.CORRECT_DATE_DIGIT_NUMBER
import com.example.mypet.validateDate
import com.example.mypet.validateTime
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProcedureScreen(navController: NavHostController, context: Context, profileId: String?) {

    val id = profileId?.toInt() ?: 0

    var mutableProc by remember {
        mutableStateOf(
            Procedure(
                ProcedureType.HairCut,
                false,
                0,
                Date(),
                Date(),
                "",
                ProcedureSettings(true, 5)
            )
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(Routes.CreateProcedure.title),
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val modifier = Modifier
                .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()

            // Тип процедуры - выпадающее меню с выбором
            val typeOptions = listOf("Гигиеническая", "Медицинская", "Пользовательская")
            var typeExpanded by remember { mutableStateOf(false) }
            var selectedType by remember { mutableStateOf(typeOptions[0]) }

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
                        .padding(bottom = 10.dp),
                    readOnly = true,
                    value = selectedType,
                    onValueChange = { },
                    label = { Text("Тип процедуры") },
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

            // Название процедуры - выпадающее меню с выбором
            val nameOptions = when (selectedType) {
                "Гигиеническая" -> listOf(
                    "Стрижка шерсти",
                    "Стрижка когтей",
                    "Чистка зубов",
                    "Обработка лап"
                )

                "Медицинская" -> listOf(
                    "Прием лекарств и витаминов",
                    "Вакцинация",
                    "Дегельминтизация",
                    "Обработка от блох"
                )

                else -> emptyList<String>()
            }
            var nameExpanded by remember { mutableStateOf(false) }
            var selectedName by remember { mutableStateOf("") }

            if (nameOptions != emptyList<String>()) {
                // если не пользовательский тип процедуры
                ExposedDropdownMenuBox(
                    expanded = nameExpanded,
                    onExpandedChange = {
                        nameExpanded = it
                    },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    TextField(
                        modifier = Modifier
                            .menuAnchor()
                            .padding(bottom = 10.dp),
                        readOnly = true,
                        value = selectedName,
                        onValueChange = { },
                        label = { Text("Название") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = nameExpanded
                            )
                        },
//                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = nameExpanded,
                        onDismissRequest = {
                            nameExpanded = false
                        }
                    ) {
                        nameOptions.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    selectedName = selectionOption
                                    nameExpanded = false
                                }
                            )
                        }
                    }
                }
            } else {
                // если пользовательский тип процедуры
                OutlinedTextField(
                    value = selectedName, // mutableProc.type.name
                    onValueChange = {
                        selectedName = it
                    }, // { mutableProc = mutableProc.copy(type = ProcedureType.User(it)) },
                    label = { Text("Название") },
                    modifier = Modifier
                        .padding(bottom = 10.dp, start = 30.dp, end = 30.dp)
                        .fillMaxWidth()
                )
            }

            // периодичность - выпадающее меню с выбором
            val frequencyOptions =
                listOf("никогда", "указать в часах", "указать в днях", "указать в неделях")
            var frequencyExpanded by remember { mutableStateOf(false) }
            var selectedFrequency by remember { mutableStateOf(frequencyOptions[0]) }
            var frequencyString by remember { mutableStateOf("") }

            ExposedDropdownMenuBox(
                expanded = frequencyExpanded,
                onExpandedChange = {
                    frequencyExpanded = it
                },
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .padding(bottom = 10.dp),
                    readOnly = true,
                    label = { Text("Периодичность") },
                    value = selectedFrequency,
                    onValueChange = { },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = frequencyExpanded)
                    }
                )
                ExposedDropdownMenu(
                    expanded = frequencyExpanded,
                    onDismissRequest = {
                        frequencyExpanded = false
                    }
                ) {
                    frequencyOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedFrequency = selectionOption
                                frequencyExpanded = false
                            }
                        )
                    }
                }
            }
            if (selectedFrequency != "никогда") {
                OutlinedTextField(
                    value = frequencyString,
                    onValueChange = { frequencyString = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = { Text(text = "Период")},
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { frequencyString = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Очистить")
                        }
                    },
                    modifier = modifier.padding(bottom = 10.dp)
                )
            }

            // Время выполнения - тайм пикер
            var openTimeDialog by remember { mutableStateOf(false) }
            val state = rememberTimePickerState()
            var timeString by remember { mutableStateOf(timeFormat.format(mutableProc.timeDone)) }

            OutlinedTextField(
                value = timeString,
                onValueChange = { },
                readOnly = true,
                label = { Text("Время выполнения") },
                supportingText = { Text(text = "Формат времени: ЧЧ.ММ") },
                trailingIcon = {
                    IconButton(
                        onClick = { openTimeDialog = true }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_access_time),
                            contentDescription = "Открыть часы"
                        )
                    }
                },
                modifier = modifier
            )
            if (openTimeDialog) {
                AlertDialog(
                    title = {
                        Text(text = "Выберите время")
                    },
                    text = { TimePicker(state = state) },
                    onDismissRequest = { openTimeDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            timeString = "${state.hour}:${state.minute}"
                            openTimeDialog = false
                        }) {
                            Text("ОК")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { openTimeDialog = false }) {
                            Text("Отмена")
                        }
                    }
                )
            }

            // дата выполнения
            var openDateDialog by remember { mutableStateOf(false) }
            var dateString by remember { mutableStateOf(dateFormat.format(mutableProc.dateDone)) }

            OutlinedTextField(
                value = dateString,
                onValueChange = {
                    if (it.length <= CORRECT_DATE_DIGIT_NUMBER) dateString = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Дата выполнения") },
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

            // уведомление
            var enableNotifications by remember { mutableStateOf(false) }
            var timeNotificationString by remember { mutableStateOf("") }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 10.dp)
                    .toggleable(
                        value = enableNotifications,
                        onValueChange = {
                            enableNotifications = it
                        },
                        role = Role.Checkbox
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Уведомления",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Checkbox(
                    checked = enableNotifications,
                    onCheckedChange = null,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            if (enableNotifications) {
                OutlinedTextField(
                    value = timeNotificationString,
                    onValueChange = { timeNotificationString = it },
                    label = { Text("За сколько минут напомнить") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    trailingIcon = {
                        IconButton(onClick = { timeNotificationString = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Очистить")
                        }
                    }
                )
            }

            // заметки
            OutlinedTextField(
                value = mutableProc.notes,
                onValueChange = { mutableProc = mutableProc.copy(notes = it) },
                label = { Text("Заметки") },
                trailingIcon = {
                    IconButton(onClick = { mutableProc = mutableProc.copy(notes = "") }) {
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
                        validateTime(timeString)
                        if (selectedName == "") {
                            throw IllegalArgumentException("Некорректное название")
                        }

                        // изменение полей на основе полученных значений
                        mutableProc = mutableProc.copy(
                            type = when (selectedName) {
                                "Стрижка шерсти" -> ProcedureType.HairCut
                                "Стрижка когтей" -> ProcedureType.NailTrim
                                "Чистка зубов" -> ProcedureType.TeethCleaning
                                "Обработка лап" -> ProcedureType.PawTreatment
                                "Прием лекарств и витаминов" -> ProcedureType.TakeMedications
                                "Вакцинация" -> ProcedureType.Vaccination
                                "Дегельминтизация" -> ProcedureType.Deworming
                                "Обработка от блох" -> ProcedureType.FleaTreatment
                                else -> ProcedureType.User(selectedName)
                            },
                            isDone = false,
                            frequency = when (selectedFrequency) {
                                "указать в часах" -> frequencyString.toInt() * 1
                                "указать в днях" -> frequencyString.toInt() * 24
                                "указать в неделях" -> frequencyString.toInt() * 168
                                else -> 0
                            },
                            dateDone = dateFormat.parse(dateString),
                            timeDone = timeFormat.parse(timeString),
                            // notes уже изменены
                            settings = ProcedureSettings(
                                enableNotifications,
                                if (!enableNotifications) 0 else timeNotificationString.toInt()
                            )
                        )

                        // добавление в список процедур
                        pets[id].procedures += mutableProc

                        Toast.makeText(
                            context,
                            "Процедура успешно добавлена",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.navigate(BottomBarRoutes.ListProcedure.route + "/" + profileId) {
                            popUpTo(BottomBarRoutes.ListProcedure.route + "/" + profileId) {
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
