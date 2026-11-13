package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.R
import com.pawspetacademy.app.data.AppData
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawShape

@Composable
fun BlogScreen(onNavigate: (String) -> Unit, onBack: () -> Unit) {
    AppScaffold("Blog", onNavigate, onBack) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(AppData.blogPosts) { post ->
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Image(
                        painter = painterResource(R.drawable.blog_logo),
                        contentDescription = "Pawsitive Pet Academy",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(120.dp).clip(PawShape)
                    )
                    Text(post.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text(post.byline, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                    Text(post.excerpt, fontSize = 10.sp)
                }
            }
        }
    }
}
