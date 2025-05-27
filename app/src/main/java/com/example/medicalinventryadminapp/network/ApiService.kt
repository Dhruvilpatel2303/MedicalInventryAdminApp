package com.example.medicalinventryadminapp.network

import com.example.medicalinventryadminapp.network.response.OrderResponse.deleteOrderResponse.DeleteOrderResponse
import com.example.medicalinventryadminapp.network.response.OrderResponse.getallordersResponse.GetAllOrdersResponse
import com.example.medicalinventryadminapp.network.response.OrderResponse.getallordersResponse.GetAllOrdersResponseItem
import com.example.medicalinventryadminapp.network.response.OrderResponse.updateorderstatus.UpdateOrderStatusResponse
import com.example.medicalinventryadminapp.network.response.ProductResponse.createProductResponse.CreateProductResponse
import com.example.medicalinventryadminapp.network.response.ProductResponse.deleteProductResponse.DeleteProductResponse
import com.example.medicalinventryadminapp.network.response.ProductResponse.getAllProductsResponse.GetAllProductsResponse
import com.example.medicalinventryadminapp.network.response.SellHistoryResponse.getUserSellHistory.GetUserSellsHistory
import com.example.medicalinventryadminapp.network.response.UsersResponse.GetAllusers.GetAllUsersResponse
import com.example.medicalinventryadminapp.network.response.UsersResponse.UpdateUserDetails.UpdateUserResponse
import com.example.medicalinventryadminapp.network.response.UsersResponse.deleteUserResponse.deleteUserResponse
import com.example.medicalinventryadminapp.network.response.UsersResponse.getSpecificUserResponse.GetSpecificUserResponse
import com.example.medicalinventryadminapp.network.response.UsersResponse.getallapprovedusers.getAllApprovedUserResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {


    @GET("getAlluser")
    suspend fun getAllUsers(): Response<GetAllUsersResponse>

    @FormUrlEncoded
    @PATCH("updateusersir")
    suspend fun approveUser(
        @Field("user_Id") user_id: String,
        @Field("isApproved") isApproved:Int
    ): Response<UpdateUserResponse>

    @FormUrlEncoded
    @POST("deleteuser")
    suspend fun deleteuser(
        @Field("user_Id") user_id: String
    ): Response<deleteUserResponse>


    @GET("getallapprovedusers")
    suspend fun getAllApprovedUsers(): Response<getAllApprovedUserResponse>


    @FormUrlEncoded
    @POST("getspecificuser")
    suspend fun getspecificuser(
        @Field("user_Id") user_Id: String
    ): Response<GetSpecificUserResponse>


    @FormUrlEncoded
    @POST("createproduct")
    suspend fun createProduct(
        @Field("product_name") product_name: String,
        @Field("product_price") product_price:String,
        @Field("product_quantity") product_quantity:String,
        @Field("product_category") product_category:String,

    ): Response<CreateProductResponse>

    @GET("getallproducts")
    suspend fun getAllProducts(

    ): Response<GetAllProductsResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path= "deleteproduct", hasBody = true)
    suspend fun deleteProduct(
        @Field("product_Id") product_Id: String
    ): Response<DeleteProductResponse>


    @GET("getallorders")
    suspend fun getAllOrders(): Response<GetAllOrdersResponse>


    @FormUrlEncoded
    @PATCH("updateordersir")
    suspend fun approveOrderStatus(
        @Field("order_Id") order_id: String,
        @Field("isApproved") isApproved:Int

    ): Response<UpdateOrderStatusResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path= "deleteorder", hasBody = true)
    suspend fun deleteOrder(
        @Field("order_Id") product_Id: String
    ): Response<DeleteOrderResponse>

    @GET("getallsells")
    suspend fun getUserSellHistory(
    ): Response<GetUserSellsHistory>





}