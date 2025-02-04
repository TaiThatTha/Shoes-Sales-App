package com.example.shopgiay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun InvoiceDetailScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Tiêu đề
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "Chi tiết hóa đơn",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mã đơn hàng
        Text(
            text = "Mã đơn hàng: HD001",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Thông tin khách hàng
        CustomerInfo(
            name = "Nguyễn Văn A",
            date = "15/12/2024 12:54",
            address = "Số 123, Đường A, Phường 14, Quận Bình Thạnh, TP.HCM",
            phone = "(+84) xxx xxx xxx"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Danh sách sản phẩm
        ProductList()

        Spacer(modifier = Modifier.height(16.dp))

        // Tổng tiền và trạng thái
        SummarySection(
            totalPrice = "6,598,000",
            discount = "0",
            amountDue = "6,598,000",
            status = "Chưa thanh toán"
        )
    }
}

@Composable
fun CustomerInfo(name: String, date: String, address: String, phone: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Customer",
                tint = Color(0xFF6600FF),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = name, fontSize = 14.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.clock),
                contentDescription = "Date",
                tint = Color(0xFF6600FF),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = date, fontSize = 14.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.location),
                contentDescription = "Address",
                tint = Color(0xFF6600FF),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = address,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.phone),
                contentDescription = "Phone",
                tint = Color(0xFF6600FF),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = phone, fontSize = 14.sp, color = Color.Black)
        }
    }
}

@Composable
fun ProductList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        ProductItem(
            name = "Giày Air Jordan 1 Low SE",
            size = "43",
            color = "Xanh rêu",
            price = "3,669,000",
            quantity = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        ProductItem(
            name = "Nike Air Force 1",
            size = "43",
            color = "Trắng",
            price = "2,929,000",
            quantity = 1
        )
    }
}

@Composable
fun ProductItem(name: String, size: String, color: String, price: String, quantity: Int) {
    Column {
        Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(text = "Size: $size", fontSize = 14.sp, color = Color.Gray)
        Text(text = "Màu: $color", fontSize = 14.sp, color = Color.Gray)
        Text(
            text = "$price x $quantity",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
    }
}

@Composable
fun SummarySection(totalPrice: String, discount: String, amountDue: String, status: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tổng tiền hàng", fontSize = 14.sp, color = Color.Black)
            Text(text = totalPrice, fontSize = 14.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Giảm giá HD", fontSize = 14.sp, color = Color.Black)
            Text(text = discount, fontSize = 14.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Khách cần trả",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = amountDue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Trạng thái: $status",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
    }
}
