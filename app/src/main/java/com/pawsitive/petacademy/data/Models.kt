package com.pawsitive.petacademy.data

enum class CourseType(val label: String) {
    SIX_WEEK("6-Week Courses"),
    SIX_MONTH("6-Month Courses")
}

data class Course(
    val id: String,
    val title: String,
    val type: CourseType,
    val duration: String,
    val fee: Int,
    val content: List<String>,
    val learn: String,
    val imageUrl: String
) {
    val feeLabel: String get() = "R$fee"
}

enum class ProductCategory(val label: String) {
    FOOD("Food"),
    TOYS("Toys"),
    LEASHES("Leashes")
}

data class Product(
    val id: String,
    val name: String,
    val category: ProductCategory,
    val price: Double,
    val imageUrl: String
)

data class Review(val name: String, val city: String, val text: String)

data class TeamMember(val name: String, val role: String, val bio: String)

data class BlogPost(
    val title: String,
    val author: String,
    val date: String,
    val tag: String,
    val summary: String,
    val body: String
)
