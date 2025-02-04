package com.example.shopgiay

import android.service.autofill.OnClickAction
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.navigation.NavController

data class OrderItem(
    val name: String,
    val price: String,
    val quantity: Int,
    val size: String,
    val color: String,
    val imageRes: Int,
    var invoiceCode: String? = null, // Mã hóa đơn
    var status: OrderStatus = OrderStatus.PENDING // Trạng thái đơn hàng
)

enum class OrderStatus {
    PENDING, APPROVED, PACKING, SHIPPING, DELIVERED, PAID // Các trạng thái
}
@Composable
fun AdReceiptScreen(navController: NavHostController) {
    // Biến lưu tab được chọn
    val selectedTab = remember { mutableStateOf(0) }

    // Danh sách đơn hàng
    val orders = remember {
        mutableStateListOf(
            OrderItem("Nike Air Force 1", "₫2,929,000", 2, "43", "Trắng", R.drawable.dj2),
            OrderItem("Nike Air Force 1", "₫2,929,000", 2, "43", "Trắng", R.drawable.dj2),
            OrderItem("Nike Air Force 1", "₫2,929,000", 2, "43", "Trắng", R.drawable.dj2),
            OrderItem("Nike Air Force 1", "₫2,929,000", 2, "43", "Trắng", R.drawable.dj2),
            OrderItem("Nike Air Force 1", "₫2,929,000", 2, "43", "Trắng", R.drawable.dj2),
            OrderItem("Adidas Ultraboost", "₫3,500,000", 1, "42", "Đen", R.drawable.dj3)
        )
    }
    var invoiceCounter = remember { mutableStateOf(1) } // Biến đếm mã hóa đơn

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6600FF), Color(0xFF1C0524))
                )
            )
    ) {
        // Tiêu đề
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = "Danh sách đơn",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tabs với icon
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val tabs = listOf(
                "Chưa duyệt" to R.drawable.clipboard,
                "Đã duyệt" to R.drawable.list,
                "Đóng hàng" to R.drawable.box,
                "Giao hàng" to R.drawable.car,
                "Đã giao" to R.drawable.checkmark,
                "Đã hủy" to R.drawable.file
            )
            itemsIndexed(tabs) { index, (label, icon) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { selectedTab.value = index } // Cập nhật tab khi nhấn
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = label,
                        modifier = Modifier.size(36.dp),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                            if (selectedTab.value == index) Color(0xFF6600FF) else Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        color = if (selectedTab.value == index) Color(0xFF6600FF) else Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị danh sách đơn theo trạng thái
        val filteredOrders = orders.filter { it.status == when (selectedTab.value) {
            0 -> OrderStatus.PENDING
            1 -> OrderStatus.APPROVED
            2 -> OrderStatus.PACKING
            3 -> OrderStatus.SHIPPING
            4 -> OrderStatus.DELIVERED
            else -> OrderStatus.PENDING
        } }

        if (filteredOrders.isEmpty()) {
            Text(
                text = "Không có đơn hàng nào.",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredOrders) { order ->
                    OrderCard(
                        order = order,
                        onApprove = if (order.status == OrderStatus.PENDING) {
                            {
                                val index = orders.indexOf(order)
                                if (index != -1) {
                                    orders[index] = order.copy(
                                        status = OrderStatus.APPROVED,
                                        invoiceCode = "HD${String.format("%03d", invoiceCounter.value)}"
                                    )
                                    invoiceCounter.value += 1
                                }
                            }
                        } else null,
                        onPack = if (order.status == OrderStatus.APPROVED) {
                            {
                                val index = orders.indexOf(order)
                                if (index != -1) {
                                    orders[index] = order.copy(status = OrderStatus.PACKING)
                                }
                            }
                        } else null,
                        onShip = if (order.status == OrderStatus.PACKING) {
                            {
                                val index = orders.indexOf(order)
                                if (index != -1) {
                                    orders[index] = order.copy(status = OrderStatus.SHIPPING)
                                }
                            }
                        } else null,
                        onDeliver = if (order.status == OrderStatus.SHIPPING) {
                            {
                                val index = orders.indexOf(order)
                                if (index != -1) {
                                    orders[index] = order.copy(status = OrderStatus.DELIVERED)
                                }
                            }
                        } else null,
                        navController = navController // Thêm navController để điều hướng
                    )
                }
            }
        }
    }
}

