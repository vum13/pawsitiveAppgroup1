package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.Routes
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawColors
import com.pawspetacademy.app.viewmodel.CartViewModel

@Composable
fun CartScreen(cart: CartViewModel, onNavigate: (String) -> Unit, onBack: () -> Unit) {
    val items by cart.items.collectAsState()
    val placed by cart.orderPlaced.collectAsState()
    val total = items.sumOf { it.lineTotal }
    AppScaffold("Checkout", onNavigate, onBack) {
        ScrollableColumn {
            SectionTitle("Cart")
            Panel(Modifier.fillMaxWidth().heightIn(min = 200.dp), PawColors.LightBlue) {
                Row(Modifier.fillMaxWidth()) {
                    Text("Product", Modifier.weight(2f), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("Quantity", Modifier.weight(1.5f), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("Total", Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("Action", Modifier.weight(1.2f), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                HorizontalDivider(color = PawColors.Dark)
                items.forEach { item ->
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(item.name, Modifier.weight(2f), fontSize = 12.sp)
                        Row(Modifier.weight(1.5f), verticalAlignment = Alignment.CenterVertically) {
                            TextButton(onClick = { cart.changeQty(item.id, -1) }) { Text("-") }
                            Text("${item.qty}")
                            TextButton(onClick = { cart.changeQty(item.id, 1) }) { Text("+") }
                        }
                        Text("R${"%.2f".format(item.lineTotal)}", Modifier.weight(1f), fontSize = 12.sp)
                        TextButton(onClick = { cart.removeItem(item.id) }, modifier = Modifier.weight(1.2f)) {
                            Text("Remove", fontSize = 12.sp)
                        }
                    }
                }
                if (items.isEmpty()) Text("Your cart is empty.", fontSize = 12.sp)
            }
            Row(Modifier.fillMaxWidth()) {
                Panel(Modifier.weight(1f), PawColors.LightBlue) {
                    SectionTitle("Your Order")
                    Text("Number of Product: ${items.sumOf { it.qty }}")
                    Text("Order Total: R${"%.2f".format(total)}")
                    Spacer(Modifier.height(12.dp))
                    Text("Total: R${"%.2f".format(total)}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    PawButton("Proceed to checkout", PawColors.Pink, Modifier.fillMaxWidth()) { cart.checkout() }
                }
                Spacer(Modifier.weight(1f))
            }
        }
    }
    if (placed) {
        AlertDialog(
            onDismissRequest = { cart.dismissOrderConfirmation() },
            title = { Text("Thank you!") },
            text = { Text("Your order has been placed. Your support helps our animals.") },
            confirmButton = {
                TextButton(onClick = {
                    cart.dismissOrderConfirmation()
                    onNavigate(Routes.HOME)
                }) { Text("Back to Home") }
            }
        )
    }
}
