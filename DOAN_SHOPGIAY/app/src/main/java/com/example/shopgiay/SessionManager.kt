package com.example.shopgiay

import android.util.Log

object SessionManager {
    private var userRole: String? = null

    fun saveUserRole(role: String) {
        userRole = role
        Log.d("SessionManager", "Vai trò đã lưu: $role")
    }

    fun getUserRole(): String? {
        Log.d("SessionManager", "Lấy vai trò: $userRole")
        return userRole
    }


    private var userName: String? = null
    private var userEmail: String? = null
    private var userId:  Int? =null
    private var userPassword: String? = null
    private var userSdt: String? = null
    private var userDiaChiMacDinh: String? = null




    fun saveUserInfo(role: String, name: String, email: String,passwordd: String,sdtd: String,diachid: String,id: Int) {
        userRole = role
        userName = name
        userEmail = email
        userPassword = passwordd
        userSdt = sdtd
        userDiaChiMacDinh = diachid
        userId = id
        Log.d("SessionManager", "Lưu thông tin: Vai trò - $role, Tên - $name, Email - $email, ID - $id")
    }


    fun getUserName(): String? = userName
    fun getUserEmail(): String? = userEmail
    fun getuserPassword(): String? = userPassword
    fun getuserSdt(): String? = userSdt
    fun getuserDiaChiMacDinh(): String? = userDiaChiMacDinh

    fun getUserId(): Int? = userId
}


