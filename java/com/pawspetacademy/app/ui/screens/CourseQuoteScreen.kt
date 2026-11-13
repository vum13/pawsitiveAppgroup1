package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.Routes
import com.pawspetacademy.app.data.AppData
import com.pawspetacademy.app.data.Course
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawColors
import com.pawspetacademy.app.viewmodel.CartViewModel

// Discount tiers: 1 course 0%, 2 courses 5%, 3 courses 10%, 4+ courses 15%
private fun discountRate(count: Int): Double = when {
    count >= 4 -> 0.15
    count == 3 -> 0.10
    count == 2 -> 0.05
    else -> 0.0
}

private fun money(value: Double): String = "R%.2f".format(value)

@Composable
fun CourseQuoteScreen(cart: CartViewModel, onNavigate: (String) -> Unit, onBack: () -> Unit) {
    val selected = remember { mutableStateListOf<Course>() }
    var calculated by remember { mutableStateOf(false) }
    var notice by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val subtotal = selected.sumOf { it.price }
    val rate = discountRate(selected.size)
    val discount = subtotal * rate
    val total = subtotal - discount

    AppScaffold("Course Details", onNavigate, onBack) {
        ScrollableColumn {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionTitle("Your Details")
                    LabeledField("Full Name", name) { name = it }
                    LabeledField("Phone Number", phone) { phone = it }
                    LabeledField("Email Address", email) { email = it }
                    Text("Your Quote", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Panel(Modifier.fillMaxWidth().height(160.dp), PawColors.LightBlue) {
                        Row(Modifier.fillMaxWidth()) {
                            Text("Items", Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("Amount", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        if (calculated) {
                            selected.forEach { c ->
                                QuoteLine(c.title, money(c.price))
                            }
                            QuoteLine("Subtotal", money(subtotal))
                            QuoteLine("Discount (${(rate * 100).toInt()}%)", "-" + money(discount))
                            QuoteLine("Total", money(total), bold = true)
                        }
                    }
                    PawButton("Calculate Total", PawColors.LightPurple, Modifier.width(150.dp).height(36.dp)) {
                        calculated = true
                        notice = ""
                    }
                    PawButton("Submit", PawColors.LightPurple, Modifier.width(150.dp).height(36.dp)) {
                        if (selected.isEmpty()) {
                            notice = "Please select at least one course."
                        } else {
                            // Replace any earlier entry for the same course so the discount is never counted twice
                            selected.forEach { c ->
                                cart.removeItem(c.id)
                                cart.addItem(c.id, c.title, c.price * (1 - rate))
                            }
                            onNavigate(Routes.CART)
                        }
                    }
                    if (notice.isNotEmpty()) Text(notice, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    SectionTitle("Select courses")
                    Text("6 weeks", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    AppData.sixWeeks.forEach { CourseCheck(it, selected) }
                    Text("6 months", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    AppData.sixMonths.forEach { CourseCheck(it, selected) }
                    Spacer(Modifier.height(8.dp))
                    Text("Discounts", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("2 courses: 5%", fontSize = 12.sp)
                    Text("3 courses: 10%", fontSize = 12.sp)
                    Text("4+ courses: 15%", fontSize = 12.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Subtotal: ${money(subtotal)}", fontWeight = FontWeight.Bold)
                    Text("Discount: ${(rate * 100).toInt()}%", fontWeight = FontWeight.Bold)
                    Text("Total: ${money(total)}", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun QuoteLine(label: String, value: String, bold: Boolean = false) {
    val weight = if (bold) FontWeight.Bold else FontWeight.Normal
    Row(Modifier.fillMaxWidth()) {
        Text(label, Modifier.weight(1f), fontWeight = weight, fontSize = 12.sp)
        Text(value, fontWeight = weight, fontSize = 12.sp)
    }
}

@Composable
private fun LabeledField(label: String, value: String, onChange: (String) -> Unit) {
    Column {
        Text(label, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        TextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = pawFieldColors()
        )
    }
}

@Composable
private fun CourseCheck(course: Course, selected: MutableList<Course>) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        Checkbox(
            checked = course in selected,
            onCheckedChange = { on -> if (on) selected.add(course) else selected.remove(course) },
            colors = CheckboxDefaults.colors(
                checkedColor = PawColors.Pink,
                uncheckedColor = PawColors.Pink,
                checkmarkColor = PawColors.Dark
            )
        )
        Text(course.title, fontSize = 12.sp)
    }
}
