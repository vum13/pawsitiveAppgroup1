package com.pawspetacademy.app.data

data class Course(val id: String, val title: String, val price: Double, val duration: String, val bullets: List<String>, val learn: String)
data class Product(val id: String, val name: String, val price: Double, val emoji: String)
data class Post(val author: String, val message: String)
data class BlogPost(val title: String, val byline: String, val excerpt: String)
data class Member(val name: String, val role: String, val initials: String, val bio: String)

object AppData {
    const val EMAIL = "pawsitivepetacademy@gmail.com"
    const val CALL = "076 643 7864"
    const val WHATSAPP = "067 535 7543"
    val address = listOf("45 Pet Lane", "Durban, 4001", "South Africa")


    val sixWeeks = listOf(
        Course("w1", "Basic Dog Walk", 750.0, "2h",
            listOf("Walking your own dog", "Basic safety and leash control", "Safety precautions", "Health and nutrition"),
            "To learn safe and professional dog walking practices."),
        Course("w2", "Puppy Care", 750.0, "2h",
            listOf("Feeding & nutrition", "Health & veterinary care", "Potty training", "Socialisation",
                "Grooming & hygiene", "Exercise & play", "Puppy-proofing the home"),
            "To learn safely take care of our new pets."),
        Course("w3", "Pet First Aid", 750.0, "2h",
            listOf("Recognising an emergency", "Wounds & bleeding", "Choking & breathing problems", "CPR basics",
                "Poisoning & dangerous substances", "Burns, bites & stings", "Heatstroke & environmental emergencies"),
            "To learn safely tend to our pets' first aid needs."),
    )
    val sixMonths = listOf(
        Course("m1", "Pet Grooming", 1500.0, "2h",
            listOf("Grooming basics", "Brushing & coat care", "Nail & paw care", "Eye & ear care", "Dental hygiene",
                "Skin problems & parasites", "Handling nervous or aggressive pets", "Breed-specific grooming",
                "When to visit a professional groomer"),
            "To learn safely tend to our pets grooming needs."),
        Course("m2", "Animal Behaviour", 1500.0, "2h",
            listOf("Understanding animal body language", "Identifying signs of stress and discomfort",
                "Managing common behavioural issues", "Building a positive relationship with your pet",
                "Training techniques for better communication", "Addressing aggression and fear-based behaviours",
                "Creating a safe and stimulating environment", "Recognising and responding to social cues",
                "When to seek professional help for behavioural concerns"),
            "We will be learning how to understand and manage animal behaviour."),
        Course("m3", "Canine Obedience Training", 1500.0, "2h",
            listOf("Building a relationship with your dog", "Teaching \"sit\" and \"down\"",
                "\"Stay\" and impulse control", "\"Leave it\" & \"Drop it\"", "Leash & walking",
                "Greeting people without jumping", "Obedience around distractions", "Real-world training",
                "Maintaining good behaviour"),
            "We will be learning how to train your dog in obedience."),
        Course("m4", "Pet Business Management", 1500.0, "2h",
            listOf("Choosing your pet business", "Creating a business plan", "Pricing your services",
                "Managing your services", "Managing your customers", "Pet records & documentation",
                "Marketing your business", "Safety & risk management", "Managing money & expenses",
                "Growing your pet business"),
            "We will be learning how to manage our pet business."),
    )
    val allCourses = sixWeeks + sixMonths
    fun course(id: String) = allCourses.find { it.id == id }

    val products = listOf(
        Product("p1", "Catnip Fish Toy", 45.0, "🐟"),
        Product("p2", "Bouncy Ball Set", 60.0, "🎾"),
        Product("p3", "Plush Turtle", 80.0, "🐢"),
        Product("p4", "Rope Chew", 35.0, "🧶"),
        Product("p5", "Sisal Ball", 40.0, "🥎"),
        Product("p6", "Bone Chew", 40.0, "🦴"),
    )

    val blogPosts = listOf(
        BlogPost("Preparing for the festive season", "by Pawsitive Team | Dec 10, 2024 | Tips & Tricks", "Busy holidays bring visitors, noise and new routines. Plan quiet spaces and short training breaks to keep your dog calm."),
        BlogPost("Managing littermate syndrome", "by Pawsitive Team | Sep 5, 2024 | Tips & Tricks", "Dogs raised together can struggle to be independent. Separate walks and training sessions help each dog grow confident."),
        BlogPost("Off-leash dogs: staying safe", "by Pawsitive Team | Apr 1, 2021 | Off Leash", "Off-leash time can end badly when recall is weak. Practise in safe spaces and reward every return to you."),
        BlogPost("Keeping your bond strong", "by Pawsitive Team | Apr 1, 2021 | Thoughts", "Consistent routines, honest rewards and patience build the trust that makes training easier for you both."),
    )

    val reviews = listOf(
        Post("Thandi M.", "My rescue pup finally walks calmly. The coaches are patient and kind!"),
        Post("James O.", "The recall course changed our daily walks completely."),
        Post("Sofia L.", "Loved the first aid class. I feel much more prepared now."),
        Post("Ben K.", "Friendly team and a really supportive community."),
    )

    val team = listOf(
        Member("Sarah Smith", "Founder and Head Trainer", "SS", "Durban native, certified behaviourist. Started Pawsitive in 2023 after 8 years with rescue dogs."),
        Member("Jake Smith", "Senior Dog Trainer and Puppy Care Specialist", "JS", "Handles 6-week courses, safe walking and handling multiple dogs."),
        Member("Aisha Patel", "Pet Groomer and First Aid Instructor", "AP", "Teaches grooming and first aid, and curates our pet toys collection."),
        Member("Thabo Dlamini", "Animal Behaviour and Business Mentor", "TD", "Teaches behaviour, obedience and business management."),
    )
}
