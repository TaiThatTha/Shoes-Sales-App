package com.example.shopgiay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A051C),
                        Color(0xFF3D145C)
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Quay lại",
                    tint = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(88.dp))

            // Tiêu đề
            Text(
                text = "SHOP 3TL",
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                color = Color.White,
                style = TextStyle(
                    shadow = androidx.compose.ui.graphics.Shadow(
                        color = Color(0xFF9932CC),
                        blurRadius = 8f
                    )
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Trường nhập liệu số điện thoại
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Số điện thoại") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Hàng chứa mã và nút gửi mã
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Mã") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { /* Xử lý gửi mã */ },
                    modifier = Modifier
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFF007F)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Gửi mã",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nút quên mật khẩu
            Button(
                onClick = { navController.navigate("ChangePassword_Screen") },
                modifier = Modifier

                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF5E17EB)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "QUÊN MẬT KHẨU",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}
