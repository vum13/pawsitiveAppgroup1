package com.pawsitive.petacademy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawsitive.petacademy.data.ContentRepository
import com.pawsitive.petacademy.data.Product
import com.pawsitive.petacademy.data.ProductCategory
import com.pawsitive.petacademy.ui.components.PawButton
import com.pawsitive.petacademy.ui.components.PawImage
import com.pawsitive.petacademy.ui.components.formatRand
import com.pawsitive.petacademy.ui.theme.Cream
import com.pawsitive.petacademy.ui.theme.InkDark
import com.pawsitive.petacademy.ui.theme.PawBlue
import com.pawsitive.petacademy.ui.theme.PawOrange
import com.pawsitive.petacademy.ui.theme.PawPurple
import com.pawsitive.petacademy.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@Composable
fun ShopScreen(cartViewModel: CartViewModel) {
    var filter by remember { mutableStateOf<ProductCategory?>(null) }
    val products = remember(filter) {
        ContentRepository.products.filter { filter == null || it.category == filter }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize().background(Cream)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    Modifier.fillMaxWidth().height(130.dp).clip(RoundedCornerShape(16.dp))
                ) {
                    PawImage(
                        model = "https://loremflickr.com/800/400/dog,ball?lock=320",
                        contentDescription = "Treat your pet like royalty",
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.35f)))
                    Text(
                        "Treat your pet like royalty!",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.align(Alignment.CenterStart).padding(16.dp)
                    )
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CategoryChip("All", filter == null) { filter = null }
                    ProductCategory.values().forEach { category ->
                        CategoryChip(category.label, filter == category) { filter = category }
                    }
                }
            }

            items(products, key = { it.id }) { product ->
                ProductCard(product) {
                    cartViewModel.addToCart(product)
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            "${product.name} added to cart",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }

        SnackbarHost(snackbarHostState, Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun CategoryChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = PawBlue,
            labelColor = InkDark,
            selectedContainerColor = PawOrange,
            selectedLabelColor = Color.White
        )
    )
}

@Composable
private fun ProductCard(product: Product, onAdd: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        PawImage(
            model = product.imageUrl,
            contentDescription = product.name,
            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
        )
        Column(Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                product.name,
                color = InkDark,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                formatRand(product.price),
                color = PawOrange,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            )
            PawButton(
                text = "Add to Cart",
                onClick = onAdd,
                modifier = Modifier.fillMaxWidth(),
                containerColor = PawPurple,
                icon = Icons.Filled.AddShoppingCart
            )
        }
    }
}
