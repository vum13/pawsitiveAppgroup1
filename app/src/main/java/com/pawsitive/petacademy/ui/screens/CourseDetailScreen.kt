package com.pawsitive.petacademy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawsitive.petacademy.data.CourseRepository
import com.pawsitive.petacademy.ui.components.AccentHeading
import com.pawsitive.petacademy.ui.components.BodyText
import com.pawsitive.petacademy.ui.components.PawButton
import com.pawsitive.petacademy.ui.components.PawImage
import com.pawsitive.petacademy.ui.components.PricePill
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.ui.theme.PawPurple

@Composable
fun CourseDetailScreen(
    courseId: String?,
    onEnquire: (String) -> Unit,
    onBack: () -> Unit
) {
    val course = courseId?.let { CourseRepository.byId(it) }
    if (course == null) {
        Box(Modifier.fillMaxSize().background(Cream), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Course not found", color = InkDark, fontWeight = FontWeight.Bold)
                PawButton("Back", onClick = onBack)
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        PawImage(
            model = course.imageUrl,
            contentDescription = course.title,
            modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(16.dp))
        )

        Text(course.title, color = InkDark, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        Text(course.type.label, color = PawPurple, fontWeight = FontWeight.SemiBold)

        if (course.content.isNotEmpty()) {
            AccentHeading("What's covered")
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                course.content.forEach { item ->
                    Row {
                        Text("\u2022  ", color = PawOrange, fontWeight = FontWeight.Bold)
                        BodyText(item, Modifier.weight(1f))
                    }
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Duration: ${course.duration}", color = InkDark, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Fee:", color = InkDark, fontWeight = FontWeight.Bold)
                PricePill(course.feeLabel, fontSize = 18.sp)
            }
        }

        AccentHeading("What will be learned:")
        BodyText(course.learn)

        Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PawButton("Back", onClick = onBack, modifier = Modifier.weight(1f), containerColor = PawPurple)
            PawButton("Enquire Now", onClick = { onEnquire(course.id) }, modifier = Modifier.weight(1f), containerColor = PawOrange)
        }
    }
}
