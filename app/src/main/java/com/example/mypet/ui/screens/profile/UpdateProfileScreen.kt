package com.example.mypet.ui.screens.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mypet.MyPetTopBar
import com.example.mypet.data.pets
import com.example.mypet.dateFormat
import com.example.mypet.nav.Routes
import com.example.mypet.validateDate
import com.example.mypet.validatePet
import com.example.mypet.validateProfileDate
import java.util.Date

const val CORRECT_DATE_DIGIT_NUMBER = 10
const val CORRECT_TIME_DIGIT_NUMBER = 5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(navController: NavHostController, context: Context) {

    var pet = pets[0]
    var mutablePet by remember { mutableStateOf(pet) }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(Routes.UpdateProfile.title),
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
            // пол
            val radioOptions = listOf("Мужской", "Женский")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

            Text(
                text = "Пол",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Row(
                Modifier
                    .selectableGroup()
                    .padding(vertical = 8.dp)
            ) {
                radioOptions.forEach { text ->
                    Column(
                    ) {
                        Row(
                            modifier = Modifier
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),

                            ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }

            // кличка
            OutlinedTextField(
                value = mutablePet.nickname,
                onValueChange = { mutablePet = mutablePet.copy(nickname = it) },
                label = { Text("Кличка") },
                trailingIcon = {
                    IconButton(onClick = { mutablePet = mutablePet.copy(nickname = "") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                }
            )

            // вид
            OutlinedTextField(
                value = mutablePet.view,
                onValueChange = { mutablePet = mutablePet.copy(view = it) },
                label = { Text("Вид") },
                trailingIcon = {
                    IconButton(onClick = { mutablePet = mutablePet.copy(view = "") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                },
                modifier = Modifier.padding(top = 5.dp)
            )

            // порода
            OutlinedTextField(
                value = mutablePet.breed,
                onValueChange = { mutablePet = mutablePet.copy(breed = it) },
                label = { Text("Порода") },
                trailingIcon = {
                    IconButton(onClick = { mutablePet = mutablePet.copy(breed = "") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                },
                modifier = Modifier.padding(top = 5.dp)
            )

            // окрас
            OutlinedTextField(
                value = mutablePet.coat,
                onValueChange = { mutablePet = mutablePet.copy(coat = it) },
                label = { Text("Окрас") },
                trailingIcon = {
                    IconButton(onClick = { mutablePet = mutablePet.copy(coat = "") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                },
                modifier = Modifier.padding(top = 5.dp)
            )

            // номер микрочипа
            OutlinedTextField(
                value = mutablePet.microchipNumber,
                onValueChange = { mutablePet = mutablePet.copy(microchipNumber = it) },
                label = { Text("Номер микрочипа") },
                trailingIcon = {
                    IconButton(onClick = {
                        mutablePet = mutablePet.copy(microchipNumber = "")
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить")
                    }
                },
                supportingText = { Text(text = "Необзяательное поле") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.padding(top = 5.dp)
            )

            // дата рождения
            var openDialog by remember { mutableStateOf(false) }
            var dateString by remember { mutableStateOf(dateFormat.format(mutablePet.birthday)) }

            OutlinedTextField(
                value = dateString,
                onValueChange = {
                    if (it.length <= CORRECT_DATE_DIGIT_NUMBER) dateString = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Дата рождения") },
                supportingText = { Text(text = "Формат даты: ДД.ММ.ГГГГ") },
                trailingIcon = {
                    IconButton(onClick = { openDialog = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Открыть календарь")
                    }
                },
                modifier = Modifier.padding(top = 5.dp)
            )
            if (openDialog) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = {
                        openDialog = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog = false
                                mutablePet = mutablePet.copy(
                                    birthday = Date(
                                        datePickerState.selectedDateMillis ?: 0
                                    )
                                )
                                dateString = dateFormat.format(mutablePet.birthday)
                            },
                        ) {
                            Text("ОК")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { openDialog = false }
                        ) {
                            Text("Отмена")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            // сохранение
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    try {
                        // Проверка на формат даты и на "дату из будущего"
                        validateDate(dateString)
                        validateProfileDate(dateString)
                        validatePet(mutablePet)

                        // Изменение поля временной переменной
                        mutablePet = mutablePet.copy(birthday = dateFormat.parse(dateString), paul = selectedOption)

                        // Временная переменная замещает основную
                        if (pet != mutablePet) {
                            pet = mutablePet
                            Toast.makeText(context, "Данные изменены", Toast.LENGTH_LONG).show()
                        }

                        navController.navigateUp()
                    }
                    catch (e: IllegalArgumentException) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                    catch (e: Exception) {
                        Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}
