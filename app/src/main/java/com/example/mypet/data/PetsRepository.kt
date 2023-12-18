//package com.example.mypet.data
//
//import android.content.Context
//import androidx.core.content.edit
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import kotlinx.coroutines.CoroutineExceptionHandler
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//class PetsRepository(context: Context) {
//
//    var pets = emptyList<Pet>()
//
//    private val sharedPreferences = context.getSharedPreferences("my_shared_preferences", Context.MODE_PRIVATE)
//
//    fun saveData() {
//        // Конвертируем имеющийся массив pets в JSON
//        val petsJson = Gson().toJson(pets)
//
//        // Сохраняем JSON в SharedPreferences
//        sharedPreferences.edit {
//            putString("pets", petsJson)
//        }
//    }
//
//    suspend fun getData():List<Pet> {
//        withContext(
//            Dispatchers.IO + CoroutineExceptionHandler { _, throwable -> println(throwable) }
//        ) {
//            // Получение JSON из SharedPreferences
//            val petsJson = sharedPreferences.getString("pets", null)
//
//            // Преобразование JSON обратно в массив объектов Pet
//            val petsType = object : TypeToken<List<Pet>>() {}.type
//            val pets = Gson().fromJson<List<Pet>>(petsJson, petsType)
//        }
//        return pets
//    }
//
//}
//
//
//
//
