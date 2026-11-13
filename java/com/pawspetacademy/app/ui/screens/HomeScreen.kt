package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.R
import com.pawspetacademy.app.Routes
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawColors

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    val rows = listOf(
        listOf("About Us" to Routes.ABOUT, "Shop" to Routes.SHOP),
        listOf("Courses" to Routes.COURSES, "Reviews" to Routes.REVIEWS),
        listOf("Pricing" to Routes.QUOTE, "Cart" to Routes.CART),
        listOf("Blog" to Routes.BLOG, "Our Team" to Routes.TEAM),
        listOf("Gallery" to Routes.GALLERY, "Contact Details" to Routes.CONTACT),
    )
    AppScaffold("Pawsitive Pet Academy", onNavigate) {
        ScrollableColumn(Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "Pawsitive Pet Academy logo",
                modifier = Modifier.size(220.dp)
            )
            rows.forEachIndexed { r, row ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    row.forEach { (label, route) ->
                        PawButton(
                            label,
                            if (r % 2 == 0) PawColors.LightBlue else PawColors.LightPurple,
                            Modifier.weight(1f).height(46.dp)
                        ) { onNavigate(route) }
                    }
                }
            }
            PawButton("Request a quote", PawColors.Pink, Modifier.width(200.dp).height(44.dp)) {
                onNavigate(Routes.QUOTE)
            }
        }
    }
}
