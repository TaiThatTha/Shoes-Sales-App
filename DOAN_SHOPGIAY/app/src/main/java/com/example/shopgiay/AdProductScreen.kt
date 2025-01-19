package com.example.shopgiay

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

// Data class for product variant
data class ProductVariant(
    val size: String,
    val quantity: Int
)

// Data class for product
data class Productt(
    val name: String,
    val price: String,
    val imageUrl: String,
    val variants: List<ProductVariant>
)

@Composable
fun AdProductScreen(navController: NavHostController) {
    // Sample product data
    val products = listOf(
        Productt(
            name = "Nike Air Force 1",
            price = "2,929,000đ",
            imageUrl = "R.drawable.dj2",
            variants = listOf(
                ProductVariant("35", 5),
                ProductVariant("36", 3),
                ProductVariant("37", 0) // Size hết hàng
            )
        ),
        Productt(
            name = "Adidas Ultraboost",
            price = "3,200,000đ",
            imageUrl = "R.drawable.dj2",
            variants = listOf(
                ProductVariant("35", 4),
                ProductVariant("36", 0) // Size hết hàng
            )
        ),
        Productt(
            name = "Converse Chuck Taylor",
            price = "1,500,000đ",
            imageUrl = "R.drawable.dj2",
            variants = listOf(
                ProductVariant("35", 10),
                ProductVariant("36", 0) // Size hết hàng
            )
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6600FF), Color(0xFF1C0524))
                )
            )
    ) {
        // Header
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(8.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SẢN PHẨM TRONG KHO",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
        }

        // Product list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(products.size) { index ->
                val product = products[index]
                val totalQuantity = product.variants.sumOf { it.quantity } // Tính tổng số lượng
                ProductItem(
                    name = product.name,
                    price = product.price,
                    imageUrl = product.imageUrl,
                    quantity = totalQuantity, // Truyền số lượng vào
                    variants = product.variants, // Truyền danh sách variants
                    onClick = {
                        // Navigate to detail screen with product data
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProductItem(
    name: String,
    price: String,
    imageUrl: String,
    quantity: Int, // Thêm số lượng
    variants: List<ProductVariant>, // Thêm danh sách variants
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
            .clickable { onClick() } // Trigger onClick event
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Product Image
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Product Info
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = price, fontSize = 14.sp, color = Color.Red)
                Text(text = "Tổng số lượng: $quantity", fontSize = 12.sp, color = Color.Gray) // Hiển thị tổng số lượng
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Display sizes and their quantities
        variants.forEach { variant ->
            Text(
                text = "Size: ${variant.size} - ${if (variant.quantity == 0) "Hết" else "${variant.quantity} đôi"}",
                fontSize = 12.sp,
                color = if (variant.quantity == 0) Color.Red else Color.Black
            )
        }
    }
}
