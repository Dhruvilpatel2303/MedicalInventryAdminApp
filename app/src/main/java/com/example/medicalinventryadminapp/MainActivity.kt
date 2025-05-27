package com.example.medicalinventryadminapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicalinventryadminapp.navigation.AppNavigation
import com.example.medicalinventryadminapp.ui.screens.AddProductScreenUI
import com.example.medicalinventryadminapp.ui.screens.AllUsersScreenUI
import com.example.medicalinventryadminapp.ui.theme.MedicalInventryAdminAppTheme
import com.example.medicalinventryadminapp.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel= viewModels<MainViewModel>()
        setContent {
            MedicalInventryAdminAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(viewModel.value)
//                    AddProductScreenUI(viewModel.value)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MedicalInventryAdminAppTheme {
        Greeting("Android")
    }
}