package com.example.mypet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mypet.ui.theme.MyPetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPetTheme {
                MyPetApp(this)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyPetTheme {

    }
}