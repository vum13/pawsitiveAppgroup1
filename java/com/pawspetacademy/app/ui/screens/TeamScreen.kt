package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.R
import com.pawspetacademy.app.data.AppData
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawColors

// Same order as AppData.team: Sarah Smith, Jake Smith, Aisha Patel, Thabo Dlamini
private val teamPhotos = listOf(
    R.drawable.team_sarah, R.drawable.team_jake, R.drawable.team_aisha, R.drawable.team_thabo
)

@Composable
fun TeamScreen(onNavigate: (String) -> Unit, onBack: () -> Unit) {
    AppScaffold("Our Team", onNavigate, onBack) {
        ScrollableColumn {
            AppData.team.forEachIndexed { i, m ->
                Panel(Modifier.fillMaxWidth(), PawColors.LightPurple) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(Modifier.size(64.dp)) {
                            Image(
                                painter = painterResource(teamPhotos[i]),
                                contentDescription = m.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(56.dp).align(Alignment.BottomEnd).clip(CircleShape)
                            )
                            PawIcon(Modifier.size(30.dp).align(Alignment.TopStart), PawColors.Dark)
                        }
                        Column(Modifier.weight(1f)) {
                            Text(m.name, fontWeight = FontWeight.Bold)
                            Text(m.role, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            Text(m.bio, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
