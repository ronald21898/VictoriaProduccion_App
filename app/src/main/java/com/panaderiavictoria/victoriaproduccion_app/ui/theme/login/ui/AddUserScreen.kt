package com.panaderiavictoria.victoriaproduccion_app.ui.theme.users.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui.LoginViewModel

@Composable
fun AddUserScreen(navController: NavHostController, viewModel: LoginViewModel) {
    var newUser by remember { mutableStateOf("") }
    val users = viewModel.users.observeAsState(initial = emptyList()).value
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Centra todo el contenido
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Agregar Nuevo Usuario", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newUser,
                onValueChange = {
                    newUser = it.trim()
                    errorMessage = if (newUser in users) "Este usuario ya existe" else ""
                },
                label = { Text("Nombre de Usuario") },
                isError = errorMessage.isNotEmpty()
            )

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (newUser.isNotEmpty() && newUser !in users) {
                        viewModel.addUser(newUser)
                        navController.popBackStack()
                    }
                },
                enabled = newUser.isNotEmpty() && errorMessage.isEmpty()
            ) {
                Text(text = "Guardar Usuario")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Volver")
            }
        }
    }
}
