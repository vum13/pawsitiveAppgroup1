package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.data.AppData
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawColors

@Composable
fun ContactScreen(onNavigate: (String) -> Unit, onBack: () -> Unit) {
    AppScaffold("Contact Us", onNavigate, onBack) {
        ScrollableColumn {
            SectionTitle("Get in touch")
            Text("Call: ${AppData.CALL}")
            Text("Email: ${AppData.EMAIL}")
            Text("WhatsApp: ${AppData.WHATSAPP}")
            ColorTile("f", PawColors.Facebook, Modifier.size(36.dp), PawColors.White)
            SectionTitle("Location")
            AppData.address.forEach { Text(it) }
            PetTile("🐶🐱🐰🦜", Modifier.fillMaxWidth().height(170.dp), PawColors.Ivory, 34.sp)
            Text(
                "Hope to hear from you soon!", Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold, fontSize = 12.sp, textAlign = TextAlign.Center
            )
        }
    }
}
