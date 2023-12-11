package com.example.mypet.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mypet.data.Pet
import com.example.mypet.nav.BottomBarRoutes
import com.example.mypet.nav.Routes
import com.example.mypet.nav.START
import java.util.Date

@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Profile Screen",
            fontSize = 30.sp
        )
        Button(
            modifier = Modifier.padding(10.dp),
            onClick = {
                navController.navigate(Routes.UpdateProfile.route) {
                    launchSingleTop = true
                }
            }
        ) {
            Text(text = "Редактировать")
        }

        Button(
            modifier = Modifier.padding(10.dp),
            onClick = {
                navController.navigate(START) {
                    popUpTo(BottomBarRoutes.ListHygiene.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Text(text = "Выход")
        }
    }
}

@Composable
fun ProfileScreen(navController: NavHostController) {

    val pet = Pet("Мурзик", "Кот", "Британская", "мужской", Date(), "черный", "123456789012345")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Информация о питомце",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Кличка: ${pet.nickname}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Вид: ${pet.view}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Порода: ${pet.breed}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Пол: ${pet.paul}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Дата рождения: ${pet.birthday}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Окрас: ${pet.coat}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Номер микрочипа: ${pet.microchipNumber}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
    FloatingActionButton(
        onClick = {
            navController.navigate(Routes.UpdateProfile.route) {
                launchSingleTop = true
            }
        },
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .offset((-20).dp, (-20).dp)
    ) {
        Icon(Icons.Rounded.Add, "Добавить животное")
    }
}