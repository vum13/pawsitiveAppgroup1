package com.pawsitive.petacademy.data

object CourseRepository {

    private fun photo(tags: String, lock: Int) = "https://loremflickr.com/600/400/$tags?lock=$lock"

    // A) 6 WEEK SHORT COURSES (5 courses)
    val sixWeekCourses: List<Course> = listOf(
        Course(
            id = "first-aid",
            title = "First Aid",
            type = CourseType.SIX_WEEK,
            duration = "2h",
            fee = 750,
            content = emptyList(),
            learn = "Basic pet first aid.",
            imageUrl = photo("veterinarian,dog", 101)
        ),
        Course(
            id = "puppy-care",
            title = "Puppy Care",
            type = CourseType.SIX_WEEK,
            duration = "2h",
            fee = 750,
            content = emptyList(),
            learn = "How to take care of puppies.",
            imageUrl = photo("puppy", 102)
        ),
        Course(
            id = "basic-dog-walk",
            title = "Basic Dog Walk",
            type = CourseType.SIX_WEEK,
            duration = "2h",
            fee = 750,
            content = listOf(
                "Walking equipment",
                "Handling multiple dogs",
                "Safety procedures",
                "Exercise routines"
            ),
            learn = "Basics of walking dogs.",
            imageUrl = photo("dog,walking", 103)
        ),
        Course(
            id = "pet-grooming",
            title = "Pet Grooming",
            type = CourseType.SIX_WEEK,
            duration = "2h",
            fee = 1500,
            content = listOf(
                "Grooming basics",
                "Brushing & coat care",
                "Nail & paw care",
                "Eye & ear care",
                "Dental hygiene",
                "Skin problems & parasites",
                "Handling nervous or aggressive pets",
                "Breed-specific grooming",
                "When to visit a professional groomer"
            ),
            learn = "To learn safely tend to our pets grooming needs.",
            imageUrl = photo("dog,grooming", 104)
        ),
        Course(
            id = "animal-behaviour",
            title = "Animal Behaviour",
            type = CourseType.SIX_WEEK,
            duration = "2h",
            fee = 1500,
            content = listOf(
                "Understanding animal body language",
                "Identifying signs of stress and discomfort",
                "Managing common behavioural issues",
                "Building a positive relationship with your pet",
                "Training techniques for better communication",
                "Addressing aggression and fear-based behaviours",
                "Creating a safe and stimulating environment",
                "Recognizing and responding to social cues",
                "When to seek professional help"
            ),
            learn = "To understand animal behaviour.",
            imageUrl = photo("cat,dog", 105)
        )
    )

    // B) 6 MONTH LONG COURSES
    // The structure supports up to 5 courses: simply append more Course(...) entries
    // with type = CourseType.SIX_MONTH and every list / pricing / enquiry screen updates automatically.
    val sixMonthCourses: List<Course> = listOf(
        Course(
            id = "canine-obedience",
            title = "Canine Obedience Training",
            type = CourseType.SIX_MONTH,
            duration = "2h",
            fee = 1500,
            content = listOf(
                "Building a relationship with your dog",
                "Teaching \"sit\" and \"down\"",
                "\"Stay\" and impulse control",
                "\"Leave it\" & \"Drop it\"",
                "Leash & walking",
                "Greeting people without jumping",
                "Obedience around distractions",
                "Real-world training",
                "Maintaining good behaviour"
            ),
            learn = "Teaching our dogs how to behave like the good boys & girls they are.",
            imageUrl = photo("dog,training", 201)
        ),
        Course(
            id = "pet-business-management",
            title = "Pet Business Management",
            type = CourseType.SIX_MONTH,
            duration = "2h",
            fee = 1500,
            content = listOf(
                "Choosing your pet business",
                "Creating a business plan",
                "Pricing your services",
                "Managing your services",
                "Managing your customers",
                "Pet records & documentation",
                "Marketing your business",
                "Safety & risk management",
                "Managing money & expenses",
                "Growing your pet business"
            ),
            learn = "We will be learning how to manage our pet business.",
            imageUrl = photo("business,pets", 202)
        )
    )

    val allCourses: List<Course> get() = sixWeekCourses + sixMonthCourses

    fun coursesFor(type: CourseType): List<Course> = when (type) {
        CourseType.SIX_WEEK -> sixWeekCourses
        CourseType.SIX_MONTH -> sixMonthCourses
    }

    fun byId(id: String): Course? = allCourses.firstOrNull { it.id == id }
}
