@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.shopgiay

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response






@Composable
fun WelcomeScreen(navController: NavHostController,sharedViewModel: SharedViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }


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
            Text(
                text = "SHOP 3TL",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 42.sp,
                color = Color.White,
                style = TextStyle(
                    shadow = androidx.compose.ui.graphics.Shadow(
                        color = Color(0xFF9932CC),
                        blurRadius = 8f
                    )
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Tài khoản", color = Color.White) }, // Chỉnh màu chữ label thành trắng
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF4B43DE),
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,  // Màu label khi focus
                    unfocusedLabelColor = Color.White  // Màu label khi không focus
                ),
                textStyle = TextStyle(color = Color.White), // Chỉnh màu chữ nhập liệu thành trắng
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu", color = Color.White) }, // Chỉnh màu chữ label thành trắng
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF4B43DE),
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,  // Màu label khi focus
                    unfocusedLabelColor = Color.White  // Màu label khi không focus
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(), // Hiện/ẩn mật khẩu
                textStyle = TextStyle(color = Color.White), // Chỉnh màu chữ nhập liệu thành trắng
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible } // Toggle trạng thái hiển thị mật khẩu
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu",
                            tint = Color.White
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        val loginRequest = LoginRequest(email, password)
                        val call = RetrofitInstance.api.login(loginRequest)

                        call.enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(
                                call: Call<LoginResponse>,
                                response: Response<LoginResponse>
                            ) {
                                if (response.isSuccessful && response.body() != null) {
                                    val loginResponse = response.body()
                                    val user = loginResponse?.user
                                    if (user != null) {
                                        SessionManager.saveUserRole(user.vaiTro)
                                        SessionManager.saveUserInfo(
                                            role = user.vaiTro,
                                            name = user.tenNguoiDung,
                                            email = user.email,
                                            passwordd = user.matKhau,
                                            sdtd=user.sdt,
                                            diachid = user.diaChiMacDinh,
                                            id = user.maNguoiDung
                                        )// Lưu vai trò
                                        sharedViewModel.updateUserInfo(user.maNguoiDung, user.tenNguoiDung, user.matKhau, user.diaChiMacDinh, user.sdt, user.email)
                                        Log.d("SharedViewModel", "Setting user name: ${user.tenNguoiDung}")
                                        Log.d("SharedViewModel", "Setting mat khau name: ${user.matKhau}")// Cập nhật tên người dùng vào ViewModel
                                        Log.d("SharedViewModel", "Current user name in ViewModel: ${sharedViewModel.userName.value}")

                                        // Điều hướng tới MainScreen
                                        navController.navigate("main") {
                                            popUpTo("welcome") { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    } else {
                                        errorMessage = "Sai tài khoản hoặc mật khẩu."
                                    }
                                } else {
                                    errorMessage = "Đăng nhập thất bại. Lỗi: ${response.message()}"
                                }
                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                errorMessage = "Lỗi kết nối đến máy chủ: ${t.message}"
                            }
                        })
                    } else {
                        errorMessage = "Vui lòng nhập đầy đủ thông tin."
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF5E17EB)),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "ĐĂNG NHẬP",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

//            Button(
//                onClick = {
//                    if (email.isNotEmpty() && password.isNotEmpty()) {
//                        val loginRequest = LoginRequest(email, password)
//                        val call = RetrofitInstance.api.login(loginRequest)
//
//                        call.enqueue(object : Callback<LoginResponse> {
//                            override fun onResponse(
//                                call: Call<LoginResponse>,
//                                response: Response<LoginResponse>
//                            ) {
//                                if (response.isSuccessful && response.body() != null) {
//                                    val loginResponse = response.body()
//                                    val user = loginResponse?.user
//                                    if (user != null) {
//                                        // Lưu vai trò vào SessionManager
//                                        SessionManager.saveUserRole(user.vaiTro)
//
//                                        // Điều hướng tới MainScreen
//                                        navController.navigate("main") {
//                                            popUpTo("welcome") { inclusive = true }
//                                            launchSingleTop = true
//                                        }
//                                    } else {
//                                        errorMessage = "Sai tài khoản hoặc mật khẩu."
//                                    }
//                                } else {
//                                    errorMessage = "Đăng nhập thất bại. Lỗi: ${response.message()}"
//                                }
//                            }
//
//                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                                errorMessage = "Lỗi kết nối đến máy chủ: ${t.message}"
//                            }
//                        })
//                    } else {
//                        errorMessage = "Vui lòng nhập đầy đủ thông tin."
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(Color(0xFF5E17EB)),
//                shape = MaterialTheme.shapes.small
//            ) {
//                Text(
//                    text = "ĐĂNG NHẬP",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp,
//                    color = Color.White
//                )
//            }






            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage,
                    fontSize = 14.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Quên mật khẩu ?",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.clickable { navController.navigate("ForgotPassword_Screen") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("Register_Screen") },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF5E17EB)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "ĐĂNG KÝ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

//
//@Composable
//fun WelcomeScreen(navController: NavHostController) {
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var errorMessage by remember { mutableStateOf("") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFF0A051C),
//                        Color(0xFF3D145C)
//                    )
//                )
//            )
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 32.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "SHOP 3TL",
//                fontWeight = FontWeight.ExtraBold,
//                fontSize = 42.sp,
//                color = Color.White,
//                style = TextStyle(
//                    shadow = androidx.compose.ui.graphics.Shadow(
//                        color = Color(0xFF9932CC),
//                        blurRadius = 8f
//                    )
//                )
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Tài khoản") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Mật khẩu") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Button(
//                onClick = {
//                    val call = RetrofitInstance.api.login(email,password)
//                    call.enqueue(object : Callback<LoginResponse> {
//                        override fun onResponse(
//                            call: Call<LoginResponse>,
//                            response: Response<LoginResponse>
//                        ) {
//                            if (response.isSuccessful) {
//                                val user = response.body()?.user
//                                if (user != null) {
//                                    when (user.vaiTro) {
//                                        "nguoidung" -> navController.navigate("NavigationAppBar")
//                                        "admin" -> navController.navigate("AdNavigationAppBar")
//                                        else -> errorMessage = "Vai trò không hợp lệ."
//                                    }
//                                } else {
//                                    errorMessage = "Sai tài khoản hoặc mật khẩu."
//                                }
//                            } else {
//                                errorMessage = "Đăng nhập thất bại."
//                            }
//                        }
//
//                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                            errorMessage = "Lỗi kết nối đến máy chủ."
//                        }
//                    })
//                },
//                modifier = Modifier
//                    .fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(Color(0xFF5E17EB)),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text(
//                    text = "ĐĂNG NHẬP",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp,
//                    color = Color.White
//                )
//            }
//
//            if (errorMessage.isNotEmpty()) {
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = errorMessage,
//                    fontSize = 14.sp,
//                    color = Color.Red,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Quên mật khẩu ?",
//                fontSize = 14.sp,
//                color = Color.White,
//                modifier = Modifier.clickable { navController.navigate("ForgotPassword_Screen") }
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Button(
//                onClick = { navController.navigate("Register_Screen") },
//                modifier = Modifier
//                    .fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(Color(0xFF5E17EB)),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text(
//                    text = "ĐĂNG KÝ",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp,
//                    color = Color.White
//                )
//            }
//        }
//    }
//}
