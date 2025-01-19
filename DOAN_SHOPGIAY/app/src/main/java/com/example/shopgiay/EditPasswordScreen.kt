package com.example.shopgiay

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.shopgiay.ApiResponse
import com.example.shopgiay.ChangePasswordRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

@Composable
fun EditPasswordScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isOldPasswordVisible by remember { mutableStateOf(false) }
    var isNewPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val userId = SessionManager.getUserId() ?: run {
        Toast.makeText(navController.context, "Không thể lấy thông tin người dùng. Vui lòng đăng nhập lại.", Toast.LENGTH_LONG).show()
        navController.popBackStack()
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding( 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Đổi mật khẩu", style = TextStyle(fontSize = 20.sp, color = Color.Black))
            }

            // Old Password
            PasswordField(
                label = "Mật khẩu cũ",
                value = oldPassword,
                onValueChange = { oldPassword = it },
                isPasswordVisible = isOldPasswordVisible,
                onVisibilityChange = { isOldPasswordVisible = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // New Password
            PasswordField(
                label = "Mật khẩu mới",
                value = newPassword,
                onValueChange = { newPassword = it },
                isPasswordVisible = isNewPasswordVisible,
                onVisibilityChange = { isNewPasswordVisible = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm New Password
            PasswordField(
                label = "Nhập lại mật khẩu mới",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                isPasswordVisible = isConfirmPasswordVisible,
                onVisibilityChange = { isConfirmPasswordVisible = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Error message
            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = Color.Red,
                    style = TextStyle(fontSize = 14.sp)
                )
            }

            // Save Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.Blue, RoundedCornerShape(8.dp))
                    .clickable {
                        // Check for empty fields or mismatched passwords
                        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                            message = "Vui lòng điền đầy đủ thông tin"
                            return@clickable
                        }

                        if (newPassword != confirmPassword) {
                            message = "Mật khẩu xác nhận không khớp"
                            return@clickable
                        }

                        isLoading = true
                        val changePasswordRequest = ChangePasswordRequest(
                            maNguoiDung = userId,
                            matKhauCu = oldPassword,
                            matKhauMoi = newPassword
                        )

                        // Make the API call to change the password
                        val apiService = RetrofitInstance.api
                        apiService.changePassword(changePasswordRequest)
                            .enqueue(object : Callback<ApiResponse> {
                                override fun onResponse(
                                    call: Call<ApiResponse>,
                                    response: Response<ApiResponse>
                                ) {
                                    isLoading = false
                                    if (response.isSuccessful) {
                                        message = response.body()?.message ?: "Đổi mật khẩu thành công"
                                        navController.popBackStack() // Go back to the previous screen
                                    } else {
                                        message = response.errorBody()?.string() ?: "Đổi mật khẩu thất bại"
                                    }
                                }

                                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                                    isLoading = false
                                    message = t.localizedMessage ?: "Lỗi kết nối"
                                }
                            })
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(text = "Lưu", style = TextStyle(fontSize = 16.sp, color = Color.White))
                }
            }
        }
    }
}

@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityChange: (Boolean) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
    )
}
