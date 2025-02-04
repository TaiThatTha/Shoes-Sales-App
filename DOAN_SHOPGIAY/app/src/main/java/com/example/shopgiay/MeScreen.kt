package com.example.shopgiay

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import java.nio.file.WatchEvent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MeScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {

    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6600FF), Color(0xFF1C0524))
                )
            )
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            // Avatar and Username
            AvatarSection(navController = navController, sharedViewModel = sharedViewModel)

            Spacer(modifier = Modifier.height(20.dp))

            // Order Section
            OrderSection(navController)

            Spacer(modifier = Modifier.weight(1f))

            // Logout Button
            LogoutButton(navController)
        }
    }
}
//
//@Composable
//fun AvatarSection(navController: NavHostController, sharedViewModel: SharedViewModel) {
//    val userName = sharedViewModel.userName.value // Lấy tên người dùng từ ViewModel
//    Log.d("SharedViewModel", "Setting user name: ${userName}")
//    Column(
//        modifier = Modifier
//            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
//            .clickable { navController.navigate("Edit_Profile_Screen") }
//    ) {
//        Spacer(modifier = Modifier.height(5.dp))
//        Divider(
//            color = Color.Black.copy(alpha = 0.2f),
//            thickness = 1.dp,
//            modifier = Modifier
//                .fillMaxWidth(0.95f)
//                .padding(bottom = 8.dp)
//                .align(Alignment.CenterHorizontally)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp))
//        ) {
//            Spacer(modifier = Modifier.width(14.dp))
//            Image(
//                painter = painterResource(id = R.drawable.userme),
//                contentDescription = "Avatar",
//                modifier = Modifier
//                    .size(64.dp)
//                    .clip(CircleShape)
//                    .background(Color(0xFFFFFFFF))
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Column {
//                Text(
//                    text = "$userName", // Hiển thị tên người dùng
//                    fontSize =  30.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//            Spacer(modifier = Modifier.weight(1f))
//            Icon(
//                imageVector = Icons.Default.ChevronRight,
//                contentDescription = "More",
//                tint = Color.Black
//            )
//        }
//        Spacer(modifier = Modifier.height(24.dp))
//        Divider(
//            color = Color.Black.copy(alpha = 0.2f),
//            thickness = 1.dp
//        )
//    }
//}

@Composable
fun AvatarSection(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val userName = SessionManager.getUserName() ?: "Tên không khả dụng"
    val userEmail = SessionManager.getUserEmail() ?: "Email không khả dụng"
    Column(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { navController.navigate("Edit_Profile_Screen") }
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Divider(
            color = Color.Black.copy(alpha = 0.2f),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp))
        ) {
            Spacer(modifier = Modifier.width(14.dp))
            Image(
                painter = painterResource(id = R.drawable.userme),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFFFFF))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "$userName",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$userEmail",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "More",
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            color = Color.Black.copy(alpha = 0.2f),
            thickness = 1.dp
        )
    }
}

@Composable
fun OrderSection(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "",
                style = TextStyle(color = Color.LightGray, fontSize = 14.sp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.clickable { navController.navigate(Navitem.OrderScreen.createRoute("Đã giao")) }
            ) {
                Text(
                    text = "Xem tất cả",
                    style = TextStyle(color = Color.White, fontSize = 16.sp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Chevron Right",
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier.background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp))
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp)),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Spacer(modifier = Modifier.width())
                Row(
                    modifier = Modifier.clickable {
                        navController.navigate(
                            Navitem.OrderScreen.createRoute(
                                "Chờ xác nhận"
                            )
                        )
                    }
                ) {
                    OrderItem(
                        iconRes = R.drawable.clipboard,
                        label = "Chờ xác nhận",
                        onClick = { navController.navigate(Navitem.OrderScreen.createRoute("Chờ xác nhận")) }
                    )
                }

                Row(modifier = Modifier.clickable {
                    navController.navigate(
                        Navitem.OrderScreen.createRoute(
                            "Chờ lấy hàng"
                        )
                    )
                }) {
                    OrderItem(
                        iconRes = R.drawable.box,
                        label = "Chờ lấy hàng",
                        onClick = { navController.navigate(Navitem.OrderScreen.createRoute("Chờ lấy hàng")) }
                    )
                }

                Row(modifier = Modifier.clickable {
                    navController.navigate(
                        Navitem.OrderScreen.createRoute(
                            "Chờ giao hàng"
                        )
                    )
                }) {
                    OrderItem(
                        iconRes = R.drawable.car,
                        label = "Chờ giao hàng",
                        onClick = { navController.navigate(Navitem.OrderScreen.createRoute("Chờ giao hàng")) }
                    )
                }
                Row() {
                    OrderItem(
                        iconRes = R.drawable.review,
                        label = "Đánh giá",
                        onClick = {/*danh gia*/}
                    )
                }
            }
        }
    }
}

@Composable
fun OrderItem(iconRes: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = Color(0xFF6200EE),
            modifier = Modifier
                .size(40.dp)
                .clickable { onClick() } // Make the icon clickable
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Black)
    }
}

@Composable
fun LogoutButton(navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate("Welcome_screen")
                  },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E17EB)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = "ĐĂNG XUẤT", color = Color.White)
    }
}
