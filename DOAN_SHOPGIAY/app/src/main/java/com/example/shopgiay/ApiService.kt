package com.example.shopgiay

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginResponse(
    val message: String,
    val user: User?
)

data class User(
    val maNguoiDung: Int,
    val tenNguoiDung: String,
    val email: String,
    val matKhau: String,
    val sdt: String,
    val diaChiMacDinh: String,
    val vaiTro: String,
    val ngayTao: String?
)

data class LoginRequest(
    val email: String,
    val matKhau: String
)


data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val imageUrl: String,
    //val priceValue: Float
)

// Định nghĩa lớp ProductResponse
data class ProductResponse(
    val maSanPham: Int,
    val tenSanPham: String,
    val moTa: String,
    val gia: Float,
    val anhSanPham: String,
    //val priceValue: Float
)

data class AnhBienThe(
    val maAnh: Int,
    val maSanPham: Int,
    val duongDan: String
)

data class Variant(
    val maBienThe: Int,
    val maSanPham: Int,
    val kichThuoc: String,
    val soLuongTon: Int
)

data class ChangeUsernameRequest(
    val maNguoiDung: Int,
    val tenNguoiDungMoi: String
)

data class ChangePhoneRequest(
    val maNguoiDung: Int,
    val sdtMoi: String
)

data class ChangeAddressRequest(
    val maNguoiDung: Int,
    val diaChiMacDinh: String
)
data class ChangePasswordRequest(
    val maNguoiDung: Int,
    val matKhauCu: String,
    val matKhauMoi: String
)
data class ApiResponse(
    val message: String
)
interface ApiService {
    @POST("login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @GET("getDanhSachSanPham")
    fun getProducts(): Call<List<ProductResponse>>

    @GET("getDanhSachAnhBienThe")
    suspend fun getDanhSachAnhBienThe(): List<AnhBienThe>

    @GET("getAllBienThe")
    suspend fun getAllBienThe(): List<Variant>


    @POST("/change_password")
    fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Call<ApiResponse>

    @POST("/change_username")
    fun changeUsername(
        @Body changeUsernameRequest: ChangeUsernameRequest
    ): Call<ApiResponse>

    @POST("change_phone")
    fun changePhone(
        @Body changePhoneRequest: ChangePhoneRequest
    ): Call<ApiResponse>

    @POST("/addAddress")
    fun changeAddress(
        @Body changeAddressRequest: ChangeAddressRequest
    ): Call<ApiResponse>
}
