package com.example.shopgiay

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.lifecycle.viewmodel.compose.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EditAddressScreen(navController: NavHostController, sharedViewModel: SharedViewModel = viewModel()) {
    // Lấy thông tin người dùng từ ViewModel
    val userName = SessionManager.getUserName() ?: "Tên không khả dụng"
    val userEmail = SessionManager.getUserEmail() ?: "Email không khả dụng"
    val userPassword= SessionManager.getuserPassword() ?: "Password không khả dụng"
    val userSdt= SessionManager.getuserSdt() ?: "SDT không khả dụng"
    val userDiaChiMacDinh= SessionManager.getuserDiaChiMacDinh() ?: "SDT không khả dụng"
    val getUserId= SessionManager.getUserId()
    val getVaiTro= SessionManager.getUserRole()?: "SDT không khả dụng"

    val newAddress = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val message = remember { mutableStateOf("") }

    // Gọi API đổi địa chỉ
    fun updateAddress() {
        val userId = SessionManager.getUserId()
        if (userId == null) {
            message.value = "Không thể xác định ID người dùng. Vui lòng đăng nhập lại."
            return
        }
        if (newAddress.value.isNotEmpty()) {
            isLoading.value = true
            val changeAddressRequest = ChangeAddressRequest(
                maNguoiDung = userId,
                diaChiMacDinh = newAddress.value
            )

            val apiService = RetrofitInstance.api
            apiService.changeAddress(changeAddressRequest)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        isLoading.value = false
                        if (response.isSuccessful) {
                            message.value = "Địa chỉ đã được cập nhật"
                            SessionManager.saveUserInfo(getVaiTro, userName, userEmail, userPassword,userSdt, newAddress.value, userId)
                        } else {
                            message.value = response.body()?.message ?: "Cập nhật địa chỉ thất bại"
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        isLoading.value = false
                        message.value = t.localizedMessage ?: "Lỗi kết nối"
                    }
                })
        } else {
            message.value = "Địa chỉ mới không được để trống"
        }
    }


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
            // Thanh điều hướng
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
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Chỉnh sửa địa chỉ", style = TextStyle(fontSize = 20.sp, color = Color.Black))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Hiển thị địa chỉ hiện tại
            Text(
                text = "Địa chỉ hiện tại:",
                style = TextStyle(fontSize = 16.sp, color = Color.Gray)
            )
            Text(
                text = userDiaChiMacDinh,
                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nhập địa chỉ mới
            Text(
                text = "Nhập địa chỉ mới:",
                style = TextStyle(fontSize = 16.sp, color = Color.Gray)
            )
            OutlinedTextField(
                value = newAddress.value,
                onValueChange = { newAddress.value = it },
                placeholder = { Text("Nhập địa chỉ giao hàng mới") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Nút Lưu
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.Blue, RoundedCornerShape(8.dp))
                    .clickable { updateAddress() },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Lưu", style = TextStyle(fontSize = 16.sp, color = Color.White))
            }

            // Hiển thị thông báo nếu có lỗi
            if (message.value.isNotEmpty()) {
                Text(
                    text = message.value,
                    color = Color.Red,
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Hiển thị loading indicator khi đang gửi yêu cầu
            if (isLoading.value) {
                Spacer(modifier = Modifier.height(24.dp))
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}
