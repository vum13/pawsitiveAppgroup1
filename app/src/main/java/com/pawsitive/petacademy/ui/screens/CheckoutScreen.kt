package com.pawsitive.petacademy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawsitive.petacademy.ui.components.PawButton
import com.pawsitive.petacademy.ui.components.formatRand
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawBlue
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.ui.theme.PawPurple
import com.pawsitive.petacademy.viewmodel.CartItem
import com.pawsitive.petacademy.viewmodel.CartViewModel

private val QtyColumnWidth = 92.dp
private val TotalColumnWidth = 64.dp
private val ActionColumnWidth = 40.dp

@Composable
fun CheckoutScreen(
    cartViewModel: CartViewModel,
    onContinueShopping: () -> Unit
) {
    val state by cartViewModel.state.collectAsState()

    Column(Modifier.fillMaxSize().background(Cream)) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ---------- Top table card: Cart ----------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PawBlue)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Cart", color = InkDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))

                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        HeaderCell("Product", Modifier.weight(1f))
                        HeaderCell("Quantity", Modifier.width(QtyColumnWidth), TextAlign.Center)
                        HeaderCell("Total", Modifier.width(TotalColumnWidth), TextAlign.End)
                        HeaderCell("Action", Modifier.width(ActionColumnWidth), TextAlign.Center)
                    }
                    HorizontalDivider(Modifier.padding(vertical = 8.dp), color = InkDark.copy(alpha = 0.5f))

                    if (state.items.isEmpty()) {
                        Column(
                            Modifier.fillMaxWidth().padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text("Your cart is empty", color = InkDark, fontWeight = FontWeight.SemiBold)
                            PawButton("Continue Shopping", onClick = onContinueShopping, containerColor = PawPurple)
                        }
                    } else {
                        state.items.forEach { item ->
                            CartRow(
                                item = item,
                                enabled = !state.isProcessing,
                                onIncrease = { cartViewModel.increase(item.product.id) },
                                onDecrease = { cartViewModel.decrease(item.product.id) },
                                onRemove = { cartViewModel.remove(item.product.id) }
                            )
                        }
                    }
                }
            }

            // ---------- Middle card: Your Order ----------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PawBlue)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Your Order", color = InkDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    SummaryLine("Number of Products", "${state.itemCount}")
                    SummaryLine("Subtotal", formatRand(state.subtotal))
                    SummaryLine(
                        "Delivery",
                        if (state.items.isEmpty()) formatRand(0.0)
                        else if (state.delivery == 0.0) "Free" else formatRand(state.delivery)
                    )
                    HorizontalDivider(color = InkDark.copy(alpha = 0.5f))
                    Surface(shape = RoundedCornerShape(10.dp), color = Color.White) {
                        Row(
                            Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Total:", color = InkDark, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(
                                formatRand(state.total),
                                color = PawOrange,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                    if (state.items.isNotEmpty() && state.delivery > 0.0) {
                        val remaining = CartViewModel.FREE_DELIVERY_THRESHOLD - state.subtotal
                        Text(
                            "Add ${formatRand(remaining)} more for free delivery.",
                            color = InkDark,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // ---------- Bottom bar: purple with full-width orange Pay Now ----------
        Row(
            modifier = Modifier.fillMaxWidth().background(PawPurple).padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = cartViewModel::pay,
                enabled = state.items.isNotEmpty() && !state.isProcessing,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PawOrange,
                    contentColor = Color.White,
                    disabledContainerColor = PawOrange.copy(alpha = 0.5f),
                    disabledContentColor = Color.White.copy(alpha = 0.85f)
                )
            ) {
                if (state.isProcessing) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("Processing payment\u2026", fontWeight = FontWeight.Bold)
                } else {
                    Text("Pay Now \u2013 ${formatRand(state.total)}", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                }
            }
        }
    }

    // ---------- Simulated payment success ----------
    state.receipt?.let { receipt ->
        AlertDialog(
            onDismissRequest = {
                cartViewModel.dismissReceipt()
                onContinueShopping()
            },
            title = { Text("Payment successful!", fontWeight = FontWeight.Bold, color = InkDark) },
            text = {
                Text(
                    "Thank you for shopping with Pawsitive Pet Academy.\n\n" +
                        "Order reference: ${receipt.reference}\n" +
                        "Items: ${receipt.itemCount}\n" +
                        "Amount paid: ${formatRand(receipt.amountPaid)}",
                    color = InkDark
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    cartViewModel.dismissReceipt()
                    onContinueShopping()
                }) {
                    Text("Back to Shop", color = PawOrange, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Cream
        )
    }
}

@Composable
private fun HeaderCell(text: String, modifier: Modifier, align: TextAlign = TextAlign.Start) {
    Text(
        text,
        modifier = modifier,
        color = InkDark,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        textAlign = align
    )
}

@Composable
private fun SummaryLine(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = InkDark, fontSize = 15.sp)
        Text(value, color = InkDark, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun CartRow(
    item: CartItem,
    enabled: Boolean,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f).padding(end = 4.dp)) {
            Text(item.product.name, color = InkDark, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text(formatRand(item.product.price) + " each", color = InkDark, fontSize = 11.sp)
        }

        Row(
            Modifier.width(QtyColumnWidth),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            QtyButton(Icons.Filled.Remove, "Decrease quantity", enabled, onDecrease)
            Text("${item.quantity}", color = InkDark, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            QtyButton(Icons.Filled.Add, "Increase quantity", enabled, onIncrease)
        }

        Text(
            formatRand(item.lineTotal),
            modifier = Modifier.width(TotalColumnWidth),
            color = InkDark,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End
        )

        IconButton(onClick = onRemove, enabled = enabled, modifier = Modifier.size(ActionColumnWidth)) {
            Icon(Icons.Filled.Delete, contentDescription = "Remove ${item.product.name}", tint = InkDark)
        }
    }
}

@Composable
private fun QtyButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    description: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (enabled) PawPurple else PawPurple.copy(alpha = 0.4f))
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = description, tint = Color.White, modifier = Modifier.size(18.dp))
    }
}
