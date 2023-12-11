package com.example.mypet.data

import java.util.Date

// Создаем перечисление для видов процедуры
sealed class ProcedureType(val name: String) {
    object HairCut: ProcedureType("Стрижка шерсти")
    object NailTrim: ProcedureType("Стрижка когтей")
    object TeethCleaning : ProcedureType("Чистка зубов")
    object PawTreatment : ProcedureType("Обработка лап")
    class Another(name: String) : ProcedureType(name)
}

// Создаем класс для хранения настроек процедуры
data class ProcedureSettings(
    val reminderTime: String,
    val reminderFrequency: String, // ежедневно, еженедельно, ежемесячно
    val isReminderEnabled: Boolean // нужно ли напоминать
)

// Создаем класс для процедуры
data class Procedure(
    val type: ProcedureType,
    val isDone: Boolean,
    val dateDone: Date,
    val timeDone: Date,
    val settings: ProcedureSettings
)
