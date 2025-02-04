package com.example.shopgiay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
val pagerState = PagerState()
@OptIn(ExperimentalPagerApi::class)

@Composable
fun ProductDetailScreen(
    product: Product,
    navController: NavHostController
) {
    var showDialog by remember { mutableStateOf(false) }
    var showAddToCartDialog by remember { mutableStateOf(false) }
    var imageUrls by remember { mutableStateOf(listOf(product.imageUrl)) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)

    // Fetch additional images
//    LaunchedEffect(product.id) {
//        scope.launch(Dispatchers.IO) {
//            try {
//                val response = apiService.getDanhSachAnhBienThe()
//                val additionalImages = response
//                    .filter { it.maSanPham == product.id }
//                    .map { BASE_API_URL + it.duongDan }
//                imageUrls = listOf(product.imageUrl) + additionalImages
//            } catch (e: Exception) {
//                println("Error fetching images: ${e.localizedMessage}")
//            } finally {
//                isLoading = false
//            }
//        }
//    }
    LaunchedEffect(product.id) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getDanhSachAnhBienThe()
                val additionalImages = response
                    .filter { it.maSanPham == product.id }
                    .map { BASE_API_URL + it.duongDan }
                if (additionalImages.isNotEmpty()) {
                    imageUrls = listOf(product.imageUrl) + additionalImages
                } else {
                    println("Không tìm thấy ảnh bổ sung.")
                }
            } catch (e: Exception) {
                println("Lỗi khi tải ảnh: ${e.localizedMessage}")
            } finally {
                isLoading = false
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // App bar
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
                text = "Chi Tiết Sản Phẩm",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        // Image slider
        if (isLoading) {
            Text(
                text = "Đang tải ảnh...",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        } else if (imageUrls.isEmpty()) {
            Text(
                text = "Không có ảnh nào.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        } else {
            Box {
                HorizontalPager(
                    count = imageUrls.size,
                    state = PagerState(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) { page ->
                    Image(
                        painter = rememberAsyncImagePainter(imageUrls[page]),
                        contentDescription = "Product Image $page",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                HorizontalPagerIndicator(
                    pagerState = PagerState(),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    activeColor = Color.Black,
                    inactiveColor = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product details
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = product.name.ifEmpty { "Tên sản phẩm không khả dụng" },
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = product.price.ifEmpty { "Giá không khả dụng" },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = product.description.ifEmpty { "Mô tả không khả dụng" },
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Bottom bar
        ProductDetailBottomBar(
            onChatClick = {
                // Logic khi nhấn nút Chat
            },
            onCartClick = {
                showAddToCartDialog = true
            },
            onBuyNowClick = {
                showDialog = true
            }
        )
    }

    // Dialogs
    if (showAddToCartDialog) {
        AddToCartDialog(
            product = product,
            onDismiss = { showAddToCartDialog = false },
            onConfirm = { size, quantity, imageUrl ->
                println("Thêm vào giỏ: Size $size, Số lượng $quantity, Ảnh $imageUrl")
                showAddToCartDialog = false
            }
        )
    }

    if (showDialog) {
        BuyNowDialog(
            product = product,
            onDismiss = { showDialog = false },
            onConfirm = { size, quantity ->
                if (size.isNotEmpty() && quantity > 0) {
                    navController.navigate(
                        "CheckoutScreen/${product.imageUrl}/${product.name}/${product.price}/$size/$quantity"
                    )
                } else {
                    println("Thông tin không hợp lệ: size hoặc số lượng không chính xác.")
                }
                showDialog = false
            },
            navController = navController
        )
    }




}

//@Composable
//fun ProductDetailScreen(
//    product: Product,
//    navController: NavHostController,
//
//) {
//    var showDialog by remember { mutableStateOf(false) }
//    var showAddToCartDialog by remember { mutableStateOf(false) }
//    var imageUrls by remember { mutableStateOf(listOf(product.imageUrl)) }
//    var isLoading by remember { mutableStateOf(true) }
//    val scope = rememberCoroutineScope()
//
//    // Retrofit instance
//    val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_API_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//    val apiService = retrofit.create(ApiService::class.java)
//
//    // Fetch additional images
//    LaunchedEffect(product.id) {
//        scope.launch(Dispatchers.IO) {
//            try {
//                val response = apiService.getDanhSachAnhBienThe()
//                val additionalImages = response
//                    .filter { it.maSanPham == product.id }
//                    .map { BASE_API_URL + it.duongDan }
//                imageUrls = listOf(product.imageUrl) + additionalImages
//            } catch (e: Exception) {
//                println("Error fetching images: ${e.localizedMessage}")
//            } finally {
//                isLoading = false
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        // App bar
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = { navController.popBackStack() }) {
//                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
//            }
//            Text(
//                text = "Chi Tiết Sản Phẩm",
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                color = Color.Black
//            )
//        }
//
//        // Image slider
//        if (isLoading) {
//            Text(
//                text = "Đang tải ảnh...",
//                fontSize = 16.sp,
//                color = Color.Gray,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp),
//                textAlign = androidx.compose.ui.text.style.TextAlign.Center
//            )
//        } else if (imageUrls.isEmpty()) {
//            Text(
//                text = "Không có ảnh nào.",
//                fontSize = 16.sp,
//                color = Color.Gray,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp),
//                textAlign = androidx.compose.ui.text.style.TextAlign.Center
//            )
//        } else Box{
//            HorizontalPager(
//                count = imageUrls.size, // Sử dụng `count` khi dùng Accompanist
//                state = pagerState,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//            ) { page ->
//                Image(
//                    painter = rememberAsyncImagePainter(imageUrls[page]),
//                    contentDescription = "Product Image $page",
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//            HorizontalPagerIndicator(
//                pagerState = pagerState,
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(16.dp),
//                activeColor = Color.Black,
//                inactiveColor = Color.Gray
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Product details
//        Column(
//            modifier = Modifier
//                .weight(1f)
//                .verticalScroll(rememberScrollState())
//                .padding(horizontal = 16.dp)
//        ) {
//            Text(
//                text = product.name.ifEmpty { "Tên sản phẩm không khả dụng" },
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                color = Color.Black,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//
//            Text(
//                text = product.price.ifEmpty { "Giá không khả dụng" },
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Red,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//            Text(
//                text = product.description.ifEmpty { "Mô tả" },
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Gray,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//
//        // Bottom bar
//        ProductDetailBottomBar(
//            onChatClick = {
//                // Logic khi nhấn nút Chat
//            },
//            onCartClick = {
//                showAddToCartDialog = true // Logic khi nhấn nút Thêm vào giỏ hàng
//            },
//            onBuyNowClick = {
//                showDialog = true // Logic khi nhấn nút Mua ngay
//            }
//        )
//    }
//
//    // Dialogs
//    if (showAddToCartDialog) {
//        AddToCartDialog(
//            product = selectedProduct!!,
//            onDismiss = { showAddToCartDialog = false },
//            onConfirm = { size, quantity, imageUrl ->
//                cartItems = cartItems + CartItem(
//                    name = selectedProduct!!.name,
//                    details = "Size $size",
//                    price = selectedProduct!!.price.toInt(),
//                    quantity = quantity,
//                    imageUrl = imageUrl
//                )
//                showAddToCartDialog = false
//            }
//        )
//
//    }
//
//    // Buy Now Dialog
//    if (showDialog) {
//        BuyNowDialog(
//            product = product,
//            onDismiss = { showDialog = false },
//            onConfirm = { size, quantity ->
//                println("Mua ngay: Size $size, Số lượng $quantity")
//                showDialog = false
//            }
//        )
//    }
//}
