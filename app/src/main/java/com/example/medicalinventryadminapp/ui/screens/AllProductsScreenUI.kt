package com.example.medicalinventryadminapp.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalinventryadminapp.navigation.routes
import com.example.medicalinventryadminapp.network.response.ProductResponse.getAllProductsResponse.GetAllProductsResponseItem
import com.example.medicalinventryadminapp.viewModel.MainViewModel
import kotlinx.coroutines.delay




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllProductsScreenUI(viewModel: MainViewModel, navController: NavController) {
    val productState = viewModel.getallproductstate.collectAsState().value
    val context = LocalContext.current



    LaunchedEffect(Unit) {
        viewModel.getAllProducts()
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Products",
                        color = Color.White,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E1E2C),
                    titleContentColor = Color.White
                ),

                actions = {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                viewModel.getAllProducts()
                                Toast
                                    .makeText(context, "Refreshing...", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(routes.AddProductScreen)
                },
                containerColor = Color(0xB7D27447),
                contentColor = Color.Black,
                modifier = Modifier.padding(bottom = 45.dp, end = 10.dp).width(60.dp).height(60.dp)

            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Product",)
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()

                .padding(innerPadding)

        ) {
            when {
                productState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                productState.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error: ${productState.error}", color = Color.Red)
                    }
                }

                productState.success != null -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(productState.success!!) { product ->
                            AdminProductCard(product = product, onDeleteClick = {
                                viewModel.deleteProduct(product.product_id)
                                viewModel.getAllProducts()
                                Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_SHORT).show()
                            })
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdminProductCard(
    product: GetAllProductsResponseItem,
    onDeleteClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .combinedClickable(
                onClick = { isExpanded = !isExpanded },
                onLongClick = { /* Optional future admin actions */ }
            ),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF27273D),
            contentColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color(0xFF64FFDA),
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 16.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.product_name ?: "N/A",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF64FFDA)
                    )
                    Text(
                        text = "Category: ${product.product_category ?: "N/A"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB0BEC5)
                    )
                    Text("💰 Price: ₹${product.product_price ?: "N/A"}")
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Toggle Details",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Divider(color = Color.DarkGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("🆔 Product ID: ${product.product_id ?: "N/A"}")

                    Text("📦 Quantity: ${product.product_quantity ?: "N/A"}")
                    Text("🗂 DB ID: ${product.id}")

                    Spacer(modifier = Modifier.height(16.dp))

                    androidx.compose.material3.Button(
                        onClick = onDeleteClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F), // Red
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Product",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("DELETE PRODUCT")
                    }
                }
            }
        }
    }
}