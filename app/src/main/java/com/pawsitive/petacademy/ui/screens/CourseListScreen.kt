package com.pawsitive.petacademy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pawsitive.petacademy.data.CourseRepository
import com.pawsitive.petacademy.data.CourseType
import com.pawsitive.petacademy.ui.components.BodyText
import com.pawsitive.petacademy.ui.components.CourseCard
import com.pawsitive.petacademy.ui.components.PawButton
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.PawBlue
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.ui.theme.PawPurple

@Composable
fun CourseListScreen(
    type: CourseType,
    onCourseClick: (String) -> Unit,
    onSwitchType: (CourseType) -> Unit
) {
    val courses = CourseRepository.coursesFor(type)
    val other = if (type == CourseType.SIX_WEEK) CourseType.SIX_MONTH else CourseType.SIX_WEEK

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Cream),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CourseType.values().forEach { t ->
                    val selected = t == type
                    PawButton(
                        text = t.label,
                        onClick = { if (!selected) onSwitchType(t) },
                        modifier = Modifier.weight(1f),
                        containerColor = if (selected) PawOrange else PawBlue,
                        contentColor = if (selected) Color.White else com.pawsitive.petacademy.ui.theme.InkDark
                    )
                }
            }
        }
        item { BodyText("Tap a course to see what's covered, the duration and the fee.") }
        items(courses, key = { it.id }) { course ->
            CourseCard(course = course, onClick = { onCourseClick(course.id) })
        }
        item {
            Box(Modifier.fillMaxWidth().padding(top = 8.dp)) {
                PawButton(
                    text = "See ${other.label}",
                    onClick = { onSwitchType(other) },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = PawPurple
                )
            }
        }
    }
}
