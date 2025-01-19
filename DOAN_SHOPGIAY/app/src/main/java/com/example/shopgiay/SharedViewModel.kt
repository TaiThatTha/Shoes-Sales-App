package com.example.shopgiay

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class SharedViewModel : ViewModel() {
    private val _userId = mutableStateOf(0)
    val userId: State<Int> = _userId

    private val _userName = mutableStateOf("")
    val userName: State<String> = _userName

    private val _matKhau = mutableStateOf("")
    val matKhau: State<String> = _matKhau

    private val _diaChiMacDinh = mutableStateOf("")
    val diaChiMacDinh: State<String> = _diaChiMacDinh

    private val _sdt = mutableStateOf("")
    val sdt: State<String> = _sdt

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    // Cập nhật thông tin người dùng
    fun updateUserInfo(
        id: Int,
        name: String,
        matKhau: String,
        diaChiMacDinh: String,
        sdt: String,
        email: String
    ) {
        _userId.value = id
        _userName.value = name
        _matKhau.value = matKhau
        _diaChiMacDinh.value = diaChiMacDinh
        _sdt.value = sdt
        _email.value = email
    }
}






