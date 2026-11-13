package com.pawspetacademy.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object PawColors {
    val Brown = Color(0xFF4E3B2B)
    val Background = Color(0xFFFFF6E9)
    val LightBlue = Color(0xFF8DE4FF)
    val LightPurple = Color(0xFFA379C9)
    val Pink = Color(0xFFF5C8C2)
    val Yellow = Color(0xFFEBD469)
    val Ivory = Color(0xFFFEF8F3)
    val White = Color(0xFFFFFFFF)
    val Dark = Color(0xFF000000)
    val Facebook = Color(0xFF1877F2)
}

val PawShape = RoundedCornerShape(8.dp)

private val PawScheme = lightColorScheme(
    primary = PawColors.Brown, onPrimary = PawColors.White,
    secondary = PawColors.LightPurple, onSecondary = PawColors.Dark,
    tertiary = PawColors.LightBlue, onTertiary = PawColors.Dark,
    background = PawColors.Background, onBackground = PawColors.Dark,
    surface = PawColors.Ivory, onSurface = PawColors.Dark,
)

private val PawShapes = Shapes(
    extraSmall = PawShape, small = PawShape, medium = PawShape, large = PawShape, extraLarge = PawShape
)

@Composable
fun PawsPetAcademyTheme(content: @Composable () -> Unit) =
    MaterialTheme(colorScheme = PawScheme, shapes = PawShapes, content = content)
