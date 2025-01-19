
package com.example.shopgiay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.text.DecimalFormat

// Dữ liệu sản phẩm trong giỏ hàng
data class CartItem(
    val name: String,
    val details: String, // Ví dụ: "Size 39"
    val price: Int,
    val quantity: Int,
    val imageUrl: String
)

val decimalFormat = DecimalFormat("#,###")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(navController: NavController) {
    var cartItems by remember {
        mutableStateOf(
            mutableListOf<CartItem>()
        )
    }
    var selectedItems by remember {
        mutableStateOf(List(cartItems.size) { false })
    }

    // Tổng số tiền
    val totalPrice = cartItems
        .filterIndexed { index, _ -> selectedItems.getOrElse(index) { false } }
        .sumOf { it.price * it.quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Giỏ hàng",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        // Danh sách sản phẩm
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            cartItems.forEachIndexed { index, item ->
                CartItemRow(
                    item = item,
                    isChecked = selectedItems.getOrElse(index) { false },
                    onCheckedChange = { isChecked ->
                        selectedItems = selectedItems.toMutableList().apply {
                            this[index] = isChecked
                        }
                    },
                    onDelete = {
                        cartItems = cartItems.toMutableList().apply {
                            removeAt(index)
                        }
                        selectedItems = selectedItems.toMutableList().apply {
                            removeAt(index)
                        }
                    }
                )
            }
        }

        // Thanh tổng tiền và nút thanh toán
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedItems.all { it },
                    onCheckedChange = { isChecked ->
                        selectedItems = List(cartItems.size) { isChecked }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Red,
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    text = "Tất cả",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f),
                    color = Color.Black
                )
                Text(
                    text = "${decimalFormat.format(totalPrice)}đ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { navController.navigate("Order1_Screen") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Thanh toán (${selectedItems.count { it }})", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red,
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = rememberAsyncImagePainter(model = item.imageUrl),
            contentDescription = "Product Image",
            modifier = Modifier
                .size(80.dp)
                .background(Color.Gray, shape = RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.details,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "${decimalFormat.format(item.price)}đ",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Blue)
        }
    }
}
