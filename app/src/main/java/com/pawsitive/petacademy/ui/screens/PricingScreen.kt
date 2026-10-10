package com.pawsitive.petacademy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pawsitive.petacademy.data.CourseRepository
import com.pawsitive.petacademy.data.CourseType
import com.pawsitive.petacademy.ui.components.AccentHeading
import com.pawsitive.petacademy.ui.components.BodyText
import com.pawsitive.petacademy.ui.components.CourseCard
import com.pawsitive.petacademy.ui.components.PawButton
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.viewmodel.CartViewModel

@Composable
fun PricingScreen(onCourseClick: (String) -> Unit, onGetQuote: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Cream),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { AccentHeading(CourseType.SIX_WEEK.label) }
        items(CourseRepository.sixWeekCourses, key = { it.id }) { course ->
            CourseCard(course, onClick = { onCourseClick(course.id) })
        }
        item { AccentHeading(CourseType.SIX_MONTH.label) }
        items(CourseRepository.sixMonthCourses, key = { it.id }) { course ->
            CourseCard(course, onClick = { onCourseClick(course.id) })
        }
        item {
            BodyText(
                "Book 2 courses and save 5%, or book 3 or more and save 10%. " +
                    "Shop delivery is R${CartViewModel.DELIVERY_FEE.toInt()}, free on orders of " +
                    "R${CartViewModel.FREE_DELIVERY_THRESHOLD.toInt()} or more."
            )
        }
        item {
            PawButton(
                text = "Get a Quote",
                onClick = onGetQuote,
                modifier = Modifier.fillMaxWidth(),
                containerColor = PawOrange
            )
        }
    }
}
