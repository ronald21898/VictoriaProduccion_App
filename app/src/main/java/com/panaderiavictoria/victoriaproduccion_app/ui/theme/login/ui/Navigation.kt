package com.panaderiavictoriaproduccion_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.home.ui.HomeScreen
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui.LoginScreen
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui.ViewModel.LoginViewModel
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui.TomarOrdenScreen
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.users.ui.AddUserScreen


@Composable
fun AppNavigation(navController: NavHostController, loginViewModel: LoginViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, loginViewModel) }
        composable("addUser") { AddUserScreen(navController, loginViewModel) }

        composable("home/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            HomeScreen(navController, username)
        }
        composable("tomarOrden") { TomarOrdenScreen(navController) }
    }
}
