package com.pawsitive.petacademy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawsitive.petacademy.data.ContentRepository
import com.pawsitive.petacademy.ui.components.Heading
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawOrange

@Composable
fun TeamScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Cream),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { Heading("Meet the people behind the paws") }
        items(ContentRepository.team, key = { it.name }) { member ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(PawOrange)
                    .padding(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(72.dp).clip(CircleShape).background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Pets, contentDescription = null, tint = Color.White, modifier = Modifier.size(34.dp))
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(member.name, color = InkDark, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp)
                    Text(member.role, color = InkDark, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text(member.bio, color = InkDark, fontSize = 12.sp, lineHeight = 17.sp)
                }
            }
        }
    }
}
