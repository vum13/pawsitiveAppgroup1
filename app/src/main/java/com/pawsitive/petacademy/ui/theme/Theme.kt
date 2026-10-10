package com.pawsitive.petacademy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ---- Design system (strict) ----
val Cream = Color(0xFFFFF6E9)        // Background
val PawOrange = Color(0xFFF5761A)    // Headers, important buttons, prices
val PawBlue = Color(0xFF8DE4FF)      // Course cards, cart table, order summary
val PawPurple = Color(0xFFA379C9)    // Bottom navigation, checkout bar, secondary buttons
val InkDark = Color(0xFF1E1E1E)      // Body text

private val PawColorScheme = lightColorScheme(
    primary = PawOrange,
    onPrimary = Color.White,
    secondary = PawPurple,
    onSecondary = Color.White,
    tertiary = PawBlue,
    onTertiary = InkDark,
    background = Cream,
    onBackground = InkDark,
    surface = Cream,
    onSurface = InkDark,
    surfaceVariant = PawBlue,
    onSurfaceVariant = InkDark,
    outline = PawOrange
)

@Composable
fun PawsitiveTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = PawColorScheme, content = content)
}
