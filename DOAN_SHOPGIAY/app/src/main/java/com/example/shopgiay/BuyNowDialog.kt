package com.example.shopgiay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
@Composable
fun BuyNowDialog(
    product: Product,
    onDismiss: () -> Unit,
    onConfirm: (String, Int) -> Unit,
    navController: NavHostController // Truyền kích thước và số lượng
) {
    var quantity by remember { mutableStateOf(1) }
    var selectedSize by remember { mutableStateOf<String?>(null) }
    var selectedStock by remember { mutableStateOf(0) }
    var variants by remember { mutableStateOf(emptyList<Variant>()) }
    var totalStock by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    // Retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)

    // Fetch variants
    LaunchedEffect(product.id) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getAllBienThe()
                val filteredVariants = response.filter { it.maSanPham == product.id }
                variants = filteredVariants
                totalStock = filteredVariants.sumOf { it.soLuongTon }
            } catch (e: Exception) {
                println("Error fetching variants: ${e.localizedMessage}")
            }
        }
    }

    androidx.compose.ui.window.Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    // Product Info
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = product.imageUrl),
                            contentDescription = "Product Image",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = product.price,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Red
                            )
                            Text(
                                text = "Kho: $totalStock",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Divider(color = Color.LightGray, thickness = 1.dp)

                    // Sizes
                    Column {
                        Text(
                            text = "Size",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val rows = variants.chunked(5) // Chia kích thước thành các dòng
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
                                                    quantity = 1 // Đặt lại số lượng về 1 khi chọn size khác
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

                    // Quantity
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
                                    if (quantity < selectedStock) quantity++ // Giới hạn số lượng
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
                    println("Buy Now: imageUrl=${product.imageUrl}, productName=${product.name}, price=${product.price}, size=$selectedSize, quantity=$quantity")

                    // Confirm Button
                    Button(
                        onClick = {
                            if (!selectedSize.isNullOrEmpty() && quantity > 0) {
                                val encodedImageUrl = URLEncoder.encode(product.imageUrl, StandardCharsets.UTF_8.toString())
                                val encodedProductName = URLEncoder.encode(product.name, StandardCharsets.UTF_8.toString())

                                navController.navigate(
                                    "CheckoutScreen/$encodedImageUrl/$encodedProductName/${product.price}/$selectedSize/$quantity"
                                )
                                onDismiss()
                            } else {
                                println("Thông tin không hợp lệ: size hoặc quantity không được chọn.")
                            }


                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = selectedSize != null
                    ) {
                        Text(
                            text = "MUA NGAY",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//    fun BuyNowDialog(
//    product: Product,
//    onDismiss: () -> Unit,
//    onConfirm: (String, Int) -> Unit,
//    navController: NavHostController // Truyền kích thước và số lượng
//) {
//    var quantity by remember { mutableStateOf(1) }
//    var selectedSize by remember { mutableStateOf<String?>(null) }
//    var selectedStock by remember { mutableStateOf(0) }
//    var variants by remember { mutableStateOf(emptyList<Variant>()) }
//    var totalStock by remember { mutableStateOf(0) }
//    val coroutineScope = rememberCoroutineScope()
//
//
//
//    // Retrofit instance
//    val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_API_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//    val apiService = retrofit.create(ApiService::class.java)
//
//    // Fetch variants
//    LaunchedEffect(product.id) {
//        coroutineScope.launch(Dispatchers.IO) {
//            try {
//                val response = apiService.getAllBienThe()
//                val filteredVariants = response.filter { it.maSanPham == product.id }
//                variants = filteredVariants
//                totalStock = filteredVariants.sumOf { it.soLuongTon }
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
//            Box(modifier = Modifier.fillMaxWidth()) {
//                IconButton(
//                    onClick = onDismiss,
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(8.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "Close",
//                        tint = Color.Black
//                    )
//                }
//
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(16.dp),
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxWidth()
//                ) {
//                    // Product Info
//                    Row(
//                        horizontalArrangement = Arrangement.spacedBy(16.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Image(
//                            painter = rememberAsyncImagePainter(model = product.imageUrl),
//                            contentDescription = "Product Image",
//                            modifier = Modifier
//                                .size(120.dp)
//                                .clip(RoundedCornerShape(12.dp))
//                        )
//                        Column(
//                            verticalArrangement = Arrangement.spacedBy(4.dp),
//                            horizontalAlignment = Alignment.Start
//                        ) {
//                            Text(
//                                text = product.price,
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 18.sp,
//                                color = Color.Red
//                            )
//                            Text(
//                                text = "Kho: $totalStock",
//                                fontSize = 14.sp,
//                                color = Color.Gray
//                            )
//                        }
//                    }
//
//                    Divider(color = Color.LightGray, thickness = 1.dp)
//
//                    // Sizes
//                    Column {
//                        Text(
//                            text = "Size",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp
//                        )
//                        Column(
//                            verticalArrangement = Arrangement.spacedBy(8.dp),
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            val rows = variants.chunked(5) // Chia kích thước thành các dòng
//                            rows.forEach { row ->
//                                Row(
//                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                                    modifier = Modifier.fillMaxWidth()
//                                ) {
//                                    row.forEach { variant ->
//                                        val isDisabled = variant.soLuongTon == 0
//                                        Box(
//                                            modifier = Modifier
//                                                .background(
//                                                    if (isDisabled) Color.LightGray else Color.White,
//                                                    shape = RoundedCornerShape(8.dp)
//                                                )
//                                                .border(
//                                                    width = 2.dp,
//                                                    color = if (selectedSize == variant.kichThuoc) Color.Blue else Color.Gray,
//                                                    shape = RoundedCornerShape(8.dp)
//                                                )
//                                                .padding(horizontal = 12.dp, vertical = 6.dp)
//                                                .clickable(enabled = !isDisabled) {
//                                                    selectedSize = variant.kichThuoc
//                                                    selectedStock = variant.soLuongTon
//                                                    quantity = 1 // Đặt lại số lượng về 1 khi chọn size khác
//                                                }
//                                        ) {
//                                            Text(
//                                                text = variant.kichThuoc,
//                                                fontSize = 14.sp,
//                                                color = if (isDisabled) Color.Gray else Color.Black
//                                            )
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    Divider(color = Color.LightGray, thickness = 1.dp)
//
//                    // Quantity
//                    Column {
//                        Text(
//                            text = "Số lượng",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp,
//                            modifier = Modifier.padding(horizontal = 8.dp)
//                        )
//                        Row(
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            IconButton(
//                                onClick = { if (quantity > 1) quantity-- },
//                                modifier = Modifier
//                                    .background(Color.LightGray, shape = RoundedCornerShape(50))
//                                    .size(36.dp)
//                            ) {
//                                Text(
//                                    text = "-",
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 20.sp,
//                                    color = Color.Black
//                                )
//                            }
//                            Text(
//                                text = "$quantity",
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 18.sp,
//                                modifier = Modifier.padding(horizontal = 16.dp),
//                                color = Color.Black
//                            )
//                            IconButton(
//                                onClick = {
//                                    if (quantity < selectedStock) quantity++ // Giới hạn số lượng
//                                },
//                                modifier = Modifier
//                                    .background(Color.LightGray, shape = RoundedCornerShape(50))
//                                    .size(36.dp)
//                            ) {
//                                Text(
//                                    text = "+",
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 20.sp,
//                                    color = Color.Black
//                                )
//                            }
//                        }
//                    }
//
//                    Divider(color = Color.LightGray, thickness = 1.dp)
//
//                    // Confirm Button
//                    Button(
//                        onClick = {
//                            println("Buy Now: product.id=${product.id}, size=$selectedSize, quantity=$quantity, imageUrl=${product.imageUrl}")
//                            if (!selectedSize.isNullOrEmpty() && quantity > 0) {
//                                val encodedImageUrl = URLEncoder.encode(product.imageUrl, StandardCharsets.UTF_8.toString())
//                                navController.navigate(
//                                    "Order1Screen/${product.id}/${selectedSize}/${quantity}/$encodedImageUrl"
//                                )
//
//                                onDismiss()
//                            } else {
//                                println("Invalid data: size or quantity is empty.")
//                            }
//                        },
//                        colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)),
//                        modifier = Modifier.fillMaxWidth(),
//                        enabled = selectedSize != null
//                    ) {
//                        Text(
//                            text = "MUA NGAY",
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//
//
//
//                }
//            }
//        }
//    }
//}
