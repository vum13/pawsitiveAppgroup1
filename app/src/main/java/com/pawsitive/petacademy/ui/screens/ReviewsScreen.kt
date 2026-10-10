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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ReviewsScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Cream),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { Heading("What our customers are saying") }

        // Alternating left / right bubbles like the wireframe.
        itemsIndexed(ContentRepository.reviews) { index, review ->
            val alignEnd = index % 2 == 1
            Box(Modifier.fillMaxWidth(), contentAlignment = if (alignEnd) Alignment.CenterEnd else Alignment.CenterStart) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.88f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(PawOrange)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("\u201C${review.text}\u201D", color = InkDark, fontSize = 14.sp)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(
                            Modifier.size(36.dp).clip(CircleShape).background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(review.name.take(1), color = PawOrange, fontWeight = FontWeight.ExtraBold)
                        }
                        Text("${review.name}, ${review.city}", color = InkDark, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
