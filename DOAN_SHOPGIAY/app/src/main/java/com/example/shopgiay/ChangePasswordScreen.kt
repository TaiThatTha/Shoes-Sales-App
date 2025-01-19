package com.example.shopgiay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun ChangePasswordScreen(navController: NavHostController) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

            // Trường nhập liệu mật khẩu mới
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Mật khẩu mới") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Trường nhập liệu nhập lại mật khẩu
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Nhập lại mật khẩu") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nút đổi mật khẩu
            Button(
                onClick = { /* Xử lý sự kiện đổi mật khẩu */ },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF5E17EB)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "ĐỔI MẬT KHẨU",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

