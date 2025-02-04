package com.example.shopgiay

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class NavRoot(val route: String) {
    object Root : NavRoot("root")
    object Main : NavRoot("main")

}

@Composable
fun RootGraph(navRootController: NavHostController) {
    val sharedViewModel: SharedViewModel = viewModel()
    NavHost(
        navController = navRootController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(navRootController, sharedViewModel = sharedViewModel)
        }
        composable("main") {
            MainScreen(navRootController)
        }
    }
}



//@Composable
//fun RootGraph(navRootController: NavHostController) {
//    NavHost(
//        navController = navRootController,
//        startDestination = NavRoot.Main.route // Màn hình đầu tiên sẽ là 'MainScreen'
//    ) {
//        composable(NavRoot.Main.route) {
//            // Truyền navRootController vào MainScreen
//            MainScreen(navRootController = navRootController)
//        }
//
//    }
//}
