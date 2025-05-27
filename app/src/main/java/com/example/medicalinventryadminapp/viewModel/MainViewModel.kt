package com.example.medicalinventryadminapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalinventryadminapp.Repositary.Repositary
import com.example.medicalinventryadminapp.common.ResultState
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val repositary = Repositary()


    //for user states
    private val _alluserstate = MutableStateFlow<AllUsersState>(AllUsersState())
    val alluserstate = _alluserstate.asStateFlow()


    private val _approveuserstate = MutableStateFlow<ApproveUserState>(ApproveUserState())
    val approveuserstate = _approveuserstate.asStateFlow()

    private val _deleteuserstate = MutableStateFlow<DeleteUserState>(DeleteUserState())
    val deleteuserstate = _deleteuserstate.asStateFlow()


    private val _getallapproveduserstate =
        MutableStateFlow<GetAllApprovedUsers>(GetAllApprovedUsers())
    val getallapproveduserstate = _getallapproveduserstate.asStateFlow()

    private val _getspecificuserstate = MutableStateFlow<GetSpecificUser>(GetSpecificUser())
    val getspecificuserstate = _getspecificuserstate.asStateFlow()


    //for product states
    private val _craeteproductstate=MutableStateFlow<CreateProduct>(CreateProduct())
    val createproductstate=_craeteproductstate.asStateFlow()

    private val _getallproductstate= MutableStateFlow<GetAllProducts>(GetAllProducts())
    val getallproductstate=_getallproductstate.asStateFlow()

    private val _deleteproductstate= MutableStateFlow<DeleteProduct>(DeleteProduct())
    val deleteproductstate=_deleteproductstate.asStateFlow()


    //orders state

    private val _getallordersstate= MutableStateFlow<GetAllOrders>(GetAllOrders())
    val getallordersstate=_getallordersstate.asStateFlow()



    private val _approveorderstate= MutableStateFlow<ApproveOrderStatus>(ApproveOrderStatus())
    val approveorderstate=_approveorderstate.asStateFlow()

    private val _deleteorderstate= MutableStateFlow<DeleteOrder>(DeleteOrder())
    val deleteorderstate=_deleteorderstate.asStateFlow()

    // for user sell histroy states
    private val _usersellhistory= MutableStateFlow<UserSellHistory>(UserSellHistory())
    val usersellhistory= _usersellhistory.asStateFlow()





    fun getAllusers() {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                repositary.getAllUsers().collect {
                    when (it) {
                        is ResultState.suceess -> {
                            _alluserstate.value = AllUsersState(success = it.data)
                        }

                        is ResultState.error -> {
                            _alluserstate.value = AllUsersState(error = it.exception.message)
                        }

                        is ResultState.loading -> {
                            _alluserstate.value = AllUsersState(isLoading = true)
                        }
                    }
                }
            } catch (e: Exception) {
                _alluserstate.value = AllUsersState(error = e.message)
            }
        }
    }

    fun approveUser(user_Id: String,isApproved:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositary.approveUser(user_Id,isApproved).collect {

                    when (it) {
                        is ResultState.suceess -> {
                            _approveuserstate.value = ApproveUserState(success = it.data)
                        }

                        is ResultState.error -> {
                            _approveuserstate.value = ApproveUserState(error = it.exception.message)
                        }

                        is ResultState.loading -> {
                            _approveuserstate.value = ApproveUserState(isLoading = true)
                        }
                    }

                }
            } catch (e: Exception) {
                _approveuserstate.value = ApproveUserState(error = e.message)
            }
        }
    }

    fun deleteUser(user_Id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositary.deleteUser(user_Id).collect {
                    when (it) {
                        is ResultState.suceess -> {
                            _deleteuserstate.value = DeleteUserState(sucess = it.data)
                        }

                        is ResultState.error -> {
                            _deleteuserstate.value = DeleteUserState(error = it.exception.message)
                        }

                        is ResultState.loading -> {
                            _deleteuserstate.value = DeleteUserState(isLoading = true)
                        }
                    }
                }
            } catch (e: Exception) {
                _approveuserstate.value = ApproveUserState(error = e.message)


            }
        }
    }

    fun getAllApprovedUser() {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                repositary.getAllApprovedUser().collect {
                    when (it) {
                        is ResultState.suceess -> {
                            _getallapproveduserstate.value = GetAllApprovedUsers(success = it.data)
                        }

                        is ResultState.error -> {
                            _getallapproveduserstate.value =
                                GetAllApprovedUsers(error = it.exception.message)
                        }

                        is ResultState.loading -> {
                            _getallapproveduserstate.value = GetAllApprovedUsers(isLoading = true)
                        }
                    }
                }
            } catch (e: Exception) {
                _getallapproveduserstate.value = GetAllApprovedUsers(error = e.message)
            }
        }
    }

    fun getSpecificUser(user_Id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositary.getSpecificUser(user_Id).collect {
                    when (it) {
                        is ResultState.error -> {
                            Log.d("tag","${it.exception.message}")
                            _getspecificuserstate.value = GetSpecificUser(error = it.exception.message, isLoading = false)

                        }

                        ResultState.loading -> {
                            _getspecificuserstate.value = GetSpecificUser(isLoading = true)

                        }

                        is ResultState.suceess -> {
                            Log.d("TAG", "getSpecificUser: ${it.data}")
                            _getspecificuserstate.value= GetSpecificUser(success=it.data, isLoading = false)

                        }
                    }
                }
            } catch (e: Exception) {
                _getspecificuserstate.value = GetSpecificUser(error = e.message, isLoading = false)

            }
        }
    }

    fun createProduct(
        product_name: String,
        product_price:String,
        product_quantity:String,
        product_category:String
    ){
        viewModelScope.launch(Dispatchers.IO) {

            repositary.createProduct(
                product_name,
                product_price,
                product_quantity,
                product_category
            ).collect {
                when(it) {
                    is ResultState.suceess -> {
                        _craeteproductstate.value = CreateProduct(success = it.data, isLoading = false)
                    }

                    is ResultState.error -> {
                        _craeteproductstate.value = CreateProduct(error = it.exception.message, isLoading = false)
                    }
                    is ResultState.loading -> {
                        _craeteproductstate.value = CreateProduct(isLoading = true)


                    }
                }
            }
        }

    }

    fun getAllProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            repositary.getAllProducts().collect {
                when(it){
                    is ResultState.error ->  {
                        Log.d("TAG5 " , "getAllProducts: ${it.exception}")
                        _getallproductstate.value= GetAllProducts(error = it.exception.message, isLoading = false)

                    }
                    ResultState.loading -> {
                        _getallproductstate.value= GetAllProducts(isLoading = true)


                    }
                    is ResultState.suceess ->{
                        Log.d("TAG5 " , "getAllProducts: ${it.data}")
                        _getallproductstate.value= GetAllProducts(success = it.data, isLoading = false)


                    }
                }



            }
        }
    }


    fun deleteProduct(product_id : String){


        viewModelScope.launch(Dispatchers.IO) {
            try {

                repositary.deleteProduct(product_id).collect {
                    when(it){
                        is ResultState.suceess ->{
                            _deleteproductstate.value= DeleteProduct(success = it.data, isLoading = false)
                        }

                        is ResultState.error ->{
                            _deleteproductstate.value= DeleteProduct(error = it.exception.message, isLoading = false)
                        }

                        is ResultState.loading ->{
                            _deleteproductstate.value= DeleteProduct(isLoading = true)
                        }
                    }



                }

            }catch (E: Exception){
                _deleteproductstate.value= DeleteProduct(error = E.message, isLoading = false)





            }





        }
    }

    fun getAllOrders(){
        viewModelScope.launch(Dispatchers.IO) {
            repositary.getAllOrders().collect{


                when(it){
                    is ResultState.loading -> {
                        _getallordersstate.value= GetAllOrders(isLoading = true)
                    }
                    is ResultState.suceess ->{
                        _getallordersstate.value= GetAllOrders(success = it.data, isLoading = false)
                    }
                    is ResultState.error ->{
                        _getallordersstate.value= GetAllOrders(error = it.exception.message, isLoading = false)

                    }




                }
            }


        }
    }


    fun approveOrderStatus(order_id : String ,isApproved: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repositary.approveOrderStatus(order_id,isApproved).collect{

                when(it){
                    is ResultState.suceess->{
                        _approveorderstate.value= ApproveOrderStatus(success = it.data, isLoading = false)
                        Log.d("TAG", "approveOrderStatus: ${it.data}")
                    }
                    is ResultState.loading->{
                        _approveorderstate.value= ApproveOrderStatus(isLoading = true)

                    }
                    is ResultState.error->{
                        _approveorderstate.value= ApproveOrderStatus(error = it.exception.message, isLoading = false)
                    }
                }

            }

        }
    }


    fun deleteOrder(order_id: String){
        viewModelScope.launch(Dispatchers.IO) {
            repositary.deleteOrder(order_id).collect{
                when(it){

                    is ResultState.suceess->{
                        _deleteorderstate.value= DeleteOrder(success = it.data, isLoading = false)
                    }
                    is ResultState.loading->{
                        _deleteorderstate.value= DeleteOrder(isLoading = true)

                    }
                    is ResultState.error->{
                        _deleteorderstate.value= DeleteOrder(error = it.exception.message, isLoading = false)

                    }
                }
            }
        }
    }

    fun getUserSellHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            repositary.getUserSellHistory().collect{

                when(it){
                    is ResultState.suceess->{
                        _usersellhistory.value= UserSellHistory(success = it.data, isLoading = false)


                    }
                    is ResultState.loading->{
                        _usersellhistory.value= UserSellHistory(isLoading = true)
                    }

                    is ResultState.error<*> -> {
                        _usersellhistory.value= UserSellHistory(error = it.exception.message, isLoading = false)

                    }
                }


            }
        }



    }
}




