package com.example.medicalinventryadminapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.asPaddingValues
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.medicalinventryadminapp.ui.screens.AddProductScreenUI
import com.example.medicalinventryadminapp.ui.screens.AllOrders.OrderScreenUI
import com.example.medicalinventryadminapp.ui.screens.AllProductsScreenUI
import com.example.medicalinventryadminapp.ui.screens.AllUsersScreenUI
import com.example.medicalinventryadminapp.ui.screens.UsersDetailsScreenUI
import com.example.medicalinventryadminapp.viewModel.MainViewModel

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    val bottomItems = listOf(
        BottomBarItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomBarItem("Product", Icons.Filled.Add, Icons.Outlined.Add),
        BottomBarItem("Orders", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
        BottomBarItem("Stocks", Icons.Filled.DateRange, Icons.Outlined.DateRange)
    )

    var selected by remember { mutableIntStateOf(0) }



    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)

                    .shadow(12.dp, shape = RoundedCornerShape(50))
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF470E0E).copy(alpha = 0.9f))
                    .height(70.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    bottomItems.forEachIndexed { index, item ->
                        val isSelected = selected == index
                        val iconSize = if (isSelected) 36.dp else 26.dp
                        val backgroundColor = if (isSelected) Color(0xFF4CAF50).copy(alpha = 0.2f) else Color.Transparent
                        val iconColor = if (isSelected) Color(0xFF4CAF50) else Color.White

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(backgroundColor)
                                .padding(10.dp)
                                .clickable {
                                    selected = index
                                    when (index) {
                                        0 -> navController.navigate(routes.AllUsersScreen)
                                        1 -> navController.navigate(routes.AllProductsScreen)
                                        2 -> navController.navigate(routes.AllOrdersScreen)
                                        3 -> navController.navigate(routes.AvailableStocksScreen)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.selectedicon else item.unselectedicon,
                                contentDescription = item.name,
                                tint = iconColor,
                                modifier = Modifier.size(iconSize)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = routes.AllUsersScreen,

        ) {
            composable<routes.AllUsersScreen> {
                AllUsersScreenUI(viewModel, navController)
            }
            composable<routes.UserDetailScreen> {
                val userId = it.toRoute<routes.UserDetailScreen>().userId
                UsersDetailsScreenUI(viewModel, userId, navController)
            }
            composable<routes.AddProductScreen> {
                AddProductScreenUI(viewModel, navController)
            }
            composable<routes.OrdersScreen> {
                Text("This is Orders Screen", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
            }
            composable<routes.AvailableStocksScreen> {
                Text("This is Available Stocks Screen", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
            }
            composable<routes.AllProductsScreen> {
                AllProductsScreenUI(viewModel, navController)
            }
            composable<routes.AllOrdersScreen> {
                OrderScreenUI(viewModel, navController)
            }
        }
    }
}

data class BottomBarItem(
    val name: String,
    val selectedicon: ImageVector,
    val unselectedicon: ImageVector
)