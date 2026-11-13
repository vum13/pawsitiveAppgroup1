package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.Routes
import com.pawspetacademy.app.data.AppData
import com.pawspetacademy.app.data.Course
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawColors

@Composable
fun CoursesScreen(onNavigate: (String) -> Unit, onBack: () -> Unit) {
    AppScaffold("Courses", onNavigate, onBack) {
        ScrollableColumn {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { SectionTitle("6-Weeks Courses") }
            AppData.sixWeeks.forEach { CourseRow(it, onNavigate) }
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { SectionTitle("6-Months Courses") }
            AppData.sixMonths.forEach { CourseRow(it, onNavigate) }
        }
    }
}

@Composable
private fun CourseRow(course: Course, onNavigate: (String) -> Unit) {
    Panel(Modifier.fillMaxWidth(), PawColors.LightPurple, { onNavigate(Routes.courseDetail(course.id)) }) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(course.title, Modifier.weight(2f), fontSize = 14.sp)
            Text("R${course.price.toInt()}", Modifier.weight(1f), fontSize = 13.sp, textAlign = TextAlign.Center)
            Text("→", fontSize = 18.sp)
        }
    }
}
