package com.pawsitive.petacademy.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pawsitive.petacademy.data.CourseType
import com.pawsitive.petacademy.ui.screens.AboutScreen
import com.pawsitive.petacademy.ui.screens.BlogScreen
import com.pawsitive.petacademy.ui.screens.CheckoutScreen
import com.pawsitive.petacademy.ui.screens.ContactScreen
import com.pawsitive.petacademy.ui.screens.CourseDetailScreen
import com.pawsitive.petacademy.ui.screens.CourseListScreen
import com.pawsitive.petacademy.ui.screens.EnquiryScreen
import com.pawsitive.petacademy.ui.screens.GalleryScreen
import com.pawsitive.petacademy.ui.screens.HomeScreen
import com.pawsitive.petacademy.ui.screens.PricingScreen
import com.pawsitive.petacademy.ui.screens.ReviewsScreen
import com.pawsitive.petacademy.ui.screens.ShopScreen
import com.pawsitive.petacademy.ui.screens.TeamScreen
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.ui.theme.PawPurple
import com.pawsitive.petacademy.viewmodel.CartViewModel

private data class BottomItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val matches: Set<String>
)

private val bottomItems = listOf(
    BottomItem("Home", Icons.Filled.Home, Routes.HOME, setOf(Routes.HOME)),
    BottomItem(
        "Courses", Icons.Filled.School, Routes.WEEKS,
        setOf(Routes.WEEKS, Routes.MONTHS, Routes.COURSE_DETAIL, Routes.PRICING, Routes.ENQUIRY)
    ),
    BottomItem("Shop", Icons.Filled.Store, Routes.SHOP, setOf(Routes.SHOP)),
    BottomItem("Gallery", Icons.Filled.PhotoLibrary, Routes.GALLERY, setOf(Routes.GALLERY)),
    BottomItem("Contact", Icons.Filled.Phone, Routes.CONTACT, setOf(Routes.CONTACT))
)

private fun titleFor(route: String?): String = when (route) {
    Routes.HOME -> "Pawsitive Pet Academy"
    Routes.ABOUT -> "About Us"
    Routes.WEEKS -> "6-Week Courses"
    Routes.MONTHS -> "6-Month Courses"
    Routes.COURSE_DETAIL -> "Course Details"
    Routes.GALLERY -> "Gallery"
    Routes.CONTACT -> "Get in Touch"
    Routes.ENQUIRY -> "Enquiry"
    Routes.SHOP -> "Pawsitive Shop"
    Routes.CHECKOUT -> "Checkout"
    Routes.REVIEWS -> "Reviews"
    Routes.TEAM -> "Our Team"
    Routes.BLOG -> "Blog"
    Routes.PRICING -> "Pricing"
    else -> "Pawsitive Pet Academy"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PawsitiveApp() {
    val navController = rememberNavController()
    // Resolved against the Activity so the same cart is shared by every screen.
    val cartViewModel: CartViewModel = viewModel()
    val cartState by cartViewModel.state.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route

    Scaffold(
        containerColor = Cream,
        contentColor = InkDark,
        topBar = {
            // Every screen: orange header with white text.
            CenterAlignedTopAppBar(
                title = { Text(titleFor(route), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    if (route != Routes.CHECKOUT) {
                        IconButton(onClick = {
                            navController.navigate(Routes.CHECKOUT) { launchSingleTop = true }
                        }) {
                            BadgedBox(badge = {
                                if (cartState.itemCount > 0) {
                                    Badge(containerColor = Color.White, contentColor = PawOrange) {
                                        Text("${cartState.itemCount}")
                                    }
                                }
                            }) {
                                Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PawOrange,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            // The checkout screen has its own purple payment bar.
            if (route != Routes.CHECKOUT) PawBottomBar(route, navController)
        }
    ) { innerPadding ->
        PawNavHost(navController, cartViewModel, Modifier.padding(innerPadding))
    }
}

@Composable
private fun PawBottomBar(currentRoute: String?, navController: NavHostController) {
    NavigationBar(containerColor = PawPurple, contentColor = Color.White) {
        bottomItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute in item.matches,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = PawOrange,
                    unselectedIconColor = Color.White.copy(alpha = 0.8f),
                    unselectedTextColor = Color.White.copy(alpha = 0.8f)
                )
            )
        }
    }
}

@Composable
private fun PawNavHost(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    fun go(route: String) = navController.navigate(route) { launchSingleTop = true }

    NavHost(navController = navController, startDestination = Routes.HOME, modifier = modifier) {

        composable(Routes.HOME) { HomeScreen(onNavigate = ::go) }

        composable(Routes.ABOUT) { AboutScreen() }

        composable(Routes.WEEKS) {
            CourseListScreen(
                type = CourseType.SIX_WEEK,
                onCourseClick = { go(Routes.courseDetail(it)) },
                onSwitchType = {
                    navController.navigate(Routes.MONTHS) {
                        popUpTo(Routes.WEEKS) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.MONTHS) {
            CourseListScreen(
                type = CourseType.SIX_MONTH,
                onCourseClick = { go(Routes.courseDetail(it)) },
                onSwitchType = {
                    navController.navigate(Routes.WEEKS) {
                        popUpTo(Routes.MONTHS) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Routes.COURSE_DETAIL,
            arguments = listOf(navArgument("courseId") { type = NavType.StringType })
        ) { entry ->
            CourseDetailScreen(
                courseId = entry.arguments?.getString("courseId"),
                onEnquire = { go(Routes.enquiry(it)) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.GALLERY) { GalleryScreen() }

        composable(Routes.CONTACT) { ContactScreen(onEnquiry = { go(Routes.enquiry()) }) }

        composable(
            route = Routes.ENQUIRY,
            arguments = listOf(navArgument("courseId") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { entry ->
            EnquiryScreen(preselectedCourseId = entry.arguments?.getString("courseId").orEmpty())
        }

        composable(Routes.SHOP) { ShopScreen(cartViewModel) }

        composable(Routes.CHECKOUT) {
            CheckoutScreen(
                cartViewModel = cartViewModel,
                onContinueShopping = {
                    navController.navigate(Routes.SHOP) {
                        popUpTo(Routes.CHECKOUT) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.REVIEWS) { ReviewsScreen() }
        composable(Routes.TEAM) { TeamScreen() }
        composable(Routes.BLOG) { BlogScreen() }

        composable(Routes.PRICING) {
            PricingScreen(
                onCourseClick = { go(Routes.courseDetail(it)) },
                onGetQuote = { go(Routes.enquiry()) }
            )
        }
    }
}
