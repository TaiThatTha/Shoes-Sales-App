package com.example.shopgiay

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MainScreen(navRootController: NavHostController, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    // Lấy vai trò từ SessionManager
    val userRole = remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        userRole.value = SessionManager.getUserRole()
        if (userRole.value == null) {
            navRootController.navigate("welcome") {
                popUpTo("main") { inclusive = true }
            }
        }
    }


    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val isLoginPage = currentRoute == "ProductDetail/{id}/{name}/{price}/{imageUrl}"


    // Định nghĩa nội dung dựa trên vai trò
    val bottomBarContent: @Composable () -> Unit = {
        when (userRole.value) {
            "admin" -> AdNavigationAppBar(navController)
            "user" -> NavigationAppBar(navController)
            else -> Text("Vai trò không hợp lệ.")
        }
    }

    val showBars = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            if (showBars.value ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "SHOP_3TL",
                            color = Color(0xFF428542),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("Search_Screen") }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color(0xFFCC00FF)
                            )
                        }
                        IconButton(onClick = { navController.navigate("ShoppingCart_Screen") }) {
                            Icon(
                                imageVector = Icons.Rounded.ShoppingCart,
                                contentDescription = "ShoppingCart",
                                tint = Color(0xFFCC00FF)
                            )
                        }
                    }
                )
            }
        },

        bottomBar = {if (showBars.value) { bottomBarContent()} }
    ) {
        Box(modifier = modifier.padding(it)) {
            NavigationGraph(navController = navController, onToggleBars = { showBars.value = it })
        }
    }
}


//@Composable
//fun MainScreen(navRootController: NavHostController, modifier: Modifier = Modifier) {
//    // Khai báo navItemController
//    val navController = rememberNavController()
//
//    // Giả sử bạn có một NavItem cho trang đăng nhập
//    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
//    val isLoginPage = currentRoute == "Welcome_screen" || currentRoute == "Register_Screen"
//            || currentRoute=="ForgotPassword_Screen" || currentRoute=="ChangePassword_Screen"
//            || currentRoute=="ProductDetail/{name}/{price}/{imageRes}"  || currentRoute == "Search_Screen"
//            || currentRoute == "Order1_Screen" || currentRoute == "ShoppingCart_Screen"|| currentRoute == "Order_Screen/{initialTab}" // Kiểm tra nếu trang hiện tại là trang đăng nhập
//    val isMepage = currentRoute == "Me" ||currentRoute == "AdReceipt" || currentRoute == "AdVoice" ||
//            currentRoute == "Edit_Profile_Screen" || currentRoute == "Edit_Name_Screen" || currentRoute == "Edit_Gender_Screen" || currentRoute == "Edit_Birthday_Screen"
//            || currentRoute == "Edit_Address_Screen"|| currentRoute == "Edit_Email_Screen"|| currentRoute == "Edit_PhoneNumber_Screen"|| currentRoute == "Edit_Password_Screen"
//            || currentRoute == "Order_Screen/{initialTab} "|| currentRoute == "Search_Screen}"
//    val isOderScreen = currentRoute == "Edit_Profile_Screen" || currentRoute == "Edit_Name_Screen" || currentRoute == "Edit_Gender_Screen" || currentRoute == "Edit_Birthday_Screen"
//            || currentRoute == "Edit_Address_Screen"|| currentRoute == "Edit_Email_Screen"|| currentRoute == "Edit_PhoneNumber_Screen"|| currentRoute == "Edit_Password_Screen"
//            || currentRoute == "Order_Screen/{initialTab}"|| currentRoute == "AdVoice"
//    val items = listOf(
//        Navitem.HOME, Navitem.FAVORITES,  Navitem.ME
//    )
//    Scaffold(
//        topBar = {
//            if (!isLoginPage && !isMepage) { // Kiểm tra nếu không phải trang đăng nhập
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "SHOP_3TL",
//                        color = Color(0xFF428542),
//                        fontSize = 25.sp,
//                        fontWeight = FontWeight.ExtraBold
//                    )
//                },
//                actions = {
//                    IconButton(onClick = { navController.navigate("Search_Screen")}) {
//                        Icon(
//                            imageVector = Icons.Default.Search,
//                            contentDescription = "Search",
//                            tint = Color.Black
//                        )
//                    }
//                    IconButton(onClick = { navController.navigate("ShoppingCart_Screen")}) {
//                        Icon(
//                            imageVector = Icons.Rounded.ShoppingCart,
//                            contentDescription = "ShoppingCart",
//                        )
//                    }
//                }
//            )
//            HorizontalDivider() // đường phân với content
//            }
//        },
//        bottomBar = {
//            if (!isLoginPage && !isOderScreen) { // Kiểm tra nếu không phải trang đăng nhập
//            Column {
//                HorizontalDivider() // đường phân với content
//                // Sử dụng navItemController cho NavigationAppBar
//                NavigationAppBar(navController = navController)
//            }
//            }
//        }
//    )
//    {
//        Box(modifier = modifier.padding(it)) {
//            // Chỉ cần truyền navItemController vào
//            NavigationGraph(navController = navController)
//        }
//    }
//}