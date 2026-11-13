package com.pawspetacademy.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.R
import com.pawspetacademy.app.Routes
import com.pawspetacademy.app.ui.theme.PawColors
import com.pawspetacademy.app.ui.theme.PawShape

@Composable
fun PawIcon(modifier: Modifier = Modifier, color: Color = PawColors.Yellow) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        drawOval(color, Offset(w * 0.22f, h * 0.5f), Size(w * 0.56f, h * 0.45f))
        listOf(0.04f to 0.36f, 0.27f to 0.1f, 0.56f to 0.1f, 0.79f to 0.36f).forEach { (x, y) ->
            drawOval(color, Offset(w * x, h * y), Size(w * 0.21f, h * 0.27f))
        }
    }
}

@Composable
fun PetTile(emoji: String, modifier: Modifier = Modifier, color: Color = PawColors.LightBlue, fontSize: TextUnit = 40.sp) {
    Box(modifier.clip(PawShape).background(color), contentAlignment = Alignment.Center) {
        Text(emoji, fontSize = fontSize)
    }
}

@Composable
fun ColorTile(text: String, color: Color, modifier: Modifier = Modifier, textColor: Color = PawColors.Dark) {
    Box(modifier.clip(PawShape).background(color), contentAlignment = Alignment.Center) {
        Text(text, color = textColor, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}

@Composable
fun Avatar(initials: String, modifier: Modifier = Modifier) {
    Box(modifier.clip(CircleShape).background(PawColors.LightBlue), contentAlignment = Alignment.Center) {
        Text(initials, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(text, fontWeight = FontWeight.Bold, fontSize = 18.sp)
}

@Composable
fun PawButton(text: String, color: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = PawShape,
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = PawColors.Dark)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun Panel(
    modifier: Modifier = Modifier,
    color: Color = PawColors.LightBlue,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier
            .clip(PawShape)
            .background(color)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        content = content
    )
}

@Composable
fun ScrollableColumn(hAlign: Alignment.Horizontal = Alignment.Start, content: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        horizontalAlignment = hAlign,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = content
    )
}

@Composable
fun pawFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = PawColors.LightBlue,
    unfocusedContainerColor = PawColors.LightBlue,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent
)

@Composable
fun AppScaffold(
    title: String,
    onNavigate: (String) -> Unit,
    onBack: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Image(painterResource(R.drawable.bg_paws), null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().height(56.dp).background(PawColors.Brown).padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.width(56.dp)) {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = PawColors.White)
                        }
                    }
                }
                Text(
                    title, Modifier.weight(1f), color = PawColors.White,
                    fontWeight = FontWeight.Bold, fontSize = 20.sp, textAlign = TextAlign.Center
                )
                Text(
                    "Cart", Modifier.width(56.dp).clickable { onNavigate(Routes.CART) },
                    color = PawColors.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.End
                )
            }
            Column(Modifier.weight(1f).fillMaxWidth(), content = content)
            Row(
                Modifier.fillMaxWidth().height(56.dp).background(PawColors.Brown),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("Home" to Routes.HOME, "Courses" to Routes.COURSES, "Shop" to Routes.SHOP,
                    "Gallery" to Routes.GALLERY, "Contact" to Routes.CONTACT).forEach { (label, route) ->
                    Text(
                        label, Modifier.weight(1f).clickable { onNavigate(route) },
                        color = PawColors.White, fontWeight = FontWeight.Bold, fontSize = 15.sp, textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
