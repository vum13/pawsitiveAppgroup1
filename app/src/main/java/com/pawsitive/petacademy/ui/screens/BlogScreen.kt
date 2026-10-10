package com.pawsitive.petacademy.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawsitive.petacademy.data.BlogPost
import com.pawsitive.petacademy.data.ContentRepository
import com.pawsitive.petacademy.ui.components.Heading
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.ui.theme.PawPurple

@Composable
fun BlogScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Cream),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Heading("Blog") }
        items(ContentRepository.blogPosts, key = { it.title }) { post -> BlogCard(post) }
    }
}

@Composable
private fun BlogCard(post: BlogPost) {
    var expanded by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(16.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            Modifier.fillMaxWidth().height(80.dp).background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("Pawsitive Pet Academy", color = PawOrange, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
        }
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(post.title, color = InkDark, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Text(
                "by ${post.author} | ${post.date} | ${post.tag}",
                color = InkDark.copy(alpha = 0.7f),
                fontSize = 11.sp
            )
            Text(
                if (expanded) post.body else post.summary,
                color = InkDark,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
            Text(
                if (expanded) "Show less" else "Read more",
                color = PawPurple,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}
