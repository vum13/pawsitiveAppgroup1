package com.pawsitive.petacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pawsitive.petacademy.navigation.PawsitiveApp
import com.pawsitive.petacademy.ui.theme.PawsitiveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Transparent status bar with light icons so the orange header reads cleanly.
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        setContent {
            PawsitiveTheme {
                PawsitiveApp()
            }
        }
    }
}
