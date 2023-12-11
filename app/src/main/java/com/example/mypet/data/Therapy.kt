package com.example.mypet.data

import java.util.Date

// Создаем перечисление для видов терапий
sealed class TherapyType(val name: String) {
    object Illness: TherapyType("Болезнь")
    object Vaccination: TherapyType("Вакцинация")
    object Analysis : TherapyType("Анализ")
    object VisitDoctor : TherapyType("Посещегние врача")
}

// Создаем класс для терапий
data class Therapy(
    val type: TherapyType,
    val name: String,
    val info: String,
    val date: Date
)
