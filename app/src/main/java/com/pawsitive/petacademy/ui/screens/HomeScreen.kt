package com.pawsitive.petacademy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawsitive.petacademy.navigation.Routes
import com.pawsitive.petacademy.ui.components.PawButton
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawBlue
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.ui.theme.PawPurple

private val homeEntries = listOf(
    "About Us" to Routes.ABOUT,
    "Shop" to Routes.SHOP,
    "Courses" to Routes.WEEKS,
    "Reviews" to Routes.REVIEWS,
    "Pricing" to Routes.PRICING,
    "Cart" to Routes.CHECKOUT,
    "Blog" to Routes.BLOG,
    "Our Team" to Routes.TEAM,
    "Gallery" to Routes.GALLERY,
    "Contact Details" to Routes.CONTACT
)

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Pawsitive Pet Academy",
            color = PawOrange,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
        Text(
            "Pet care & business school",
            color = InkDark,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier.size(112.dp).clip(CircleShape).background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Pets, contentDescription = "Pawsitive logo", tint = Cream, modifier = Modifier.size(64.dp))
        }
        Spacer(Modifier.height(24.dp))

        // 2-column menu grid, alternating blue / purple rows exactly like the wireframe.
        homeEntries.chunked(2).forEachIndexed { rowIndex, pair ->
            val isBlue = rowIndex % 2 == 0
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                pair.forEach { (label, route) ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isBlue) PawBlue else PawPurple)
                            .clickable { onNavigate(route) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            label,
                            color = if (isBlue) InkDark else Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        PawButton(
            text = "Request a Quote",
            onClick = { onNavigate(Routes.enquiry()) },
            modifier = Modifier.fillMaxWidth(),
            containerColor = PawOrange
        )
    }
}
