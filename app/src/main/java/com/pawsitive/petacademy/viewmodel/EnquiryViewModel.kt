package com.pawsitive.petacademy.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.pawsitive.petacademy.data.Course
import com.pawsitive.petacademy.data.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

data class Quote(
    val courses: List<Course>,
    val subtotal: Int,
    val discountRate: Double,
    val discount: Double,
    val total: Double
) {
    companion object {
        /** 2 courses = 5% off, 3 or more = 10% off. */
        fun from(courses: List<Course>): Quote {
            val subtotal = courses.sumOf { it.fee }
            val rate = when {
                courses.size >= 3 -> 0.10
                courses.size == 2 -> 0.05
                else -> 0.0
            }
            val discount = subtotal * rate
            return Quote(courses, subtotal, rate, discount, subtotal - discount)
        }
    }
}

data class EnquiryUiState(
    val fullName: String = "",
    val phone: String = "",
    val email: String = "",
    val selectedIds: Set<String> = emptySet(),
    val showTotal: Boolean = false,
    val nameError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val selectionError: String? = null
) {
    val quote: Quote
        get() = Quote.from(CourseRepository.allCourses.filter { it.id in selectedIds })
}

class EnquiryViewModel : ViewModel() {

    private val _state = MutableStateFlow(EnquiryUiState())
    val state: StateFlow<EnquiryUiState> = _state.asStateFlow()

    fun onNameChange(value: String) = _state.update { it.copy(fullName = value, nameError = null) }
    fun onPhoneChange(value: String) = _state.update { it.copy(phone = value, phoneError = null) }
    fun onEmailChange(value: String) = _state.update { it.copy(email = value, emailError = null) }

    fun toggleCourse(id: String) = _state.update {
        val ids = if (id in it.selectedIds) it.selectedIds - id else it.selectedIds + id
        it.copy(selectedIds = ids, selectionError = null, showTotal = false)
    }

    fun preselect(id: String) {
        if (CourseRepository.byId(id) != null) {
            _state.update { it.copy(selectedIds = it.selectedIds + id, showTotal = false) }
        }
    }

    fun calculateTotal() = _state.update {
        if (it.selectedIds.isEmpty()) it.copy(selectionError = "Select at least one course", showTotal = false)
        else it.copy(showTotal = true, selectionError = null)
    }

    fun validate(): Boolean {
        val s = _state.value
        val nameError = if (s.fullName.trim().length < 2) "Please enter your full name" else null
        val digits = s.phone.count { it.isDigit() }
        val phoneError = if (digits !in 9..13) "Enter a valid phone number" else null
        val emailError =
            if (!Patterns.EMAIL_ADDRESS.matcher(s.email.trim()).matches()) "Enter a valid email address" else null
        val selectionError = if (s.selectedIds.isEmpty()) "Select at least one course" else null
        _state.update {
            it.copy(
                nameError = nameError,
                phoneError = phoneError,
                emailError = emailError,
                selectionError = selectionError,
                showTotal = selectionError == null
            )
        }
        return listOf(nameError, phoneError, emailError, selectionError).all { it == null }
    }

    fun buildEmailBody(): String {
        val s = _state.value
        val q = s.quote
        fun money(v: Double) = String.format(Locale.US, "R%.2f", v)
        return buildString {
            appendLine("Hello Pawsitive Pet Academy,")
            appendLine()
            appendLine("I would like to enquire about the following course(s):")
            q.courses.forEach { appendLine("- ${it.title} (${it.type.label.removeSuffix(" Courses")}) - ${it.feeLabel}") }
            appendLine()
            appendLine("Subtotal: ${money(q.subtotal.toDouble())}")
            appendLine("Discount: ${money(q.discount)}")
            appendLine("Total: ${money(q.total)}")
            appendLine()
            appendLine("Name: ${s.fullName.trim()}")
            appendLine("Phone: ${s.phone.trim()}")
            appendLine("Email: ${s.email.trim()}")
        }
    }
}
