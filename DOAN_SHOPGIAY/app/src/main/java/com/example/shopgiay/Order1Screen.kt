package com.example.shopgiay

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Order1Screen(navController: NavController) {
    val userId = SessionManager.getUserId() ?: run {
        Toast.makeText(navController.context, "Không thể lấy thông tin người dùng. Vui lòng đăng nhập lại.", Toast.LENGTH_LONG).show()
        navController.popBackStack()
        return
    }

    val userName = SessionManager.getUserName() ?: "Tên không khả dụng"
    val userSdt = SessionManager.getuserSdt() ?: "SDT không khả dụng"
    val userDiaChiMacDinh = SessionManager.getuserDiaChiMacDinh() ?: "SDT không khả dụng"



    var selectedShippingOption by remember { mutableStateOf("Nhanh") }
    var selectedPaymentOption by remember { mutableStateOf("Thanh toán khi nhận hàng") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fixed Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
            Text(
                text = "Đặt hàng",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        // Main Content in LazyColumn
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            item {
                // Address section with icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location Icon",
                        tint = Color(0xFF007BFF)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.clickable {
                            navController.navigate("Edit_Address_Screen")
                        }
                    ) {
                        Text(
                            text = userName +" - "+ userSdt,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text =  userDiaChiMacDinh,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                // Product section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.LightGray, RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Giày Air Jordan 1 Low SE",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Xanh rêu, 39",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "₫3,669,000",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
            }

            item {
                // Voucher section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Voucher", fontSize = 16.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    ClickableText(
                        text = AnnotatedString("Chọn hoặc nhập mã"),
                        onClick = { /* Handle click */ },
                        style = TextStyle(color = Color.Blue, fontSize = 14.sp)
                    )
                }
            }

            item {
                // Shipping methods
                Text(
                    text = "Phương thức vận chuyển",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                ShippingOptionWithBox(
                    name = "Nhanh",
                    price = 12000,
                    selected = selectedShippingOption == "Nhanh",
                    onSelect = { selectedShippingOption = "Nhanh" }
                )
                ShippingOptionWithBox(
                    name = "Hỏa tốc",
                    price = 60000,
                    selected = selectedShippingOption == "Hỏa tốc",
                    onSelect = { selectedShippingOption = "Hỏa tốc" }
                )
            }

            item {
                // Total price
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tổng tiền",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "₫3,671,000",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        textAlign = TextAlign.End
                    )
                }
            }

            item {
                // Payment methods
                Column {
                    Text(
                        text = "Phương thức thanh toán",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    PaymentOption(
                        name = "Thanh toán khi nhận hàng",
                        selected = selectedPaymentOption == "Thanh toán khi nhận hàng",
                        onSelect = { selectedPaymentOption = "Thanh toán khi nhận hàng" }
                    )
                    PaymentOption(
                        name = "Thẻ tín dụng",
                        selected = selectedPaymentOption == "Thẻ tín dụng",
                        onSelect = { selectedPaymentOption = "Thẻ tín dụng" }
                    )
                    PaymentOption(
                        name = "ZaloPay",
                        selected = selectedPaymentOption == "ZaloPay",
                        onSelect = { selectedPaymentOption = "ZaloPay" }
                    )
                    PaymentOption(
                        name = "Momo",
                        selected = selectedPaymentOption == "Momo",
                        onSelect = { selectedPaymentOption = "Momo" }
                    )
                }
            }
        }

        // Fixed Bottom Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Tổng (1 mặt hàng)",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "₫3,671,000",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Button(
                onClick = { /* Handle order */ },
                modifier = Modifier
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(
                    text = "Đặt hàng",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun PaymentOption(name: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onSelect),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onSelect)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = name, fontSize = 16.sp)
    }
}

@Composable
fun ShippingOptionWithBox(name: String, price: Int, selected: Boolean, onSelect: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(
                width = 2.dp,
                color = if (selected) Color.Green else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = selected, onClick = onSelect)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = name, fontSize = 16.sp)
                Text(text = "₫${price}", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}