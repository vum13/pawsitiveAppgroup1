package com.pawsitive.petacademy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawBlue
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.ui.theme.PawPurple
import java.util.Locale

/** "R750" for whole amounts, "R377.50" otherwise. */
fun formatRand(amount: Double): String =
    if (amount % 1.0 == 0.0) "R${amount.toLong()}" else "R" + String.format(Locale.US, "%.2f", amount)

@Composable
fun Heading(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier, color = InkDark, fontSize = 20.sp, fontWeight = FontWeight.Bold)
}

/** Heading with a short orange accent bar underneath. */
@Composable
fun AccentHeading(text: String, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(text, color = InkDark, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Box(Modifier.width(44.dp).height(3.dp).background(PawOrange, RoundedCornerShape(2.dp)))
    }
}

@Composable
fun BodyText(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier, color = InkDark, fontSize = 14.sp, lineHeight = 21.sp)
}

@Composable
fun PawButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = PawPurple,
    contentColor: Color = Color.White,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier.heightIn(min = 48.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.45f),
            disabledContentColor = contentColor.copy(alpha = 0.75f)
        )
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, fontWeight = FontWeight.SemiBold)
    }
}

/** Orange price on a white pill so it stays legible on the light-blue cards. */
@Composable
fun PricePill(text: String, modifier: Modifier = Modifier, fontSize: TextUnit = 16.sp) {
    Surface(modifier = modifier, shape = RoundedCornerShape(50), color = Color.White) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            color = PawOrange,
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

/** Network or local image with a loading spinner and a paw fallback if it cannot load. */
@Composable
fun PawImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier.background(PawBlue.copy(alpha = 0.35f)),
        contentScale = contentScale,
        loading = {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PawOrange, strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
            }
        },
        error = {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.Pets, contentDescription = null, tint = PawOrange)
            }
        }
    )
}
