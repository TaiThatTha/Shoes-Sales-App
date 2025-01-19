package com.example.shopgiay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun ProductCardInFavorites(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Ảnh sản phẩm
        Image(
            painter = rememberAsyncImagePainter(model = product.imageUrl),
            contentDescription = "Product Image",
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        // Thông tin sản phẩm
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Tên sản phẩm
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            // Giá tiền
            Text(
                text = product.price,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Red
            )
        }
    }
}