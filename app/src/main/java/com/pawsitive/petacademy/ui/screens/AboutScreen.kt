package com.pawsitive.petacademy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pawsitive.petacademy.ui.components.AccentHeading
import com.pawsitive.petacademy.ui.components.BodyText
import com.pawsitive.petacademy.ui.components.PawImage
import com.pawsitive.petacademy.ui.theme.Cream

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        AccentHeading("About Pawsitive Pet Academy")

        PawImage(
            model = "https://loremflickr.com/800/500/dog,training?lock=31",
            contentDescription = "Dog training class",
            modifier = Modifier.fillMaxWidth().height(190.dp).clip(RoundedCornerShape(16.dp))
        )

        AccentHeading("Our History")
        BodyText(
            "Pawsitive Pet Academy was established in 2023 and offers training courses in Durban. " +
                "Hundreds of pet owners and aspiring pet care professionals have completed both the " +
                "six-month Professional Development Programmes and the six-week Practical Skills Courses."
        )

        AccentHeading("Vision")
        BodyText(
            "To be South Africa's leading academy for pet care and training, building a compassionate " +
                "nation where every pet is understood, well-cared for and cherished."
        )

        AccentHeading("Our Mission")
        BodyText(
            "Pawsitive Pet Academy exists to empower pet owners, families, aspiring professionals, and " +
                "future entrepreneurs through expert-led and accessible education in pet care, training, " +
                "and business skills. Since 2023, the academy has equipped hundreds of graduates through " +
                "practical six-week short courses and comprehensive six-month programmes offered in " +
                "Durban and beyond."
        )

        AccentHeading("Our Goals")
        BodyText(
            "To educate and equip pet owners and aspiring professionals with proper animal care and " +
                "behavioural training techniques."
        )
        BodyText(
            "To create meaningful employment and business opportunities for individuals who are " +
                "passionate about working with animals."
        )
        BodyText(
            "To deliver a seamless, user-centred digital experience through the website and mobile app " +
                "to connect more learners with life-changing knowledge."
        )
    }
}
