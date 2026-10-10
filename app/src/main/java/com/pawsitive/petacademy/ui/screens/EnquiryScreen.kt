package com.pawsitive.petacademy.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pawsitive.petacademy.data.Course
import com.pawsitive.petacademy.data.CourseRepository
import com.pawsitive.petacademy.data.CourseType
import com.pawsitive.petacademy.ui.components.Heading
import com.pawsitive.petacademy.ui.components.PawButton
import com.pawsitive.petacademy.ui.components.formatRand
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawBlue
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.ui.theme.PawPurple
import com.pawsitive.petacademy.viewmodel.EnquiryViewModel

private const val ACADEMY_EMAIL = "pawsitivepetacademy@gmail.com"

@Composable
fun EnquiryScreen(
    preselectedCourseId: String,
    viewModel: EnquiryViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val quote = state.quote

    LaunchedEffect(preselectedCourseId) {
        if (preselectedCourseId.isNotBlank()) viewModel.preselect(preselectedCourseId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Heading("Your Details")

        DetailField("Full Name", state.fullName, state.nameError, viewModel::onNameChange)
        DetailField("Phone Number", state.phone, state.phoneError, viewModel::onPhoneChange, KeyboardType.Phone)
        DetailField("Email Address", state.email, state.emailError, viewModel::onEmailChange, KeyboardType.Email)

        Heading("Select Courses", Modifier.padding(top = 8.dp))
        CourseGroup(
            title = "6 weeks",
            courses = CourseRepository.coursesFor(CourseType.SIX_WEEK),
            selected = state.selectedIds,
            onToggle = viewModel::toggleCourse
        )
        CourseGroup(
            title = "6 months",
            courses = CourseRepository.coursesFor(CourseType.SIX_MONTH),
            selected = state.selectedIds,
            onToggle = viewModel::toggleCourse
        )
        state.selectionError?.let {
            Text(it, color = Color(0xFFB3261E), fontSize = 13.sp)
        }

        Heading("Your Quote", Modifier.padding(top = 8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PawBlue)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Items", color = InkDark, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Amount", color = InkDark, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                HorizontalDivider(color = InkDark.copy(alpha = 0.5f))
                if (quote.courses.isEmpty()) {
                    Text("No courses selected yet", color = InkDark, fontSize = 13.sp)
                } else {
                    quote.courses.forEach { course ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(course.title, color = InkDark, fontSize = 14.sp, modifier = Modifier.weight(1f))
                            Text(course.feeLabel, color = InkDark, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            QuoteLine("Subtotal:", formatRand(quote.subtotal.toDouble()))
            val pct = (quote.discountRate * 100).toInt()
            QuoteLine(
                if (pct > 0) "Discount ($pct%):" else "Discount:",
                if (quote.discount > 0) "-" + formatRand(quote.discount) else formatRand(0.0)
            )
            if (state.showTotal) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total:", color = InkDark, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(
                        formatRand(quote.total),
                        color = PawOrange,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                }
            }
        }

        Row(Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PawButton(
                text = "Calculate Total",
                onClick = viewModel::calculateTotal,
                modifier = Modifier.weight(1f),
                containerColor = PawPurple
            )
            PawButton(
                text = "Submit Enquiry",
                onClick = {
                    if (viewModel.validate()) {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$ACADEMY_EMAIL")).apply {
                            putExtra(Intent.EXTRA_SUBJECT, "Course enquiry from ${state.fullName.trim()}")
                            putExtra(Intent.EXTRA_TEXT, viewModel.buildEmailBody())
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(context, "No email app installed", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                containerColor = PawOrange
            )
        }
    }
}

@Composable
private fun DetailField(
    label: String,
    value: String,
    error: String?,
    onChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = PawBlue,
            unfocusedContainerColor = PawBlue,
            errorContainerColor = PawBlue,
            focusedBorderColor = PawOrange,
            unfocusedBorderColor = PawBlue,
            focusedLabelColor = InkDark,
            unfocusedLabelColor = InkDark,
            focusedTextColor = InkDark,
            unfocusedTextColor = InkDark,
            cursorColor = PawOrange
        )
    )
}

@Composable
private fun CourseGroup(
    title: String,
    courses: List<Course>,
    selected: Set<String>,
    onToggle: (String) -> Unit
) {
    Column {
        Text(title, color = InkDark, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        courses.forEach { course ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle(course.id) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = course.id in selected,
                    onCheckedChange = { onToggle(course.id) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PawOrange,
                        uncheckedColor = PawOrange,
                        checkmarkColor = Color.White
                    )
                )
                Text(course.title, color = InkDark, fontSize = 14.sp, modifier = Modifier.weight(1f))
                Text(course.feeLabel, color = InkDark, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun QuoteLine(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = InkDark, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(value, color = InkDark, fontSize = 15.sp)
    }
}
