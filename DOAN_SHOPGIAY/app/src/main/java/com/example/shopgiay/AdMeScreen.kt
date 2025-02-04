package com.example.shopgiay

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun AdMeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6600FF), Color(0xFF1C0524))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Divider(
                color = Color.Black.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth(0.95f) // Chiều dài ngắn hơn
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Header Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                // Avatar
                AsyncImage(
                    model = "https://via.placeholder.com/100",
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)) // Placeholder background
                )
                Spacer(modifier = Modifier.width(16.dp))
                // Username
                Column {
                    Text(
                        text = "ADMIN",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Divider(
                color = Color.Black.copy(alpha = 0.2f),
                thickness = 1.dp
            )
            // More Options Icon

        }


        Spacer(modifier = Modifier.height(18.dp))
        // Section Title
        Row(modifier = Modifier.fillMaxWidth()
            .clickable{navController.navigate("AdProduct_Screen")}
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE0E0E0))
            .padding(vertical = 10.dp)

        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "SẢN PHẨM TRONG KHO",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.Black,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logout Button
        Button(
            onClick = { navController.navigate("Welcome_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE581A2)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "ĐĂNG XUẤT",
                fontWeight = FontWeight.Bold
            )
        }
    }
}
