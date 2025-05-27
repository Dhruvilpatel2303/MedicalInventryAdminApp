package com.example.medicalinventryadminapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalinventryadminapp.R
import com.example.medicalinventryadminapp.navigation.routes
import com.example.medicalinventryadminapp.network.response.SellHistoryResponse.getUserSellHistory.GetUserSellsHistoryItem
import com.example.medicalinventryadminapp.network.response.UsersResponse.getSpecificUserResponse.GetSpecificUserResponse
import com.example.medicalinventryadminapp.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersDetailsScreenUI(viewModel: MainViewModel, userId: String, navController: NavController) {
    val userState by viewModel.getspecificuserstate.collectAsState()
    val sellHistoryState by viewModel.usersellhistory.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSpecificUser(userId)
        viewModel.getUserSellHistory()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 95.dp),
                    ) {
                        Text(
                            text = "User Details",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 34.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF1B2430))
            )
        },
        containerColor = Color(0xFF101C2D)
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            when {
                userState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                userState.success != null -> {
                    LazyColumn {
                        item {
                            UserCard3(
                                user = userState.success!!,
                                onDeleteClick = {
                                    viewModel.deleteUser(userId)
                                    navController.navigate(routes.AllUsersScreen)
                                },
                                onApproveClick = {
                                    viewModel.approveUser(userId, isApproved = 1)
                                    viewModel.getSpecificUser(userId)
                                },
                                onBlockClick = {
                                    viewModel.approveUser(userId, isApproved = 0)
                                    viewModel.getSpecificUser(userId)
                                }
                            )
                        }

                        // Sell History Section
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Sell History",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp)
                            )
                            Divider(color = Color.Gray, thickness = 1.dp)
                        }

                        when {
                            sellHistoryState.isLoading -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                            sellHistoryState.success != null -> {
                                val filteredSellHistory = sellHistoryState.success?.filter {
                                    it.user_id.toString() == userId
                                } ?: emptyList()

                                if (filteredSellHistory.isEmpty()) {
                                    item {
                                        Text(
                                            text = "No sell history available",
                                            color = Color.Gray,
                                            fontSize = 18.sp,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else {
                                    items(filteredSellHistory) { item ->
                                        SellHistoryCard(item)
                                    }
                                }
                            }
                            else -> {
                                item {
                                    Text(
                                        text = "Error loading sell history",
                                        color = Color.Red,
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Error loading user details",
                            color = Color.Red,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard3(
    user: GetSpecificUserResponse,
    onApproveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBlockClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF101C2D))
            .padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF1A1F38), Color(0xFF222A4C))
                        )
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "👤 ${user.name}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xA2D98F1E)
                )

                Spacer(modifier = Modifier.height(10.dp))

                InfoRow(label = "📧 Email", value = user.email ?: "N/A")
                InfoRow(label = "📱 Phone", value = user.phone_number ?: "N/A")
                InfoRow(label = "🏠 Address", value = user.address ?: "N/A")
                InfoRow(label = "📍 Pin Code", value = user.pin_code ?: "N/A")
                InfoRow(label = "🆔 User ID", value = user.user_id ?: "N/A")
                InfoRow(label = "🔒 Password", value = user.user_password ?: "N/A")
                InfoRow(
                    label = "✅ Approved",
                    value = if (user.isApproved == 1) "Yes" else "No",
                    valueColor = if (user.isApproved == 1) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
                InfoRow(label = "📅 Created", value = user.date_of_account_created ?: "N/A")
                InfoRow(
                    label = "🚫 Blocked",
                    value = if (user.isBlocked == 1) "Yes" else "No",
                    valueColor = if (user.isBlocked == 1) Color(0xFFFF5722) else Color(0xFF9E9E9E)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (user.isApproved == 1) onBlockClick() else onApproveClick()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (user.isApproved == 0) Color(0xFF4CAF50) else Color(0xFFEF5350)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = if (user.isApproved == 1) Icons.Default.Clear else Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (user.isApproved == 1) "Disapprove" else "Approve",
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = onDeleteClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Delete", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, valueColor: Color = Color(0xFFBBDEFB)) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            color = Color(0xFFFFC107),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            color = valueColor,
            fontSize = 18.sp
        )
    }
}

@Composable
fun SellHistoryCard(item: GetUserSellsHistoryItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A1F38))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Product Name: ",
                    color = Color(0xFFFFC107),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = item.product_name,
                    color = Color(0xFFBBDEFB),
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Quantity:",
                    color = Color(0xFFFFC107),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = item.quantity.toString(),
                    color = Color(0xFFBBDEFB),
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Price",
                    color = Color(0xFFFFC107),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = item.price.toString(),
                    color = Color(0xFFBBDEFB),
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Amount",
                    color = Color(0xFFFFC107),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = item.total_amount.toString(),
                    color = Color(0xFFBBDEFB),
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Remaining Stock",
                    color = Color(0xFFFFC107),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = item.remaining_stock.toString(),
                    color = Color(0xFFBBDEFB),
                    fontSize = 18.sp
                )
            }
        }
    }
}