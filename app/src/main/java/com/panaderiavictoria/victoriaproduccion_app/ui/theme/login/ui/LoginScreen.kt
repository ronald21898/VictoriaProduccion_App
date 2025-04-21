package com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui.ViewModel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(Modifier.align(Alignment.Center), viewModel, navController)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navController: NavHostController) {
    val users = viewModel.users.observeAsState(initial = emptyList()).value
    val selectedUser = viewModel.selectedUser.observeAsState(initial = "").value
    val loginEnable: Boolean = viewModel.loginEnable.observeAsState(initial = false).value
    val isLoading: Boolean = viewModel.isLoading.observeAsState(initial = false).value

    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown para seleccionar usuario
            UserDropdown(users, selectedUser) { viewModel.onUserSelected(it) }
            Spacer(modifier = Modifier.height(8.dp))

            // Botón para eliminar usuario
            if (selectedUser.isNotEmpty()) {
                Text(
                    text = "Eliminar usuario",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { viewModel.deleteUser(selectedUser) }
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de inicio de sesión
            LoginButton(loginEnable) {
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                    navController.navigate("home/$selectedUser")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para añadir usuario
            Text(
                text = "Añadir un nuevo usuario",
                color = Color.Blue,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable { navController.navigate("addUser") }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun UserDropdown(users: List<String>, selectedUser: String, onUserSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Selecciona un usuario",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF636262)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDEDDDD))
            ) {
                Text(
                    text = if (selectedUser.isEmpty()) "Seleccionar usuario" else selectedUser,
                    color = Color.Black
                )
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                users.forEach { user ->
                    DropdownMenuItem(
                        text = { Text(user) },
                        onClick = {
                            onUserSelected(user)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF4303),
            disabledContainerColor = Color(0xFFF78058),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ), enabled = loginEnable
    ) {
        Text(text = "Iniciar sesión")
    }
}
