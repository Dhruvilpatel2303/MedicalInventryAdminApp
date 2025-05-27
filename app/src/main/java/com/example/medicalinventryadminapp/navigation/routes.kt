package com.example.medicalinventryadminapp.navigation

import kotlinx.serialization.Serializable

sealed class routes {


    @Serializable
     object AllUsersScreen

    @Serializable
    data class UserDetailScreen(val userId: String)


    @Serializable
    object  ProductDetailsScreen

    @Serializable
    object  AddProductScreen

    @Serializable
    object OrdersScreen

    @Serializable
    object AvailableStocksScreen

    @Serializable
    object AllProductsScreen


    @Serializable
    object AllOrdersScreen
}