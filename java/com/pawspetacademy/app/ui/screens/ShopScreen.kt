package com.pawspetacademy.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.pawspetacademy.app.R
import com.pawspetacademy.app.Routes
import com.pawspetacademy.app.data.AppData
import com.pawspetacademy.app.ui.components.*
import com.pawspetacademy.app.ui.theme.PawColors
import com.pawspetacademy.app.ui.theme.PawShape
import com.pawspetacademy.app.viewmodel.CartViewModel

// Same order as AppData.products: Catnip Fish, Bouncy Balls, Plush Turtle, Rope Chew, Sisal Ball, Bone Chew
private val productPhotos = listOf(
    R.drawable.product_catnip_fish,
    R.drawable.product_bouncy_balls,
    R.drawable.product_plush_turtle,
    R.drawable.product_rope_chew,
    R.drawable.product_sisal_ball,
    R.drawable.product_bone_chew,
)

@Composable
fun ShopScreen(cart: CartViewModel, onNavigate: (String) -> Unit, onBack: () -> Unit) {
    AppScaffold("Pawsitive Shop", onNavigate, onBack) {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(Modifier.fillMaxWidth().height(130.dp)) {
                PetTile("🐶", Modifier.fillMaxSize(), PawColors.Ivory, 64.sp)
                Text(
                    "Treat your pet like royalty!", Modifier.align(Alignment.TopEnd).padding(8.dp),
                    fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.End
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(AppData.products) { i, p ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(productPhotos[i]),
                            contentDescription = p.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(100.dp).clip(PawShape)
                        )
                        TextButton(
                            onClick = { cart.addItem(p.id, p.name, p.price) },
                            colors = ButtonDefaults.textButtonColors(contentColor = PawColors.Dark)
                        ) {
                            Text("Add to cart", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                PawButton("Back", PawColors.LightBlue, Modifier.weight(1f).height(40.dp)) { onBack() }
                PawButton("Next", PawColors.LightPurple, Modifier.weight(1f).height(40.dp)) { onNavigate(Routes.CART) }
            }
        }
    }
}
