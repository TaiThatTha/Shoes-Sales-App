package com.example.shopgiay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@Composable
fun AddToCartDialog(
    product: Product,
    onDismiss: () -> Unit,
    onConfirm: (String, Int, String) -> Unit // Thêm imageUrl vào callback
) {
    var quantity by remember { mutableStateOf(1) }
    var selectedSize by remember { mutableStateOf<String?>(null) }
    var selectedStock by remember { mutableStateOf(0) }
    var variants by remember { mutableStateOf(emptyList<Variant>()) }
    val coroutineScope = rememberCoroutineScope()

    // Retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)

    // Lấy danh sách kích thước và tồn kho
    LaunchedEffect(product.id) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getAllBienThe()
                variants = response.filter { it.maSanPham == product.id }
            } catch (e: Exception) {
                println("Error fetching variants: ${e.localizedMessage}")
            }
        }
    }

    // Dialog UI
    androidx.compose.ui.window.Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Nút đóng
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.Black
                        )
                    }
                }

                // Thông tin sản phẩm
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = product.imageUrl),
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Column {
                        Text(
                            text = product.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Text(
                            text = product.price,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    }
                }

                Divider(color = Color.LightGray, thickness = 1.dp)

                // Lựa chọn kích thước
                Column {
                    Text(text = "Size", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val rows = variants.chunked(5)
                        rows.forEach { row ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                row.forEach { variant ->
                                    val isDisabled = variant.soLuongTon == 0
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                if (isDisabled) Color.LightGray else Color.White,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .border(
                                                width = 2.dp,
                                                color = if (selectedSize == variant.kichThuoc) Color.Blue else Color.Gray,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                            .clickable(enabled = !isDisabled) {
                                                selectedSize = variant.kichThuoc
                                                selectedStock = variant.soLuongTon
                                                quantity = 1 // Reset số lượng khi chọn size mới
                                            }
                                    ) {
                                        Text(
                                            text = variant.kichThuoc,
                                            fontSize = 14.sp,
                                            color = if (isDisabled) Color.Gray else Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Divider(color = Color.LightGray, thickness = 1.dp)

                // Lựa chọn số lượng
                Column {
                    Text(
                        text = "Số lượng",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            modifier = Modifier
                                .background(Color.LightGray, shape = RoundedCornerShape(50))
                                .size(36.dp)
                        ) {
                            Text(
                                text = "-",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                        Text(
                            text = "$quantity",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.Black
                        )
                        IconButton(
                            onClick = {
                                if (quantity < selectedStock) quantity++
                            },
                            modifier = Modifier
                                .background(Color.LightGray, shape = RoundedCornerShape(50))
                                .size(36.dp)
                        ) {
                            Text(
                                text = "+",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                    }
                }

                Divider(color = Color.LightGray, thickness = 1.dp)

                // Nút xác nhận
                Button(
                    onClick = {
                        if (selectedSize != null) {
                            onConfirm(selectedSize!!, quantity, product.imageUrl)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedSize != null
                ) {
                    Text("Thêm vào giỏ")
                }
            }
        }
    }
}


//@Composable
//fun AddToCartDialog(
//    product: Product,
//    onDismiss: () -> Unit,
//    onConfirm: (String, Int) -> Unit // Truyền kích thước và số lượng
//) {
//    var quantity by remember { mutableStateOf(1) }
//    var selectedSize by remember { mutableStateOf<String?>(null) }
//    var selectedStock by remember { mutableStateOf(0) } // Số lượng tồn kho của size được chọn
//    var variants by remember { mutableStateOf(emptyList<Variant>()) } // Danh sách size và tồn kho
//    val coroutineScope = rememberCoroutineScope()
//
//    // Retrofit instance
//    val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_API_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//    val apiService = retrofit.create(ApiService::class.java)
//
//    // Fetch size and stock data
//    LaunchedEffect(product.id) {
//        coroutineScope.launch(Dispatchers.IO) {
//            try {
//                val response = apiService.getAllBienThe()
//                variants = response.filter { it.maSanPham == product.id }
//            } catch (e: Exception) {
//                println("Error fetching variants: ${e.localizedMessage}")
//            }
//        }
//    }
//
//    androidx.compose.ui.window.Dialog(
//        onDismissRequest = { onDismiss() }
//    ) {
//        Surface(
//            shape = RoundedCornerShape(12.dp),
//            color = Color.White,
//            modifier = Modifier.fillMaxWidth(0.95f)
//        ) {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//            ) {
//                // Close button
//                Box(
//                    modifier = Modifier.fillMaxWidth(),
//                    contentAlignment = Alignment.TopEnd
//                ) {
//                    IconButton(onClick = onDismiss) {
//                        Icon(
//                            imageVector = Icons.Default.Close,
//                            contentDescription = "Close",
//                            tint = Color.Black
//                        )
//                    }
//                }
//
//                // Product information
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painter = rememberAsyncImagePainter(model = product.imageUrl),
//                        contentDescription = "Product Image",
//                        modifier = Modifier
//                            .size(120.dp)
//                            .clip(RoundedCornerShape(12.dp))
//                    )
//                    Column {
//                        Text(
//                            text = product.name,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 18.sp,
//                            color = Color.Black
//                        )
//                        Text(
//                            text = product.price,
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp,
//                            color = Color.Red
//                        )
//                    }
//                }
//
//                Divider(color = Color.LightGray, thickness = 1.dp)
//
//                // Size selection
//                Column {
//                    Text(text = "Size", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//                    Column(
//                        verticalArrangement = Arrangement.spacedBy(8.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        val rows = variants.chunked(5) // Chia thành các dòng
//                        rows.forEach { row ->
//                            Row(
//                                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                row.forEach { variant ->
//                                    val isDisabled = variant.soLuongTon == 0
//                                    Box(
//                                        modifier = Modifier
//                                            .background(
//                                                if (isDisabled) Color.LightGray else Color.White,
//                                                shape = RoundedCornerShape(8.dp)
//                                            )
//                                            .border(
//                                                width = 2.dp,
//                                                color = if (selectedSize == variant.kichThuoc) Color.Blue else Color.Gray,
//                                                shape = RoundedCornerShape(8.dp)
//                                            )
//                                            .padding(horizontal = 12.dp, vertical = 6.dp)
//                                            .clickable(enabled = !isDisabled) {
//                                                selectedSize = variant.kichThuoc
//                                                selectedStock = variant.soLuongTon
//                                                quantity = 1 // Reset quantity về 1 khi chọn size mới
//                                            }
//                                    ) {
//                                        Text(
//                                            text = variant.kichThuoc,
//                                            fontSize = 14.sp,
//                                            color = if (isDisabled) Color.Gray else Color.Black
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                Divider(color = Color.LightGray, thickness = 1.dp)
//
//                // Quantity selection
//                Column {
//                    Text(
//                        text = "Số lượng",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp,
//                        modifier = Modifier.padding(horizontal = 8.dp)
//                    )
//                    Row(
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        IconButton(
//                            onClick = { if (quantity > 1) quantity-- },
//                            modifier = Modifier
//                                .background(Color.LightGray, shape = RoundedCornerShape(50))
//                                .size(36.dp)
//                        ) {
//                            Text(
//                                text = "-",
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 20.sp,
//                                color = Color.Black
//                            )
//                        }
//                        Text(
//                            text = "$quantity",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 18.sp,
//                            modifier = Modifier.padding(horizontal = 16.dp),
//                            color = Color.Black
//                        )
//                        IconButton(
//                            onClick = {
//                                if (quantity < selectedStock) quantity++ // Giới hạn số lượng
//                            },
//                            modifier = Modifier
//                                .background(Color.LightGray, shape = RoundedCornerShape(50))
//                                .size(36.dp)
//                        ) {
//                            Text(
//                                text = "+",
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 20.sp,
//                                color = Color.Black
//                            )
//                        }
//                    }
//                }
//
//                Divider(color = Color.LightGray, thickness = 1.dp)
//
//                // Confirm button
//                Button(
//                    onClick = {
//                        if (selectedSize != null) {
//                            onConfirm(selectedSize!!, quantity)
//                        }
//                    },
//                    colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)),
//                    modifier = Modifier.fillMaxWidth(),
//                    enabled = selectedSize != null
//                ) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Icon(
//                            imageVector = Icons.Default.ShoppingCart,
//                            contentDescription = "Add to Cart",
//                            tint = Color.White,
//                            modifier = Modifier.size(20.dp)
//                        )
//                        Spacer(modifier = Modifier.width(4.dp))
//                        Text(
//                            text = "Thêm vào giỏ",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
