package com.example.shopgiay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController

@Composable
fun ProductDetailBottomBar(
    onChatClick: () -> Unit,
    onCartClick: () -> Unit,
    onBuyNowClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFF6F6F6)), // Màu nền của BottomBar
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nút Chat
        IconButton(
            onClick = { onChatClick() },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Chat,
                contentDescription = "Chat",
                tint = Color.Black
            )
        }

        // Đường phân cách
        HorizontalDivider(
            modifier = Modifier
                .width(1.dp)
                .height(100.dp),
            color = Color.Black
        )

        // Nút Thêm vào giỏ hàng
        IconButton(
            onClick = { onCartClick() },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.AddShoppingCart,
                contentDescription = "AddShoppingCart",
                tint = Color.Black
            )
        }

        // Nút Mua ngay
        Button(
            onClick = { onBuyNowClick() },
            colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)), // Màu xanh
            modifier = Modifier
                .weight(3f)
                .padding(8.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = "MUA NGAY",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
