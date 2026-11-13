package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.data.AppData
import com.pawspetacademy.app.data.Post
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawColors

@Composable
fun ReviewsScreen(onNavigate: (String) -> Unit, onBack: () -> Unit) {
    val messages = remember { mutableStateListOf<Post>().apply { addAll(AppData.reviews) } }
    var draft by remember { mutableStateOf("") }
    AppScaffold("Reviews", onNavigate, onBack) {
        ScrollableColumn {
            messages.forEachIndexed { i, post ->
                val left = i % 2 == 0
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = if (left) Arrangement.Start else Arrangement.End
                ) {
                    Panel(Modifier.widthIn(max = 280.dp), if (left) PawColors.LightPurple else PawColors.LightBlue) {
                        Text(post.author, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text(post.message)
                    }
                }
            }
            TextField(
                value = draft,
                onValueChange = { draft = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Share your experience") },
                colors = pawFieldColors()
            )
            PawButton("Send", PawColors.LightPurple, Modifier.fillMaxWidth()) {
                if (draft.isNotBlank()) {
                    messages.add(Post("You", draft.trim()))
                    draft = ""
                }
            }
        }
    }
}
