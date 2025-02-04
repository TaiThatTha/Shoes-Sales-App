package com.example.shopgiay

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EditNameScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val userId = SessionManager.getUserId() ?: run {
        Toast.makeText(navController.context, "Không thể lấy thông tin người dùng. Vui lòng đăng nhập lại.", Toast.LENGTH_LONG).show()
        navController.popBackStack()
        return
    }

    val userName = SessionManager.getUserName() ?: "Tên không khả dụng"
    val userEmail = SessionManager.getUserEmail() ?: "Email không khả dụng"
    val userPassword = SessionManager.getuserPassword() ?: "Password không khả dụng"
    val userSdt = SessionManager.getuserSdt() ?: "SDT không khả dụng"
    val userDiaChiMacDinh = SessionManager.getuserDiaChiMacDinh() ?: "SDT không khả dụng"
    val getVaiTro = SessionManager.getUserRole() ?: "Vai trò không khả dụng"

    var username by remember { mutableStateOf(userName) }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nút Back
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,

                    )
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Tiêu đề
            Text(
                text = "Sửa tên",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            )
            Spacer(modifier = Modifier.weight(1f))
            // Nút Lưu
            TextButton(
                onClick = {
                    // Gửi yêu cầu đổi tên
                    if (username.isNotEmpty()) {
                        isLoading = true
                        val changeUsernameRequest = ChangeUsernameRequest(
                            maNguoiDung = userId,
                            tenNguoiDungMoi = username
                        )

                        // Gọi API đổi tên
                        val apiService = RetrofitInstance.api
                        apiService.changeUsername(changeUsernameRequest)
                            .enqueue(object : Callback<ApiResponse> {
                                override fun onResponse(
                                    call: Call<ApiResponse>,
                                    response: Response<ApiResponse>
                                ) {
                                    isLoading = false
                                    if (response.isSuccessful) {
                                        message = response.body()?.message ?: "Đổi tên thành công"
                                        navController.popBackStack()
                                        SessionManager.saveUserInfo(
                                            getVaiTro, username, userEmail, userPassword, userSdt, userDiaChiMacDinh, userId
                                        )
                                    } else {
                                        message = response.errorBody()?.string() ?: "Đổi tên thất bại"
                                    }
                                }

                                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                                    isLoading = false
                                    message = t.localizedMessage ?: "Lỗi kết nối"
                                }
                            })
                    } else {
                        message = "Tên người dùng không được để trống"
                    }
                }
            ) {
                Text(
                    text = "Lưu",
                    style = TextStyle(fontSize = 16.sp, color = Color.Red)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input Field để chỉnh sửa tên
        BasicTextField(
            value = username,
            onValueChange = { if (it.length <= 100) username = it }, // Giới hạn 100 ký tự
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Spacer(modifier = Modifier.height(8.dp))

        // Text thông báo số ký tự giới hạn
        Text(
            text = "Giới hạn 100 ký tự",
            style = TextStyle(fontSize = 14.sp, color = Color.Gray),
            modifier = Modifier.padding(start = 8.dp)
        )

        // Hiển thị thông báo nếu có lỗi
        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = Color.Red,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(8.dp)
            )
        }

        // Loading indicator khi đang gửi yêu cầu
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
