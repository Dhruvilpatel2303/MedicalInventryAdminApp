package com.example.medicalinventryadminapp.network.response.UsersResponse.getallapprovedusers

data class getAllApprovedUserResponseItem(
    val address: String,
    val date_of_account_created: String,
    val email: String,
    val id: Int,
    val isApproved: Int,
    val isBlocked: Int,
    val name: String,
    val phone_number: String,
    val pin_code: String,
    val user_id: String,
    val user_password: String
)