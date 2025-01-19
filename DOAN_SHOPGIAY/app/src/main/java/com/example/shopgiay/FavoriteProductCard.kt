package com.example.shopgiay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
@Composable
fun FavoriteProductCard(product: Product) {
    var showAddToCartDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = product.imageUrl),
            contentDescription = "Product Image",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 16.sp
            )
            Text(
                text = product.price,
                color = Color.Red,
                fontSize = 14.sp
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { showAddToCartDialog = true },
                    colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(width = 158.dp, height = 48.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Add to Cart",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Thêm vào giỏ", fontSize = 12.sp)
                    }
                }

                IconButton(
                    onClick = { /* Logic xóa sản phẩm khỏi danh sách yêu thích */ },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Black
                    )
                }
            }
        }
    }

    // Hiển thị cửa sổ "Thêm vào giỏ hàng"
//    if (showAddToCartDialog) {
//        AddToCartDialog(
//            product = product,
//            onDismiss = { showAddToCartDialog = false },
//            onConfirm = { selectedSize, quantity ->
//                // Xử lý thêm vào giỏ hàng với size và số lượng đã chọn
//                println("Sản phẩm được thêm vào giỏ: Size $selectedSize, Số lượng $quantity")
//                showAddToCartDialog = false
//            }
//        )
//    }
}
