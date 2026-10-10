package com.pawsitive.petacademy.navigation

object Routes {
    const val HOME = "home"
    const val ABOUT = "about"
    const val WEEKS = "courses_weeks"
    const val MONTHS = "courses_months"
    const val COURSE_DETAIL = "course/{courseId}"
    const val GALLERY = "gallery"
    const val CONTACT = "contact"
    const val ENQUIRY = "enquiry?courseId={courseId}"
    const val SHOP = "shop"
    const val CHECKOUT = "checkout"
    const val REVIEWS = "reviews"
    const val TEAM = "team"
    const val BLOG = "blog"
    const val PRICING = "pricing"

    fun courseDetail(id: String) = "course/$id"
    fun enquiry(courseId: String = "") = "enquiry?courseId=$courseId"
}
