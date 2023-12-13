package com.example.mypet

import com.example.mypet.data.Pet
import com.example.mypet.data.Procedure
import java.util.Date

fun validateDate(dateString: String) {
    val regex = "^\\d{2}\\.\\d{2}\\.\\d{4}$".toRegex()
    if ( !(dateString.matches(regex)) ) {
        throw IllegalArgumentException("Неверный формат даты")
    }
}
fun validateProfileDate(dateString: String) {
    if (dateFormat.parse(dateString) > Date()) {
        throw IllegalArgumentException("Дата больше сегодняшней")
    }
}

fun validateTime(timeString: String) {
    val regex = "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$".toRegex()
    if ( !(timeString.matches(regex)) ) {
        throw IllegalArgumentException("Неверный формат даты")
    }

}

fun validatePet(pet: Pet) {
    // буквы, тире и пробелы
    val regex = "^[а-яА-Я-\\s-]+$".toRegex()
    // пустой или 15 цифр
    val chipNumberRegex = "^$|\\d{15}$".toRegex()

    if ( !(pet.nickname.matches(regex)) ) {
        throw IllegalArgumentException("Некорректная кличка")
    }
    if ( !(pet.breed.matches(regex)) ) {
        throw IllegalArgumentException("Некорректная порода")
    }
    if ( !(pet.view.matches(regex)) ) {
        throw IllegalArgumentException("Некорректный вид")
    }
    if ( !(pet.coat.matches(regex)) ) {
        throw IllegalArgumentException("Некорректный окрас")
    }
    if ( !(pet.microchipNumber.matches(chipNumberRegex)) ) {
        throw IllegalArgumentException("Некорректный номер чипа")
    }
}


fun validateProcedureSettings(procedure: Procedure) {

}