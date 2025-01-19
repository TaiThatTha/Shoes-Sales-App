package com.example.shopgiay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AdHomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFCC00FF),
                        Color(0xFF0A051C)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header

            Spacer(modifier = Modifier.height(16.dp))

            // Revenue Cards
            listOf(
                "TỔNG DOANH THU" to 12095000,
                "DOANH THU THÁNG NÀY" to 12095000,
                "DOANH THU HÔM NAY" to 12095000
            ).forEach { (title, revenue) ->
                RevenueCard(title = title, revenue = revenue, orders = 9)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RevenueCard(title: String, revenue: Int, orders: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₫$revenue",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6600FF)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Divider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Đơn đã giao",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$orders",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Icons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.Money,
                    contentDescription = "ShoppingCart",
                    tint = Color(0xFF4285F4),
                    modifier = Modifier.size(32.dp),

                )
                Spacer(modifier = Modifier.height(16.dp))
                Icon(
                    imageVector = Icons.Rounded.CheckCircleOutline,
                    contentDescription = "ShoppingCart",
                    tint = Color(0xFF4285F4),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
