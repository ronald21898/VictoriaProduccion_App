package com.panaderiavictoria.victoriaproduccion_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.VictoriaProduccion_AppTheme
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui.ViewModel.LoginViewModel
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