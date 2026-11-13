package com.pawspetacademy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pawspetacademy.app.ui.screens.*
import com.pawspetacademy.app.ui.theme.PawsPetAcademyTheme
import com.pawspetacademy.app.viewmodel.CartViewModel

object Routes {
    const val HOME = "home"
    const val ABOUT = "about"
    const val COURSES = "courses"
    const val COURSE_DETAIL = "course/{courseId}"
    const val QUOTE = "quote"
    const val CONTACT = "contact"
    const val TEAM = "team"
    const val CART = "cart"
    const val SHOP = "shop"
    const val BLOG = "blog"
    const val GALLERY = "gallery"
    const val REVIEWS = "reviews"
    fun courseDetail(id: String) = "course/$id"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PawsPetAcademyTheme {
                val nav = rememberNavController()
                val cart: CartViewModel = viewModel()
                val go: (String) -> Unit = { nav.navigate(it) }
                val back: () -> Unit = { nav.popBackStack() }
                NavHost(navController = nav, startDestination = Routes.HOME) {
                    composable(Routes.HOME) { HomeScreen(go) }
                    composable(Routes.ABOUT) { AboutScreen(go, back) }
                    composable(Routes.COURSES) { CoursesScreen(go, back) }
                    composable(Routes.COURSE_DETAIL, listOf(navArgument("courseId") { type = NavType.StringType })) {
                        CourseDetailScreen(it.arguments?.getString("courseId").orEmpty(), go, back)
                    }
                    composable(Routes.QUOTE) { CourseQuoteScreen(cart, go, back) }
                    composable(Routes.CONTACT) { ContactScreen(go, back) }
                    composable(Routes.TEAM) { TeamScreen(go, back) }
                    composable(Routes.CART) { CartScreen(cart, go, back) }
                    composable(Routes.SHOP) { ShopScreen(cart, go, back) }
                    composable(Routes.BLOG) { BlogScreen(go, back) }
                    composable(Routes.GALLERY) { GalleryScreen(go, back) }
                    composable(Routes.REVIEWS) { ReviewsScreen(go, back) }
                }
            }
        }
    }
}
