package com.example.mypet.data

import java.util.Date

data class Pet(
    val nickname: String,
    val view: String,
    val breed: String,
    val paul: String, // "мужской" | "женский"
    val birthday: Date,
    val coat: String,
    val microchipNumber: String, // 15 цифр
)