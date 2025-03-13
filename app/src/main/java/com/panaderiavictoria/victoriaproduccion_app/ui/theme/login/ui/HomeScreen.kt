package com.panaderiavictoria.victoriaproduccion_app.ui.theme.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController, username: String) {

    val categories = listOf("PC", "PE", "PAS", "BOC")
    val products = mapOf(
        "PC" to listOf("Pan francés", "Pan integral", "Pan ciabatta"),
        "PE" to listOf("Baguette", "Focaccia", "Pan de centeno"),
        "PAS" to listOf("Torta de chocolate", "Torta de manzana"),
        "BOC" to listOf("Empanaditas", "Volovanes", "Petit fours")
    )
    val units = listOf("Sin unidad", "Unidad", "Docena", "Coche", "Ciento")

    var selectedCategory by remember { mutableStateOf("PC") }
    val orders = remember { mutableStateListOf<OrderItem>() }

    var showDialog by remember { mutableStateOf(false) } // Estado para mostrar la alerta

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido, $username",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón 1: Tomar Orden de Producción
            MenuButton(text = "Tomar Orden de Producción") {
                navController.navigate("tomarOrden?categories=$categories&products=$products&units=$units&selectedCategory=$selectedCategory") {
                    launchSingleTop = true
                    restoreState = true
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón 2: Ver Lista de Producción
            MenuButton(text = "Ver Lista de Producción") {
                navController.navigate("verListaProduccion")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón 3: Ver Productos
            MenuButton(text = "Ver Productos") {
                navController.navigate("verProductos")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Cerrar Sesión con alerta
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cerrar Sesión")
            }

            // Alerta de Confirmación para Cerrar Sesión
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "¿ESTÁS SEGURO DE QUE QUIERES CERRAR SESIÓN?") },
                    text = { Text(text = "Se perderán los datos no guardados y no se enviará la lista") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog = false
                                navController.popBackStack("login", inclusive = false)
                            }
                        ) {
                            Text("Sí, cerrar sesión")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}

data class OrderItem(val type: String, val product: String, val quantity: String, val unit: String, val checked: Boolean)