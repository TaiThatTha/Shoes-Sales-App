package com.example.shopgiay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
//@Composable
//fun AdNavigationAppBar(navController: NavHostController) {
//    val items = listOf(
//        Navitem.ADHOME,
//        Navitem.ADRECEIPT,
//        Navitem.ADME
//    )
//    Box(
//        modifier = Modifier
//            .background(
//                Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFF3D145C),
//                        Color(0xFF0A051C)
//                    )
//                )
//            )
//    ) {
//        NavigationBar(
//            containerColor = Color.Transparent, // Đảm bảo NavigationBar không đè lên màu nền
//        ) {
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentRoute = navBackStackEntry?.destination?.route
//
//            items.forEach { item ->
//                NavigationBarItem(
//                    icon = {
//                        Icon(
//                            imageVector = item.icon ?: Icons.Rounded.Home,
//                            contentDescription = item.route
//                        )
//                    },
//                    colors = NavigationBarItemDefaults.colors(
//                        selectedIconColor = Color(0xFFFF00CC), // Màu biểu tượng khi được chọn
//                        unselectedIconColor = Color.Gray       // Màu biểu tượng không được chọn
//                    ),
//                    alwaysShowLabel = false,
//                    selected = currentRoute == item.route,
//                    onClick = {
//                        navController.navigate(item.route) {
//                            popUpTo(navController.graph.startDestinationRoute ?: item.route) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                )
//            }
//        }
//    }
//}
@Composable
fun AdNavigationAppBar(navController: NavHostController) {
    val items = listOf(
        Navitem.ADHOME,
        Navitem.ADRECEIPT,
        Navitem.ADME
    )

    // Lấy route hiện tại từ NavController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Điều hướng mặc định đến ADHOME nếu không có route hiện tại
    if (currentRoute == null) {
        navController.navigate(Navitem.ADHOME.route) {
            popUpTo(navController.graph.startDestinationRoute ?: Navitem.ADHOME.route) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3D145C),
                        Color(0xFF0A051C)
                    )
                )
            )
    ) {
        NavigationBar(
            containerColor = Color.Transparent, // Đảm bảo NavigationBar không đè lên màu nền
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon ?: Icons.Rounded.Home,
                            contentDescription = item.route
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFF00CC), // Màu biểu tượng khi được chọn
                        unselectedIconColor = Color.Gray       // Màu biểu tượng không được chọn
                    ),
                    alwaysShowLabel = false,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationRoute ?: item.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
