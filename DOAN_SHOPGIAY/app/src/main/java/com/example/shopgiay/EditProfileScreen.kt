package com.example.shopgiay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun EditProfileScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {


    val userName = SessionManager.getUserName() ?: "Tên không khả dụng"
    val userEmail = SessionManager.getUserEmail() ?: "Email không khả dụng"
    val userPassword= SessionManager.getuserPassword() ?: "Password không khả dụng"
    val userSdt= SessionManager.getuserSdt() ?: "SDT không khả dụng"
    val userDiaChiMacDinh= SessionManager.getuserDiaChiMacDinh() ?: "SDT không khả dụng"
    val getUserId= SessionManager.getUserId()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6600FF), Color(0xFF1C0524))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar with Back Arrow
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sửa hồ sơ",
                    style = TextStyle(fontSize = 20.sp, color = Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            // Avatar Section
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.userme), // Đường dẫn tới ảnh trong drawable
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFFFFF)) // Placeholder background
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Details List in Cards
            val profileItems = listOf(
                "Tên" to "$userName",
                "Số điện thoại" to "$userSdt",
                "Email" to "$userEmail",
                "Địa chỉ giao hàng" to "Thiết lập",
                "Đổi mật khẩu" to "Thiết lập"
            )

            Column {
                profileItems.forEach { item ->
                    ProfileCardItem(
                        label = item.first,
                        value = item.second,
                        onClick = {
                            if (item.first == "Tên") {
                                navController.navigate("Edit_Name_Screen")
                            }
                            else if (item.first == "Số điện thoại") {
                                navController.navigate("Edit_PhoneNumber_Screen")
                            }
                            else if (item.first == "Địa chỉ giao hàng") {
                                navController.navigate("Edit_Address_Screen")
                            }
                            else if (item.first == "Đổi mật khẩu") {
                                navController.navigate("Edit_Password_Screen")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileCardItem(label: String, value: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = TextStyle(fontSize = 16.sp, color = Color.Black)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = value,
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (label != "Email") {

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Chevron Right",
                        tint = Color.LightGray
                    )
                }
            }
        }
    }
}
