package com.example.shopgiay

import RetrofitInstance.api
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegisterViewModel : ViewModel() {
    val name = mutableStateOf("")
    val phone = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val errorMessage = mutableStateOf("")


    fun handleRegister(name: String, phone: String, email: String, password: String, confirmPassword: String, navController: NavHostController) {
        if (password != confirmPassword) {
            errorMessage.value = "Mật khẩu không khớp"
            return
        }

        val registerRequest = RegisterRequest(
            maNguoiDung = 0, // ID sẽ được tạo ở backend
            tenNguoiDung = name,
            email = email,
            matKhau = password,
            sdt = phone,
            diaChiMacDinh = "", // Có thể để trống
            vaiTro = "user",
            ngayTao = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        )

        val call = api.register(registerRequest)
        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    navController.navigate("Welcome_screen")
                } else {
                    errorMessage.value = "Đăng ký thất bại. Vui lòng thử lại!"
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                errorMessage.value = "Lỗi kết nối mạng!"
            }
        })
    }
}
