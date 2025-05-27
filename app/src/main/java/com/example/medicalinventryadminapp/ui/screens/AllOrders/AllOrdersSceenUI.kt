package com.example.medicalinventryadminapp.ui.screens.AllOrders

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalinventryadminapp.navigation.routes
import com.example.medicalinventryadminapp.network.response.OrderResponse.getallordersResponse.GetAllOrdersResponseItem
import com.example.medicalinventryadminapp.viewModel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreenUI(viewModel: MainViewModel, navController: NavController) {
    val getallordersstate = viewModel.getallordersstate.collectAsState().value
    val approveOrderStatus = viewModel.approveorderstate.collectAsState().value
    val deleteorderstate = viewModel.deleteorderstate.collectAsState().value

    var selectedFilter by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        viewModel.getAllOrders()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        Icons.Default.List,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { }
                            .size(50.dp)
                            .padding(start = 20.dp),
                        tint = Color(0xFFCEA819)
                    )
                },
                title = {
                    Text(
                        text = "Your Orders",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = Color(0xFFD9B514),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                },
                actions = {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { viewModel.getAllOrders() }
                            .padding(end = 16.dp)
                            .size(28.dp),
                        tint = Color(0xFFCEA819)
                    )
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { navController.navigate(routes.AllOrdersScreen) }
                            .padding(end = 16.dp),
                        tint = Color.White
                    )
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 40.dp)
                .padding(8.dp)
        ) {
            Column {
                // Filter buttons
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    FilterButton("All", selectedFilter) { selectedFilter = "All" }
                    FilterButton("Approved", selectedFilter) { selectedFilter = "Approved" }
                    FilterButton("Disapproved", selectedFilter) { selectedFilter = "Disapproved" }
                }

                when {
                    getallordersstate.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFF85C72B),
                                trackColor = Color(0xFF3F7B6C),
                                strokeCap = StrokeCap.Square,
                                strokeWidth = 7.dp,
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }

                    getallordersstate.error != null -> {
                        Text("Error: ${getallordersstate.error}", color = Color.Red)
                    }

                    getallordersstate.success != null -> {
                        val filteredList = when (selectedFilter) {
                            "Approved" -> getallordersstate.success.filter { it.isApproved == 1 }
                            "Disapproved" -> getallordersstate.success.filter { it.isApproved != 1 }
                            else -> getallordersstate.success
                        }

                        LazyColumn {
                            items(filteredList) { order ->
                                OrderDetails(
                                    order,
                                    onApproveClick = {
                                        viewModel.approveOrderStatus(order.order_id, 1)
                                        viewModel.getAllOrders()
                                    },
                                    onDeleteClick = {
                                        viewModel.deleteOrder(order.order_id)
                                        viewModel.getAllOrders()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(label: String, selectedFilter: String, onClick: () -> Unit) {
    val isSelected = label == selectedFilter
    Button(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 4.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFCEA819) else Color.DarkGray
        )
    ) {
        Text(text = label, color = Color.White)
    }
}

@Composable
fun OrderDetails(order: GetAllOrdersResponseItem,onApproveClick:()->Unit,onDeleteClick:()->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color(0xFF1C1C1E), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Text("🆔 Order ID: ${order.order_id}", color = Color.White)
            Text("👤 User: ${order.user_name}", color = Color.White)
            Text("📦 Product: ${order.orderd_product_name}", color = Color.White)
            Text("📅 Date: ${order.date_of_order_creation}", color = Color.Gray)
            Text("💰 Price: ₹${order.order_price}", color = Color.Green)
            Text("🔢 Quantity: ${order.order_quantity}", color = Color.Yellow)
            Text("💵 Total: ₹${order.total_amount}", color = Color.Cyan)
            Text("✅ Approved: ${if (order.isApproved == 1) "Yes" else "No"}", color = if (order.isApproved == 1) Color.Green else Color.Red)



          Row(
              horizontalArrangement = Arrangement.SpaceAround,
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.fillMaxWidth().padding(20.dp)
          ) {
              Spacer(modifier = Modifier.height(20.dp))


              Button(onClick = { onApproveClick()
                  Log.d("TAG", "OrderDetails: ${order.order_id}")

              }) {
                  Text("Approve Order")
              }
              Spacer(modifier = Modifier.width(20.dp))

              Button(onClick = { onDeleteClick()
                  Log.d("TAG", "OrderDetails: ${order.order_id}")

              }) {
                  Text("Delete Order")
              }
          }
        }
    }
}