package com.example.shopgiay

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

sealed class Navitem(var icon: ImageVector? = null, val route: String) {

    object ADVOICE : Navitem(Icons.Rounded.Receipt, "AdVoice")


    object WELCOME : Navitem(route = "Welcome_screen")
    object HOME : Navitem(Icons.Rounded.Home, "Home")
    object FAVORITES : Navitem(Icons.Rounded.Favorite, "Favorite")
    object ME : Navitem(Icons.Rounded.AccountCircle, "Me")

    object ADHOME : Navitem(Icons.Rounded.Home, "AdHome")
    object ADRECEIPT : Navitem(Icons.Rounded.Receipt, "AdReceipt")
    object ADME : Navitem(Icons.Rounded.AccountCircle, "AdMe")



    object RegisterScreen : Navitem(route = "Register_Screen")
    object ForgotPasswordScreen : Navitem(route = "ForgotPassword_Screen")
    object ChangePasswordScreen : Navitem(route = "ChangePassword_Screen")

    object EditProfileScreen: Navitem(route = "Edit_Profile_Screen")
    object EditNameScreen: Navitem(route = "Edit_Name_Screen")
    object EditGenderScreen: Navitem(route = "Edit_Gender_Screen")
    object EditBirthdayScreen: Navitem(route = "Edit_Birthday_Screen")
    object EditAddressScreen: Navitem(route = "Edit_Address_Screen")
    object EditEmailScreen: Navitem(route = "Edit_Email_Screen")
    object EditPhoneNumberScreen: Navitem(route = "Edit_PhoneNumber_Screen")
    object EditPasswordScreen: Navitem(route = "Edit_Password_Screen")


    object OrderScreen: Navitem(route = "Order_Screen/{initialTab}") {
        fun createRoute(initialTab: String): String {
            return "Order_Screen/$initialTab"
        }
    }

    object ShoppingCartScreen: Navitem(route = "ShoppingCart_Screen")
    object Order1Screen: Navitem(route = "Order1_Screen")
    object SeacrchScreen: Navitem(route = "Search_Screen")
    object AdProductScreen: Navitem(route = "AdProduct_Screen")

}

@Composable
fun NavigationGraph(navController: NavHostController) {
    val sharedViewModel: SharedViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Navitem.WELCOME.route,
    ) {
        composable(Navitem.ADVOICE.route) {
            InvoiceDetailScreen(navController)
        }

        composable("NavigationAppBar") { NavigationAppBar(navController) }
        composable("AdNavigationAppBar") { AdNavigationAppBar(navController) }



        composable(
            route = "ProductDetail/{id}/{name}/{price}/{imageUrl}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType },
                navArgument("price") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType }

            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val name = backStackEntry.arguments?.getString("name").orEmpty()
            val price = backStackEntry.arguments?.getString("price").orEmpty()
            val imageUrl = backStackEntry.arguments?.getString("imageUrl").orEmpty()

            ProductDetailScreen(
                product = Product(
                    id = id,
                    name = name,
                    description = "Chi tiết sản phẩm không có sẵn",
                    price = price,
                    imageUrl = imageUrl,
                    //priceValue = price.toFloat()
                ),
                navController = navController
            )
        }




        composable(Navitem.WELCOME.route) {
            WelcomeScreen(navController, sharedViewModel = sharedViewModel)
        }
        composable(Navitem.HOME.route) {
            ContactListScreen(navController)
        }
        composable(Navitem.FAVORITES.route) {
            FavoritesScreen()
        }
        composable(Navitem.ME.route) {
            MeScreen(navController, sharedViewModel = sharedViewModel)
        }


        composable(Navitem.ADHOME.route) {
            AdHomeScreen(navController)
        }
        composable(Navitem.ADRECEIPT.route) {
            AdReceiptScreen(navController)
        }
        composable(Navitem.ADME.route) {
            AdMeScreen(navController)
        }


        composable(Navitem.ChangePasswordScreen.route) {
            ChangePasswordScreen(navController)
        }
        composable(Navitem.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navController)
        }
        composable(Navitem.RegisterScreen.route) {
            RegisterScreen(navController)
        }

        composable(Navitem.EditProfileScreen.route) {
            EditProfileScreen(navController, sharedViewModel = sharedViewModel)
        }
        composable(Navitem.EditNameScreen.route) {
            EditNameScreen(navController, sharedViewModel = sharedViewModel)
        }
        composable(Navitem.EditGenderScreen.route) {
            EditGenderScreen(navController)
        }
        composable(Navitem.EditBirthdayScreen.route) {
            EditBirthdayScreen(navController)
        }
        composable(Navitem.EditAddressScreen.route){
            EditAddressScreen(navController)
        }
        composable(Navitem.EditEmailScreen.route){
            EditEmailScreen(navController)
        }
        composable(Navitem.EditPhoneNumberScreen.route){
            EditPhoneNumberScreen(navController, sharedViewModel = sharedViewModel)
        }
        composable(Navitem.EditPasswordScreen.route){
            EditPasswordScreen(navController, sharedViewModel = sharedViewModel)
        }
        composable(
            route = Navitem.OrderScreen.route,
            arguments = listOf(navArgument("initialTab") { defaultValue = "Chờ xác nhận" })
        ) { backStackEntry ->
            val initialTab = backStackEntry.arguments?.getString("initialTab")
            OrderScreen(navController, initialTab = initialTab)
        }

        composable(Navitem.ShoppingCartScreen.route) {
            ShoppingCartScreen(navController)
        }
        composable(Navitem.Order1Screen.route) {
            Order1Screen(navController)
        }
        composable(Navitem.SeacrchScreen.route) {
            SearchScreen(navController)
        }
        composable(Navitem.AdProductScreen.route) {
            AdProductScreen(navController)
        }
    }
}
