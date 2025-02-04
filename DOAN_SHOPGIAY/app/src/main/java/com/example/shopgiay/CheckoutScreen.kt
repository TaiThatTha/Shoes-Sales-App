package com.example.shopgiay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CheckoutScreen(
    navController: NavHostController,
    imageUrl: String,
    productName: String,
    price: String,
    size: String,
    quantity: Int
) {
    println("CheckoutScreen: imageUrl=$imageUrl, productName=$productName, price=$price, size=$size, quantity=$quantity")

    val userName = SessionManager.getUserName() ?: "Tên không khả dụng"
    val userEmail = SessionManager.getUserEmail() ?: "Email không khả dụng"
    val userPassword = SessionManager.getuserPassword() ?: "Password không khả dụng"
    val userSdt = SessionManager.getuserSdt() ?: "SDT không khả dụng"
    val userDiaChiMacDinh = SessionManager.getuserDiaChiMacDinh() ?: "SDT không khả dụng"
    val getVaiTro = SessionManager.getUserRole() ?: "Vai trò không khả dụng"
    val getUserId= SessionManager.getUserId()
    // Kiểm tra dữ liệu đầu vào
    if (imageUrl.isEmpty() || productName.isEmpty() || price.isEmpty() || size.isEmpty() || quantity <= 0) {
        Text(text = "Dữ liệu không hợp lệ. Vui lòng thử lại.", color = Color.Red)
        return
    }

    // Chuyển đổi giá sản phẩm
    val cleanedPrice = price.replace("[^\\d]".toRegex(), "") // Chỉ giữ lại các chữ số
    val productPrice = cleanedPrice.toIntOrNull() ?: 0
    if (productPrice <= 0) {
        Text(text = "Giá sản phẩm không hợp lệ.", color = Color.Red)
        return
    }
    println("ProductPrice (converted): $productPrice") // Kiểm tra giá sản phẩm

    // Trạng thái lựa chọn phương thức vận chuyển
    var selectedShippingMethod by remember { mutableStateOf("Nhanh") }
    val shippingCost = remember(selectedShippingMethod) {
        if (selectedShippingMethod == "Nhanh") 12000 else 60000
    }
    println("ShippingCost: $shippingCost") // Kiểm tra phí vận chuyển

    // Tính tổng tiền
    val totalAmount = productPrice * quantity + shippingCost
    println("TotalAmount: $totalAmount") // Kiểm tra tổng tiền

    // Định dạng tổng tiền
    val formattedTotal = String.format("%,d", totalAmount).replace(",", ".")
    println("Formatted Total: $formattedTotal") // Kiểm tra tổng tiền định dạng

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF007BFF))
            }
            Text(
                text = "Thông tin đặt hàng",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        // Thông tin người nhận
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.clickable{navController.navigate("Edit_Address_Screen")}) {
                Text(text = userName+" - "+userSdt, fontWeight = FontWeight.Bold,color = Color.Black, fontSize = 14.sp)
                Text(text = userDiaChiMacDinh+"", color = Color.Gray, fontSize = 12.sp)
            }
        }

        Divider()

        // Sản phẩm
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = productName, fontWeight = FontWeight.Bold,color = Color.Black, fontSize = 16.sp)
                Text(text = "Xanh rêu, Size: $size", color = Color.Gray, fontSize = 12.sp)
                Text(text = "${price}đ", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = "Số lượng: $quantity", color = Color.Black, fontSize = 12.sp)
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Chọn hoặc nhập mã", color = Color.Gray) },
                modifier = Modifier.weight(1f)
            )
        }

        // Ghi chú
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Để lại lời nhắn", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Phương thức vận chuyển
        Column {
            Text(text = "Phương thức vận chuyển", fontWeight = FontWeight.Bold, color = Color.Black,fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (selectedShippingMethod == "Nhanh") Color.LightGray else Color.White,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = selectedShippingMethod == "Nhanh",
                    onClick = { selectedShippingMethod = "Nhanh" }
                )
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(text = "Nhanh - 12,000đ", fontSize = 12.sp,color = Color.Black, fontWeight = FontWeight.Bold)
                    Text(text = "Nhận hàng vào ngày mai", fontSize = 10.sp, color = Color.Gray)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (selectedShippingMethod == "Hỏa tốc") Color.LightGray else Color.White,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = selectedShippingMethod == "Hỏa tốc",
                    onClick = { selectedShippingMethod = "Hỏa tốc" }
                )
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(text = "Hỏa tốc - 60,000đ", fontSize = 12.sp, color = Color.Black,fontWeight = FontWeight.Bold)
                    Text(text = "Đảm bảo nhận vào ngày mai", fontSize = 10.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Divider()
        Column {
            Text(text = "Phương thức thanh toán", fontWeight = FontWeight.Bold,color = Color.Black, fontSize = 14.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = true, onClick = { /* Handle payment method */ })
                Text(text = "Thanh toán khi nhận hàng", modifier = Modifier.padding(start = 8.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Divider()
        // Tổng tiền
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tổng tiền:", fontWeight = FontWeight.Bold, color = Color.Black,fontSize = 14.sp)
            Text(
                text = "$formattedTotal đ",
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Nút đặt hàng
        Button(
            onClick = {
                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val orderRequest = OrderRequest(
                    maDonHang = 1, // ID đơn hàng giả định, bạn có thể thay bằng ID thực
                    maNguoiDung = getUserId ?: 0,
                    diaChiGiaoHang = userDiaChiMacDinh,
                    tongTien = totalAmount.toDouble(),
                    trangThai = "Chưa duyệt", // Trạng thái đơn hàng
                    ngayTao = currentDate // Ngày tạo, có thể thay bằng ngày hiện tại
                )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Gọi API để tạo đơn hàng
                        val response = RetrofitInstance.api.createOrder(orderRequest)
                        withContext(Dispatchers.Main) {
                            if (response.success) {
                                println("Đơn hàng được tạo thành công: ${response.message}")
                                navController.navigate("OrderSuccessScreen")
                            } else {
                                println("Tạo đơn hàng thất bại: ${response.message}")
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            println("Lỗi khi gọi API: ${e.message}")
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7))
        ) {
            Text(text = "ĐẶT HÀNG", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}
