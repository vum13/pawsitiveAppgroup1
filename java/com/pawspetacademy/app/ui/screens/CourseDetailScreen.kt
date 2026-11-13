package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.pawspetacademy.app.ui.theme.PawShape

// Course id -> photo. Every course now has a photo.
private val coursePhotos = mapOf(
    "w1" to R.drawable.course_dog_walk,
    "w2" to R.drawable.course_puppy_care,
    "w3" to R.drawable.course_pet_first_aid,
    "m1" to R.drawable.course_pet_grooming,
    "m2" to R.drawable.course_animal_behaviour,
    "m3" to R.drawable.course_canine_obedience,
    "m4" to R.drawable.course_pet_business,
)

@Composable
fun CourseDetailScreen(courseId: String, onNavigate: (String) -> Unit, onBack: () -> Unit) {
    val course = AppData.course(courseId)
    AppScaffold("Courses", onNavigate, onBack) {
        if (course == null) {
            Text("This course is not available.", Modifier.padding(16.dp))
        } else {
            Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    val photo = coursePhotos[course.id]
                    if (photo != null) {
                        Image(
                            painter = painterResource(photo),
                            contentDescription = course.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.weight(1f).height(190.dp).clip(PawShape)
                        )
                    } else {
                        PetTile("🦮", Modifier.weight(1f).height(190.dp), PawColors.LightBlue, 56.sp)
                    }
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        SectionTitle(course.title)
                        course.bullets.forEach { Text("• $it", fontSize = 13.sp) }
                    }
                }
                Text("Duration: ${course.duration}", Modifier.align(Alignment.End), fontWeight = FontWeight.Bold)
                Text("Fee: R ${course.price.toInt()}", Modifier.align(Alignment.End), fontWeight = FontWeight.Bold)
                SectionTitle("What will be learned:")
                Text(course.learn)
            }
        }
    }
}