//@Composable
//fun AdReceiptScreen(navController: NavHostController) {
//    // Biến lưu tab được chọn
//    val selectedTab = remember { mutableStateOf(0) }
//
//    // Danh sách đơn hàng
//    val orders = remember {
//        mutableStateListOf(
//            OrderItem("Nike Air Force 1", "₫2,929,000", 2, "43", "Trắng", R.drawable.dj2),
//            OrderItem("Nike Air Force 1", "₫2,929,000", 2, "43", "Trắng", R.drawable.dj2),
//            OrderItem("Nike Air Force 1", "₫2,929,000", 2, "43", "Trắng", R.drawable.dj2),
//            OrderItem("Nike Air Force 1", "₫2,929,000", 2, "43", "Trắng", R.drawable.dj2),
//            OrderItem("Nike Air Force 1", "₫2,929,000", 2, "43", "Trắng", R.drawable.dj2),
//            OrderItem("Adidas Ultraboost", "₫3,500,000", 1, "42", "Đen", R.drawable.dj3)
//        )
//    }
//    var invoiceCounter = remember { mutableStateOf(1) } // Biến đếm mã hóa đơn
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Brush.verticalGradient(
//                    colors = listOf(Color(0xFF6600FF), Color(0xFF1C0524))
//                )
//            )
//    ) {
//        // Tiêu đề
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = { navController.popBackStack() }) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBack,
//                    contentDescription = "Back",
//                    tint = Color.White
//                )
//            }
//            Text(
//                text = "Danh sách đơn",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.White
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Tabs với icon
//        LazyRow(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.White, shape = RoundedCornerShape(8.dp))
//                .padding(8.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            val tabs = listOf(
//                "Chưa duyệt" to R.drawable.clipboard,
//                "Đã duyệt" to R.drawable.list,
//                "Đóng hàng" to R.drawable.box,
//                "Giao hàng" to R.drawable.car,
//                "Đã giao" to R.drawable.checkmark,
//                "Đã hủy" to R.drawable.file
//            )
//            itemsIndexed(tabs) { index, (label, icon) ->
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .padding(horizontal = 8.dp)
//                        .clickable { selectedTab.value = index } // Cập nhật tab khi nhấn
//                ) {
//                    Image(
//                        painter = painterResource(id = icon),
//                        contentDescription = label,
//                        modifier = Modifier.size(36.dp),
//                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
//                            if (selectedTab.value == index) Color(0xFF6600FF) else Color.Gray
//                        )
//                    )
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Text(
//                        text = label,
//                        fontSize = 12.sp,
//                        color = if (selectedTab.value == index) Color(0xFF6600FF) else Color.Gray
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Hiển thị danh sách đơn theo trạng thái
//        val filteredOrders = orders.filter { it.status == when (selectedTab.value) {
//            0 -> OrderStatus.PENDING
//            1 -> OrderStatus.APPROVED
//            2 -> OrderStatus.PACKING
//            3 -> OrderStatus.SHIPPING
//            4 -> OrderStatus.DELIVERED
//            else -> OrderStatus.PENDING
//        } }
//
//        if (filteredOrders.isEmpty()) {
//            Text(
//                text = "Không có đơn hàng nào.",
//                fontSize = 16.sp,
//                color = Color.White,
//                modifier = Modifier.padding(16.dp)
//            )
//        } else {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 8.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(filteredOrders) { order ->
//                    OrderCard(
//                        order = order,
//                        onApprove = if (order.status == OrderStatus.PENDING) {
//                            {
//                                val index = orders.indexOf(order)
//                                if (index != -1) {
//                                    orders[index] = order.copy(
//                                        status = OrderStatus.APPROVED,
//                                        invoiceCode = "HD${String.format("%03d", invoiceCounter.value)}"
//                                    )
//                                    invoiceCounter.value += 1
//                                }
//                            }
//                        } else null,
//                        onPack = if (order.status == OrderStatus.APPROVED) {
//                            {
//                                val index = orders.indexOf(order)
//                                if (index != -1) {
//                                    orders[index] = order.copy(status = OrderStatus.PACKING)
//                                }
//                            }
//                        } else null,
//                        onShip = if (order.status == OrderStatus.PACKING) {
//                            {
//                                val index = orders.indexOf(order)
//                                if (index != -1) {
//                                    orders[index] = order.copy(status = OrderStatus.SHIPPING)
//                                }
//                            }
//                        } else null,
//                        onDeliver = if (order.status == OrderStatus.SHIPPING) {
//                            {
//                                val index = orders.indexOf(order)
//                                if (index != -1) {
//                                    orders[index] = order.copy(status = OrderStatus.DELIVERED)
//                                }
//                            }
//                        } else null
//                    )
//                }
//            }
//        }
//    }
//}

@Composable
fun OrderCard(
    order: OrderItem,
    onApprove: (() -> Unit)? = null,
    onPack: (() -> Unit)? = null,
    onShip: (() -> Unit)? = null,
    onDeliver: (() -> Unit)? = null,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF6600FF), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = order.imageRes),
                contentDescription = order.name,
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        println("Navigating to ADRECEIPT")
                        navController.navigate(Navitem.ADVOICE.route)
                    }
            )

