package com.pawsitive.petacademy.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawsitive.petacademy.ui.components.AccentHeading
import com.pawsitive.petacademy.ui.components.PawButton
import com.pawsitive.petacademy.ui.components.PawImage
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawBlue
import com.pawsitive.petacademy.ui.theme.PawOrange

private const val PHONE_DISPLAY = "078 643 7864"
private const val EMAIL = "pawsitivepetacademy@gmail.com"
private const val WHATSAPP_DISPLAY = "087 535 7543"
private const val ADDRESS = "45 Pet Lane, Durban, 4001, South Africa"

private fun Context.open(intent: Intent) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "No app found to handle this action", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ContactScreen(onEnquiry: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AccentHeading("Get in touch")

        ContactRow(Icons.Filled.Phone, "Call", PHONE_DISPLAY) {
            context.open(Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + PHONE_DISPLAY.filter { it.isDigit() })))
        }
        ContactRow(Icons.Filled.Email, "Email", EMAIL) {
            context.open(
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$EMAIL")).apply {
                    putExtra(Intent.EXTRA_SUBJECT, "Pawsitive Pet Academy enquiry")
                }
            )
        }
        ContactRow(Icons.Filled.Chat, "WhatsApp", WHATSAPP_DISPLAY) {
            // South African international format: drop the leading 0 and prefix 27
            val digits = WHATSAPP_DISPLAY.filter { it.isDigit() }.removePrefix("0")
            context.open(Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/27$digits")))
        }
        ContactRow(Icons.Filled.Public, "Facebook", "Pawsitive Pet Academy") {
            context.open(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pawsitivepetacademy")))
        }
        ContactRow(Icons.Filled.LocationOn, "Location", ADDRESS) {
            context.open(Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Uri.encode(ADDRESS))))
        }

        PawImage(
            model = "https://loremflickr.com/800/450/pets,dog,cat?lock=61",
            contentDescription = "Pets",
            modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(16.dp))
        )

        Text(
            "Hope to hear from you soon!",
            modifier = Modifier.fillMaxWidth(),
            color = InkDark,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        PawButton(
            text = "Send an Enquiry",
            onClick = onEnquiry,
            modifier = Modifier.fillMaxWidth(),
            containerColor = PawOrange
        )
    }
}

@Composable
private fun ContactRow(icon: ImageVector, label: String, value: String, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Card(
        modifier = Modifier.fillMaxWidth().clip(shape).clickable(onClick = onClick),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = PawBlue)
    ) {
        Row(
            Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(icon, contentDescription = label, tint = InkDark)
            Column {
                Text(label, color = InkDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(value, color = InkDark, fontSize = 15.sp)
            }
        }
    }
}
