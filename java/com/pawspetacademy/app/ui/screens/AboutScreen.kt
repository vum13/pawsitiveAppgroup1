package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawColors

@Composable
fun AboutScreen(onNavigate: (String) -> Unit, onBack: () -> Unit) {
    AppScaffold("About Us", onNavigate, onBack) {
        ScrollableColumn {
            PetTile("🐕🧑", Modifier.fillMaxWidth(0.7f).height(170.dp), PawColors.Ivory, 44.sp)
            Section("Our History", "Pawsitive Pet Academy was established in 2023 and offers training courses in Durban. Hundreds of pet owners and aspiring pet care professionals have completed both the six-month Professional Development Programmes and the six-week Practical Skills Courses.")
            Section("Vision", "To be South Africa's leading academy for pet care and training, building a compassionate nation where every pet is understood, well-cared for and cherished.")
            Section("Our Mission", "Pawsitive Pet Academy exists to empower pet owners, families, aspiring professionals and future entrepreneurs through expert-led, accessible education in pet care, training and business skills.")
            Section("Our Goals", "To educate and equip pet owners and aspiring professionals with proper animal care and behavioural training techniques. To create meaningful employment and business opportunities for people who love working with animals.")
        }
    }
}

@Composable
private fun Section(title: String, body: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(body, fontSize = 13.sp)
    }
}
