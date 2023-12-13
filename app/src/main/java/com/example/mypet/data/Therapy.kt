package com.example.mypet.data

import java.util.Date

// Создаем перечисление для видов терапий
sealed class TherapyType(val name: String) {
    object Illness: TherapyType("Болезнь")
    object Vaccination: TherapyType("Вакцинация")
    object Analysis : TherapyType("Анализ")
    object VisitDoctor : TherapyType("Посещение врача")
    class User(name: String) : TherapyType(name)
}

// Создаем класс для терапий
data class Therapy(
    var type: TherapyType,
    var name: String,
    var notes: String,
    var date: Date
)
