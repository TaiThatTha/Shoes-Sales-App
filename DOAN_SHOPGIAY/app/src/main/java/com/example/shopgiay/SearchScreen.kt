package com.example.shopgiay

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }
    val productList = remember { mutableStateListOf<Product>() }
    val filteredProducts = remember { mutableStateListOf<Product>() }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    var showPriceMenu by remember { mutableStateOf(false) } // Trạng thái hiển thị menu Giá
    var showCategoryMenu by remember { mutableStateOf(false) } // Trạng thái hiển thị menu Danh mục
    var selectedCategory by remember { mutableStateOf("Tất cả") } // Danh mục giày đã chọn

    // Retrofit setup
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    // Fetch product list
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
                                price = "₫${productResponse.gia}",
                                imageUrl = "${BASE_API_URL.trimEnd('/')}/${productResponse.anhSanPham.trimStart('/')}",
                                //priceValue = productResponse.gia // Giá dạng số để sắp xếp
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

    // Lọc sản phẩm theo tên tìm kiếm và danh mục
    val filteredByCategory = productList.filter { product ->
        selectedCategory == "Tất cả" || product.name.startsWith(selectedCategory, ignoreCase = true)
    }

    Column {
        // TopAppBar with search bar and buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    // Khi thay đổi searchText, lọc lại filteredProducts theo danh mục và từ khóa
                    filteredProducts.clear()
                    val filteredList = filteredByCategory.filter { product ->
                        product.name.contains(searchText, ignoreCase = true)
                    }
                    filteredProducts.addAll(filteredList)
                },
                placeholder = { Text(text = "Tìm kiếm sản phẩm...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            IconButton(onClick = { println("Tìm kiếm: $searchText") }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Blue
                )
            }
        }

        // Filter and buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            // Danh mục button and dropdown menu
            Box {
                Button(
                    onClick = { showCategoryMenu = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "Danh mục:", color = Color.Black)
                }

                // Dropdown menu for category options
                DropdownMenu(
                    expanded = showCategoryMenu,
                    onDismissRequest = { showCategoryMenu = false }
                ) {
                    listOf("Tất cả", "Nike Air Max", "Nike Blazer", "Air Jordan", "Nike Dunk").forEach { category -> // Sửa tại đây
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                showCategoryMenu = false
                                filteredProducts.clear()

                                // Lọc lại filteredProducts theo danh mục đã chọn
                                val filteredList = productList.filter { product ->
                                    selectedCategory == "Tất cả" || product.name.startsWith(selectedCategory, ignoreCase = true)
                                }

                                // Nếu có từ khóa tìm kiếm, lọc lại theo cả danh mục và từ khóa
                                if (searchText.isNotBlank()) {
                                    filteredProducts.addAll(
                                        filteredList.filter { product ->
                                            product.name.contains(searchText, ignoreCase = true)
                                        }
                                    )
                                } else {
                                    filteredProducts.addAll(filteredList)
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Giới hạn lọc theo giá
            Box {
                Button(
                    onClick = { showPriceMenu = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "Giá", color = Color.Black)
                }

                // Dropdown menu for price options
                DropdownMenu(
                    expanded = showPriceMenu,
                    onDismissRequest = { showPriceMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Tăng dần") },
                        onClick = {
                            showPriceMenu = false
                            //filteredProducts.sortBy { it.priceValue }
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Giảm dần") },
                        onClick = {
                            showPriceMenu = false
                            //filteredProducts.sortByDescending { it.priceValue }
                        }
                    )
                }
            }
        }

        // Product list or loading indicator
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                if (filteredProducts.isEmpty()) {
                    Text(
                        text = if (searchText.isBlank()) "Nhập từ khóa để tìm kiếm." else "Không tìm thấy sản phẩm nào.",
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(filteredProducts) { product ->
                            ProductCard(product = product, navController = navController)
                        }
                    }
                }
            }
        }
    }
}
