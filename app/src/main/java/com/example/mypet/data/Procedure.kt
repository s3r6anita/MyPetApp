package com.example.mypet.data

import java.util.Date

// Создаем перечисление для видов процедуры
sealed class ProcedureType(val name: String, val type: String) {
    object HairCut: ProcedureType("Стрижка шерсти", "Гигиеническая")
    object NailTrim: ProcedureType("Стрижка когтей", "Гигиеническая")
    object TeethCleaning : ProcedureType("Чистка зубов", "Гигиеническая")
    object PawTreatment : ProcedureType("Обработка лап", "Гигиеническая")
    object TakeMedications : ProcedureType("Прием лекарств и витаминов", "Медицинская")
    object Vaccination : ProcedureType("Вакцинация", "Медицинская")
    object Deworming : ProcedureType("Дегельминтизация", "Медицинская")
    object FleaTreatment : ProcedureType("Обработка от блох", "Медицинская")
    class User(name: String) : ProcedureType(name, "Пользовательская")
}

// Создаем класс для хранения настроек процедуры
data class ProcedureSettings(
    var isReminderEnabled: Boolean, // нужно ли напоминать
    var beforeReminderTime: Int // за сколько минут напомнить
)

// Создаем класс для процедуры
data class Procedure(
    var type: ProcedureType,
    var isDone: Boolean,
    var frequency: Int, // через сколько часов повторять
    var dateDone: Date,
    var timeDone: Date,
    var notes: String,
    var settings: ProcedureSettings
)
