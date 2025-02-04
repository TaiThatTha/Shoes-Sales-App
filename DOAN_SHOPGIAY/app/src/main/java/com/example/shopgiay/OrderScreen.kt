package com.example.shopgiay

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.navigation.NavHostController

@Composable
fun OrderScreen(navController: NavHostController, initialTab: String? = null) {
    var selectedTab by remember { mutableStateOf(initialTab ?: "Chờ xác nhận") }

    val tabs = listOf("Chờ xác nhận", "Chờ lấy hàng", "Chờ giao hàng", "Đã giao", "Đã hủy")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header and Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Đơn mua", fontSize = 20.sp)
        }

        LazyRow {
            items(tabs) { tab ->
                TabItem(
                    title = tab,
                    isSelected = tab == selectedTab,
                    onClick = { selectedTab = tab }
                )
            }
        }

        val orderContent = when (selectedTab) {
            "Chờ xác nhận" -> listOf("Đơn hàng 1", "Đơn hàng 2")
            "Chờ lấy hàng" -> listOf("Đơn hàng 3")
            "Chờ giao hàng" -> listOf("Đơn hàng 4", "Đơn hàng 5")
            "Đã giao" -> listOf("Đơn hàng 6", "Đơn hàng 7", "Đơn hàng 8")
            "Đã hủy" -> listOf("Đơn hàng 9")
            else -> emptyList()
        }

        if (orderContent.isEmpty()) {
            EmptyState()
        } else {
            OrderList(orderContent = orderContent)
        }
    }
}
@Composable
fun TabItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = title,
        fontSize = 16.sp,
        color = if (isSelected) Color(0xFF6200EE) else Color.Gray,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    )
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_agenda),
            contentDescription = "Empty",
            modifier = Modifier.size(64.dp),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Bạn chưa có đơn hàng nào cả",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun OrderList(orderContent: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        orderContent.forEach { order ->
            OrderItem(title = order)
        }
    }
}

@Composable
fun OrderItem(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://via.placeholder.com/100",
            contentDescription = "Order Image",
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}