data class AllUsersState(
    val isLoading: Boolean = false,
    val success: GetAllUsersResponse? = null,
    val error: String? = null
)

data class ApproveUserState(
    val isLoading: Boolean=false,
    val success: UpdateUserResponse? =null,
    val error:String? =null
)

data class DeleteUserState(
    val isLoading: Boolean=false,
    val sucess:deleteUserResponse? =null,
    val error: String? =null
)

data class GetAllApprovedUsers(
    val isLoading: Boolean=false,
    val success: getAllApprovedUserResponse? =null,
    val error: String? =null
)
data class GetSpecificUser(
    val isLoading: Boolean=false,
    val success: GetSpecificUserResponse? =null,
    val error: String? =null
)
data class CreateProduct(
    val isLoading : Boolean =false,
    val success : CreateProductResponse? =null,
    val error: String ?=null
)

data class GetAllProducts(
    val isLoading: Boolean =false,
    val success : GetAllProductsResponse ?= null,
    val error: String?=null
)

data class DeleteProduct(
    val isLoading:Boolean =false,
    val success:DeleteProductResponse?=null,
    val error:String?=null


)

data class GetAllOrders(
    val isLoading: Boolean=false,
    val success: GetAllOrdersResponse? =null,
    val error:String? =null
)

data class ApproveOrderStatus(
    val isLoading: Boolean=false,
    val success: UpdateOrderStatusResponse? =null,
    val error:String? =null

)

data class DeleteOrder(
    val isLoading: Boolean=false,
    val success: DeleteOrderResponse?=null,
    val error:String ?=null
)

data class UserSellHistory(
    val isLoading: Boolean = false,
    val success: GetUserSellsHistory? = null,
    val error: String? = null
)