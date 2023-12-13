package com.example.mypet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mypet.data.PetsRepository
import com.example.mypet.ui.theme.MyPetTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    var petsRepository = PetsRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch  {
            petsRepository.getData()
        }

        setContent {
            MyPetTheme {
                MyPetApp(this, petsRepository.pets)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        petsRepository.saveData()
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyPetTheme {

    }
}