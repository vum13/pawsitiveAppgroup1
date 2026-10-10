package com.pawsitive.petacademy.data

object ContentRepository {

    private fun photo(tags: String, lock: Int) = "https://loremflickr.com/400/400/$tags?lock=$lock"

    // ---------------- SHOP ----------------
    val products: List<Product> = listOf(
        Product("food-kibble", "Premium Dog Kibble 2kg", ProductCategory.FOOD, 189.0, photo("dog,food", 301)),
        Product("food-puppy", "Puppy Starter Food 1.5kg", ProductCategory.FOOD, 159.0, photo("puppy,food", 302)),
        Product("food-cat", "Cat Crunch Mix 1.5kg", ProductCategory.FOOD, 129.0, photo("cat,food", 303)),
        Product("toy-turtle", "Plush Turtle Toy", ProductCategory.TOYS, 69.0, photo("dog,toy", 304)),
        Product("toy-balls", "Rubber Ball Set (3)", ProductCategory.TOYS, 59.0, photo("dog,ball", 305)),
        Product("toy-bone", "Chew Bone Toy", ProductCategory.TOYS, 49.0, photo("dog,chew", 306)),
        Product("leash-nylon", "Nylon Leash 1.5m", ProductCategory.LEASHES, 99.0, photo("dog,leash", 307)),
        Product("leash-retract", "Retractable Leash 5m", ProductCategory.LEASHES, 189.0, photo("dog,collar", 308)),
        Product("leash-harness", "Harness & Leash Set", ProductCategory.LEASHES, 249.0, photo("dog,harness", 309))
    )

    // ---------------- GALLERY ----------------
    val galleryImages: List<String> = listOf(
        photo("puppy", 401), photo("rabbit", 402), photo("dachshund", 403),
        photo("beagle", 404), photo("hamster", 405), photo("gecko", 406),
        photo("husky", 407), photo("hedgehog", 408), photo("retriever", 409),
        photo("bunny", 410), photo("cockatiel", 411), photo("dog,costume", 412)
    )

    // ---------------- REVIEWS ----------------
    val reviews: List<Review> = listOf(
        Review("Olivia", "Durban", "Pawsitive changed everything! My dog finally listens and the chew toys last so long!"),
        Review("Thando", "Durban", "Amazing courses! From basic to career training, plus my puppy loves the toys."),
        Review("Themba", "Cape Town", "My dog learned so fast! Positive training and cute toy packaging too!"),
        Review("Priya", "Durban", "Did the 6-month program and started my own pet business. Toys are safe and quality!"),
        Review("Emily", "Pretoria", "Love the toys as training rewards. Great for energy and bedtime!")
    )

    // ---------------- TEAM ----------------
    val team: List<TeamMember> = listOf(
        TeamMember(
            "Sarah Smith",
            "Founder & Head Trainer",
            "Zoologist and certified behaviourist who started Pawsitive Pet Academy in 2023 and brings years of hands-on experience with dogs of every age."
        ),
        TeamMember(
            "Thabo Dlamini",
            "Animal Behaviour & Business Mentor",
            "Leads our Animal Behaviour course and the Pet Business Management programme, helping graduates turn their passion into a business."
        ),
        TeamMember(
            "Jane Smith",
            "Senior Dog Trainer & Puppy Care Specialist",
            "Runs our puppy, obedience and dog-walking classes, specialising in positive-reinforcement training for young dogs."
        ),
        TeamMember(
            "Aisha Patel",
            "Pet Groomer & First Aid Instructor",
            "Teaches Pet Grooming and Pet First Aid, and is our go-to for keeping nervous pets calm and comfortable."
        )
    )

    // ---------------- BLOG ----------------
    val blogPosts: List<BlogPost> = listOf(
        BlogPost(
            title = "Preparing for the Festive Season",
            author = "Sarah Smith",
            date = "Dec 10, 2024",
            tag = "Tips & Tricks For A Happy Pup",
            summary = "Visitors, parties and fireworks can overwhelm even the calmest dog. Here is how to keep your pup relaxed.",
            body = "The holidays bring more noise, more people and more disruption to routine than any other time of year. Young dogs and dogs who are already anxious are the most likely to become overstimulated.\n\nCreate a quiet retreat with a bed, a chew and a closed door where your dog can rest. Keep walks and meals on schedule, introduce guests one at a time, and reward calm behaviour. If fireworks are likely, practise with recorded sounds at low volume well beforehand."
        ),
        BlogPost(
            title = "Managing Littermate & Housemate Syndrome in Dogs",
            author = "Jane Smith",
            date = "Sep 5, 2024",
            tag = "Tips & Tricks For A Happy Pup",
            summary = "Raising two young dogs together is rewarding, but they can bond so tightly to each other that training suffers.",
            body = "Dogs raised together can struggle to cope when apart, and often pay more attention to each other than to you. The solution is to build each dog's independence.\n\nTrain, walk and feed them separately for part of every day, give each their own space and toys, and spend one-on-one time with each dog. Short, positive sessions with the same cue words help both dogs learn to focus on you."
        ),
        BlogPost(
            title = "The Problem With Off-Leash Dogs",
            author = "Jane Smith",
            date = "Apr 1, 2025",
            tag = "Off Leash",
            summary = "A sensitive topic, but one every dog walker should understand: a friendly off-leash dog can cause a reactive dog real distress.",
            body = "Not every dog enjoys meeting strangers. Dogs that are nervous, recovering from injury or still in training need space, and an approaching off-leash dog can undo weeks of progress.\n\nKeep your dog on a lead in shared spaces unless you have reliable recall, call them back early when you see another dog, and always ask before letting dogs greet. Respecting space keeps every walk safer."
        ),
        BlogPost(
            title = "Keep Yourself in Check: Staying Calm With Your Dog",
            author = "Sarah Smith",
            date = "Jun 18, 2025",
            tag = "Thoughts Of A Trainer",
            summary = "Our dogs read our body language and mood. Training starts with managing our own emotions.",
            body = "Dogs are experts at reading us. Tension on the lead, a sharp tone or an anxious breath all tell your dog that something is wrong.\n\nBefore a training session, take a slow breath and set a small goal. If you feel frustrated, end on an easy success and try again later. A calm handler makes learning easier for the dog and builds trust on both sides."
        )
    )
}
