package com.example.shopgiay

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun EditGenderScreen(navController: NavHostController) {
    val selectedGender = remember { mutableStateOf<String?>(null) } // Lưu giới tính đã chọn

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Top Bar with Back Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Chọn giới tính",
                    style = TextStyle(fontSize = 20.sp, color = Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Gender Options
            GenderOptionItem(
                label = "Nam",
                isSelected = selectedGender.value == "Nam",
                onClick = { selectedGender.value = "Nam" }
            )

            Spacer(modifier = Modifier.height(16.dp))

            GenderOptionItem(
                label = "Nữ",
                isSelected = selectedGender.value == "Nữ",
                onClick = { selectedGender.value = "Nữ" }
            )

            Spacer(modifier = Modifier.height(16.dp))

            GenderOptionItem(
                label = "Khác",
                isSelected = selectedGender.value == "Khác",
                onClick = { selectedGender.value = "Khác" }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Save Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (selectedGender.value != null) Color.Blue else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(enabled = selectedGender.value != null) {
                        // Lưu giới tính và quay lại màn hình trước
                        navController.popBackStack()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Lưu",
                    style = TextStyle(fontSize = 16.sp, color = Color.White)
                )
            }
        }
    }
}

@Composable
fun GenderOptionItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) Color(0xFFE3F2FD) else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {  }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = TextStyle(fontSize = 16.sp, color = Color.Black)
        )
        if (isSelected) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Add,
                contentDescription = "Selected",
                tint = Color.Blue
            )
        }
    }
}
