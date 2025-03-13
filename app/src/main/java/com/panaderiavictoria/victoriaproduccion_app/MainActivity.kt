package com.panaderiavictoria.victoriaproduccion_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.VictoriaProduccion_AppTheme
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.home.ui.HomeScreen
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui.LoginScreen
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui.LoginViewModel
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.users.ui.AddUserScreen
import com.panaderiavictoriaproduccion_app.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java] // Instancia del ViewModel

        setContent {
            VictoriaProduccion_AppTheme {
                val navController = rememberNavController() // Controlador de navegación
                AppNavigation(navController, loginViewModel) // Llamar solo a la función correcta
            }
        }
    }
}