//            Image(
//                painter = painterResource(id = order.imageRes),
//                contentDescription = order.name,
//                modifier = Modifier
//                    .size(80.dp)
//                    .clickable { navController.navigate(Navitem.ADRECEIPT.route) } // Điều hướng khi nhấn vào ảnh
//            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Navitem.ADVOICE.route)
                    }
            ){
//            Column(
//                modifier = Modifier.clickable { navController.navigate(Navitem.ADRECEIPT.route) } // Điều hướng khi nhấn vào tên
//            ) {
                Text(
                    text = order.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = order.price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Text(
                    text = "Số lượng: ${order.quantity}, size: ${order.size}, màu: ${order.color}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                if (order.invoiceCode != null) {
                    Text(
                        text = "Mã hóa đơn: ${order.invoiceCode}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6600FF),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            when (order.status) {
                OrderStatus.PENDING -> {
                    Button(
                        onClick = { onApprove?.invoke() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6600FF)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Duyệt đơn hàng", fontSize = 14.sp, color = Color.White)
                    }
                }
                OrderStatus.APPROVED -> {
                    Button(
                        onClick = { onPack?.invoke() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6600FF)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Tạo đơn", fontSize = 14.sp, color = Color.White)
                    }
                }
                OrderStatus.PACKING -> {
                    Button(
                        onClick = { onShip?.invoke() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6600FF)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Giao hàng", fontSize = 14.sp, color = Color.White)
                    }
                }
                OrderStatus.SHIPPING -> {
                    Button(
                        onClick = { onDeliver?.invoke() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6600FF)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Đã nhận", fontSize = 14.sp, color = Color.White)
                    }
                }
                OrderStatus.DELIVERED -> {
                    Text(
                        text = "Đã Thanh toán",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                else -> {}
            }
        }
    }
}

//
//@Composable
//fun OrderCard(
//
//    order: OrderItem,
//    onApprove: (() -> Unit)? = null,
//    onPack: (() -> Unit)? = null,
//    onShip: (() -> Unit)? = null,
//    onDeliver: (() -> Unit)? = null
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White, shape = RoundedCornerShape(12.dp))
//            .border(1.dp, Color(0xFF6600FF), shape = RoundedCornerShape(12.dp))
//            .padding(12.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(// HINH SAN PHAM
//
//                painter = painterResource(id = order.imageRes),
//                contentDescription = order.name,
//                modifier = Modifier.size(80.dp)
//
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Column {
//                Text( // TEN SAN PHAM
//                    text = order.name,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                )
//                Text(
//                    text = order.price,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Red
//                )
//                Text(
//                    text = "Số lượng: ${order.quantity}, size: ${order.size}, màu: ${order.color}",
//                    fontSize = 12.sp,
//                    color = Color.Gray
//                )
//                if (order.invoiceCode != null) {
//                    Text(
//                        text = "Mã hóa đơn: ${order.invoiceCode}",
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF6600FF),
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Nút hành động
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            when (order.status) {
//                OrderStatus.PENDING -> {
//                    Button(
//                        onClick = { onApprove?.invoke() },
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6600FF)),
//                        shape = RoundedCornerShape(8.dp),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text("Duyệt đơn hàng", fontSize = 14.sp, color = Color.White)
//                    }
//                }
//                OrderStatus.APPROVED -> {
//                    Button(
//                        onClick = { onPack?.invoke() },
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6600FF)),
//                        shape = RoundedCornerShape(8.dp),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text("Tạo đơn", fontSize = 14.sp, color = Color.White)
//                    }
//                }
//                OrderStatus.PACKING -> {
//                    Button(
//                        onClick = { onShip?.invoke() },
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6600FF)),
//                        shape = RoundedCornerShape(8.dp),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text("Giao hàng", fontSize = 14.sp, color = Color.White)
//                    }
//                }
//                OrderStatus.SHIPPING -> {
//                    Button(
//                        onClick = { onDeliver?.invoke() },
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6600FF)),
//                        shape = RoundedCornerShape(8.dp),
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text("Đã nhận", fontSize = 14.sp, color = Color.White)
//                    }
//                }
//                OrderStatus.DELIVERED -> {
//                    Text(
//                        text = "Đã Thanh toán",
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Green,
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
//                }
//                else -> {}
//            }
//        }
//    }
//}
