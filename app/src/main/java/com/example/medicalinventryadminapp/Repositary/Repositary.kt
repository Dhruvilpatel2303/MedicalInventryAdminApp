package com.example.medicalinventryadminapp.Repositary

import android.util.Log
import com.example.medicalinventryadminapp.common.ResultState
import com.example.medicalinventryadminapp.network.ApiProvider
import com.example.medicalinventryadminapp.network.response.OrderResponse.deleteOrderResponse.DeleteOrderResponse
import com.example.medicalinventryadminapp.network.response.OrderResponse.getallordersResponse.GetAllOrdersResponse
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repositary {


    suspend fun getAllUsers(

    ): Flow<ResultState<GetAllUsersResponse>> = flow {

        emit(ResultState.loading)

        try {
            val response=ApiProvider.ProvideApiService().getAllUsers()

            if (response.isSuccessful){
                emit(ResultState.suceess<GetAllUsersResponse>(response.body()!!))
            }else{
                emit(ResultState.error(exception = Exception(response.message())))
            }
        }
        catch (e:Exception){
            emit(ResultState.error(exception = e))

        }
    }

    suspend fun approveUser(
        user_Id: String,
        isApproved:Int
    ): Flow<ResultState<UpdateUserResponse>> = flow {
        emit(ResultState.loading)

        try {
            val response=ApiProvider.ProvideApiService().approveUser(user_Id,isApproved)

            if (response.isSuccessful){
                emit(ResultState.suceess<UpdateUserResponse>(response.body()!!))
            }else{
                emit(ResultState.error(exception = Exception(response.message())))
            }
        }
        catch (e: Exception){
            emit(ResultState.error(exception = e))

        }
    }

    suspend fun deleteUser(
        user_Id: String
    ): Flow<ResultState<deleteUserResponse>> =flow{
        emit(ResultState.loading)

        try {
            val response=ApiProvider.ProvideApiService().deleteuser(user_Id)

            if (response.isSuccessful){
                emit(ResultState.suceess<deleteUserResponse>(response.body()!!))
            }else{
                emit(ResultState.error(exception = Exception(response.message())))
            }
        }catch (e: Exception){
            emit(ResultState.error(exception = e))


        }
    }
    suspend fun getAllApprovedUser(

    ): Flow<ResultState<getAllApprovedUserResponse>> = flow {

        emit(ResultState.loading)

        try {
            val response = ApiProvider.ProvideApiService().getAllApprovedUsers()
            if (response.isSuccessful) {
                emit(ResultState.suceess<getAllApprovedUserResponse>(response.body()!!))
            } else {
                emit(ResultState.error(exception = Exception(response.message())))
            }
        } catch (e: Exception) {
            emit(ResultState.error(exception = e))
        }
    }

    suspend fun getSpecificUser(
        user_Id:String
    ): Flow<ResultState<GetSpecificUserResponse>> = flow{
        emit(ResultState.loading)

        try {
            val response=ApiProvider.ProvideApiService().getspecificuser(user_Id)
            if (response.isSuccessful){
                emit(ResultState.suceess<GetSpecificUserResponse>(response.body()!!))

            }
            else{
                emit(ResultState.error(exception = Exception(response.message())))
            }
        }catch (e: Exception){
            emit(ResultState.error(exception = e))


        }
    }





    //for product related functions


    suspend fun createProduct(
        product_name: String,
        product_price:String,
        product_quantity:String,
        product_category:String,
    ):Flow<ResultState<CreateProductResponse>> = flow{

        emit(ResultState.loading)

        try {
            val response=ApiProvider.ProvideApiService().createProduct(product_name,product_price,product_quantity,product_category)

            if (response.isSuccessful){
                emit(ResultState.suceess<CreateProductResponse>(response.body()!!))
            }else {
                emit(ResultState.error(exception = Exception(response.message())))
            }
        }catch (e:Exception){
            emit(ResultState.error(exception = e))

        }
    }

    suspend fun getAllProducts():Flow<ResultState<GetAllProductsResponse>> = flow{
        emit(ResultState.loading)

        try {

            val response = ApiProvider
                .ProvideApiService()
                .getAllProducts()

            Log.d("TAG5 " , "getAllProducts: ${response.body()}")

            if (response.isSuccessful) {
                emit(ResultState.suceess<GetAllProductsResponse>(response.body()!!))
            } else {
                emit(ResultState.error(exception = Exception(response.message())))
            }

        } catch (e: Exception) {
            Log.d("TAG5 " , "getAllProducts: ${e.message}")
            emit(ResultState.error(exception = e))
        }
    }


    suspend fun deleteProduct(
        product_id:String
    ) : Flow<ResultState<DeleteProductResponse>> =flow{

        emit(ResultState.loading)

        try {


        val response= ApiProvider.ProvideApiService().deleteProduct(product_id)

        if(response.isSuccessful){
            emit(ResultState.suceess<DeleteProductResponse>(response.body()!!))
        }
            else{
            emit(ResultState.error(exception = Exception(response.message())))
        }
        }catch (e: Exception){
            emit(ResultState.error(exception = e))


        }
    }
    suspend fun getAllOrders():Flow<ResultState<GetAllOrdersResponse>> = flow{
        emit(ResultState.loading)

        try {
            val response=ApiProvider.ProvideApiService().getAllOrders()

            if (response.isSuccessful){
                emit(ResultState.suceess<GetAllOrdersResponse>(response.body()!!))
            }else{
                emit(ResultState.error(exception = Exception(response.message())))
            }
        }
        catch (e: Exception){
            emit(ResultState.error(exception = e))

        }




    }

    suspend fun approveOrderStatus(
        order_id: String,
        isApproved:Int
    ):Flow<ResultState<UpdateOrderStatusResponse>> = flow{
        emit(ResultState.loading)

        try {
            val response= ApiProvider.ProvideApiService().approveOrderStatus(order_id,isApproved)


            if (response.isSuccessful){
                emit(ResultState.suceess<UpdateOrderStatusResponse>(response.body()!!))
            }else{
                emit(ResultState.error(exception = Exception(response.message())))
            }

        }catch (e: Exception){
            emit(ResultState.error(exception = e))

        }


    }

    suspend fun deleteOrder(
        order_id: String

    ):Flow<ResultState<DeleteOrderResponse>> = flow{
        emit(ResultState.loading)


        try {
            val response= ApiProvider.ProvideApiService().deleteOrder(order_id)

            if (response.isSuccessful){
                emit(ResultState.suceess<DeleteOrderResponse>(response.body()!!))
            }else{
                emit(ResultState.error(exception = Exception(response.message())))
            }

        }catch (e: Exception){
            emit(ResultState.error(exception = e))

        }
    }

    suspend fun getUserSellHistory(

    ): Flow<ResultState<GetUserSellsHistory>> = flow{
        emit(ResultState.loading)

        try {
            val response=ApiProvider.ProvideApiService().getUserSellHistory()


            if (response.isSuccessful){
                emit(ResultState.suceess<GetUserSellsHistory>(response.body()!!))
            }else{
                emit(ResultState.error(exception = Exception(response.message())))
            }
        }
        catch (e: Exception){
            emit(ResultState.error(exception = e))
        }
    }
}