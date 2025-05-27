package com.example.medicalinventryadminapp.network.response.OrderResponse.getallordersResponse

data class GetAllOrdersResponseItem(
    val category: String,
    val date_of_order_creation: String,
    val id: Int,
    val isApproved: Int,
    val messaage: String,
    val order_id: String,
    val order_price: Int,
    val order_quantity: Int,
    val orderd_product_name: String,
    val product_id: String,
    val total_amount: Int,
    val user_id: String,
    val user_name: String
)