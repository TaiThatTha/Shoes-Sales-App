package com.example.shopgiay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat

@Composable
fun ContactListScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val productList = remember { mutableStateListOf<Product>() }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    val decimalFormat = DecimalFormat("#,000")
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getProducts().execute()
                if (response.isSuccessful) {
                    response.body()?.let { products ->
                        productList.addAll(products.map { productResponse ->
                            Product(
                                id = productResponse.maSanPham,
                                name = productResponse.tenSanPham,
                                description = productResponse.moTa,
                                price = "${decimalFormat.format(productResponse.gia)}₫",
                                imageUrl = "${BASE_API_URL.trimEnd('/')}/${productResponse.anhSanPham.trimStart('/')}",
                                //priceValue = productResponse.gia
                            )
                        })
                    }
                }
            } catch (e: Exception) {
                println("Exception: ${e.localizedMessage}")
            } finally {
                isLoading = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFCC00FF), Color(0xFF0A051C))
                )
            )
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            if (productList.isEmpty()) {
                Text(
                    text = "Không có sản phẩm nào.",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(productList) { product ->
                        ProductCard(product = product, navController = navController)
                    }
                }
            }
        }
    }
}
