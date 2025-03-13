package com.panaderiavictoria.victoriaproduccion_app.ui.theme.login.ui
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TomarOrdenScreen(navController: NavHostController) {
    val categories = listOf("PC", "PE", "PAS", "BOC")
    val products = mapOf(
        "PC" to listOf("Pan francés", "Pan integral", "Pan ciabatta", "Pan fras", "Pantegral", "Panta", "Pan fraés", "Pan ingral", "Panbatta"),
        "PE" to listOf("Baguette", "Focaccia", "Pan de centeno"),
        "PAS" to listOf("Torta de chocolate", "Torta de manzana", "Milhojas", "Chocotejas", "Alfajores"),
        "BOC" to listOf("Empanaditas", "Volovanes", "Petit fours")
    )

    val units = listOf("Sin unidad", "Unidad", "Docena", "Coche", "Ciento")

    var selectedCategory by remember { mutableStateOf("PC") }
    val orders = remember { mutableStateListOf<OrderItem>() }

    LaunchedEffect(selectedCategory) {
        orders.clear()
        products[selectedCategory]?.forEach { name ->
            orders.add(OrderItem(selectedCategory, name, "", "Sin unidad", false))
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            DropdownMenuBox(
                options = categories,
                selectedOption = selectedCategory,
                onOptionSelected = { selectedCategory = it },
                width = 80
            )

            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Producto", fontSize = 16.sp, modifier = Modifier.weight(1f))
            Text(text = "Cantidad", fontSize = 16.sp, modifier = Modifier.weight(1f))
            Text(text = "Unidad", fontSize = 16.sp, modifier = Modifier.weight(1f))
        }

        // Espaciado para alinear con el checkbox
        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn {
            items(orders) { order ->
                OrderRow(order) { updatedOrder ->
                    val index = orders.indexOfFirst { it.product == order.product }
                    if (index != -1) {
                        orders[index] = updatedOrder
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Volver")
        }
    }
}

@Composable
fun DropdownMenuBox(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    width: Int
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(
            onClick = { expanded = true },
            modifier = Modifier
                .height(36.dp)
                .width(width.dp)
        ) {
            Text(text = selectedOption)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun OrderRow(order: OrderItem, onUpdate: (OrderItem) -> Unit) {
    val units = listOf("Sin unidad", "Unidad", "Docena", "Coche", "Ciento")

    var quantity by rememberSaveable { mutableStateOf(order.quantity) }

    LaunchedEffect(order.quantity) {
        quantity = order.quantity
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = order.type, modifier = Modifier.weight(1f))
        Text(text = order.product, modifier = Modifier.weight(2f))

        if (order.unit == "Sin unidad") {
            Checkbox(
                checked = order.checked,
                onCheckedChange = { isChecked ->
                    onUpdate(order.copy(checked = isChecked))
                },
                modifier = Modifier.weight(0.5f)
            )
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(58.dp)
                    .padding(2.dp)
            ) {
                TextField(
                    value = quantity,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                            quantity = newValue
                            onUpdate(order.copy(quantity = newValue))
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp, lineHeight = 12.sp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }

        DropdownMenuBox(
            options = units,
            selectedOption = order.unit,
            onOptionSelected = { newUnit ->
                onUpdate(order.copy(unit = newUnit, checked = false, quantity = ""))
            },
            width = 120
        )
    }
}

data class OrderItem(val type: String, val product: String, val quantity: String, val unit: String, val checked: Boolean)